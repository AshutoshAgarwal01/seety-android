package com.example.pranav.helloandroid;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WorkSummaryActivity extends Activity {
    private android.support.design.widget.BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worksummary);

        initialize();
        }

    private void initialize() {
        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.review);

        initializeBottomNav();

        showSummary();
    }

    private void showSummary() {
        LinearLayout issueNavBody = findViewById(R.id.issueNavBody);

        ScrollView scv = new ScrollView(this);
        scv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        scv.setFillViewport(true);
        issueNavBody.addView(scv);

        //Need this because scroll views can have only one direct child.
        LinearLayout innerLinear = new LinearLayout(this);
        innerLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        innerLinear.setOrientation(LinearLayout.VERTICAL);
        innerLinear.setHorizontalGravity(Gravity.LEFT);
        innerLinear.setVerticalGravity(Gravity.CENTER_VERTICAL);
        scv.addView(innerLinear);

        //Show category selection first.
        OptionNode tempNode = GlobalVariable.get_topCategoryNode();
        while (tempNode != null && tempNode.get_childrenNodes() != null && tempNode.get_childrenNodes().size() >0 ){
            CardView cv = getReviewCardView(tempNode.get_description(), getResponseForNode(tempNode));
            innerLinear.addView(cv);
            ArrayList<OptionNode> selectedChildren = tempNode.getSelectedChildren();

            if(selectedChildren == null || selectedChildren.size() == 0){
                break;
            }

            //TODO: Test this when we have a MULTI node with children
            tempNode = (OptionNode) selectedChildren.toArray()[0];
        }

        //Show More project info
        ArrayList<String> projInfoResp = new ArrayList<>();
        projInfoResp.add(CustomerResponse.MoreProjectInfo);
        CardView projInfoCard = getReviewCardView(getResources().getString(R.string.moreProjectInfo), projInfoResp);
        innerLinear.addView(projInfoCard);

        //Show Project Time
        ArrayList<String> projTimeResp = new ArrayList<>();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        String timeStr = timeFormatter.format(CustomerResponse.ProjectTime.getTime());

        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, MMM d, yyyy");
        String dateStr = dateFormatter.format(CustomerResponse.ProjectDate.getTime());

        projTimeResp.add(String.format("On %s at %s", dateStr, timeStr));
        CardView projTimeCard = getReviewCardView(getResources().getString(R.string.projectTime), projTimeResp);
        innerLinear.addView(projTimeCard);

        //Show Project Address
        ArrayList<String> projAddressResp = new ArrayList<>();
        projAddressResp.add(CustomerResponse.Address.toString());

        CardView projAddressCard = getReviewCardView(getResources().getString(R.string.projectAddress), projAddressResp);
        innerLinear.addView(projAddressCard);


        //Show Customer Info
        ArrayList<String> customerInfoResp = new ArrayList<>();
        customerInfoResp.add(String.format("%s %s", CustomerResponse.CustomerPersonalInfo.getFirstName(), CustomerResponse.CustomerPersonalInfo.getLastName()));
        customerInfoResp.add(String.format("e-mail: %s", CustomerResponse.CustomerPersonalInfo.getEmail()));
        customerInfoResp.add(String.format("Phone: %s", CustomerResponse.CustomerPersonalInfo.getPhoneNumber()));

        CardView customerInfoCard = getReviewCardView(getResources().getString(R.string.customerInfo), customerInfoResp);
        innerLinear.addView(customerInfoCard);
    }

    private ArrayList<String> getResponseForNode(OptionNode curNode) {
        ArrayList<String> responses = new ArrayList<>();
        ArrayList<OptionNode> selectedChildren = curNode.getSelectedChildren();
        if(selectedChildren != null && selectedChildren.size() > 0){
            for(int i = 0; i < selectedChildren.size(); i++){
                OptionNode selected = selectedChildren.get(i);
                responses.add(selected.get_name());
            }
        }
        return responses;
    }

    private void initializeBottomNav(){
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_back:
                        prevClick(bottomNav);
                        return true;
                    case R.id.nav_home:
                        return true;
                    case  R.id.nav_next:
                        nextClick(bottomNav);
                        return true;
                }
                return false;
            }
        });
    }

    public void prevClick(View v){
        this.finish();
    }
    public void nextClick(View v){

    }

    private CardView getReviewCardView(String question, ArrayList<String> responses) {
        CardView card = getEmptyCard();

        //Now create linear layout inside this card.
        LinearLayout innerLinear = new LinearLayout(this);
        innerLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        innerLinear.setOrientation(LinearLayout.VERTICAL);
        innerLinear.setHorizontalGravity(Gravity.LEFT);
        innerLinear.setVerticalGravity(Gravity.CENTER_VERTICAL);

        int innerLayoutMargin = Utilities.getMeasureinDp(this,10);
        Utilities.setMargins(innerLinear, innerLayoutMargin, innerLayoutMargin, innerLayoutMargin, innerLayoutMargin);

        //Create image view that will go inside inner linear view.
        TextView qView = new TextView(this);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.gravity = Gravity.LEFT | Gravity.CENTER;
        qView.setLayoutParams(tvParams);
        qView.setTypeface(Typeface.DEFAULT_BOLD);
        Utilities.setMargins(qView, Utilities.getMeasureinDp(this,25), 0, 0, 0);
        //qView.setText(curNode.get_description());
        qView.setText(question);
        innerLinear.addView(qView);

        //Create a view to show line
        View v = new View(this);
        LinearLayout.LayoutParams vParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utilities.getMeasureinDp(this,1));
        v.setLayoutParams(vParams);
        v.setBackgroundColor(getResources().getColor(R.color.lightGray));
        int tendp = Utilities.getMeasureinDp(this,10);
        Utilities.setMargins(v, tendp, tendp, tendp, tendp);

        if(responses != null && responses.size() > 0){
            for(int i = 0; i < responses.size(); i++){
                String response = responses.get(i);
                //Create text view that will go inside inner linear view.
                TextView tv = new TextView(this);
                tvParams.gravity = Gravity.LEFT | Gravity.CENTER;
                tv.setLayoutParams(tvParams);
                Utilities.setMargins(tv, Utilities.getMeasureinDp(this,25), 0, 0, 0);
                tv.setText(response);
                innerLinear.addView(tv);
            }
        }

        card.addView(innerLinear);

        return card;
    }

    private CardView getEmptyCard() {
        CardView card = new CardView(this);
        int cvHeight = Utilities.getMeasureinDp(this,80);
        card.setLayoutParams(new LinearLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT));
        card.setRadius(Utilities.getMeasureinDp(this,0));
        card.setCardElevation(Utilities.getMeasureinDp(this,0));
        card.setMinimumHeight(cvHeight);

        //Set card margin
        int cardMargin = Utilities.getMeasureinDp(this,8);
        Utilities.setMargins(card, cardMargin, 0, 0, cardMargin);

        return card;
    }
}
