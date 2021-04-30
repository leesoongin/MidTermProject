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
            System.out.println("1.도서검색 2.도서대여 3.도서반납 (종료하려면 아무키나 누르세요.)");

            switch (userInputManager.scanner.next()){
                case "1":
                    libraryManager.searchBook();
                    break;
                case "2" :
                    libraryManager.rentalBook();
                    break;
                case "3":
                    libraryManager.returnBook();
                    break;
                default:
                    System.out.println("끝남");
                    condition = false;
                    return;
            }
        }//while
    }
}
