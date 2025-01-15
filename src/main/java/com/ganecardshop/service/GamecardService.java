package com.ganecardshop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganecardshop.dto.GamecardDTO;
import com.ganecardshop.model.Discount;
import com.ganecardshop.model.Gamecard;
import com.ganecardshop.model.Gamecode;
import com.ganecardshop.model.Publisher;
import com.ganecardshop.repository.DiscountRepository;
import com.ganecardshop.repository.GamecardRepository;
import com.ganecardshop.repository.GamecodeRepository;
import com.ganecardshop.repository.PublisherRepository;

@Service
public class GamecardService {
    @Autowired
    private GamecardRepository gamecardRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private GamecodeRepository gamecodeRepository;

    public List<GamecardDTO> getFilteredGamecards(Integer search_publisher) {
        if (search_publisher != null) {
            // Tìm theo nhà phát hành
            return gamecardRepository.findByPublisherId(search_publisher)
                                     .stream()
                                     .map(this::convertToDTO)
                                     .collect(Collectors.toList());
        }
        // Nếu không có tìm kiếm, trả về tất cả gamecards
        return gamecardRepository.findAll()
                                 .stream()
                                 .map(this::convertToDTO)
                                 .collect(Collectors.toList());
    }
    

    public GamecardDTO getGamecardById(Integer id) {
        Gamecard gamecard = gamecardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thẻ game không tồn tại"));
        return convertToDTO(gamecard);
    }

    public List<Gamecard> getGamecardsByPublisher(Integer publisherId) {
        List<Gamecard> gamecards = gamecardRepository.findByPublisherId(publisherId);
        return gamecards;
    }

    private GamecardDTO convertToDTO(Gamecard gamecard) {
        // Kiểm tra discount status, nếu không hợp lệ thì gán là null
        Discount discount = gamecard.getDiscount();
        if (discount != null && (discount.getStatus() == 0 || discount.getStatus() == 3)) {
            discount = null;
        }
        return new GamecardDTO(
                gamecard.getId(),
                gamecard.getPublisher().getName(),
                gamecard.getPublisher().getId(),
                gamecard.getName(),
                gamecard.getPrice(),
                discount != null ? discount.getName() : null,
                discount != null ? discount.getId() : null,
                gamecard.getStock());
    }

    // Phương thức tạo mã thẻ game mới (tự động sinh mã) cả chữ lẫn số
    private String generateNewCode() {
        String code = "";
        for (int i = 0; i < 16; i++) {
            int random = (int) (Math.random() * 62); // 62 ký tự (26 chữ cái viết thường, 26 chữ cái viết hoa, 10 số)
            if (random < 26) {
                code += (char) (random + 97); // Chữ cái viết thường
            } else if (random < 52) {
                code += (char) (random + 39); // Chữ cái viết hoa
            } else {
                code += (char) (random - 52 + 48); // Số
            }
        }
        return code;
    }

    public void createGamecard(GamecardDTO gamecardDto) {
        // Lấy thông tin nhà phát hành
        Publisher publisher = publisherRepository.findById(gamecardDto.getPublisherId())
                .orElseThrow(() -> new IllegalArgumentException("Nhà phát hành không tồn tại"));

        // Lấy thông tin giảm giá (nếu có)
        Discount discount = null;
        if (gamecardDto.getDiscountId() != null) {
            discount = discountRepository.findById(gamecardDto.getDiscountId()).orElse(null); // Không ném ngoại lệ, chỉ
                                                                                              // trả về null nếu không
                                                                                              // tìm thấy
        }

        // Tạo đối tượng Gamecard
        Gamecard gamecard = new Gamecard();
        gamecard.setName(gamecardDto.getCardName());
        gamecard.setPublisher(publisher);
        gamecard.setDiscount(discount);
        gamecard.setPrice(gamecardDto.getPrice());
        gamecard.setStock(gamecardDto.getStock()); // Mặc định số lượng là 0

        // Lưu vào cơ sở dữ liệu
        gamecardRepository.save(gamecard);

        // Tạo Gamecode mới thẻ game vừa tạo
        for (int i = 0; i < gamecardDto.getStock(); i++) {
            Gamecode gamecode = new Gamecode();
            gamecode.setGamecard(gamecard);
            gamecode.setCode(generateNewCode());
            // Mark the gamecode as used (if necessary after saving)
            gamecodeRepository.save(gamecode); // Update gamecode in the database

        }
    }

    public void updateGamecard(GamecardDTO gamecardDto) {
        Gamecard gamecard = gamecardRepository.findById(gamecardDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Thẻ game không tồn tại"));

        Publisher publisher = publisherRepository.findById(gamecardDto.getPublisherId())
                .orElseThrow(() -> new IllegalArgumentException("Nhà phát hành không tồn tại"));

        Discount discount = null;
        if (gamecardDto.getDiscountId() != null) {
            discount = discountRepository.findById(gamecardDto.getDiscountId()).orElse(null); // Không ném ngoại lệ, chỉ
                                                                                              // trả về null nếu không
                                                                                              // tìm thấy
        }

        gamecard.setName(gamecardDto.getCardName());
        gamecard.setPublisher(publisher);
        gamecard.setDiscount(discount);
        gamecard.setPrice(gamecardDto.getPrice());
        gamecardRepository.save(gamecard);
    }

    public void deleteGameCard(Integer id) {
        Gamecard gameCard = gamecardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GameCard not found"));
        gamecardRepository.delete(gameCard);

        // Xóa tất cả các mã thẻ game liên quan
        List<Gamecode> gamecodes = gamecodeRepository.findByGamecardId(id);
        gamecodeRepository.deleteAll(gamecodes);
    }

}
