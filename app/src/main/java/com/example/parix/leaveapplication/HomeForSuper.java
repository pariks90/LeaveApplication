package com.example.parix.leaveapplication;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by Parix on 2/15/2016.
 */
public class HomeForSuper extends TabActivity {

    private TabHost tabHost;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        String Name = getIntent().getStringExtra("EMPNAME");
        TextView tv = (TextView) findViewById(R.id.Welcome);
        tv.setText("Welcome " + Name);

        tabHost = getTabHost();

        userName = getIntent().getStringExtra("USERNAME");
        TabHost.TabSpec tabspec1 = tabHost.newTabSpec("worklist");
        tabspec1.setIndicator("WorkList");
        Intent in1 = new Intent(this,WorkList.class);
        in1.putExtra("USERNAME",userName);
        tabspec1.setContent(in1);
        tabHost.addTab(tabspec1);


        TabHost.TabSpec tabspec2 = tabHost.newTabSpec("myreportees");
        tabspec2.setIndicator("My Reportees");
        Intent in2 = new Intent(this,MyReportees.class);
        tabspec2.setContent(in2);
        tabHost.addTab(tabspec2);

        TabHost.TabSpec tabspec3 = tabHost.newTabSpec("myprofile");
        tabspec3.setIndicator("My Profile");
        Intent in3 = new Intent(this,MyProfile.class);
        in3.putExtra("USERNAME",userName);
        tabspec3.setContent(in3);
        tabHost.addTab(tabspec3);
    }
}
