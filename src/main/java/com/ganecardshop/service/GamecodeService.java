package com.ganecardshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ganecardshop.dto.GamecodeDTO;
import com.ganecardshop.model.Gamecard;
import com.ganecardshop.model.Gamecode;
import com.ganecardshop.repository.GamecardRepository;
import com.ganecardshop.repository.GamecodeRepository;

@Service
public class GamecodeService {

    @Autowired
    private GamecodeRepository gamecodeRepository;

    @Autowired
    private GamecardRepository gamecardRepository;

    public Page<GamecodeDTO> findFilteredGamecodes(String search_gamecode, Integer search_publisher,
            Integer search_gamecard, Integer search_isUsed, Pageable pageable) {
        return gamecodeRepository.findFilteredGamecodes(search_gamecode, search_publisher, search_gamecard,
                search_isUsed, pageable).map(this::convertToDTO);
    }

    public GamecodeDTO getGamecodeById(Integer id) {
        Gamecode gamecard = gamecodeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thẻ game không tồn tại"));
        return convertToDTO(gamecard);
    }

    private GamecodeDTO convertToDTO(Gamecode gamecode) {
        GamecodeDTO dto = new GamecodeDTO();
        dto.setId(gamecode.getId());
        dto.setOrderId(gamecode.getOrder() != null ? gamecode.getOrder().getId() : null);
        dto.setCode(gamecode.getCode());
        dto.setGameCardName(gamecode.getGamecard() != null ? gamecode.getGamecard().getName() : null);
        dto.setPublisherName(gamecode.getGamecard() != null ? gamecode.getGamecard().getPublisher().getName() : null);
        dto.setPublisherId(gamecode.getGamecard() != null ? gamecode.getGamecard().getPublisher().getId() : null);
        dto.setGameCardId(gamecode.getGamecard() != null ? gamecode.getGamecard().getId() : null);
        dto.setUsedDate(gamecode.getUsedDate());
        dto.setUsed(gamecode.getIsUsed() != null && gamecode.getIsUsed() == 1);
        return dto;
    }

    // Kiểm tra mã thẻ đã tồn tại hay chưa
    private boolean checkDuplicateCode(String code, Integer gamecodeId) {
        // Tìm kiếm gamecode có mã thẻ trùng với mã thẻ hiện tại, ngoại trừ gamecode
        // hiện tại
        Optional<Gamecode> existingGamecode = gamecodeRepository.findByCode(code);

        if (existingGamecode.isPresent() && !existingGamecode.get().getId().equals(gamecodeId)) {
            throw new RuntimeException("Mã thẻ " + code + " đã tồn tại");
        }
        return existingGamecode.isPresent(); // Trả về true nếu mã thẻ trùng, ngược lại trả về false
    }

    public List<String> createGamecode(GamecodeDTO gamecodeDTO) {
        List<String> duplicateCodes = new ArrayList<>(); // Danh sách lưu mã thẻ trùng

        // Kiểm tra các mã thẻ trong danh sách có bị trùng không
        for (String code : gamecodeDTO.getListcode()) {
            try {
                checkDuplicateCode(code, null); // Kiểm tra mã thẻ trùng (null cho trường hợp thêm mới)
            } catch (RuntimeException e) {
                duplicateCodes.add(code); // Thêm mã thẻ trùng vào danh sách
            }
        }

        // Nếu không có mã thẻ trùng, tiếp tục tạo gamecode
        if (duplicateCodes.isEmpty()) {
            Gamecard gamecard = gamecardRepository.findById(gamecodeDTO.getGameCardId())
                    .orElseThrow(() -> new RuntimeException("Gamecard not found"));

            List<Gamecode> gamecodes = new ArrayList<>();

            // Duyệt qua danh sách mã thẻ trong GamecodeDTO và tạo mới Gamecode
            for (String code : gamecodeDTO.getListcode()) {
                Gamecode gamecode = new Gamecode();
                gamecode.setCode(code);
                gamecode.setGamecard(gamecard);
                gamecodes.add(gamecode);
            }

            // Lưu tất cả các gamecode vào cơ sở dữ liệu
            gamecodeRepository.saveAll(gamecodes);

            // cập nhật số lượng thẻ game trong kho
            gamecard.setStock(gamecard.getStock() + gamecodeDTO.getListcode().size());
            gamecardRepository.save(gamecard);

        }

        return duplicateCodes;
    }

    public void updateGamecode(GamecodeDTO gamecodeDTO) {
        Optional<Gamecode> existingGamecode = gamecodeRepository.findById(gamecodeDTO.getId());
        Gamecard gameccard = gamecardRepository.findById(gamecodeDTO.getGameCardId())
                .orElseThrow(() -> new RuntimeException("Gamecard not found"));

        if (existingGamecode.isPresent()) {
            Gamecode gamecode = existingGamecode.get();
            // Kiểm tra mã thẻ có bị trùng không
            checkDuplicateCode(gamecodeDTO.getCode(), gamecodeDTO.getId()); // Truyền gamecodeID cho bản cập nhật

            gamecode.setCode(gamecodeDTO.getCode());
            gamecode.setGamecard(gameccard);

            // Cập nhật dữ liệu vào DB
            gamecodeRepository.save(gamecode);
        }
    }

    // Xóa mã thẻ game theo ID
    public void deleteGamecode(Integer id) {
        gamecodeRepository.deleteById(id);
    }
}
