package com.example.pranav.helloandroid;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mainDrawer;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utilities.getHierarchies(this
            ,new IVolleyCallback(){
                @Override
                public void onSuccessResponse(JSONArray array) {
                    ArrayList<OptionNode> nodes = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        OptionNode node;
                        try{
                            JSONObject row = array.getJSONObject(i);
                            node = new Gson().fromJson(row.toString(),OptionNode.class);
                            nodes.add(node);
                        }
                        catch (Exception ex){
                        }
                    }
                    GlobalVariable.set_allCategories(nodes);
                    renderTopCategories();
                }
            });
        initialize();
    }

    private void setupToolbar(){
        mainDrawer = findViewById(R.id.main_drawer);
        toolbar = findViewById(R.id.toolbar);

        AppCompatImageView logo = findViewById(R.id.toolbar_logo);
        logo.setImageResource(R.drawable.seetylogo2);

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.app_name, R.string.app_name);
        mainDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }*/

    private void initialize() {
        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.top_header);
        setupToolbar();
    }

    private void renderTopCategories() {
        android.support.v7.widget.GridLayout grid_layout = findViewById(R.id.mainGrid);
        ArrayList<OptionNode> topCategories = GlobalVariable.get_allCategories();
        for (int i = 0; i < topCategories.size(); i++) {
            View to_add = LayoutInflater.from(this).inflate(R.layout.maincardview, grid_layout);

            to_add.setId(topCategories.get(i).get_nodeId());
            TextView tv = findViewById(R.id.cardTextId);
            tv.setId(topCategories.get(i).get_nodeId()+1000);
            tv.setTag(R.string.card_text_view);
            tv.setText(topCategories.get(i).get_name());
        }
    }

    public void onTopCategoryClick(View v){
        TextView tv = v.findViewWithTag(R.string.card_text_view);
        String categoryName = (String) tv.getText();

        /*switch (v.getId()){
            case R.id.idAppliance:
                categoryName = "Appliances";
                break;
            case R.id.idCarpentry:
                categoryName = "Carpentry";
                break;
            case R.id.idElectrical:
                categoryName = "Electrical";
                break;
            case R.id.idPainting:
                categoryName = "Painting";
                break;
            case R.id.idPlumbing:
                categoryName = "Plumbing";
                break;
            case R.id.idOthers:
                categoryName = "Others";
                break;
        }*/

        //Load top category tree in global variable
        Utilities util = new Utilities();
        util.loadCategoryTree(categoryName);

        OptionNode node = GlobalVariable.get_topCategoryNode();
        node.select();

        GlobalVariable.set_activityOptionNode(node);
        //Start new activity
        Intent i = new Intent(this, IssueNavigation.class);
        i.putExtra("node", node);
        startActivity(i);
    }
}
