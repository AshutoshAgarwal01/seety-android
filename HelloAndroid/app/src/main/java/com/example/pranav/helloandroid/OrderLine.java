package com.example.pranav.helloandroid;

import java.util.Date;

public class OrderLine {
    public int OrderLineId;
    public Date ProjectTime;
    public String MoreInformation;
    public OptionNode ServiceInfo;

    public OrderLine(int orderLineId, Date projectTime, String moreInformation, OptionNode serviceInfo){
        OrderLineId = orderLineId;
        ProjectTime = projectTime;
        MoreInformation = moreInformation;
        ServiceInfo = serviceInfo;
    }
}
