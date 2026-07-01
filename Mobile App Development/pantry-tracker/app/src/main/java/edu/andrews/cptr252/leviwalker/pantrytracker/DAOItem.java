package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DAOItem extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private final String TABLE = "Pantry";
    private static final String DATABASE = "Inventory";
    private static final String PREFS_NAME = "filters";
    private static final String KEY_EXPIRED_CHECKED = "expired_checked";
    private static final String KEY_LESS_THAN_5_CHECKED = "less_than_5_checked";

    private SharedPreferences sharedPreferences;

    public DAOItem(Context context){super(context, DATABASE, null, VERSION);
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);}

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE " + TABLE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "expiration TEXT, " +
                "quantity TEXT, " +
                "photo TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<InfoItem> getList(String order, String category,@Nullable String searchQuery){
        if (searchQuery.isEmpty()) {
            searchQuery = "";
        } else {
            searchQuery = " WHERE name LIKE '%"+searchQuery+"%'";
        }
        boolean expiredChecked = sharedPreferences.getBoolean(KEY_EXPIRED_CHECKED, false);
        boolean lessThan5Checked = sharedPreferences.getBoolean(KEY_LESS_THAN_5_CHECKED, false);

        List<InfoItem> items = new ArrayList<>();
        Cursor cursor;
        if (category.equals("quantity") && !expiredChecked && !lessThan5Checked) {
            cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE  + searchQuery +
                    " ORDER BY CAST(" + category + " AS INT) " + order + ";", null);
        } else if (expiredChecked && lessThan5Checked) {
            String currentDate = "date('now')";
            String expiredAndLessThan5Query = "SELECT * FROM " + TABLE +
                    " WHERE expiration < " + currentDate +
                    " AND CAST(quantity AS INT) < 5 " +
                    " ORDER BY CAST(" + category + " AS INT) " + order + ";";
            cursor = getReadableDatabase().rawQuery(expiredAndLessThan5Query, null);
        } else if (expiredChecked) {
            String currentDate = "date('now')";
            String query = "SELECT * FROM " + TABLE +
                    " WHERE expiration < " + currentDate +
                    " ORDER BY CAST(" + category + " AS INT) " + order + ";";
            cursor = getReadableDatabase().rawQuery(query, null);

        } else if (lessThan5Checked) {
            String lessThan5Query = "SELECT COUNT(*) FROM " + TABLE + " WHERE CAST(quantity AS INT) < 5";
            Cursor countCursor = getReadableDatabase().rawQuery(lessThan5Query, null);
            countCursor.moveToFirst();
            int count = countCursor.getInt(0);
            countCursor.close();
            if (count > 0) {
                String query = "SELECT * FROM " + TABLE +
                        " WHERE CAST(quantity AS INT) < 5 " +
                        " ORDER BY CAST(" + category + " AS INT) " + order + ";";
                cursor = getReadableDatabase().rawQuery(query, null);
            } else {
                cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE + " WHERE 1=0", null);
            }
        } else {
            cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE + searchQuery +
                    " ORDER BY " + category + " " + order + ";", null);
        }

        while(cursor.moveToNext()){
            InfoItem item = new InfoItem();

            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int expirationIndex = cursor.getColumnIndex("expiration");
            int quantityIndex = cursor.getColumnIndex("quantity");
            int photoIndex = cursor.getColumnIndex("photo");

            item.setId(cursor.getLong(idIndex));
            item.setName(cursor.getString(nameIndex));
            String formattedDate = cursor.getString(expirationIndex);
            item.setExpiration(String.format("%s/%s/%s", formattedDate.substring(5,7),  formattedDate.substring(8,10), formattedDate.substring(0,4)));
            item.setQuantity(cursor.getString(quantityIndex));
            item.setPhoto(cursor.getString(photoIndex));

            items.add(item);
        }
        cursor.close();
        return items;
    }

    public void insertItem(InfoItem item){
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("expiration", item.getExpiration());
        values.put("quantity", item.getQuantity());
        values.put("photo", item.getPhoto());
        getWritableDatabase().insert(TABLE, null, values);
    }

    public void editItem(InfoItem c){
        ContentValues values = new ContentValues();
        values.put("id", c.getId());
        values.put("name", c.getName());
        values.put("expiration", c.getExpiration());
        values.put("quantity", c.getQuantity());
        values.put("photo", c.getPhoto());

        String[] idToEdit = {c.getId().toString()};
        getWritableDatabase().update(TABLE, values, "id=?", idToEdit);
    }

    public void deleteItem(InfoItem itm){
        SQLiteDatabase db = getWritableDatabase();
        String[] idToDelete = {itm.getId().toString()};
        deleteImage(itm.getPhoto());
        db.delete(TABLE, "id=?", idToDelete);
    }

    private void deleteImage(String path) {
        File imageFile = new File(path);
        if (imageFile.exists()) {
            if (imageFile.delete()) {
                Log.d("DeleteImage", "Image deleted successfully");
            } else {
                Log.e("DeleteImage", "Error: Failed to delete image");
            }
        } else {
            Log.e("DeleteImage", "Error: Image file not found");
        }
    }



}
