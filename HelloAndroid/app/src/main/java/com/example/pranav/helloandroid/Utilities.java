package com.example.pranav.helloandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utilities {
    public void loadCategoryTree(String categoryName){
        OptionNode rootNode = this.getMockedTree(categoryName);

        GlobalVariable.set_topCategoryNode(rootNode);
    }

    private OptionNode getMockedTree(String categoryName){
        OptionNode appliancesRoot = new OptionNode(1, "Appliances", "What type of work do you need done?", OptionType.SINGLE);
        OptionNode node1 = new OptionNode(2, "Install or Replace", "What kind of appliance do you need help with?", OptionType.SINGLE);
        OptionNode node2 = new OptionNode(3, "Painting or refinishing", "Which appliance(s) do you need painted/refinished? (Check all that apply.)", OptionType.SINGLE);
        OptionNode node3 = new OptionNode(4, "Repair or service", "Which household appliance needs repair?", OptionType.SINGLE);
        appliancesRoot.AddChild(node1);
        appliancesRoot.AddChild(node2);
        appliancesRoot.AddChild(node3);

        OptionNode node4 = new OptionNode(5, "Major electrical", "Select which appliances you would like to be installed.", OptionType.MULTIPLE);
        OptionNode node5 = new OptionNode(6, "Small appliance", "What kind of appliance needs to be installed? (Select all that apply)", null);
        OptionNode node6 = new OptionNode(7, "Major Gas", "What kind of appliance needs to be installed? (Select all that apply)", null);
        OptionNode node7 = new OptionNode(8, "Microwave", "What kind of microwave oven do you want installed?", null);
        node1.AddChild(node4);
        node1.AddChild(node5);
        node1.AddChild(node6);
        node1.AddChild(node7);

        OptionNode node8 = new OptionNode(9, "Ceiling fan", null, null);
        OptionNode node9 = new OptionNode(10, "Kitchen exhaust", null, null);
        OptionNode node10 = new OptionNode(11, "Kitchen chimney", null, null);
        OptionNode node11 = new OptionNode(12, "Lighting fixtures", null, null);
        node4.AddChild(node8);
        node4.AddChild(node9);
        node4.AddChild(node10);
        node4.AddChild(node11);

        return appliancesRoot;
    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int getMeasureinDp(Context context, int i) {
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }

    public static void addResponseNodesToLeaf(ArrayList<OptionNode> nodes){
        OptionNode tempNode = CustomerResponse.RootResponseNode;
        while (tempNode.get_childrenNodes() != null && tempNode.get_childrenNodes().size() != 0){
            //Assuming that MULTI will be the last node.
            tempNode = (OptionNode) tempNode.get_childrenNodes().toArray()[0];
        }
        for(int i = 0; i < nodes.size(); i++){
            tempNode.AddChild((OptionNode) nodes.toArray()[i]);
        }
    }

    public static Order GetFinalOrder() {
        Order o = new Order();
        o.CreatedDateTime = Calendar.getInstance().getTime();
        o.Customer = CustomerResponse.CustomerPersonalInfo;
        o.Location = CustomerResponse.Address;
        o.OrderId = "1";

        Date projTime = getCalendarObj(CustomerResponse.ProjectDate, CustomerResponse.ProjectTime);

        o.OrderLines = new ArrayList<>();
        o.OrderLines.add(new OrderLine(1, projTime, CustomerResponse.MoreProjectInfo, GetServiceInfo()));

        return o;
    }

    private static OptionNode GetServiceInfo() {

        OptionNode topNode = GlobalVariable.get_topCategoryNode();

        return  topNode.Clone(topNode);
    }

    private static Date getCalendarObj(Calendar date, Calendar time) {
        int hours = time.get(Calendar.HOUR_OF_DAY);
        int minutes = time.get(Calendar.MINUTE);
        int ampm = time.get((Calendar.AM_PM));

        int day = date.get(Calendar.DAY_OF_MONTH);
        int month = date.get(Calendar.MONTH);
        int year = date.get(Calendar.YEAR);

        Calendar c = Calendar.getInstance();
        c.set(year, month, day, hours, minutes);

        return c.getTime();
    }
}
