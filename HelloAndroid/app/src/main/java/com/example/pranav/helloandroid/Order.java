package com.example.pranav.helloandroid;

import java.util.Date;
import java.util.List;

public class Order {
    public String OrderId;
    public List<OrderLine> OrderLines;
    public CustomerInformation Customer;
    public ProjectLocationInformation Location;
    public Date CreatedDateTime;
}
