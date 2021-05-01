package com.example.midtermproject.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

@Component
public class LibraryManager {
    //파일에서 검색해서 해당하는 정보를 나타내기 ..
    @Autowired
    UserInputManager userInputManager;
    String filePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/books.txt";

    public void rentalBook() {
        System.out.println("---------도서 대여--------");
        System.out.println("대여할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine();
        String bookNameBeSearched = userInputManager.scanner.nextLine();
        String contents = "";
        String tmpStr = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] users = line.split("/");
                tmpStr = "";
                if (users[0].equals(bookNameBeSearched)) {
                    if (Integer.parseInt(users[4]) > 0){
                        System.out.println("-------대여가 완료되었습니다.-------");
                        //재고 -1, 재고가 0 이 될때 대여 가능 여부 변경
                        if (Integer.parseInt(users[4])-1 == 0){ //재고가 0이 될때
                            users[4] = Integer.toString(Integer.parseInt(users[4])-1);
                            users[5] = "대여불가";
                            users[6] = "예약불가";
                            tmpStr += String.format("%s/%s/%s/%s/%s/%s/%s\n",users[0],users[1],users[2],users[3],users[4],users[5],users[6]);
                            contents += tmpStr+"\n";
                        }else{
                            tmpStr += line.replaceAll(users[4],Integer.toString(Integer.parseInt(users[4])-1));
                            contents += tmpStr+"\n";
                        }
                    }else{//이미 재고가 0일때 대여 불가능
                        System.out.println("----------"+users[5]+" 모든 도서가 대여중입니다.---------\n");
                        contents += line;
                    }//inner else
                }else{
                    contents += line+"\n";
                }//else
            }//while
            FileWriter writer = new FileWriter(filePath);
            writer.write(String.format("%s",contents));
            writer.flush();
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnBook() {
        System.out.println("returnbook");
    }

    public void searchBook() {
        System.out.println("----------도서 검색-----------");
        System.out.println("검색할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine(); // 버퍼있는거 날릴려고
        String bookNameBeSearched = userInputManager.scanner.nextLine();

        try {
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
            System.out.println("------검색 결과------");
            System.out.println("검색한 내용과 일치하는 도서가 존재하지 않습니다.\n");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}