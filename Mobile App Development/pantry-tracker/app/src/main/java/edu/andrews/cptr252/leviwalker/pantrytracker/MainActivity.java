package edu.andrews.cptr252.leviwalker.pantrytracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.andrews.cptr252.leviwalker.pantrytracker.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnButtonClickListener {

    private RecyclerView recyclerItems;
    private AdapterItem adapter;
    private DAOItem helper;
    private List<InfoItem> itemList;
    private final int REQUEST_NEW = 1;
    private final int REQUEST_EDIT = 2;
    private int action;
    private ImageButton btnSearch;
    private String listDirection;
    private Button direction;
    private String sortCategory = "name";
    private Button sortBy;
    private EditText edtSearch;
    private String[] sortingOptions = {"Product Name", "Expiration Date", "Quantity"};
    final int[] optionIndex = {0};
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static final String PREFS_NAME = "filters";
    private static final String KEY_IS_OPEN = "filter_open";
    private boolean filterOpen= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("sortingPreferences", MODE_PRIVATE);
        listDirection = sharedPreferences.getString("sortDirection","ASC");
        optionIndex[0] = sharedPreferences.getInt("sortIndex", 0);
        sortCategory = sharedPreferences.getString("sortCategory","name");

        // The coursework UI supports a light theme only.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(MainActivity.this, EditActivity.class);
                edit.putExtra("item", new InfoItem());

                action = REQUEST_NEW;
                itemlauncher.launch(edit);
            }
        });

        direction = findViewById(R.id.btnDirection);
        sortBy = findViewById(R.id.btnSortBy);
        edtSearch = findViewById(R.id.edtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                edtSearch.clearFocus();

                itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
                adapter.setItemList(itemList);
                adapter.notifyDataSetChanged();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    v.clearFocus();

                    itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
                    adapter.setItemList(itemList);
                    adapter.notifyDataSetChanged();

                    return true;
                }
                return false;
            }
        });

        setDirectionArrow();
        setCategoryText();

        helper = new DAOItem(this);
        itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());

        recyclerItems = findViewById(R.id.RecyclerItems);
        recyclerItems.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItems.setLayoutManager(llm);

        adapter = new AdapterItem(itemList);
        recyclerItems.setAdapter(adapter);

        itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
        adapter.setItemList(itemList);
        adapter.notifyDataSetChanged();

        recyclerItems.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openOptions(itemList.get(position));
                    }
                }
        ));

        direction.setOnClickListener(v -> {
            changeDirection();
        });

        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("sortingPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                optionIndex[0] = (optionIndex[0] + 1) % 3;
                editor.putInt("sortIndex", optionIndex[0]);
                setCategoryText();
                switch (sortBy.getText().toString()) {
                    case "Product Name":
                        sortCategory = "name";
                        break;
                    case "Expiration Date":
                        sortCategory = "expiration";
                        break;
                    case "Quantity":
                        sortCategory = "quantity";
                        break;
                }
                editor.putString("sortCategory", sortCategory);
                editor.apply();
                itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
                adapter.setItemList(itemList);
                adapter.notifyDataSetChanged();
            }
        });


        Button filter;
        View filterView;
        filter = findViewById(R.id.filter);
        filterView = findViewById(R.id.FilterView);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterView.setVisibility(View.VISIBLE);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(filterView.getId(), new FilterFragment());
                fragmentTransaction.commit();

                filterOpen = true;

            }
        });




    }

    @Override
    public void onButtonClicked() {
        itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
        adapter.setItemList(itemList);
        adapter.notifyDataSetChanged();
        findViewById(R.id.FilterView).setVisibility(View.GONE);
    }

    private void setCategoryText() {
        sortBy.setText(sortingOptions[optionIndex[0]]);
    }

    private void openOptions(InfoItem item){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Item: " + item.getName());
        builder.setItems(new CharSequence[]{"Edit", "Delete",},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("item", item);
                                action = REQUEST_EDIT;
                                itemlauncher.launch(intent1);
                                break;
                            case 1:
                                itemList.remove(item);
                                helper.deleteItem(item);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private final ActivityResultLauncher<Intent> itemlauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        if(data != null){
                            InfoItem infoItem = data.getParcelableExtra("item");
                            if(action == REQUEST_NEW){
                                helper.insertItem(infoItem);
                            }
                            else if(action == REQUEST_EDIT){
                                helper.editItem(infoItem);
                            }
                            itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
                            adapter.setItemList(itemList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

    private void changeDirection() {
        SharedPreferences sharedPreferences = getSharedPreferences("sortingPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (listDirection.equals("ASC")) {
            listDirection = "DESC";
            editor.putString("sortDirection", "DESC");
        }
        else {
            listDirection = "ASC";
            editor.putString("sortDirection", "ASC");
        }
        editor.apply();
        setDirectionArrow();
        itemList = helper.getList(listDirection, sortCategory, edtSearch.getText().toString());
        adapter.setItemList(itemList);
        adapter.notifyDataSetChanged();
    }

    private void setDirectionArrow() {
        if(listDirection.equals("ASC")) {
            direction.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.direction_arrow_up, 0, 0);
        }
        else {
            direction.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.direction_arrow_down, 0, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
