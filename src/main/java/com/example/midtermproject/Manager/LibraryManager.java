package com.example.midtermproject.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class LibraryManager {
    //파일에서 검색해서 해당하는 정보를 나타내기 ..
    @Autowired
    UserInputManager userInputManager;
    String filePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/books.txt";

    public void rentalBook(){
        System.out.println("렌탈북");
    }
    public void returnBook(){
        System.out.println("returnbook");
    }
    public void searchBook(){
        System.out.println("----------도서 검색-----------");
        System.out.println("검색할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine(); // 버퍼있는거 날릴려고
        String bookNameBeSearched = userInputManager.scanner.nextLine();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] users = line.split("/");

                if (users[0].equals(bookNameBeSearched)) {
                    System.out.println("------검색 결과------");
                    System.out.println(String.format("제목: %s\n작가: %s\n출판사: %s\n카테고리: %s\n재고: %s\n대여가능여부: %s\n예약가능여부: %s\n", users[0], users[1], users[2], users[3], users[4], users[5], users[6]));
                    return;
                }
            }
            System.out.println("검색한 내용과 일치하는 도서가 존재하지 않습니다.");
            reader.close();
        }catch (IOException e){ e.printStackTrace(); }
    }
}
