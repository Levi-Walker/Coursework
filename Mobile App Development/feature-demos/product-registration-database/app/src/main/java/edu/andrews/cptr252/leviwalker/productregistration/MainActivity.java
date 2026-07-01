package edu.andrews.cptr252.leviwalker.productregistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.andrews.cptr252.leviwalker.productregistration.R;

public class MainActivity extends AppCompatActivity {

    HelperTable myHelper;
    SQLiteDatabase myDB;
    Button delete;
    Button save;
    TextView DBView;
    Spinner categories;
    EditText id;
    EditText productName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        delete = findViewById(R.id.Delete);
        save = findViewById(R.id.Save);
        DBView = findViewById(R.id.displayDB);
        categories = findViewById(R.id.categories);
        id = findViewById(R.id.productID);
        productName = findViewById(R.id.productName);

        String[] categoryList = {"Kitchen", "Computing", "Furniture", "Food", "Automotive"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        categories.setAdapter(adapter);

        myHelper = new HelperTable(this);
        myDB = myHelper.getWritableDatabase();

        readDB();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty() || productName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!!!",Toast.LENGTH_SHORT).show();
                }
                else {
                    addItem(Integer.parseInt(id.getText().toString()), categories.getSelectedItem().toString(), productName.getText().toString());
                    readDB();
                    id.getText().clear();
                    productName.getText().clear();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().isEmpty() && productName.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Fields are empty!!!",Toast.LENGTH_SHORT).show();
                }
                else if (!productName.getText().toString().isEmpty() && id.getText().toString().isEmpty()){
                    deleteItem(productName.getText().toString());
                }
                else if (!id.getText().toString().isEmpty() && productName.getText().toString().isEmpty()){
                    deleteItemByID(Integer.parseInt(id.getText().toString()));
                }
                else {
                    Toast.makeText(MainActivity.this, "Please delete by ID OR Name",Toast.LENGTH_SHORT).show();
                }

                readDB();
                id.getText().clear();
                productName.getText().clear();

            }
        });



    }

    void addItem(int id, String category, String productName){
        ContentValues values = new ContentValues();
        values.clear();

        values.put(ConfigTable.Columns.ID, id);
        values.put(ConfigTable.Columns.CATEGORY, category);
        values.put(ConfigTable.Columns.ITEM, productName);

        myDB.insertWithOnConflict(ConfigTable.TABLE, null, values,SQLiteDatabase.CONFLICT_IGNORE);
    }

    void readDB(){
        Cursor cursor = myDB.query(ConfigTable.TABLE,
                new String[]{ConfigTable.Columns.ITEM, ConfigTable.Columns.CATEGORY, ConfigTable.Columns.ID},
                null,null,null,null,null);

        int itemCol = cursor.getColumnIndex(ConfigTable.Columns.ITEM);
        int categoryCol = cursor.getColumnIndex(ConfigTable.Columns.CATEGORY);
        int idCol = cursor.getColumnIndex(ConfigTable.Columns.ID);

        StringBuilder result = new StringBuilder();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String item = cursor.getString(itemCol);
            int id = cursor.getInt(idCol);
            String cat = cursor.getString(categoryCol);
            result.append(id).append(": ").append(item).append(" (").append(cat).append(")").append("\n");
            cursor.moveToNext();
        }
        TextView text = findViewById(R.id.displayDB);
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
