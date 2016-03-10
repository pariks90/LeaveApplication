package com.example.parix.leaveapplication;


        import android.app.Activity;
        import android.app.TabActivity;
        import android.content.Intent;
        import android.content.res.Resources;
        import android.os.Bundle;
        import android.widget.TabHost;
        import android.widget.TextView;

public class HomeActivity extends TabActivity {


    private TabHost tabHost;
    private String userName;
//    Resources rec = getResources();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        String Name = getIntent().getStringExtra("EMPNAME");
        TextView tv = (TextView) findViewById(R.id.Welcome);
        tv.setText("Welcome "+Name);

        tabHost = getTabHost();

        userName = getIntent().getStringExtra("USERNAME");
        TabHost.TabSpec tabspec1 = tabHost.newTabSpec("applyleave");
        tabspec1.setIndicator("Apply Leave");
        Intent in1 = new Intent(this,ApplyLeave.class);
        in1.putExtra("USERNAME",userName);
        tabspec1.setContent(in1);
        tabHost.addTab(tabspec1);


        TabHost.TabSpec tabspec2 = tabHost.newTabSpec("leavebalance");
        tabspec2.setIndicator("Leave Balance");
        Intent in2 = new Intent(this,LeaveBalance.class);
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
