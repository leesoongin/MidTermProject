package com.example.midtermproject.System;

import com.example.midtermproject.Manager.LibraryManager;
import com.example.midtermproject.Manager.UserInputManager;
import com.example.midtermproject.SystemBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class LibraryManagementSystem implements SystemBean {
    @Autowired
    UserInputManager userInputManager;
    @Autowired
    LibraryManager libraryManager;
    @Override
    public void runSystem() {

        Boolean condition = true;
        System.out.println("----------로그인 성공-----------");

        while(condition){
            System.out.println("원하는 항목을 선택해주세요.");
            System.out.println("1.도서목록 2.도서대여 3.도서반납 4.도서검색 5.테스트기능(랜탈리스트읽기)  (종료하려면 아무키나 누르세요.)");

            switch (userInputManager.scanner.next()){
                case "1":
                    libraryManager.loadBooks();
                    break;
                case "2" :
                    libraryManager.rentalBook();
                    break;
                case "3":
                    libraryManager.returnBook();
                    break;
                case "4":
                    libraryManager.searchBook();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("끝남");
                    condition = false;
                    return;
            }
        }//while
    }
}
