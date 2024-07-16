package com.example.phone;

import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {
    private IPhoneBookService<IPhoneBook> phoneBookService;

    public void setPhoneBookService(IPhoneBookService<IPhoneBook> phoneBookService) throws Exception {
        this.phoneBookService = phoneBookService;
        this.phoneBookService.loadData();
    }

    public ConsoleApplication() {
    }

    public void printTitle() {
        System.out.println("============================================================================");
        System.out.println("1.연락처생성|2.목록|3.수정|4.삭제|5.이름검색|6.그룹검색|7.폰검색|8.이메일검색|9.종료");
        System.out.println("============================================================================");
    }

    public int getChoice(Scanner input) throws Exception {
        System.out.print("선택 > ");
        String a = input.nextLine();
        return Integer.parseInt(a);
    }

    public void printList() {
        this.printList(this.phoneBookService.getAllList());
    }

    private EPhoneGroup getGroupFromScanner(Scanner input, String title) {
        boolean doWhile = false;
        EPhoneGroup eGroup = EPhoneGroup.Friends;
        do {
            System.out.print(title + "연락처 그룹{Friends(1),Families(2),Schools(3),Jobs(4),Hobbies(5)} :");
            String group = input.nextLine();
            switch (group) {
                case "1":
                    eGroup = EPhoneGroup.Friends;
                    doWhile = false;
                    break;
                case "2":
                    eGroup = EPhoneGroup.valueOf("Families");
                    doWhile = false;
                    break;
                case "3":
                    eGroup = EPhoneGroup.Schools;
                    doWhile = false;
                    break;
                case "4":
                    eGroup = EPhoneGroup.Jobs;
                    doWhile = false;
                    break;
                case "5":
                    eGroup = EPhoneGroup.Hobbies;
                    doWhile = false;
                    break;
                default:
                    doWhile = true;
                    System.out.println("Friends(1),Families(2),Schools(3),Jobs(4),Hobbies(5) 1~5사이에 입력");
                    break;
            }
        } while (doWhile);
        return eGroup;
    }

    public String needInput(Scanner input, String title) {
        if ("".equals(title)) {
            return null;
        }
        while(true) {
            if ("이름".equals(title)) {
                System.out.print("연락처 ");
            }
            System.out.print(title + " : ");
            String str = input.nextLine();
            if (str.equals("")) {
                if ("전화번호".equals(title)) {
                    System.out.println(title + "는 반드시 입력해주세요.");
                } else {
                    System.out.println(title + "은 반드시 입력해주세요.");
                }
            }
            else {
                return str;
            }
        }
    }

    public void insert(Scanner input) throws Exception {
        System.out.println("--------");
        System.out.println("연락처 생성");
        System.out.println("--------");
        String name, phone, email;

        name = needInput(input, "이름");

        EPhoneGroup group = this.getGroupFromScanner(input, "");

        phone = needInput(input, "전화번호");

        email = needInput(input, "이메일");

        if (this.phoneBookService.insert(name, group, phone, email)) {
            this.phoneBookService.saveData();
            System.out.println("결과: 데이터 추가 성공되었습니다.");
        }
    }

    public void update(Scanner input) throws Exception {
        IPhoneBook result = getFindIdConsole(input, "수정할");
        if (result == null) {
            System.out.println("에러: ID 데이터 가 존재하지 않습니다.");
            return;
        }

        String name = this.needInput(input, "이름");

        EPhoneGroup group = this.getGroupFromScanner(input, "");

        String phone = this.needInput(input, "전화번호");

        String email = this.needInput(input, "이메일");

        IPhoneBook update = PhoneBook.builder()
                .id(result.getId()).name(name)
                .group(group)
                .phoneNumber(phone).email(email).build();
        if (this.phoneBookService.update(update.getId(), update)) {
            this.phoneBookService.saveData();
            System.out.println("결과: 데이터 수정 성공되었습니다.");
        }
    }

    public void delete(Scanner input) throws Exception {
        IPhoneBook result = getFindIdConsole(input, "삭제할");
        if (result == null) {
            System.out.println("에러: ID 데이터 가 존재하지 않습니다.");
            return;
        }
        if (this.phoneBookService.remove(result.getId())) {
            this.phoneBookService.saveData();
            System.out.println("결과: 데이터 삭제 성공되었습니다.");
        } else {
            System.out.println("결과: 데이터 삭제 실패되었습니다.");
        }
    }

    private IPhoneBook getFindIdConsole(Scanner input, String title) {
        long l = 0L;
        do {
            System.out.print(title + " ID 번호:");
            String id = input.nextLine();
            try {
                l = Long.parseLong(id);
            } catch (Exception ex) {
                System.out.println("ID 번호를 숫자로만 입력하세요.");
            }
        } while (l <= 0);
        IPhoneBook iPhoneBook = (IPhoneBook) this.phoneBookService.findById(l);
        return iPhoneBook;
    }

    private void printList(List<IPhoneBook> array) {
        for (IPhoneBook object : array) {
            System.out.println(object.toString());
        }
    }

    public void searchByName(Scanner input) {
        System.out.print("찾을 이름 :");
        String name = input.nextLine();

        List<IPhoneBook> list = this.phoneBookService.getListFromName(name);
        this.printList(list);
    }

    public void searchByGroup(Scanner input) {
        EPhoneGroup group = this.getGroupFromScanner(input, "찾을 ");

        List<IPhoneBook> list = this.phoneBookService.getListFromGroup(group);
        this.printList(list);
    }

    public void searchByPhone(Scanner input) {
        System.out.print("찾을 번호 :");
        String findPhone = input.nextLine();

        List<IPhoneBook> list = this.phoneBookService.getListFromPhoneNumber(findPhone);
        this.printList(list);
    }

    public void searchByEmail(Scanner input) {
        System.out.print("찾을 Email :");
        String findEmail = input.nextLine();

        List<IPhoneBook> list = this.phoneBookService.getListFromEmail(findEmail);
        this.printList(list);
    }
}
