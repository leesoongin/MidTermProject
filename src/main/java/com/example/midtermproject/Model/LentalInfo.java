package com.example.midtermproject.Model;

import org.springframework.stereotype.Component;

@Component
public class LentalInfo {
    private String serialNumber; //책의 일련번호
    private String bookName; //책의 이름
    private String author; //책의 저자
    private String lender; // 대여한 사람
    private String phoneOfLender; //대여한 사람의 폰번호
    private String lentalDate;  //대여일
    private String returnDate;  //반납일

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhoneOfLender() {
        return phoneOfLender;
    }
    public void setPhoneOfLender(String phoneOfLender) {
        this.phoneOfLender = phoneOfLender;
    }
    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLentalDate() {
        return lentalDate;
    }

    public void setLentalDate(String lentalDate) {
        this.lentalDate = lentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String toString(){
        //  -->  대여된 책의 일련번호/빌린사람 이름/폰번호/대여일/반납일
        return String.format("%s/%s/%s%s/%s/%s/%s",this.serialNumber,this.bookName,this.author,
                this.lender,this.phoneOfLender,this.lentalDate,this.returnDate);
    }
    public String toStringForFetch(){
        //  -->  대여된 책의 일련번호/빌린사람 이름/폰번호/대여일/반납일
        return String.format("%s/%s/%s/%s/%s/%s/%s",this.serialNumber,this.bookName,this.author,
                this.lender,this.phoneOfLender,this.lentalDate,this.returnDate);
    }
}
