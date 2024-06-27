package com.example.phone;

import org.json.simple.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PhoneBookTextRepository implements IPhoneBookRepository<IPhoneBook> {
    private String fileName;

    public PhoneBookTextRepository(String fileName) {
        this.fileName = fileName;
    }

    public PhoneBook getObjectFromText(String sLine) throws Exception {
        if (sLine == null) {
            throw new Exception("Error : Input jsonObject is null");
        }
        PhoneBook object = new PhoneBook();
        String[] items = sLine.split(",");

        object.setId(Long.valueOf(items[0]));
        object.setName(items[1]);
        object.setGroup(EPhoneGroup.valueOf(items[2]));
        object.setPhoneNumber(items[3]);
        object.setEmail(items[4]);
        return object;
    }

    public String getTextFromObject(IPhoneBook object) throws Exception {
        if (object == null) {
            throw new Exception("Error : Input object is null");
        }

        String str = String.format("%d,%s,%s,%s,%s",
                object.getId(), object.getName(), object.getGroup(), object.getPhoneNumber(), object.getEmail());
        return str;
    }

    @Override
    public boolean saveData(List<IPhoneBook> listData) throws Exception {
        FileOutputStream fileOut = new FileOutputStream(fileName);
        OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8);

        for (IPhoneBook pb : listData) {
            String str = this.getTextFromObject(pb);
            writer.write(str +"\n");
        }
        writer.close();
        return true;
    }

    @Override
    public boolean loadData(List<IPhoneBook> listData) throws Exception {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        }

        BufferedReader inFile = new BufferedReader(new FileReader(file));
        String sLine = null;
        listData.clear();

        while ((sLine = inFile.readLine()) != null) {
            IPhoneBook pb = this.getObjectFromText(sLine);
            listData.add(pb);
        }

        return true;
    }
}