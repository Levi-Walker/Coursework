package edu.andrews.cptr252.leviwalker.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    HelperTable myHelper;
    SQLiteDatabase myDB;
    Button delete;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemByID(count);
                readDB();
                count++;
            }
        });

        myHelper = new HelperTable(this);
        myDB = myHelper.getWritableDatabase();
        addItem();
        readDB();


    }

    void addItem(){
        ContentValues values = new ContentValues();
        values.clear();

        values.put(ConfigTable.Columns.ITEM, "Mouse");
        values.put(ConfigTable.Columns.ID, 1);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "Keyboard");
        values.put(ConfigTable.Columns.ID, 2);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "Monitor");
        values.put(ConfigTable.Columns.ID, 3);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "Headset");
        values.put(ConfigTable.Columns.ID, 4);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "Television");
        values.put(ConfigTable.Columns.ID, 5);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "Laptop");
        values.put(ConfigTable.Columns.ID, 6);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

        values.put(ConfigTable.Columns.ITEM, "UPS");
        values.put(ConfigTable.Columns.ID, 7);
        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);

    }

    void readDB(){
        Cursor cursor = myDB.query(ConfigTable.TABLE,
                new String[]{ConfigTable.Columns.ITEM, ConfigTable.Columns.ID},
                null,null,null,null,null);

        int itemCol = cursor.getColumnIndex(ConfigTable.Columns.ITEM);
        int idCol = cursor.getColumnIndex(ConfigTable.Columns.ID);

        StringBuilder result = new StringBuilder();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String item = cursor.getString(itemCol);
            int id = cursor.getInt(idCol);
            result.append(id).append(": ").append(item).append("\n");
            cursor.moveToNext();
        }
        TextView text = findViewById(R.id.txtmsg);
        text.setText(result);
    }

    void deleteItem(String item){
        String myQuery = String.format("DELETE FROM %s WHERE %s = '%s'",
                ConfigTable.TABLE, ConfigTable.Columns.ITEM, item);

        myDB.execSQL(myQuery);
    }

    void deleteItemByID(int id){
        String myQuery = String.format("DELETE FROM %s WHERE %s = %s",
                ConfigTable.TABLE, ConfigTable.Columns.ID, id);

        myDB.execSQL(myQuery);
    }
}
