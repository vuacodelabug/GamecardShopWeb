package com.ganecardshop.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GamecodeDTO {
    private Integer id;
    private Integer orderId;
    private String code;
    private String publisherName;
    private String gameCardName;
    private String usedDate; // When the game code was used
    private boolean isUsed; // True if used, false otherwise

    private Integer gameCardId;
    private Integer publisherId;
    private List<String> listcode;

    // Default Constructor
    public GamecodeDTO() {
    }

    // Parameterized Constructor
    public GamecodeDTO(Integer id, Integer orderId, String code, String gameCardName, String publisherName,
            LocalDateTime usedDateLCDT, boolean isUsed) {
        this.id = id;
        this.orderId = orderId == null ? 0 : orderId;
        this.code = code;
        this.gameCardName = gameCardName == null ? "Unknown" : gameCardName;
        this.publisherName = publisherName == null ? "Unknown" : publisherName;
        this.usedDate = (usedDateLCDT != null) ? usedDateLCDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "Not Used";
        this.isUsed = isUsed;
    }

    public GamecodeDTO(Integer gameCardId, List<String> listcode) {
        this.gameCardId = gameCardId;
        this.listcode = listcode;
    }

    // Getter và Setter
    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public List<String> getListcode() {
        return listcode;
    }

    public void setListcode(List<String> listcode) {
        this.listcode = listcode;
    }

    public Integer getGameCardId() {
        return gameCardId;
    }

    public void setGameCardId(Integer gameCardId) {
        this.gameCardId = gameCardId;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId == null ? 0 : orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGameCardName() {
        return gameCardName;
    }

    public void setGameCardName(String gameCardName) {
        this.gameCardName = gameCardName == null ? "Unknown" : gameCardName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName == null ? "Unknown" : publisherName;
    }

    public String getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDateTime usedDateLCDT) {
        this.usedDate = (usedDateLCDT != null) ? usedDateLCDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "Not Used";
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
