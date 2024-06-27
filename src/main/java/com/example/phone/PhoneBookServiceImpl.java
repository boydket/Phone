package com.example.phone;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookServiceImpl implements IPhoneBookService<IPhoneBook> {
    private List<IPhoneBook> iplist = new ArrayList<>();
    private final IPhoneBookRepository<IPhoneBook> ipbr;

    public PhoneBookServiceImpl(String arg1, String fileName) throws Exception {
        if ("-j".equals(arg1)) {
            ipbr = new PhoneBookJsonRepository(fileName);
        } else if ("-t".equals(arg1)) {
            ipbr = new PhoneBookTextRepository(fileName);
        } else {
            throw new Exception("Error : You need program arguments (-j/-t) (filename) !");
        }
    }

//    public void setInitRepository(String arg1, String fileName) throws Exception {
//        if ("-j".equals(arg1)) {
//            ipbr = new PhoneBookJsonRepository(fileName);
//        } else if ("-t".equals(arg1)) {
//            ipbr = new PhoneBookTextRepository(fileName);
//        } else {
//            throw new Exception("Error : You need program arguments (-j/-t) (filename) !");
//        }
//    }

    @Override
    public int size() {
        return this.iplist.size();
    }

    @Override
    public Long getMaxId() {
        Long max = 0L;
        for(IPhoneBook pb : this.iplist) {
            if(pb.getId()>max) {
                max = pb.getId();
            }
        }
        return ++max;
    }

    @Override
    public IPhoneBook findById(Long id) {
        for ( IPhoneBook obj : this.iplist ) {
            if ( id.equals(obj.getId()) ) {
                return obj;
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
        if ( findIndex >= 0 ) {
            phoneBook.setId(id);
            this.iplist.set(findIndex, phoneBook);
            return true;
        }
        return false;
    }

    private int findIndexById(Long id) {
        for ( int i = 0; i < this.iplist.size(); i++ ) {
            if ( id.equals(this.iplist.get(i).getId()) ) {
                return i;
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
