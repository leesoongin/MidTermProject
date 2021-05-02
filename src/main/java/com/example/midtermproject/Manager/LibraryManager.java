package com.example.midtermproject.Manager;

import com.example.midtermproject.Model.Account;
import com.example.midtermproject.Model.Book;
import com.example.midtermproject.Model.LentalInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Component
public class LibraryManager {
    //파일에서 검색해서 해당하는 정보를 나타내기 ..
    @Autowired
    UserInputManager userInputManager;
    @Autowired
    AccountManager accountManager;

    String filePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/books.txt";
    String lentalFilePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/lentalList.txt";

    @Autowired
    ArrayList<Book> books ;
    @Autowired
    ArrayList<LentalInfo> lentalInfos;

    public void loadBooks(){
        initBooks(); // 도서정보 업데이트
        System.out.println("일련번호      제목        작가        출판사       카테고리      대여여부      예약여부      ");

        for(int i=0;i< books.size();i++){
            System.out.println(books.get(i).toString());
        }
    }

    public void searchBook(){
        initBooks(); //도서정보 업데이트
        System.out.println("----------도서 검색-----------");
        System.out.println("검색할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine(); // 버퍼있는거 날릴려고
        String bookNameBeSearched = userInputManager.scanner.nextLine();
        Boolean isFlag = false;

        for(int i=0;i<books.size();i++){
            if (books.get(i).getBookName().equals(bookNameBeSearched)){
//                System.out.println("--------검색 성공-------");
//                System.out.println("--------검색 결과-------");
                isFlag = true;
                System.out.println("제목        작가        출판사       카테고리      대여여부      예약여부      ");
                System.out.println(books.get(i).toString());
            }
        }
        if (!isFlag){
            System.out.println("\n-------검색 실패--------");
            System.out.println("-------해당하는 도서정보가 존재하지 않습니다.--------\n\n");
        }
    }

    public void rentalBook(){
        initBooks(); // 도서정보 업데이트
        System.out.println("---------도서 대여--------");
        System.out.println("대여할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine();
        String bookNameBeSearched = userInputManager.scanner.nextLine();
        Boolean isFlag = false;

        for(int i=0;i<books.size();i++){
            if(books.get(i).getBookName().equals(bookNameBeSearched)){
                if(books.get(i).getRental()){ // 대여 가능이라면
                    //TODO: 1. 책의 상태 정보를 변화 후 저장, 2. 대여 정보 갱신(추가)
                    isFlag = true;
                    books.get(i).setRental(false); //일단 상태정보 변화
                    fetchBooks(); // 상태정보 변화한거 반영
                    break;
                }//inner if
            }//if   해당 책을 찾고
        }//for
        if (!isFlag){
            System.out.println("\n-------대여 실패-------");
            System.out.println("-------해당하는 도서정보가 존재하지 않습니다.--------\n\n");
        }
    }
    public void returnBook() {
        System.out.println("returnbook");
    }

    private void addRentalList(Book lentaledBook, Account currentUser){
    }

    private void initBooks(){
        books.clear();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = reader.readLine()) != null){
                String[] booksInfo = line.split("/"); //한 라인의 북 정보를 가져오기
                books.add(setBookData(booksInfo));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void initLentalInfos() {
        lentalInfos.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] lentalInfo = line.split("/"); //한 라인의 북 정보를 가져오기
                lentalInfos.add(setLentalInfo(lentalInfo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void fetchBooks(){
        //TODO: books의 내용이 수정돠었을때, 그 내용을 다시 텍스트파일에 저장할거다 !
        String changeStr = "";

        for(int i=0;i<books.size();i++){
            changeStr += books.get(i).toStringForFetch() + "\n";
        }
        try{
            FileWriter writer = new FileWriter(filePath);
            writer.write(String.format("%s",changeStr));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private LentalInfo setLentalInfo(String[] lentalData){
        //대여한사람/대여한 사람의 폰번호/책의 일련번호/대여일/반납일
        LentalInfo lentalInfo = new LentalInfo();
        lentalInfo.setLender(lentalData[0]);
        lentalInfo.setLentaledBook(lentalData[1]);
        lentalInfo.setPhoneOfLender(lentalData[2]);
        lentalInfo.setLentalDate(lentalData[3]);
        lentalInfo.setReturnDate(lentalData[4]);

        return lentalInfo;
    }

    private Book setBookData(String[] bookData){
        Book book = new Book();
        book.setSerialNumber(bookData[0]);
        book.setBookName(bookData[1]);
        book.setAuthor(bookData[2]);
        book.setPublisher(bookData[3]);
        book.setKategory(bookData[4]);
        book.setRental(Boolean.parseBoolean(bookData[5]));
        book.setReservation(Boolean.parseBoolean(bookData[6]));
        return book;
    }

    private String getLentalDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat todayformat = new SimpleDateFormat ( "yyyy년 MM월 dd일");
        Date time = new Date();
        return todayformat.format(time);
    }

    private String getReturnDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat todayformat = new SimpleDateFormat ( "yyyy-MM-dd일");

        cal.add(cal.DATE,+7);
        return todayformat.format(cal.getTime());
    }
}
