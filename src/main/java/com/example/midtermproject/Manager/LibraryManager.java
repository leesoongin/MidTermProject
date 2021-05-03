package com.example.midtermproject.Manager;

import com.example.midtermproject.Model.Account;
import com.example.midtermproject.Model.Book;
import com.example.midtermproject.Model.LentalInfo;
import com.example.midtermproject.Model.ReservationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
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

    private String filePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/books.txt";
    private String lentalFilePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/lentalList.txt";
    private String reservationFilePath = "/Users/isung-in/IdeaProjects/MidTermProject/Account/ReservationList.txt";
    @Autowired
    ArrayList<Book> books ;
    @Autowired
    ArrayList<LentalInfo> lentalInfos;
    @Autowired
    ArrayList<ReservationInfo> reservationInfos;

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
                isFlag = true;
                System.out.println("일련번호      제목        작가        출판사       카테고리      대여여부      예약여부      ");
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
        initLentalInfos();
        System.out.println("---------도서 대여--------");
        System.out.println("대여할 도서의 이름을 입력해주세요 :");
        userInputManager.scanner.nextLine();
        String bookNameBeSearched = userInputManager.scanner.nextLine();
        Boolean isLent = false; // 빌렸는지 빌리지 못했는지 여부
        Boolean isExist = false; // 검색어에 해당하는 도서가 존재하는지의 여부

        for(int i=0;i<books.size();i++){
            if(books.get(i).getBookName().equals(bookNameBeSearched)){
                isExist = true; // 한번이라도 들어간다면 검색어가 존재함
               if(books.get(i).getRental()){ // 대여 가능이라면
                    isLent = true;
                    books.get(i).setRental(false); //일단 상태정보 변화
                    addLentalInfo(books.get(i),accountManager.accessedId);
                    fetchBooks(); // 상태정보 변화한거 반영
                    fetchLentalInfos();
                    System.out.println("---------대여 완료---------\n");
                    break;
                }//inner if
            }//if   해당 책을 찾고
        }//for
        if(!isExist){
            System.out.println("\n-------대여 실패-------");
            System.out.println("--------해당하는 도서정보가 존재하지 않습니다.---------\n");
        }else if(!isLent){
            System.out.println("\n-------대여 실패-------");
            System.out.println("--------모든 도서가 대출중 입니다. 예약 서비스를 이용해주세요. ---------\n");
        }
    }
    //TODO: 1. 내가 빌린 책들의 리스트를 불러주고    2. 거기서 유니크한 키값으로 뭘 반납할지 봄
    // 3. 반납시 books의 대여여부를 false->true로, lentalList의 해당하는 일련번호의 데이터를 삭제
    public void returnBook() {
        initLentalInfos(); //대여정보 불러오기
        initBooks();
        userInputManager.scanner.nextLine();
        System.out.println("---------도서 대여 목록--------");
        Boolean isSuccess = false;
        if(loadLentList()){
            while(!isSuccess){
                System.out.println("\n반납할 도서의 일련번호를 입력해주세요 :");
                isSuccess = false;
                String bookNameBeSearched = userInputManager.scanner.nextLine();
                //TODO: lentalList수정
                for(int i=0;i<lentalInfos.size();i++){
                    if(lentalInfos.get(i).getSerialNumber().equals(bookNameBeSearched)){ //동일한 시리얼 넘버라면, 지우고 book수정
                        lentalInfos.remove(i); //제거
                        fetchLentalInfos(); // 제거한 내용을 fetch
                        isSuccess = true;
                    }//if
                }//for
                //TODO: books수정
                for(int i=0;i<books.size();i++){
                    if(books.get(i).getSerialNumber().equals(bookNameBeSearched)){ //같은 일련번호를 가진 시리얼 넘버라면, 대여여부를 false -> true
                        if(!books.get(i).getRental()){
                            books.get(i).setRental(true); //정보 수정
                            fetchBooks(); //수정한 내용을 fetch
                            isSuccess = true;
                        }
                    }//if
                }//for
                if(isSuccess){
                    System.out.println("\n----------반납이 완료되었습니다.---------\n");
                }else{
                    System.out.println("---------올바른 일련번호를 입력해주세요.---------\n");
                }
            }//올바른 일련번호를 입력할떄까지 안내보내줌 ㅋㅋ
        }//out if
    }
    public void reservationBook(){
        initBooks();
        initReservationInfos();
        initLentalInfos();
        Boolean isFlag = false;
        System.out.println("--------도서 예약 시스템--------\n");
        System.out.println("---------예약이 가능한 도서 목록-----------");
        if(loadReservationList()){
            System.out.println("\n예약할 도서의 일련번호를 입력해주세요.");
            userInputManager.scanner.nextLine();
            String bookNameBeSearched = userInputManager.scanner.nextLine();
            //1.book의 예약여부 변경 2. 텍스트파일 새로만듬
            for(int i=0;i<books.size();i++){ //도서정보 fetch
                if(books.get(i).getSerialNumber().equals(bookNameBeSearched)){
                    isFlag = true;
                    if(books.get(i).getReservation() && !books.get(i).getRental()){
                        books.get(i).setReservation(false);
                        fetchBooks();
                        for(int j=0;j<lentalInfos.size();j++){
                            if(lentalInfos.get(j).getSerialNumber().equals(bookNameBeSearched)){
                                addReservationInfo(books.get(i),lentalInfos.get(j));
                                fetchReservationInfos();
                            }//inner if
                        }//inner for
                        System.out.println("\n-----예약이 성공적으로 완료되었습니다.------\n");
                    }else{
                        System.out.println("-------잘못된 입력입니다 다시 시도해주세요.--------");
                    }//inner else
                }//if
            }//for
            if(!isFlag){
                System.out.println("-------잘못된 입력입니다 다시 시도해주세요.--------");
            }
        }//if
    }

    public void viewRentalList(){
        System.out.println("---------나의 도서 대여 목록--------");
        loadLentList();
    }

    public void viewReservationList(){
        initReservationInfos();
        String myName = accountManager.accessedId.getName();
        String myPhone = accountManager.accessedId.getPhone();
        System.out.println("---------나의 도서 예약 목록--------");
        for(int i=0;i<reservationInfos.size();i++){
            if(reservationInfos.get(i).getLender().equals(myName) && reservationInfos.get(i).getPhoneOfLender().equals(myPhone)){
                System.out.println("일련번호   제목        작가        이름             전화번호           대여일            반납");
                System.out.println(reservationInfos.get(i).toString()+"\n");
            }
        }

    }
    private Boolean loadReservationList(){
        initBooks(); //북 정보 초기화해오기
        Boolean isFlag = false;
        for(int i=0;i<books.size();i++){
            if(books.get(i).getReservation() && !books.get(i).getRental() ){
                isFlag = true;
                System.out.println("일련번호      제목        작가        출판사       카테고리      대여여부      예약여부      ");
                System.out.println(books.get(i).toString()+"\n");
            }
        }
        if(!isFlag){
            System.out.println("\n-------예약 가능한 도서가 존재하지 않습니다.-------\n");
        }
        return isFlag;
    }

    private Boolean loadLentList(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(lentalFilePath));
            String line = "";
            Boolean isEmpty = true;

            while ((line = reader.readLine()) != null){
                String[] lentInfo = line.split("/");
                //현재 로그인한 유저가 대여한 책의 리스트를 프린트하자
                if(lentInfo[3].equals(accountManager.accessedId.getName()) && lentInfo[4].equals(accountManager.accessedId.getPhone())){
                    //일련번호/책이름/저자/대여일/반납일
                    isEmpty = false;
                    System.out.println("일련번호      제목        작가        대여일            반납일");
                    System.out.println(String.format("%-10s%-10s%-10s%-15s%-15s\n",lentInfo[0],lentInfo[1],lentInfo[2],lentInfo[5],lentInfo[6]));
                }
            }//while
            if(isEmpty){
                System.out.println("----------대여중인 책이 존재하지 않습니다.-----------\n");
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    private void addLentalInfo(Book lentaledBook, Account currentUser){
        LentalInfo lentalInfo = new LentalInfo();
        lentalInfo.setLender(currentUser.getName());
        lentalInfo.setBookName(lentaledBook.getBookName());
        lentalInfo.setAuthor(lentaledBook.getAuthor());
        lentalInfo.setPhoneOfLender(currentUser.getPhone());
        lentalInfo.setSerialNumber(lentaledBook.getSerialNumber());
        lentalInfo.setLentalDate(getLentalDate());
        lentalInfo.setReturnDate(getReturnDate());
        lentalInfos.add(lentalInfo);
    }
    private void addReservationInfo(Book book,LentalInfo lentalInfo){
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setSerialNumber(book.getSerialNumber());
        reservationInfo.setBookName(book.getBookName());
        reservationInfo.setAuthor(book.getAuthor());
        reservationInfo.setLender(accountManager.accessedId.getName());
        reservationInfo.setPhoneOfLender(accountManager.accessedId.getPhone());
        String reservationDate = getReservationDate(lentalInfo.getReturnDate());
        String returnDate = getReservationReturnDate(reservationDate);
        reservationInfo.setReservationDate(reservationDate);
        reservationInfo.setReturnDate(returnDate);
        reservationInfos.add(reservationInfo);
    }

    public void initBooks(){
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
            BufferedReader reader = new BufferedReader(new FileReader(lentalFilePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] lentalInfo = line.split("/"); //한 라인의 북 정보를 가져오기
                lentalInfos.add(setLentalInfo(lentalInfo));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initReservationInfos() {
        reservationInfos.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(reservationFilePath));
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] reservationInfo = line.split("/"); //한 라인의 북 정보를 가져오기
                reservationInfos.add(setReservationInfo(reservationInfo));
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

    private void fetchLentalInfos(){
        //TODO: books의 내용이 수정돠었을때, 그 내용을 다시 텍스트파일에 저장할거다 !
        String changeStr = "";

        for(int i=0;i<lentalInfos.size();i++){
            changeStr += lentalInfos.get(i).toStringForFetch() + "\n";
        }
        try{
            FileWriter writer = new FileWriter(lentalFilePath);
            writer.write(String.format("%s",changeStr));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private void fetchReservationInfos(){
        //TODO: books의 내용이 수정돠었을때, 그 내용을 다시 텍스트파일에 저장할거다 !
        String changeStr = "";

        for(int i=0;i<reservationInfos.size();i++){
            changeStr += reservationInfos.get(i).toStringForFetch() + "\n";
        }
        try{
            FileWriter writer = new FileWriter(reservationFilePath);
            writer.write(String.format("%s",changeStr));
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
//    7/소래흠/숭인1/dltnddls/01028277137/2021-05-11/2021-05-18
    private ReservationInfo setReservationInfo(String[] reservationData){
        ReservationInfo reservationInfo = new ReservationInfo();
        reservationInfo.setSerialNumber(reservationData[0]);
        reservationInfo.setBookName(reservationData[1]);
        reservationInfo.setAuthor(reservationData[2]);
        reservationInfo.setLender(reservationData[3]);
        reservationInfo.setPhoneOfLender(reservationData[4]);
        reservationInfo.setReservationDate(reservationData[5]);
        reservationInfo.setReturnDate(reservationData[6]);
        return reservationInfo;
    }

    private LentalInfo setLentalInfo(String[] lentalData){
        // 일련번호/책이름/저자/대여한사람/폰번호/대여일/반납일
        LentalInfo lentalInfo = new LentalInfo();
        lentalInfo.setSerialNumber(lentalData[0]);
        lentalInfo.setBookName(lentalData[1]);
        lentalInfo.setAuthor(lentalData[2]);
        lentalInfo.setLender(lentalData[3]);
        lentalInfo.setPhoneOfLender(lentalData[4]);
        lentalInfo.setLentalDate(lentalData[5]);
        lentalInfo.setReturnDate(lentalData[6]);

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
        SimpleDateFormat todayformat = new SimpleDateFormat ( "yyyy-MM-dd");
        Date time = new Date();
        return todayformat.format(time);
    }

    private String getReturnDate(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat todayformat = new SimpleDateFormat ( "yyyy-MM-dd");

        cal.add(cal.DATE,+7);
        return todayformat.format(cal.getTime());
    }
    //1.불러와서 날짜로 바꾸고 거기서 +1
    private String getReservationDate(String returnDate){
        Calendar cal = Calendar.getInstance();
        Date strToDate = new Date();
        SimpleDateFormat reservFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{ strToDate = reservFormat.parse(returnDate); }catch (ParseException e){ e.printStackTrace(); }
        cal.setTime(strToDate);
        cal.add(cal.DATE,+1);
        return reservFormat.format(cal.getTime());
    }
    private  String getReservationReturnDate(String reservationDate){
        Calendar cal = Calendar.getInstance();
        Date strToDate = new Date();
        SimpleDateFormat reservFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{ strToDate = reservFormat.parse(reservationDate); }catch (ParseException e){ e.printStackTrace(); }
        cal.setTime(strToDate);
        cal.add(cal.DATE,+7);
        return reservFormat.format(cal.getTime());
    }
}
