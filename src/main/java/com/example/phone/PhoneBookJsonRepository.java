package com.example.phone;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.List;

public class PhoneBookJsonRepository implements IPhoneBookRepository<IPhoneBook> {
    private String fileName;
    public PhoneBookJsonRepository(String fileName) {
        this.fileName = fileName;
    }

    public IPhoneBook getObjectFromJson(JSONObject jsonObject) throws Exception {
        if ( jsonObject == null ) {
            throw new Exception("Error : Input jsonObject is null");
        }
        IPhoneBook object = new PhoneBook();
        object.setId( (Long)jsonObject.get("id") );
        object.setName( (String)jsonObject.get("name") );
        object.setGroup( EPhoneGroup.valueOf((String)jsonObject.get("group")) );
        object.setPhoneNumber( (String)jsonObject.get("phoneNumber") );
        object.setEmail( (String)jsonObject.get("email") );
        return object;
    }

    public JSONObject getJsonFromObject(IPhoneBook object) throws Exception {
        if( object == null ) {
            throw new Exception("Error : Input object is null");
        }

        JSONObject jobj = new JSONObject();
        jobj.put("id", object.getId());
        jobj.put("name", object.getName());
        jobj.put("group", object.getGroup().toString());
        jobj.put("phoneNumber", object.getPhoneNumber());
        jobj.put("email", object.getEmail());
        return jobj;
    }

    @Override
    public boolean saveData(List<IPhoneBook> listData) throws Exception {
        if( listData == null ) {
            return false;
        }

        JSONArray jsonArray = new JSONArray();

        for ( IPhoneBook ipb : listData) {
            JSONObject jobj = getJsonFromObject((PhoneBook)ipb);
            if(jobj!=null) {
                jsonArray.add(jobj);
            }
        }
        JSONObject jroot = new JSONObject();
        jroot.put("phonebooks", jsonArray);

        FileWriter fileWriter = new FileWriter(this.fileName, Charset.defaultCharset());
        fileWriter.write(jroot.toString());
        fileWriter.flush();
        fileWriter.close();

        return true;
    }

    @Override
    public boolean loadData(List<IPhoneBook> listData) throws Exception {
        if ( listData == null ) {
            return false;
        }
        JSONParser parser = new JSONParser();
        File file = new File(this.fileName);
        if ( !file.exists() ) {
            return false;
        }
        FileReader reader = new FileReader(file, Charset.defaultCharset());
        JSONObject jsonObject = (JSONObject)parser.parse(reader);

        reader.close();

        JSONArray jsonArray = (JSONArray) jsonObject.get("phonebooks");
        listData.clear();

        for ( Object object : jsonArray ) {
            IPhoneBook ipb = getObjectFromJson((JSONObject)object);
            if(ipb!=null) {
                listData.add(ipb);
            }
        }
        return true;
    }
}
