package com.example.pranav.helloandroid;

public class ProjectLocationInformation {
    private String Street;
    private String City;
    private String State;
    private String Pin;

    public ProjectLocationInformation(String street, String city, String state, String pin) {
        Street = street;
        City = city;
        State = state;
        Pin = pin;
    }

    @Override
    public String toString(){
        return Street + ", " + City + ", " + State + ", " + Pin;
    }

    public String getStreet() {
        return Street;
    }

    public String getCity() {
        return City;
    }

    public String getState() {
        return State;
    }

    public String getPin() {
        return Pin;
    }
}
