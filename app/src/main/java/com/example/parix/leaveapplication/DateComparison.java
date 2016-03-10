package com.example.parix.leaveapplication;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

/**
 * Created by Parix on 2/9/2016.
 */
public class DateComparison {

    Date dateTo;
    Date dateFrom;
    DateComparison(String dateFrom,String dateTo){
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            this.dateFrom = (Date) df.parse(dateFrom);
            this.dateTo = (Date) df.parse(dateTo);
        }
        catch (ParseException e){
            Log.i("Error",e.getMessage());
        }
    }
    public Integer CompareDate()
    {
        Integer noOfDays;
        long diff = dateTo.getTime() - dateFrom.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        noOfDays = (int)diffDays;
        return noOfDays+1;
    }

}
