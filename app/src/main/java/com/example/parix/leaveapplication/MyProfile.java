package com.example.parix.leaveapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Parix on 2/5/2016.
 */
public class MyProfile extends Activity {

    TextView txtName;
    TextView txtEmpId;
    TextView txtEmail;
    TextView txtRole;
    TextView txtSuperName;
    TextView txtSuperEmiId;
    Button btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile_tab);

        SQLiteHelper profile = new SQLiteHelper(getApplicationContext(),null,null,1);
        SignupBO profileBo = profile.getProfile(getIntent().getStringExtra("USERNAME"));

        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText("NAME: "+profileBo.getEmpName());

        txtEmpId = (TextView) findViewById(R.id.txtEmpId);
        txtEmpId.setText("EMP ID: "+profileBo.getEmpid());

        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtEmail.setText("EMAIL ID: "+profileBo.getEmail());

        txtRole = (TextView) findViewById(R.id.txtRole);
        txtRole.setText("ROLE: "+profileBo.getEmpRole());

        String Super = profileBo.getSuperName();
        if (Super != null) {
            String[] supername = Super.split(":");
            txtSuperName = (TextView) findViewById(R.id.txtSuperName);
            txtSuperName.setText("SUPERVISOR NAME: " + supername[1]);

            txtSuperEmiId = (TextView) findViewById(R.id.txtSuperEmpId);
            txtSuperEmiId.setText("SUPERVISOR EMPID: " + supername[0]);
        }


    }

    public void onUpdateClick(View v) {


    }

    public void onLogOutClick(View v){

            v.getContext();
            finish();

    }
}
