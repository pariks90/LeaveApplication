package com.example.parix.leaveapplication;

import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

public class MainActivity extends Activity implements OnClickListener,OnFocusChangeListener  {

    private EditText userName;
    private EditText passWord;
    private Button 	 login, signup;
    SQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = (EditText) findViewById(R.id.uName);
        passWord = (EditText) findViewById(R.id.Passwd);
        login =    (Button) findViewById(R.id.bttnLogin);
        signup = 	(Button) findViewById(R.id.BttnSignup);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        userName.setOnFocusChangeListener(this);
        passWord.setOnFocusChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        if (v == login) {
            if (validateText(userName) && validateText(passWord)) {
                String passFromDb[], uname, psswd;

                uname = userName.getText().toString();
                psswd = passWord.getText().toString();

                helper = new SQLiteHelper(getApplicationContext(), null, null, 1);
                passFromDb = helper.searchPass(uname);

                if (psswd.equals(passFromDb[0])) {
                    if (passFromDb[2].equals("Employee")) {
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        intent.putExtra("EMPNAME", passFromDb[1]);
                        intent.putExtra("USERNAME", uname);
                        startActivity(intent);
                    }else
                    {
                        Intent intent = new Intent(getApplicationContext(), HomeForSuper.class);
                        intent.putExtra("EMPNAME", passFromDb[1]);
                        intent.putExtra("USERNAME", uname);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(
                            v.getContext(),
                            "The Username or Password is incorrect",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        else if (v==signup)
        {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
        }
    }

    public boolean validateText(final EditText text)
    {
        boolean valid = true;
        if (text.getText().toString().isEmpty()) {
            switch (text.getId()) {
                case R.id.uName:
                    text.setError("User Name can not be empty");
                    break;
                case R.id.Passwd:
                    text.setError("Password can not be empty");
                    break;
                default:
                    break;

            }

            text.requestFocus();
            valid = false;
        }

        return valid;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText text;
        text = (EditText)v;
        text.setError(null);

    }
}



