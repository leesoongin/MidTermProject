package com.example.midtermproject.Model;

import org.springframework.stereotype.Component;

@Component
public class Book {
    //도서번호 이름 저자  출판사 카테고리 재고 대여가능여부
    private String serialNumber;
    private String bookName;
    private String author;
    private String publisher;
    private String kategory;
    private Boolean isRental;
    private Boolean isReservation;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumer) {
        this.serialNumber = serialNumer;
    }

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

    public Boolean getRental() {
        return isRental;
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


    public void setRental(Boolean retal) {
        isRental = retal;
    }

    public void setReservation(Boolean reservation) {
        isReservation = reservation;
    }

    public String toString(){
        return String.format("%-10s%-10s%-10s%-10s%-10s%-10b%-10b\n",this.serialNumber,this.bookName,this.author,this.publisher,this.kategory,this.isRental,this.isReservation);
    }

    public String toStringForFetch(){
        return String.format("%s/%s/%s/%s/%s/%b/%b",this.serialNumber,this.bookName,this.author,this.publisher,this.kategory,this.isRental,this.isReservation);
    }
}
