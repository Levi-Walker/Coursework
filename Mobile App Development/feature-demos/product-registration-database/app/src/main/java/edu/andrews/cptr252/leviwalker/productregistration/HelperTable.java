package edu.andrews.cptr252.leviwalker.productregistration;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperTable extends SQLiteOpenHelper {
    public HelperTable(@Nullable Context context){
        super(context,ConfigTable.DBNAME,null,ConfigTable.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String myQuery = String.format("CREATE TABLE IF NOT EXISTS %s (%s VARCHAR, %s VARCHAR, %s INT(5))",
                ConfigTable.TABLE, ConfigTable.Columns.ITEM, ConfigTable.Columns.CATEGORY, ConfigTable.Columns.ID);
        db.execSQL(myQuery);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ConfigTable.TABLE);
    }
}
