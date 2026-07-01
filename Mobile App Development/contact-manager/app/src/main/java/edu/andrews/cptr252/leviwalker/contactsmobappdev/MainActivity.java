package edu.andrews.cptr252.leviwalker.contactsmobappdev;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.andrews.cptr252.leviwalker.contactsmobappdev.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerContacts;
    private AdapterContact adapter;
    private DAOContact helper;
    private List<InfoContact> contactList;
    private final int REQUEST_NEW = 1;
    private final int REQUEST_EDIT = 2;
    private int action;

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(MainActivity.this, EditActivity.class);
                edit.putExtra("contact", new InfoContact());

                action = REQUEST_NEW;
                contactLauncher.launch(edit);
            }
        });

        helper = new DAOContact(this);
        contactList = helper.getList("ASC");

        recyclerContacts = findViewById(R.id.RecyclerContacts);
        recyclerContacts.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerContacts.setLayoutManager(llm);

        adapter = new AdapterContact(contactList);
        recyclerContacts.setAdapter(adapter);

        recyclerContacts.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        openOptions(contactList.get(position));
                    }
                }
        ));
    }

    private void openOptions(InfoContact contact){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Contact: " + contact.getName());
        builder.setItems(new CharSequence[]{"Call", "Edit", "Delete",},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+contact.getPhone()));
                                startActivity(intent);
                                break;
                            case 1:
                                Intent intent1 = new Intent(MainActivity.this, EditActivity.class);
                                intent1.putExtra("contact", contact);
                                action = REQUEST_EDIT;
                                contactLauncher.launch(intent1);
                                break;
                            case 2:
                                contactList.remove(contact);
                                helper.deleteContact(contact);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    private final ActivityResultLauncher<Intent> contactLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK){
                        Intent data = result.getData();
                        if(data != null){
                            InfoContact infoContact = data.getParcelableExtra("contact");
                            if(action == REQUEST_NEW){
                                helper.insertContact(infoContact);
                            }
                            else if(action == REQUEST_EDIT){
                                helper.editContact(infoContact);
                            }
                            contactList = helper.getList("ASC");
                            adapter.setContactList(contactList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

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
