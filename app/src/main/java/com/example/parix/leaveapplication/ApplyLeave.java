package com.example.parix.leaveapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Parix on 2/5/2016.
 */
public class ApplyLeave extends Activity{

    Calendar calender = Calendar.getInstance();
    private Button btnDateFrom;
    private Button btnDateTo;
    private String fromDate = "";
    private String toDate = "";
    private EditText txtReason;
    private TextView totalDays;
    private Spinner sp;

    SQLiteHelper dbHelper;
    ApplyLeaveBO applyLeaveBo;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_leave_tab);


        sp = (Spinner) findViewById(R.id.spLeaveType);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, R.array.leave_type, R.layout.spinner_item);
        sp.setAdapter(adapter);

        btnDateTo = (Button) findViewById(R.id.btnDateTo);
        btnDateFrom = (Button) findViewById(R.id.btnDateFrom);
        txtReason = (EditText) findViewById(R.id.edtxtReason);
        totalDays = (TextView) findViewById(R.id.txtTotalDays);


        userName = getIntent().getStringExtra("USERNAME");

    }
    public void onDateFrom(View v){
        new DatePickerDialog(ApplyLeave.this,dateSelectListner,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener dateSelectListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            fromDate = (dayOfMonth+"/"+(monthOfYear+1)+"/"+year).toString();
            btnDateFrom.setText(fromDate);

            if (!toDate.isEmpty() && !fromDate.isEmpty()){
                String total = (new DateComparison(fromDate,toDate).CompareDate()).toString();
                if (Integer.parseInt(total)<=0){
                    totalDays.setText(null);
                    Toast.makeText(ApplyLeave.this,
                            "To Date can't be less than From date",
                            Toast.LENGTH_SHORT).show();
                }else {
                    total = "Total " + total + " days of " + sp.getSelectedItem().toString();
//              LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//              params.setMargins(30,30,30,30);
//              totalDays.setLayoutParams(params);
//              totalDays.setTextColor(Color.WHITE);
//              totalDays.setTextSize(20);
                    totalDays.setText(total);
//              LinearLayout ln = (LinearLayout) findViewById(R.id.lnlayoutForTotalDays);
//              ln.addView(totalDays);
                }
            }else {
                totalDays.setText(null);
            }

        }
    };
    public void onDateTo(View v){
        new DatePickerDialog(ApplyLeave.this,dateSelectListner2,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)).show();

    }
    DatePickerDialog.OnDateSetListener dateSelectListner2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            toDate = (dayOfMonth+"/"+(monthOfYear+1)+"/"+year).toString();
            btnDateTo.setText(toDate);
            if (!toDate.isEmpty() && !fromDate.isEmpty()){

                String total = (new DateComparison(fromDate,toDate).CompareDate()).toString();
                if (Integer.parseInt(total)<=0){
                    totalDays.setText(null);
                    Toast.makeText(ApplyLeave.this,
                            "To Date can't be less than From date",
                            Toast.LENGTH_SHORT).show();
                }else {
                    total = "Total " + total + " days of " + sp.getSelectedItem().toString();
//              LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//              params.setMargins(30,30,30,30);
//              totalDays.setLayoutParams(params);
//              totalDays.setTextColor(Color.WHITE);
//              totalDays.setTextSize(20);
                    totalDays.setText(total);
//              LinearLayout ln = (LinearLayout) findViewById(R.id.lnlayoutForTotalDays);
//              ln.addView(totalDays);
                }
            }
            else {
                totalDays.setText(null);
            }
        }
    };

    public void onApplyLeave(View v){

        boolean leaveSuccess = false;

        if(fromDate.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select the From Date",Toast.LENGTH_SHORT).show();
        }
        else if(toDate.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select the To Date",Toast.LENGTH_SHORT).show();
        }
        else if (txtReason.getText().toString().isEmpty()){
            txtReason.setError("Please specify a reason!");
            txtReason.requestFocus();
        }
        else if (new DateComparison(fromDate,toDate).CompareDate() <= 0){
            Toast.makeText(getApplicationContext(),"To Date can't be less than From date",Toast.LENGTH_SHORT).show();
        }
        else {
         txtReason.setError(null);
         applyLeaveBo = new ApplyLeaveBO();
         applyLeaveBo.setUname(userName);
         applyLeaveBo.setDateFrom(fromDate);
         applyLeaveBo.setDateTo(toDate);
         applyLeaveBo.setLeaveType(sp.getSelectedItem().toString());
         applyLeaveBo.setTotalDays(new DateComparison(fromDate, toDate).CompareDate());
         applyLeaveBo.setReason(txtReason.getText().toString());

            dbHelper = new SQLiteHelper(getApplicationContext(),null,null,1);
           leaveSuccess = dbHelper.insert(applyLeaveBo);
           if (leaveSuccess == true) {
               Toast.makeText(getApplicationContext(),
                       "Your leave has been succesfully applied",
                       Toast.LENGTH_SHORT).show();

           }
           }

    }

}
