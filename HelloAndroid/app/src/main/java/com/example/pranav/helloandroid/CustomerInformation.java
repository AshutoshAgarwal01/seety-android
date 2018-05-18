package com.example.pranav.helloandroid;

public class CustomerInformation {
    private String FirstName;
    private String LastName;
    private String Email;
    private String PhoneNumber;

    public CustomerInformation(String firstName, String lastName, String email, String phoneNumber){
        FirstName = firstName;
        LastName = lastName;
        Email = email;
        PhoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }
}
