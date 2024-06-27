package com.example.phone;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PhoneBookRepositoryTests {

    @Test
    public void jsonRepositoryTest() throws Exception {
        String json = "{\"id\":123,\"name\":\"hong\",\"group\":\"Jobs\",\"phoneNumber\":\"010-1234-5678\",\"email\":\"abc@naver.com\"}";
        PhoneBookJsonRepository repository = new PhoneBookJsonRepository("test.json");

        JSONParser jsonParser = new JSONParser();
        IPhoneBook phoneBook = null;

        Object obj = jsonParser.parse(json);

        phoneBook = repository.getObjectFromJson( (JSONObject)obj );

        assertThat(phoneBook.getId()).isEqualTo(123L);
        assertThat(phoneBook.getName()).isEqualTo("hong");
        assertThat(phoneBook.getGroup()).isEqualTo(EPhoneGroup.Jobs);
        assertThat(phoneBook.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(phoneBook.getEmail()).isEqualTo("abc@naver.com");


//        JSONObject js = repository.getJsonFromObject(phoneBook);
//        //System.out.println(js);
//        assertThat((Long)js.get("id")).isEqualTo(123L);
//        assertThat((String)js.get("name")).isEqualTo("hong");
//        assertThat(js.get("group")).isEqualTo(EPhoneGroup.Jobs);
//        assertThat((String)js.get("phoneNumber")).isEqualTo("010-1234-5678");
//        assertThat((String)js.get("email")).isEqualTo("abc@naver.com");
//        assertThat(jobject.toJSONString()).isEqualTo("{\"phoneNumber\":\"1111-2222\",\"name\":\"폰북\",\"id\":88,\"email\":\"abcdefg@daum.net\",\"group\":\"Hobbies\"}");
        IPhoneBook phoneBook2 = new PhoneBook();
        phoneBook2.setId(88L);
        phoneBook2.setName("폰북");
        phoneBook2.setGroup(EPhoneGroup.Hobbies);
        phoneBook2.setPhoneNumber("1111-2222");
        phoneBook2.setEmail("abcdefg@daum.net");
        System.out.println(phoneBook2.toString());
        JSONObject jobject = repository.getJsonFromObject(phoneBook2);
        assertThat((Long)jobject.get("id")).isEqualTo(88L);
        assertThat((String)jobject.get("name")).isEqualTo("폰북");
        assertThat(EPhoneGroup.valueOf((String)jobject.get("group"))).isEqualTo(EPhoneGroup.Hobbies);
        assertThat((String)jobject.get("group")).isEqualTo("Hobbies");
        assertThat((String)jobject.get("phoneNumber")).isEqualTo("1111-2222");
        assertThat((String)jobject.get("email")).isEqualTo("abcdefg@daum.net");
        assertThat(jobject.toJSONString()).isEqualTo("{\"phoneNumber\":\"1111-2222\",\"name\":\"폰북\",\"id\":88,\"email\":\"abcdefg@daum.net\",\"group\":\"Hobbies\"}");
    }

    @Test
    public void testRepositoryTest() throws Exception {
        String test = "123,hong,Jobs,010-1234-5678,abc@naver.com";
        PhoneBookTextRepository repository = new PhoneBookTextRepository("test.txt");
        Throwable ex = assertThrows(Exception.class, () -> repository.getObjectFromText(""));


        PhoneBook pb = repository.getObjectFromText(test);
        assertThat(pb.getId()).isEqualTo(123L);
        assertThat(pb.getName()).isEqualTo("hong");
        assertThat(pb.getGroup()).isEqualTo(EPhoneGroup.Jobs);
        assertThat(pb.getPhoneNumber()).isEqualTo("010-1234-5678");
        assertThat(pb.getEmail()).isEqualTo("abc@naver.com");

        String str = repository.getTextFromObject(pb);
        String[] items2 = str.split(",");
        assertThat(Long.valueOf(items2[0])).isEqualTo(123L);
        assertThat(items2[1]).isEqualTo("hong");
        assertThat(EPhoneGroup.valueOf(items2[2])).isEqualTo(EPhoneGroup.Jobs);
        assertThat(items2[3]).isEqualTo("010-1234-5678");
        assertThat(items2[4]).isEqualTo("abc@naver.com");
        assertThat(str).isEqualTo("123,hong,Jobs,010-1234-5678,abc@naver.com");
    }
}
