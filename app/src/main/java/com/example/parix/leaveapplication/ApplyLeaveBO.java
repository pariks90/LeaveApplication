package com.example.parix.leaveapplication;

/**
 * Created by Parix on 2/9/2016.
 */
public class ApplyLeaveBO {

    private String Uname;
    private String DateFrom;
    private String DateTo;
    private String LeaveType;
    private int TotalDays;
    private String Reason;

    public void setUname(String Uname)
    {
        this.Uname = Uname;
    }

    public void setDateFrom(String DateFrom)
    {
        this.DateFrom = DateFrom;
    }


    public void setDateTo(String DateTo)
    {
        this.DateTo = DateTo;
    }

    public void setLeaveType(String LeaveType)
    {
        this.LeaveType = LeaveType;
    }

    public void setTotalDays(int TotalDays)
    {
        this.TotalDays = TotalDays;
    }

    public void setReason(String Reason)
    {
        this.Reason = Reason;
    }

    public String getUname()
    {
        return Uname;
    }

    public String getDateFrom()
    {
        return DateFrom;
    }

    public String getDateTo()
    {
        return DateTo;
    }

    public String getLeaveType()
    {
        return LeaveType;
    }

    public int getTotalDays()
    {
        return TotalDays;
    }

    public String getReason()
    {
        return Reason;
    }

}
