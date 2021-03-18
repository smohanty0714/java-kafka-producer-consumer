package com.hackerrank.kafka.model;

public class Card {
    private String cardNumber;
    private String cardExpirationDate;
    private String cardSecurityCode;

    public Card() {
    }

    public Card(String cardNumber, String cardExpirationDate, String cardSecurityCode) {
        this.cardNumber = cardNumber;
        this.cardExpirationDate = cardExpirationDate;
        this.cardSecurityCode = cardSecurityCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardExpirationDate='" + cardExpirationDate + '\'' +
                ", cardSecurityCode='" + cardSecurityCode + '\'' +
                '}';
    }
    
    
}
