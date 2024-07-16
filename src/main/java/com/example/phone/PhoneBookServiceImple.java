package com.example.phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookServiceImple implements IPhoneBookService<IPhoneBook> {
    private List<IPhoneBook> iplist = new ArrayList<>();
    private final IPhoneBookRepository<IPhoneBook> ipbr;

    public PhoneBookServiceImple(String arg1, String fileName) throws Exception {
        if ("-j".equals(arg1)) {
            ipbr = new PhoneBookJsonRepository(fileName);
        } else if ("-t".equals(arg1)) {
            ipbr = new PhoneBookTextRepository(fileName);
        } else {
            throw new Exception("Error : You need program arguments (-j/-t) (filename) !");
        }
    }


    @Override
    public int size() {
        return this.iplist.size();
    }

    @Override
    public Long getMaxId() {
        Long max = 0L;
        if ( this.size() == 0 ) {
            return 1L;
        }
        else {
            return ( this.iplist.get( this.size() - 1 ).getId() )+1;
        }
    }

    @Override
    public IPhoneBook findById(Long id) {
        int low = 0;
        int high = this.size()-1;
        int mid;

            while (low <= high) {
                mid = (low + high) / 2;
                IPhoneBook obj = this.iplist.get(mid);

                if (obj.getId() == id) {
                    return obj;
                } else if (obj.getId() < id) {
                    low = mid + 1;
                } else {
                    high = mid -1;
                }
            }
            return null;

    }

    @Override
    public List<IPhoneBook> getAllList() {
        return this.iplist;
    }

    @Override
    public boolean insert(String name, EPhoneGroup group, String phoneNumber, String email) throws Exception {
        IPhoneBook phoneBook = PhoneBook.builder()
                .id(this.getMaxId())
                .name(name).group(group)
                .phoneNumber(phoneNumber).email(email).build();
        this.iplist.add(phoneBook);
        return true;
    }

    @Override
    public boolean insert(IPhoneBook phoneBook) throws Exception {
        this.iplist.add(phoneBook);
        return true;
    }

    @Override
    public boolean remove(Long id) throws Exception {
        IPhoneBook find = this.findById(id);
        if ( find != null ) {
            this.iplist.remove(find);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Long id, IPhoneBook phoneBook) throws Exception {
        int findIndex = this.findIndexById(id);
        System.out.println("수정할 idx는 " + findIndex);
        if ( findIndex >= 0 ) {
            phoneBook.setId(id);
            this.iplist.set(findIndex, phoneBook);
            return true;
        }
        return false;
    }

    public int findIndexById(Long id) {
        int low = 0;
        int high = this.size()-1;
        int mid;

        while (low <= high) {
            mid = (low + high) / 2;
            Long indexId = this.iplist.get(mid).getId();

            if (indexId == id) {
                return mid;
            } else if (indexId < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    @Override
    public List<IPhoneBook> getListFromName(String findName) {
        List<IPhoneBook> findArr = new ArrayList<>();
        for ( IPhoneBook phoneBook : this.iplist ) {
            if (phoneBook.getName().contains(findName)) {
                findArr.add(phoneBook);
            }
        }
        return findArr;
    }

    @Override
    public List<IPhoneBook> getListFromGroup(EPhoneGroup phoneGroup) {
        List<IPhoneBook> findArr = new ArrayList<>();
        for ( IPhoneBook phoneBook : this.iplist ) {
            if (phoneGroup.equals(phoneBook.getGroup())) {
                findArr.add(phoneBook);
            }
        }
        return findArr;
    }

    @Override
    public List<IPhoneBook> getListFromPhoneNumber(String findPhone) {
        List<IPhoneBook> findArr = new ArrayList<>();
        for ( IPhoneBook phoneBook : this.iplist ) {
            if (phoneBook.getPhoneNumber().contains(findPhone)) {
                findArr.add(phoneBook);
            }
        }
        return findArr;
    }

    @Override
    public List<IPhoneBook> getListFromEmail(String findEmail) {
        List<IPhoneBook> findArr = new ArrayList<>();
        for ( IPhoneBook phoneBook : this.iplist ) {
            if (phoneBook.getEmail().contains(findEmail)) {
                findArr.add(phoneBook);
            }
        }
        return findArr;
    }

    @Override
    public void loadData() throws Exception {
        this.ipbr.loadData(this.iplist);
    }

    @Override
    public void saveData() throws Exception {
        this.ipbr.saveData(this.iplist);
    }


}
