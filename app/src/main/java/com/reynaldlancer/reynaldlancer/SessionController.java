package com.reynaldlancer.reynaldlancer;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class SessionController {
    public SessionController() {
    }
    public  void  addSession(Context ctx, String data){
        SqlHelper helper = new SqlHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into login_data values ('"+data+"')");
        db.close();
    }

    public void clearSession(Context ctx){
        SqlHelper helper = new SqlHelper(ctx);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from login_data");
        db.close();
    }

    public String getActiveUser(Context ctx){
        SqlHelper helper = new SqlHelper(ctx);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from login_data", null);
        cursor.moveToLast();
        if (cursor.getCount() >= 1 ) {
//            Toast.makeText(ctx, cursor.getString(0), Toast.LENGTH_LONG).show();
            return cursor.getString(0);
        }else{
            return null;
        }
    }
}
