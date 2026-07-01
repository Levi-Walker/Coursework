package edu.andrews.cptr252.leviwalker.veggietalesviews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] veggietalesStringList = {"Bob", "Larry", "Junior", "Laura","Madame Blueberry", "Petunia", "Mr. Lunt", "Archibald"};
    int[] iconList = {R.drawable.bob,R.drawable.larry,R.drawable.junior,R.drawable.laura,R.drawable.madameblueberry,R.drawable.petunia,R.drawable.mrlunt,R.drawable.archibald};

    String[] descriptionList = {
    "Since 1993, Bob is the bright red, round face of the VeggieTales franchise, alongside his best friend and show co-host, Larry. Although he is prone to frustration from his friends sometimes, Bob never gives up on his goal to teach kids about God. Bob first appeared in a 1992 screen test for VeggieTales called Take 38, where he attempted to do an inspirational Martin Luther King Jr. type speech to promote the idea of the new series to the viewer. Despite some silly interruptions from Larry the Cucumber, Bob was able to successfully get through his speech. In 1993, Bob officially became the host of VeggieTales alongside his best friend, Larry. Since then, Bob has gone on to star in nearly every episode of the series. Bob often takes on the role of storyteller and narrator, but has played a variety of roles and characters over the years. Some of his most memorable roles include Rack in Rack, Shack and Benny, The Skipper in Larry’s Lagoon, Dr. Watson in Sheerluck Holmes and the Golden Ruler, ThingamaBob in The League of Incredible Vegetables, Tomato Sawyer in Tomato Sawyer and Huckleberry Larry’s Big River Rescue, and Cavis Appythart in both The Star of Christmas and An Easter Carol.",
    "Larry made his first on-screen debut in a 1992 VeggieTales screen test aptly called Mr. Cuke’s Screen Test, where he pops out from a ceramic bowl on the kitchen countertop and hops towards the camera and smiles at the viewer. Later that year, an additional screen test was made entitled, VeggieTales Promo: Take 38. Here, Larry is searching for his blue, plastic wind-up lobster, unintentionally interrupting Bob the Tomato throughout the short as he gives a speech to do a show pitch of VeggieTales for potential investors. Eventually, in 1993, Larry made his big public debut in VeggieTales, co-hosting alongside Bob. Since then, Larry has gone on to become an integral part of the VeggieTales family, gaining fame and notoriety for his many performances in character roles and personas such as Daniel of Daniel and the Lion’s Den, Little Joe in The Ballad of Little Joe, Moe in Moe and the Big Exit, the strapping adventurer Minnesota Cuke, the first mate of the Pirates Who Don’t Do Anything, a member of the band Boyz in the Sink, and of course, his superhero alter-ego, Larry-Boy. Above all, Larry is probably most famous for starring and singing in his own segment on the series: Silly Songs with Larry (much to the chagrin of Archibald Asparagus). With this segment, Larry has gone on to sing about all sorts of silly and bizarre things, such as a water buffalo, a hairbrush, lips, a yodeling veterinarian, SUVs, pants, a soap opera starring manatees, and many, many more. According to Larry’s official bio — if he weren’t an actor, he would’ve been the world champion at Candy Land or be clerking in the Supreme Court.",
    "Junior made his first onscreen debut in VeggieTales’ first show Where’s God When I’m S-Scared?. He stars in the show’s first segment, Tales From the Crisper, where he stays up late to watch a scary movie about the fearsome Frankencelery. Junior ultimately gets scared and has trouble falling asleep, only to be visited by Bob the Tomato and Larry the Cucumber as they teach him that God is bigger than all his fears and has nothing to be afraid of. Since then, Junior has gone on to appear in many VeggieTales shows, often portraying himself among a vast list of titular character roles. Such roles include the biblical David in Dave and the Giant Pickle, the benevolent Lyle in Lyle the Kindly Viking, Riccochet in The League of Incredible Vegetables, and the wooden toy Pistachio in the show of the same name. Junior lives in a suburban home with his mother and father, Lisa and Mike Asparagus, respectively. He is very close to his parents, especially his father. Junior loves spending time with them and is always willing to listen and learn from them. Junior is best friends with Laura Carrot and good friends with Annie Onion and Percy Pea. Later series depict him making even more friends, including Callie Flower and Gary Garlic. Junior is also very close with Bob, Larry and the rest of the VeggieTales cast. At one point, Junior had a negative relationship with a bully named Gourdon. Junior eventually stood up for himself and even tried to befriend the bully, but Gourdon gave up, realizing he couldn’t bother Junior anymore and started leaving him alone. According to his official bio — if he weren’t an actor, Junior would’ve been an aerospace major at Veggie Valley Grade School.",
    "Laura is a friendly little carrot girl. She lives in the city of Bumblyburg with her parents, Mom and Dad Carrot, as well as her two younger brothers, Lenny and Baby Lou. Besides being best friends with Junior Asparagus, she is very close with other Veggie kids such as Percy Pea, Annie Onion and Callie Flower. She is also good friends with Bob and Larry, Laura first appeared alongside her father and brother Lenny in the VeggieTales episode Are You My Neighbor? as one of the inhabitants of Jibber-De-Lot in the Story of Flibber-o-loo segment. It was’t until the following episode, Rack, Shack and Benny, that she was properly introduced. It is here where she established her high-spirited personality. Since then, Laura has gone on to appear in multiple VeggieTales episodes and series as a lead character and many supporting roles. Some of her more notable roles include Miriam in Babysitter in DeNile, Princess Poppyseed in Princess and the Popstar, and portraying herself in many LarryBoy episodes. In VeggieTales in the House and in the City, Laura took on a superhero alter-ego named Night Pony. She later joined and helped form the League of Veggie Heroes alongside other superheroes inside the house. According to her official bio, if she weren’t an actor, she’d be scootering around town on her scooter.",
    "Madame Blueberry is a French blueberry who likes having anything she wants, though very friendly to everyone. She is also the mayor of Bumblyburg in the main Larry-Boy series. In her debut episode, she wanted more things after seeing all the nice stuff all her friends got, and sobbed about it. Later, some salesmen showed up and told her about the new store called Stuff-Mart that has everything she wants. “I’ve been so foolish. For so long I have had so much. A roof over my head, plenty of food, good friends. But all I wanted was more, more! No more! There’s a new Madame Blueberry in town and she’s going to be thankful for what she has. — Madame Blueberry while giving up on something more she wanted",
    "Petunia first appeared in 2005 in the VeggieTales episode Duke and the Great Pie War where she played her breakout role as Princess Petunia. Since then, she has gone on to become a main player in the VeggieTales cast. She is the president of the VeggieTales Fan Club, as mentioned in The VeggieTales Show. She is also the executive producer of the VeggieTales podcast, Very Veggie Silly Stories.",
    "Mr. Lunt is a decorative Latin gourd, who grew up in New Jersey. He often appears as Mr. Nezzer’s assistant and they debuted together in Rack, Shack, and Benny. He was once again alongside Mr. Nezzer in Esther… The Girl Who Became Queen as Haman, King Xerxes’ right-hand man. Mr. Lunt also frequently appears as part of The Pirates Who Don’t Do Anything, and even once got his own Love Song (His Cheeseburger) and starred in two others (The Bellybutton Song and A Mess Down in Egypt). Mr. Lunt is the former secondary antagonist of Jonah: A VeggieTales Movie. Mr. Lunt was called “Sedgewick” in The Pirates Who Don’t Do Anything: A VeggieTales Movie. A notable feature of Mr. Lunt has invisible eyes. He often wears a beige Safari-like hat and uses the brim for expression. Mr. Lunt has a dark brown moustache. He also has a gold tooth.",
    "Archibald first appeared in VeggieTales Promo: Take 38. He at this point was an unnamed snooty British asparagus who criticized the idea of VeggieTales being a children’s show. He wasn’t named Archibald until Where’s God When I’m S-Scared?, where he bashes Larry singing silly songs and is the actor of King Darius in “Daniel and the Lions’ Den”. Since Larry-Boy! and the Fib from Outer Space!, Archibald is also known for playing Larry-Boy’s butler, Alfred.",
    };
    String[] buttonText = {
            "Bob is a tomato",
            "Larry is a cucumber",
            "Junior is an asparagus",
            "Laura is a carrot",
            "Madame Blueberry is... rich",
            "Petunia is not a flower",
            "Mr. Lunt likes cheezeburgers",
            "Archibald is a robot"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), R.layout.veggietales_cell);

        for (int i = 0; i < veggietalesStringList.length; i++) {
            edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData veggietalesData;
            veggietalesData = new edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData(iconList[i], veggietalesStringList[i],descriptionList[i],buttonText[i]);
            myAdapter.add(veggietalesData);
        }

        ListView veggietalesList = findViewById(R.id.veggietalesList);
        veggietalesList.setAdapter(myAdapter);

        veggietalesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData veggietalesData;
                veggietalesData= (edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData) myAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this,SecondScreen.class);
                intent.putExtra("title", veggietalesData.getTitle());
                intent.putExtra("description", veggietalesData.getDescription());
                intent.putExtra("icon",veggietalesData.getIcon());
                intent.putExtra("button", veggietalesData.getButtonText());
                startActivity(intent);

            }
        });
    }

    public void clicked(View view){
        Intent intent = new Intent(MainActivity.this,SecondScreen.class);
        intent.putExtra("title","My Zoo");
        startActivity(intent);
    }

    void veggietalesAlert(edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData veggietalesData){
        AlertDialog.Builder myAlert;
        myAlert = new AlertDialog.Builder(MainActivity.this);
        myAlert.setTitle(veggietalesData.getTitle());
        myAlert.setMessage(veggietalesData.getDescription());
        myAlert.setIcon(veggietalesData.getIcon());
        myAlert.setCancelable(true);
        myAlert.create();
        myAlert.show();
    }
}


class VeggietalesData {
    private int icon;
    private String title;
    private String description;
    private String buttonText;

    public VeggietalesData(int icon, String title, String description, String buttonText) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.buttonText = buttonText;
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

    public String getButtonText(){
        return buttonText;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


class ViewVeggietales {
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
        edu.andrews.cptr252.leviwalker.veggietalesviews.ViewVeggietales viewAnimal;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)
                    this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.veggietales_cell,parent,false);

            viewAnimal = new edu.andrews.cptr252.leviwalker.veggietalesviews.ViewVeggietales();
            viewAnimal.icon= (ImageView) myView.findViewById(R.id.icon);
            viewAnimal.title= (TextView) myView.findViewById(R.id.veggietalesTitle);

            myView.setTag(viewAnimal);
        }

        else{
            viewAnimal = (edu.andrews.cptr252.leviwalker.veggietalesviews.ViewVeggietales) myView.getTag();
        }
        edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData veggietalesData = (edu.andrews.cptr252.leviwalker.veggietalesviews.VeggietalesData) this.getItem(position);

        viewAnimal.icon.setImageResource(veggietalesData.getIcon());
        viewAnimal.title.setText(veggietalesData.getTitle());

        return myView;

    }
}
