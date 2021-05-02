package com.example.midtermproject.Model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LentalInfo {
    private String lender; // 대여한 사람
    private String phoneOfLender; //대여한 사람의 폰번호
    private String lentaledBook; //책의 정보
    private String lentalDate;  //대여일
    private String returnDate;  //반납일

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

    public String getLentaledBook() {
        return lentaledBook;
    }

    public void setLentaledBook(String lentaledBook) {
        this.lentaledBook = lentaledBook;
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
        return String.format("%s,%s,%s,%s,%s",this.lentaledBook,
                this.lender,this.phoneOfLender,this.lentalDate,this.returnDate);
    }
}
