package com.example.pranav.helloandroid;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utilities {

    public void loadCategoryTree(String categoryName){
        OptionNode rootNode = null; // = this.getMockedTree(categoryName);

        try{
            for (OptionNode node: GlobalVariable.get_allCategories()){
                if(node.get_name().toLowerCase().equals(categoryName.toLowerCase())){
                    rootNode = node;
                    break;
                }
            }
        }
        catch (Exception ex){
            int x = 1;
        }


        if(rootNode == null){
            return;
        }

        GlobalVariable.set_topCategoryNode(rootNode);
    }

    /*public static void getHierarchy(Context context, String nodeId, final IVolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://seetywebapi.azurewebsites.net/api/Hierarchy/"+nodeId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Handle your response
                callback.onSuccessResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Handle your error
                int x = 1;

            }
        });

        requestQueue.add(jsonObjectRequest);
    }*/

    public static void getHierarchies(final Context context, final IVolleyCallback callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://seetywebapi.azurewebsites.net/api/Hierarchy";

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        callback.onSuccessResponse(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
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

    public static LinearLayout CreateScrollView(Context context, LinearLayout parentLiner){
        ScrollView scv = new ScrollView(context);
        scv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        scv.setFillViewport(true);
        parentLiner.addView(scv);

        //Need this because scroll views can have only one direct child.
        LinearLayout innerLinear = new LinearLayout(context);
        innerLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        innerLinear.setOrientation(LinearLayout.VERTICAL);
        innerLinear.setHorizontalGravity(Gravity.LEFT);
        innerLinear.setVerticalGravity(Gravity.TOP);
        scv.addView(innerLinear);

        return innerLinear;
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
