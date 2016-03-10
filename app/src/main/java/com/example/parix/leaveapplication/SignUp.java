package com.example.parix.leaveapplication;

        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.RadioGroup.OnCheckedChangeListener;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.view.View.OnClickListener;

        import java.util.ArrayList;

public class SignUp extends Activity implements OnCheckedChangeListener,OnClickListener, View.OnFocusChangeListener {

    private RadioGroup rdbttnGroup;
    private Spinner spinner;
    private Button bttnSignup;
    SQLiteHelper DbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        rdbttnGroup = (RadioGroup) findViewById(R.id.rdbttnGroup);
        rdbttnGroup.setOnCheckedChangeListener(this);
        bttnSignup = (Button) findViewById(R.id.bttnSignUp);
        bttnSignup.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner1);
       /* EditText uName = (EditText) findViewById(R.id.Uname);
        EditText empId = (EditText) findViewById(R.id.EmpId);
        EditText name = (EditText) findViewById(R.id.Name);
        EditText eMail = (EditText) findViewById(R.id.Email);
        EditText pass = (EditText) findViewById(R.id.Pass);
        EditText confPass = (EditText) findViewById(R.id.ConfPass);*/

        DbHandler = new SQLiteHelper(getApplicationContext(), null, null, 1);
        ArrayList<String> list = DbHandler.getSuperDetails();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // TODO Auto-generated method stub


        if(checkedId == R.id.rdbttnEmp)
        {
            spinner.setVisibility(View.VISIBLE);
            /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });*/

        }
        else
        {
            spinner.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (validateText((EditText) findViewById(R.id.Uname)) &&
                validateText((EditText) findViewById(R.id.EmpId)) &&
                validateText((EditText) findViewById(R.id.Name)) &&
                validateText((EditText) findViewById(R.id.Email)) &&
                validateText((EditText) findViewById(R.id.Pass)) &&
                validateText((EditText) findViewById(R.id.Pass),(EditText) findViewById(R.id.ConfPass)) &&
                validateText(spinner))

        {

            boolean successFlag = false;
            if (v == bttnSignup) {
                DbHandler = new SQLiteHelper(getApplicationContext(), null, null, 1);
                SignupBO bo = new SignupBO();

                bo.setUname(((EditText) findViewById(R.id.Uname)).getText().toString());

                bo.setEmpid(((EditText) findViewById(R.id.EmpId)).getText().toString());

                bo.setEmpName(((EditText) findViewById(R.id.Name)).getText().toString());

                bo.setEmail(((EditText) findViewById(R.id.Email)).getText().toString());

                RadioGroup rbg = (RadioGroup) findViewById(R.id.rdbttnGroup);
                RadioButton rbttn = (RadioButton) findViewById(rbg.getCheckedRadioButtonId());
                bo.setEmpRole(rbttn.getText().toString());

                Spinner sp = (Spinner) findViewById(R.id.spinner1);
                if (rbttn.getId() == R.id.rdbttnEmp) {
                    bo.setSuperName(sp.getSelectedItem().toString());

                } else
                    bo.setSuperName(null);

                bo.setPassword(((EditText) findViewById(R.id.Pass)).getText().toString());

                successFlag = DbHandler.insert(bo);
            }

            if (successFlag == true) {
                Toast.makeText(
                        v.getContext(),
                        "Your Registration is successful",
                        Toast.LENGTH_LONG).show();
                v.getContext();
                finish();
            }
            else {
                Toast.makeText(
                        v.getContext(),
                        "Your Registration is failed",
                        Toast.LENGTH_LONG).show();
                v.getContext();
                finish();
            }
        }
        else {
            ((EditText) findViewById(R.id.Uname)).setOnFocusChangeListener(this);
            ((EditText) findViewById(R.id.EmpId)).setOnFocusChangeListener(this);
            ((EditText) findViewById(R.id.Name)).setOnFocusChangeListener(this);
            ((EditText) findViewById(R.id.Email)).setOnFocusChangeListener(this);
            ((EditText) findViewById(R.id.Pass)).setOnFocusChangeListener(this);
            ((EditText) findViewById(R.id.ConfPass)).setOnFocusChangeListener(this);

        }
    }

    public boolean validateText(final EditText text)
    {
        boolean valid = true;
        if (text.getText().toString().isEmpty()) {
            switch (text.getId()) {
                case R.id.Uname:
                    text.setError("User Name can not be empty");
                    break;
                case R.id.EmpId:
                    text.setError("Employee ID can not be empty");
                    break;
                case R.id.Name:
                    text.setError("Employee Name can not be empty");
                    break;
                case R.id.Email:
                    text.setError("Email can not be empty");
                    break;
                case R.id.Pass:
                    text.setError("Password can not be empty");
                    break;
                default:
                    break;

            }
            text.requestFocus();
            valid = false;
        }
        if(text.getId() == R.id.Email)
        {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!text.getText().toString().matches(emailPattern)) {
                text.setError("This is not a valid Email Address");
                text.requestFocus();
                valid = false;
            }
        }

        if(text.getId() == R.id.Uname)
        {
            String uname = text.getText().toString();
            SQLiteHelper helper = new SQLiteHelper(getApplicationContext(), null, null, 1);
            String passFromDb[] = helper.searchPass(uname);
            if(passFromDb[0] != null)
            {
                text.setError("This Username already exists!");
                text.requestFocus();
                valid = false;
            }
        }
        if(text.getId() == R.id.EmpId)
        {
            String empID = text.getText().toString();
            RadioGroup rbg = (RadioGroup) findViewById(R.id.rdbttnGroup);
            RadioButton rbttn = (RadioButton) findViewById(rbg.getCheckedRadioButtonId());
            String role = rbttn.getText().toString();
            SQLiteHelper helper = new SQLiteHelper(getApplicationContext(), null, null, 1);

            if(helper.ifExists(empID,role))
            {
                text.setError("This Emp ID is already registered in "+role+" role!");
                text.requestFocus();
                valid = false;
            }
        }

        return valid;
    }

    public boolean validateText(final EditText text1,final EditText text2)
    {
        boolean valid = true;
        if (!text1.getText().toString().equals(text2.getText().toString())) {
            text2.setError("Both Passwords Don't Match");
            valid = false;
        }
        return valid;
    }

    public boolean validateText(final Spinner sp)
    {
        boolean valid = true;
        if (sp.getVisibility() == View.VISIBLE)
        if (sp.getSelectedItem().toString().equals("Chose your Supervisor")) {
            View v = sp.getSelectedView();
            TextView textView = (TextView) v;
            textView.setError("Please chose your Supervisor");
            valid = false;
        }
        return valid;
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText text = (EditText) v;
        text.setError(null);
    }
}

