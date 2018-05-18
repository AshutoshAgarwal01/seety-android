package com.example.pranav.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;

public class IssueNavigation extends Activity {
    private OptionNode currentNode;
    private android.support.design.widget.BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentNode =  GlobalVariable.get_activityOptionNode(); //(OptionNode) getIntent().getSerializableExtra("node");
        setContentView(R.layout.issuenavigation);

        initialize();
    }

    private void initialize() {
        initializeBottomNav();

        TextView tv = findViewById(R.id.topCategories);
        tv.setText(currentNode.get_description());
        renderOptions(currentNode.get_optionType());
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

        //unselect all nodes
        ArrayList<OptionNode> children = currentNode.get_childrenNodes();
        for(int i = 0; i < children.size(); i++){
            OptionNode curNode = children.get(i);
            curNode.unselect();
        }

        this.finish();
    }

    public void nextClick(View v){
        ArrayList<OptionNode> selectedNodes = getSelectedNodes();
        if(selectedNodes == null
                || selectedNodes.size() == 0){
            //Logic to show error
            //More graceful - disable next button until any option is selected.
            Toast.makeText(this, "Please make at-least one selection", Toast.LENGTH_SHORT).show();
            return;
        }

        //Sending first selected item in case of SINGLE option type is fine
        //When option type is multiple, then we send any one selected item
        //assuming that all items in this group will have same child.
        OptionNode node = (OptionNode) selectedNodes.toArray()[0];

        if(isLeaf(node)){
            //when, Little more, address, name-email-phone
            Intent i = new Intent(this, ProjectTime.class);
            startActivity(i);
        }
        else {
            GlobalVariable.set_activityOptionNode(node);
            //Start new issue navigation activity
            Intent i = new Intent(this, IssueNavigation.class);
            i.putExtra("node", node);
            startActivity(i);
        }
    }

    private ArrayList<OptionNode> getSelectedNodes() {
        ArrayList<OptionNode> children = currentNode.get_childrenNodes();
        ArrayList<OptionNode> selectedNodes = new ArrayList<>();
        for(int i = 0; i < children.size(); i++){
            OptionNode curNode = children.get(i);
            CardView cv = findViewById(curNode.get_nodeId());

            curNode.unselect();

            if(cv.getCardBackgroundColor().getDefaultColor() != -1){
                selectedNodes.add(curNode);
                curNode.select();
            }
        }
        return selectedNodes;
    }

    private boolean isLeaf(OptionNode node) {
        ArrayList<OptionNode> children = (ArrayList<OptionNode>)node.get_childrenNodes();
        return children == null || children.size() == 0;
    }

    private void renderOptions(OptionType optionType){
        if(optionType == OptionType.SINGLE){
            renderCards(optionType);
        }
        else if (optionType == OptionType.MULTIPLE){
            renderCards(optionType);
        }
    }

    private void renderCards(OptionType optionType){
        ArrayList<OptionNode> children = currentNode.get_childrenNodes();

        LinearLayout mainLinear = new LinearLayout(this);
        mainLinear.setId(R.id.mainLinearLayoutId);
        mainLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mainLinear.setOrientation(LinearLayout.VERTICAL);

        for(int i = 0; i < children.size(); i++){
            final OptionNode curNode = children.get(i);

            //Create cardview for this option and attach that to main linear layout.
            CardView cv = getCardView(curNode);
            setCardClickEvent(cv, curNode, optionType);

            mainLinear.addView(cv);
        }
        LinearLayout issueNavBody = findViewById(R.id.issueNavBody);
        issueNavBody.addView(mainLinear);
    }

    private void setCardClickEvent(final CardView cv, final OptionNode node, OptionType optionType) {
        if(optionType == OptionType.SINGLE){
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout lv = findViewById(R.id.mainLinearLayoutId);
                    for(int i = 0; i < lv.getChildCount(); i++){
                        CardView c = (CardView) lv.getChildAt(i);
                        c.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                    cv.setCardBackgroundColor(Color.LTGRAY);

                    //nextClick(v);

                    /*if(isLeaf(node)){
                        //when, Little more, address, name-email-phone
                        Intent i = new Intent(cv.getContext(), ProjectTime.class);
                        startActivity(i);
                    }
                    else {
                        //Start new issue navigation activity
                        Intent i = new Intent(cv.getContext(), IssueNavigation.class);
                        i.putExtra("node", node);
                        startActivity(i);
                    }*/
                }
            });
        }
        else if(optionType == OptionType.MULTIPLE){
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cv.getCardBackgroundColor().getDefaultColor() == -1){
                        cv.setCardBackgroundColor(Color.LTGRAY);
                    }
                    else {
                        cv.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    }
                }
            });
        }
    }

    private CardView getCardView(OptionNode curNode) {
        CardView card = new CardView(this);
        card.setId(curNode.get_nodeId());
        int cvHeight = Utilities.getMeasureinDp(this,80);
        card.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, cvHeight));
        card.setRadius(Utilities.getMeasureinDp(this,0));
        card.setCardElevation(Utilities.getMeasureinDp(this,8));

        //Set card margin
        int cardMargin = Utilities.getMeasureinDp(this,8);
        Utilities.setMargins(card, cardMargin, 0, cardMargin, cardMargin);

        //Now create linear layout inside this card.
        LinearLayout innerLinear = new LinearLayout(this);
        innerLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        innerLinear.setOrientation(LinearLayout.HORIZONTAL);
        innerLinear.setHorizontalGravity(Gravity.LEFT);
        innerLinear.setVerticalGravity(Gravity.CENTER_VERTICAL);

        int innerLayoutMargin = Utilities.getMeasureinDp(this,10);
        Utilities.setMargins(innerLinear, innerLayoutMargin, innerLayoutMargin, innerLayoutMargin, innerLayoutMargin);

        //Create image view that will go inside inner linear view.
        ImageView iv = new ImageView(this);
        int ivHeight = Utilities.getMeasureinDp(this,64);
        int ivWidth = Utilities.getMeasureinDp(this,64);
        LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(ivWidth, ivHeight);
        iv.setLayoutParams(ivParams);

        Utilities.setMargins(iv, Utilities.getMeasureinDp(this,16), 0, 0, 0);
        iv.setImageResource(R.drawable.homeautomation);
        innerLinear.addView(iv);

        //Create text view that will go inside inner linear view.
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvParams.gravity = Gravity.CENTER;
        tv.setLayoutParams(tvParams);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        Utilities.setMargins(tv, Utilities.getMeasureinDp(this,25), 0, 0, 0);
        tv.setText(curNode.get_name());
        innerLinear.addView(tv);

        card.addView(innerLinear);

        return card;
    }

    /*private ArrayList<OptionNode> getSelectedNodesOld() {
        ArrayList<OptionNode> children = (ArrayList<OptionNode>)currentNode.get_childrenNodes();
        ArrayList<OptionNode> selectedNodes = new ArrayList<>();
        if(currentNode.get_optionType() == OptionType.SINGLE){
            OptionNode node = null;
            RadioGroup rg = findViewById(R.id.radioGroupId);
            int selectedId = rg.getCheckedRadioButtonId();
            for(int i = 0; i < children.size(); i++){
                OptionNode curNode = (OptionNode) children.toArray()[i];
                if(curNode.get_nodeId() == selectedId){
                    node = curNode;
                    break;
                }
            }
            selectedNodes.add(node);
        }
        else if (currentNode.get_optionType() == OptionType.MULTIPLE){
            for(int i = 0; i < children.size(); i++){
                OptionNode curNode = (OptionNode) children.toArray()[i];
                CheckBox cb = findViewById(curNode.get_nodeId());

                if(cb.isChecked()){
                    selectedNodes.add(curNode);
                }
            }
        }
        return selectedNodes;
    }
    private void renderAsCheckBoxes() {
        LinearLayout issueNavBody = findViewById(R.id.issueNavBody);
        ArrayList<OptionNode> children = (ArrayList<OptionNode>)currentNode.get_childrenNodes();

        for(int i = 0; i < children.size(); i++){
            OptionNode curNode = (OptionNode) children.toArray()[i];
            String text = curNode.get_name();
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(text);
            cb.setTextSize(20);
            cb.setId(curNode.get_nodeId());
            issueNavBody.addView(cb);
        }
    }
    private void renderAsRadioButtons1(){
        ArrayList<OptionNode> children = (ArrayList<OptionNode>)currentNode.get_childrenNodes();

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(R.id.radioGroupId);
        radioGroup.setOrientation(RadioGroup.VERTICAL);
        RadioGroup.LayoutParams layoutParams;
        for(int i = 0; i < children.size(); i++){
            OptionNode curNode = (OptionNode) children.toArray()[i];
            String text = curNode.get_name();
            RadioButton rb = new RadioButton(this);
            rb.setId(curNode.get_nodeId());
            rb.setTextSize(20);
            rb.setText(text);
            layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,RadioGroup.LayoutParams.MATCH_PARENT);
            radioGroup.addView(rb, layoutParams);
        }
        LinearLayout issueNavBody = findViewById(R.id.issueNavBody);
        issueNavBody.addView(radioGroup);
    }*/
}
