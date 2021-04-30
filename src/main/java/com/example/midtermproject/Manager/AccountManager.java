package com.example.midtermproject.Manager;

import com.example.midtermproject.Model.Account;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Component
public class AccountManager{
    //회원가입(유저정보 저장) , 정보수정(유저정보 수정) <- 로그인 시
    public static Account accessedId = null; // 나중에 로그인 되면 여기에 사용자 id 넣어놓자
    Scanner sc = new Scanner(System.in);

    //수정은 나중에
    public void updateAccount(){
        String id,password = "";
        String willChangePassword = "";
        String changeStr = "";
        System.out.println("아이디를 입력해주세요");
        id = sc.next();
        System.out.println("비밀번호를 입력해주세요");
        password = sc.next();
        System.out.println("변경할 비밀번호를 입력해주세요");
        willChangePassword = sc.next();

        try{
            String contents = new String(Files.readAllBytes(Paths.get("/Users/isung-in/IdeaProjects/MidTermProject/Account/user.txt")), Charset.defaultCharset()); // contents 는 전체 파일 내용
            String[] content = contents.split(",");

            for(int i=0;i<content.length;i++){
                if(i%2==0 && content[i].equals(id) && content[i+1].equals(password)){
                    content[i+1] = willChangePassword;
                }
            }
            for(int i=0;i<content.length;i++){
                changeStr += content[i]+",";
            }
            System.out.println(changeStr);

            FileWriter writer = new FileWriter("/Users/isung-in/IdeaProjects/MidTermProject/Account/user.txt");
            writer.write(String.format("%s",changeStr));
            writer.flush();
            writer.close();
        }catch (IOException e){ e.printStackTrace(); }
    }

    public void createAccount(){
        String[] accountInfo = {"아이디: ","비밀번호: ","이름: ","전화번호: ","주소: "};
        String[] userInput = new String[5];

        System.out.println("----------회원정보 관리 시스템-----------");
        System.out.println("------회원 등록--------");
        System.out.println("정보를 입력해주세요.");

        Account account = new Account();

        for(int i=0;i<accountInfo.length;i++){
            System.out.println(accountInfo[i]);
            userInput[i] = sc.next();
        }
        account.setId(userInput[0]);
        account.setPassword(userInput[1]);
        account.setName(userInput[2]);
        account.setPhone(userInput[3]);
        account.setAddress(userInput[4]);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/isung-in/IdeaProjects/MidTermProject/Account/user.txt", true));
            String accountDirectory = "/Users/isung-in/IdeaProjects/MidTermProject/Account/" + account.getId() + ".txt";
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(accountDirectory, true));
            writer.write(String.format("%s,%s,%s,%s,%s", account.getId(), account.getPassword(),account.getName(),account.getPhone(),account.getAddress()));

            writer.newLine();
            writer.flush();
            writer.close();
            System.out.println("작성 완료.");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
