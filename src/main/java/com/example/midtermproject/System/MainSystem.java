package com.example.midtermproject.System;

import com.example.midtermproject.Manager.AccountManager;
import com.example.midtermproject.Manager.LoginManager;
import com.example.midtermproject.Manager.UserInputManager;
import com.example.midtermproject.System.LibraryManagementSystem;
import com.example.midtermproject.SystemBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class MainSystem implements SystemBean {

    @Autowired
    AccountManager accountManager;
    @Autowired
    LoginManager loginManager;
    @Autowired
    LibraryManagementSystem libraryManagementSystem;
    @Autowired
    UserInputManager userInputManager;
    @Override
    public void runSystem() {
        //1. 종료를 누를 때 까지 무한 반복
        //2. 각 항목들을 인풋으로 입력받기 (도서 검색,대여,반납, 사용자 정보 입)
        //3. 각 항목별 기능 수행 일단 요종도로 하고 끝내자
        Boolean condition = true;

        while (condition){
            System.out.println("------도서관 관리시스템--------");
            System.out.println("1.로그인  2.회원 등록 (종료를 위해서는 아무키나 누르셈.)\n");
            switch (userInputManager.scanner.next()){
                case "1":
//                    System.out.println("1.검색 2.대여 3.반납 4.사용자 정보 입력 5.아무거나입력하면 종료");
                    //검색 대여 반납 기능하는 곳으로 이동
                    if (loginManager.login()){
                        libraryManagementSystem.runSystem();
                    }else{
                        System.out.println("실패");
                    }
                    break;
                case "2":
                    //회원 등록하는 곳으로 이동
                    accountManager.createAccount();
                    break;
                default :
                    condition = false;
                    System.out.println("시스템 종료");
                    break;
            }//switch
        }//while
    }//runSystem
}
