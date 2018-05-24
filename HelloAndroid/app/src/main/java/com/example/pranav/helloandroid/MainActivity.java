package com.example.pranav.helloandroid;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mainDrawer;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void onTopCategoryClick(View v){
        String categoryName = null;
        switch (v.getId()){
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
        }

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
