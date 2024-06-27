package com.example.phone;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhoneBook implements IPhoneBook {
    private Long id;
    private String name;
    private EPhoneGroup group;
    private String phoneNumber;
    private String email;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name=name;
    }

    public EPhoneGroup getGroup() {
        return this.group;
    }
    public void setGroup(EPhoneGroup group) {
        this.group=group;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber=phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return String.format("%d\t\t%s\t\t%s\t\t%s\t\t%s",
                this.id, this.name, this.group, this.phoneNumber, this.email);
    }
}
