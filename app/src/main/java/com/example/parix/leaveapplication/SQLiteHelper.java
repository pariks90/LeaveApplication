package com.example.parix.leaveapplication;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteDatabase.CursorFactory;
        import android.database.sqlite.SQLiteOpenHelper;
        import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LeaveApp.db";
//  Table spec for sign up
    private static final String TABLE_NAME = "Signup";
    private static final String COLOUMN_1 = "User_Name";
    private static final String COLOUMN_2 = "Emp_ID";
    private static final String COLOUMN_3 = "Emp_Name";
    private static final String COLOUMN_4 = "Email";
    private static final String COLOUMN_5 = "Emp_Role";
    private static final String COLOUMN_6 = "Supervisor_Name";
    private static final String COLOUMN_7 = "Password";

//  Table spec for aplly leave
    private static final String LEAVE_TABLE_NAME = "Leave_Details";
    private static final String LEAVE_COLOUMN_1 = "Leave_ID";
    private static final String LEAVE_COLOUMN_2 = "User_Name";
    private static final String LEAVE_COLOUMN_3 = "Date_from";
    private static final String LEAVE_COLOUMN_4 = "Date_to";
    private static final String LEAVE_COLOUMN_5 = "Leave_type";
    private static final String LEAVE_COLOUMN_6 = "Total Days";
    private static final String LEAVE_COLOUMN_7 = "Reason";
    private static final String LEAVE_COLOUMN_8 = "Leave_Status";

    public SQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            String createQuery = "create table " + TABLE_NAME + "(" +
                    COLOUMN_1 + " text," +
                    COLOUMN_2 + " text, " +
                    COLOUMN_3 + " text, " +
                    COLOUMN_4 + " text, " +
                    COLOUMN_5 + " text, " +
                    COLOUMN_6 + " text, " +
                    COLOUMN_7 + " text, constraint pk primary key (" + COLOUMN_1 + "," + COLOUMN_2 + "," + COLOUMN_3 + "," + COLOUMN_5 + "));";
            db.execSQL(createQuery);

            String createQuery2 = "create table " + LEAVE_TABLE_NAME + "(" +
                    LEAVE_COLOUMN_1 + " integer autoincrement," +
                    LEAVE_COLOUMN_2 + " text, " +
                    LEAVE_COLOUMN_3 + " text, " +
                    LEAVE_COLOUMN_4 + " text, " +
                    LEAVE_COLOUMN_5 + " text, " +
                    LEAVE_COLOUMN_6 + " text, " +
                    LEAVE_COLOUMN_7 + " text, " +
                    LEAVE_COLOUMN_8 + " text, " +
                    "constraint pk2 primary key (" + COLOUMN_1 + "," + COLOUMN_2 + "," + COLOUMN_3 + "," + COLOUMN_4 + "));";
            db.execSQL(createQuery2);
        }
        catch (SQLException e){

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LEAVE_TABLE_NAME);

        onCreate(db);

    }


    public boolean insert(SignupBO bo)
    {

        boolean flag;
        ContentValues content = new ContentValues();

        try{

            content.put(COLOUMN_1, bo.getUname());
            content.put(COLOUMN_2, bo.getEmpid());
            content.put(COLOUMN_3, bo.getEmpName());
            content.put(COLOUMN_4, bo.getEmail());
            content.put(COLOUMN_5, bo.getEmpRole());
            content.put(COLOUMN_6, bo.getSuperName());
            content.put(COLOUMN_7, bo.getPassword());

            SQLiteDatabase db = getWritableDatabase();
            db.insert(TABLE_NAME, null, content);
            db.close();
            flag = true;
        }
        catch(SQLException e)
        {
            flag = false;
        }
        return flag;
    }

    public boolean insert(ApplyLeaveBO bo)
    {

        boolean flag;
        ContentValues content = new ContentValues();

        try{

            content.put(LEAVE_COLOUMN_2, bo.getUname());
            content.put(LEAVE_COLOUMN_3, bo.getDateFrom());
            content.put(LEAVE_COLOUMN_4, bo.getDateTo());
            content.put(LEAVE_COLOUMN_5, bo.getLeaveType());
            content.put(LEAVE_COLOUMN_6, bo.getTotalDays());
            content.put(LEAVE_COLOUMN_7, bo.getReason());
            content.put(LEAVE_COLOUMN_7, "In Progress");

            SQLiteDatabase db = getWritableDatabase();
            db.insert(LEAVE_TABLE_NAME, null, content);
            db.close();
            flag = true;
        }
        catch(SQLException e)
        {
            flag = false;
        }
        return flag;
    }

    public String[] searchPass(String uName)
    {

        String[] Pass = new String[3];
        Pass[0] = null;
        Pass[1] = null;
        try
        {
            String searchQuery = "select "+COLOUMN_1+","+COLOUMN_3+","+COLOUMN_5+","+COLOUMN_7+" from "+TABLE_NAME+" where "+COLOUMN_1+" = '"+uName+"';";
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(searchQuery, null);
            if(cursor.moveToFirst())
            {
                do{

                    if(cursor.getString(cursor.getColumnIndex(COLOUMN_1)).equals(uName))
                    {
                        Pass[0] = cursor.getString(cursor.getColumnIndex(COLOUMN_7));
                        Pass[1] = cursor.getString(cursor.getColumnIndex(COLOUMN_3));
                        Pass[2] = cursor.getString(cursor.getColumnIndex(COLOUMN_5));
                        break;
                    }
                }while(cursor.moveToNext());
            }
        }
        catch (SQLException e) {

        }
        return Pass;
    }

    public boolean ifExists(String empID,String role)
    {

        try {
            String searchQuery = "select * from "+TABLE_NAME+" where "+COLOUMN_2+" = '"+empID+"' and "+COLOUMN_5+" ='"+role+"';";
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(searchQuery, null);
            if (cursor.getCount() != 0)
            {
                return true;
            }
        }
        catch (SQLException e)
        {

        }
        return false;

    }

    public ArrayList<String> getSuperDetails()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Chose your Supervisor");

        try
        {
            String searchQuery = "select "+COLOUMN_2+","+COLOUMN_3+" from "+TABLE_NAME+" where "+COLOUMN_5+" = 'Supervisor';";
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(searchQuery, null);
            if(cursor.moveToFirst())
            {
                do{
                   String superId = cursor.getString(cursor.getColumnIndex(COLOUMN_2));
                   String superName = cursor.getString(cursor.getColumnIndex(COLOUMN_3));
                   list.add(superId+": "+superName);
                }while(cursor.moveToNext());
            }
//            db.setTransactionSuccessful();
        }
        catch (SQLException e) {

        }
/*        finally {
//            db.endTransaction();
//            db.close();
        }*/
        return list;
    }
    public SignupBO getProfile(String uName)
    {
        SignupBO profileBo = new SignupBO();
        try
        {
            String searchQuery = "select * from "+TABLE_NAME+" where "+COLOUMN_1+" = '"+uName+"';";
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(searchQuery, null);
            if(cursor.moveToFirst())
            {
                do{
                    String name = cursor.getString(cursor.getColumnIndex(COLOUMN_3));
                    profileBo.setEmpName(name);
                    String empId = cursor.getString(cursor.getColumnIndex(COLOUMN_2));
                    profileBo.setEmpid(empId);
                    String email = cursor.getString(cursor.getColumnIndex(COLOUMN_4));
                    profileBo.setEmail(email);
                    String role = cursor.getString(cursor.getColumnIndex(COLOUMN_5));
                    profileBo.setEmpRole(role);
                    String supername = cursor.getString(cursor.getColumnIndex(COLOUMN_6));
                    profileBo.setSuperName(supername);

                }while(cursor.moveToNext());
            }
//            db.setTransactionSuccessful();
        }
        catch (SQLException e) {

        }
        return profileBo;
    }
}
