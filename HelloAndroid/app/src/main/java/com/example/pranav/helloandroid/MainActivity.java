package com.example.pranav.helloandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.top_header);
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
