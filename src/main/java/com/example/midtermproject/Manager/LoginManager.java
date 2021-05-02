package com.example.midtermproject.Manager;

import com.example.midtermproject.Model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class LoginManager{
    @Autowired
    UserInputManager userInputManager;
    @Autowired
    AccountManager accountManager;

    public Boolean login(){
        String id, password = "";

        System.out.println("----------로그인----------");
        System.out.println("아이디를 입력하세요 : ");
        id = userInputManager.scanner.next();
        System.out.println("비밀번호를 입력하세요 : ");
        password = userInputManager.scanner.next();

        try{
            BufferedReader reader = new BufferedReader(new FileReader("/Users/isung-in/IdeaProjects/MidTermProject/Account/user.txt"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] users = line.split(",");

                for(int i=0;i<users.length;i++){
                    if (i%2==0 && users[i].equals(id) && users[i+1].equals(password)){
                        setAccessedUserId(users[0],users[1],users[2],users[3],users[4]);
                        return true;
                    }
                }
            }
            reader.close();
        }catch (IOException e){ e.printStackTrace(); }
        return false;
    }

    private void setAccessedUserId(String id,String password,String name,String phone,String address){
        Account account = new Account();
        account.setId(id);
        account.setPassword(password);
        account.setName(name);
        account.setPhone(phone);
        account.setAddress(address);
        accountManager.accessedId = account;
    }
}
