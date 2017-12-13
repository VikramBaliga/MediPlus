package projects.android.my.mediplus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 10-12-2017.
 */

public class MediPlusDB extends SQLiteOpenHelper
{

    public MediPlusDB(Context context)
    {
       super(context, "MediPlus", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table tblDrug(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,DRUG_NAME TEXT,DRUG_DESC TEXT,DRUG_PRICE INTEGER)");
        db.execSQL("create table tbtAlarms(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,ALARM_DESC TEXT,ALARM_DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
       db.execSQL("DROP TABLE IF EXISTS tblDrug");
        db.execSQL("DROP TABLE IF EXISTS tbtAlarms");
        onCreate(db);
    }
}
