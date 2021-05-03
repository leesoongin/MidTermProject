package com.example.midtermproject.Manager;

import com.example.midtermproject.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Component
public class AccountManager{
    @Autowired
    UserInputManager userInputManager;
    public static Account accessedId = null;
    private String filePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/user.txt";

    public void updateAccount(){
        //TODO: 1. 비밀번호 변경  2. 주소변경
        System.out.println("\n----------내 정보 수정하기-----------\n");
        System.out.println("-------변경할 정보를 입력해주세요. (아무키나 누르면 종료)--------");
        System.out.println("1.비밀번호 변경  2.주소 변경");

        switch(userInputManager.scanner.next()){
            case "1":
                updatePassword();
                break;
            case "2":
                updateAddress();
                break;
            default:
                System.out.println("---------내 정보 수정하기 종료.-------");
                break;
        }
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
            userInput[i] = userInputManager.scanner.next();
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
    private void updatePassword(){
        //비번변경기능
        userInputManager.scanner.nextLine();
        System.out.println("-----비밀번호 변경------\n");
        System.out.println("아이디를 입력하세요.");
        String id = userInputManager.scanner.nextLine();
        System.out.println("비밀번호를 입력하세요.");
        String password = userInputManager.scanner.nextLine();
        String changePassword = "";
        String changeStr = "";

        if (isSuccess(id,password)){
            try {
                System.out.println("변경할 비밀번호를 입력해주세요");
                changePassword = userInputManager.scanner.nextLine();

                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] userInfo = line.split(","); //한 라인의 북 정보를 가져오기
                    if (userInfo[0].equals(id) && userInfo[1].equals(password)) {
                        userInfo[1] = changePassword;
                    }
                    changeStr += String.format("%s,%s,%s,%s,%s\n",userInfo[0],userInfo[1],userInfo[2],userInfo[3],userInfo[4]);
                }
                FileWriter writer = new FileWriter(filePath);
                writer.write(String.format("%s",changeStr));
                writer.flush();
                writer.close();
                System.out.println("-----비밀번호 변경이 완료되었습니다.-----\n");
            }catch (IOException e){ e.printStackTrace(); }
        }else{
            System.out.println("아이디 또는 패스워드가 일치하지 않습니다. 다시 시도해주세요. \n");
        }
    }
    private void updateAddress(){
        //비번변경기능
        userInputManager.scanner.nextLine();
        System.out.println("-----주소 변경------\n");
        System.out.println("아이디를 입력하세요.");
        String id = userInputManager.scanner.nextLine();
        System.out.println("비밀번호를 입력하세요.");
        String password = userInputManager.scanner.nextLine();
        String changeAddress = "";
        String changeStr = "";
        if (isSuccess(id,password)){
            try {
                System.out.println("변경할 주소를 입력해주세요");
                changeAddress = userInputManager.scanner.nextLine();

                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    String[] userInfo = line.split(","); //한 라인의 북 정보를 가져오기
                    if (userInfo[0].equals(id) && userInfo[1].equals(password)) {
                        userInfo[4] = changeAddress;
                    }
                    changeStr += String.format("%s,%s,%s,%s,%s\n",userInfo[0],userInfo[1],userInfo[2],userInfo[3],userInfo[4]);
                }
                System.out.println("--> "+changeStr);
                FileWriter writer = new FileWriter(filePath);
                writer.write(String.format("%s",changeStr));
                writer.flush();
                writer.close();
                System.out.println("-----주소 변경이 완료되었습니다.-----\n");
            }catch (IOException e){ e.printStackTrace(); }
        }else{
            System.out.println("아이디 또는 패스워드가 일치하지 않습니다. 다시 시도해주세요. \n");
        }
    }

    private Boolean isSuccess(String id,String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] userInfo = line.split(","); //한 라인의 북 정보를 가져오기
                if(userInfo[0].equals(id) && userInfo[1].equals(password)){
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}