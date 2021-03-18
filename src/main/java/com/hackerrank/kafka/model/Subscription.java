package com.hackerrank.kafka.model;

public class Subscription {
    private Long subscriberId;
    private String productName;
    private Double unitPrice;
    private Integer quantity;
    private String currency;
    private String frequency;
    private Card card;

    public Subscription() {
    }

    public Subscription(Long subscriberId, String productName, Double unitPrice, Integer quantity, String currency, String frequency, Card card) {
        this.subscriberId = subscriberId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.currency = currency;
        this.frequency = frequency;
        this.card = card;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "subscriberId=" + subscriberId +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", currency='" + currency + '\'' +
                ", frequency='" + frequency + '\'' +
                ", card=" + card +
                '}';
    }
}
