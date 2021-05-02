package com.example.midtermproject;

import com.example.midtermproject.Manager.AccountManager;
import com.example.midtermproject.Manager.LibraryManager;
import com.example.midtermproject.Manager.LoginManager;
import com.example.midtermproject.Manager.UserInputManager;
import com.example.midtermproject.Model.Book;
import com.example.midtermproject.Model.LentalInfo;
import com.example.midtermproject.System.MainSystem;
import com.example.midtermproject.System.LibraryManagementSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Scanner;

@Configuration
public class JavaConfig {
    @Bean
    public ArrayList<Book> books(){
        return new ArrayList<Book>();
    }

    @Bean
    public ArrayList<LentalInfo> lentalInfos(){
        return new ArrayList<LentalInfo>();
    }
    @Bean
    public SystemBean systemBean(){
        SystemBean systemBean = new MainSystem();

        return systemBean;
    }
    @Bean
    public LoginManager loginManager(){
        LoginManager loginManager = new LoginManager();

        return loginManager;
    }
    @Bean
    public AccountManager accountManager(){
        return new AccountManager();
    }

    @Bean
    public LibraryManagementSystem libraryManagementSystem(){
        return new LibraryManagementSystem();
    }
    @Bean
    public LibraryManager libraryManager(){
        return new LibraryManager();
    }
    @Bean
    public UserInputManager userInputManager(){
        return new UserInputManager();
    }
}
