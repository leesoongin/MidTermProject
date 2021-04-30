package com.example.midtermproject.Model;

public class Book {
    //도서번호 이름 저자  출판사 카테고리 재고 대여가능여부
    private String bookName;
    private String author;
    private String publisher;
    private String kategory;
    private int stoke;
    private Boolean isRetal;
    private Boolean isReservation;

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getKategory() {
        return kategory;
    }

    public int getStoke() {
        return stoke;
    }

    public Boolean getRetal() {
        return isRetal;
    }

    public Boolean getReservation() {
        return isReservation;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setKategory(String kategory) {
        this.kategory = kategory;
    }

    public void setStoke(int stoke) {
        this.stoke = stoke;
    }

    public void setRetal(Boolean retal) {
        isRetal = retal;
    }

    public void setReservation(Boolean reservation) {
        isReservation = reservation;
    }
}
