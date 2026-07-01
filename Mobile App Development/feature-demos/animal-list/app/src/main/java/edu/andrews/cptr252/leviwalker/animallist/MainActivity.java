package edu.andrews.cptr252.leviwalker.animallist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] animalStringList = {"Blue Whale", "Chameleon", "Elephant", "Gorilla","Lion", "Octopus", "Owl", "Penguin", "Shark","Snake"};
    int[] iconList = {R.drawable.blue_whale,R.drawable.chameleon,R.drawable.elephant,R.drawable.gorilla,R.drawable.lion,R.drawable.octopus,R.drawable.owl,R.drawable.penguin,R.drawable.shark,R.drawable.snake};
    String[] descriptionList = {
         "Largest animal on the planet, they feed on small crustaceans and are known for their complex underwater songs.",
    "Reptiles known for their ability to change color to blend in with their environment. They have independent eyes that can move separately.",
    "Largest land mammal, they have a trunk used for manipulating objects, communicating, and feeding. They have an incredible memory.",
    "Robust and social primates that live in groups led by a dominant male. They share significant genetic proximity with humans.",
    "Large feline and one of the biggest terrestrial carnivores. They live in social groups called prides, and females are the primary hunters.",
    "Marine invertebrates with eight tentacles and a highly developed brain. They are known for their intelligence and camouflaging abilities.",
    "Nocturnal birds with excellent vision and hearing, known for their symbolic wisdom. They are efficient predators in hunting.",
    "Birds adapted to life in water, many species live in Antarctica and have impressive swimming abilities. Some species form monogamous pairs.",
    "Characteristic: Marine predators with various species, they come in diverse shapes and sizes. They are crucial for maintaining the balance of marine ecosystems.",
    "Legless carnivorous reptiles, many species are venomous and have amazing camouflaging abilities. They shed their skin periodically."
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), R.layout.animal_cell);

        for (int i = 0; i < animalStringList.length; i++) {
            AnimalData animalData;
            animalData = new AnimalData(iconList[i], animalStringList[i],descriptionList[i]);
            myAdapter.add(animalData);
        }

        ListView animalList = findViewById(R.id.animalList);
        animalList.setAdapter(myAdapter);

        animalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AnimalData animalData;
                animalData= (AnimalData) myAdapter.getItem(position);
                animalAlert(animalData);
            }
        });
    }

    void animalAlert(AnimalData animalData){
        AlertDialog.Builder myAlert;
        myAlert = new AlertDialog.Builder(MainActivity.this);
        myAlert.setTitle(animalData.getTitle());
        myAlert.setMessage(animalData.getDescription());
        myAlert.setIcon(animalData.getIcon());
        myAlert.setCancelable(true);
        myAlert.create();
        myAlert.show();
    }
}


class AnimalData{
    private int icon;
    private String title;
    private String description;

    public AnimalData(int icon, String title, String description) {
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


class ViewAnimal{
    ImageView icon;
    TextView title;
    TextView description;
}

class MyAdapter extends ArrayAdapter{

    public MyAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View myView = convertView;
        ViewAnimal viewAnimal;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.animal_cell,parent,false);

            viewAnimal = new ViewAnimal();
            viewAnimal.icon= (ImageView) myView.findViewById(R.id.icon);
            viewAnimal.title= (TextView) myView.findViewById(R.id.animalTitle);
            viewAnimal.description= (TextView) myView.findViewById(R.id.description);

            myView.setTag(viewAnimal);
        }

        else{
            viewAnimal = (ViewAnimal) myView.getTag();
        }
        AnimalData animalData = (AnimalData) this.getItem(position);

        viewAnimal.icon.setImageResource(animalData.getIcon());
        viewAnimal.title.setText(animalData.getTitle());
        viewAnimal.description.setText(animalData.getDescription());

        return myView;

    }
}
