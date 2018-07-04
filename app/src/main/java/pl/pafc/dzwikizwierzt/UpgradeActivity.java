package pl.pafc.dzwikizwierzt;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class UpgradeActivity extends AppCompatActivity {

    TextView cowNumbersTextView2;
    TextView catNumbersTextView2;
    TextView birdNumberTextView2;

    TextView introducingTextView2;
    ImageView introducingCow2;
    ImageView introducingCloud2;

    Cow  cow  = new Cow();
    Cat  cat  = new Cat();
    Bird bird = new Bird();

    Button cowInGlassesButton;
    Button cowPurpleButton;
    Button firstCatButton;
    Button catInHatButton;
    Button firstBirdButton;

    int cowNumbers;
    int catNumbers;
    int birdNumbers;
    int tutorialNumbers2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        cowNumbersTextView2 = findViewById(R.id.cowNumberTextViewID2);
        catNumbersTextView2 = findViewById(R.id.catNumberTextViewID2);
        birdNumberTextView2 = findViewById(R.id.birdNumberTextViewID2);
        cowInGlassesButton  = findViewById(R.id.cow_in_glasses_buttonID);
        cowPurpleButton     = findViewById(R.id.cowPurpleButtonID);
        firstCatButton      = findViewById(R.id.firstCatButtonID);
        catInHatButton      = findViewById(R.id.cat_in_hatButtonID);
        firstBirdButton     = findViewById(R.id.firstBirdButtonID);
        introducingTextView2 = findViewById(R.id.introductionTextViewID2);
        introducingCow2     = findViewById(R.id.introductionCowID2);
        introducingCloud2   = findViewById(R.id.introducingCloudID2);

        tutorial2();


        final Bundle bundle = getIntent().getExtras();

        cowNumbers  = bundle.getInt("cow");
        catNumbers  = bundle.getInt("cat");
        birdNumbers = bundle.getInt("bird");


        if (bundle.getBoolean("cowInGlasses")) {
            cow.setCowInGlassesUlkocked(true);
            cowInGlassesButton.setEnabled(false);
            cowInGlassesButton.setText("Kupione");
            introducingCloud2.setVisibility(INVISIBLE);
            introducingCow2.setVisibility(INVISIBLE);
            introducingTextView2.setVisibility(INVISIBLE);
            tutorialNumbers2 = 2;
            tutorial2();
        }
        if (bundle.getBoolean("cowPurple")) {
            cow.setCowPurpleUnlocked(true);
            cowPurpleButton.setEnabled(false);
            cowPurpleButton.setText("kupione");
        }
        if (bundle.getBoolean("firstCat")) {
            cat.setCatLocked(false);
            firstCatButton.setEnabled(false);
            firstCatButton.setText("kupione");
        }
        if (bundle.getBoolean("catInHat")) {
            cat.setCatInHatUnlocked(true);
            catInHatButton.setEnabled(false);
            catInHatButton.setText("kupione");
        }
        if (bundle.getBoolean("firstBird")) {
            bird.setBirdUnlocked(true);
            firstBirdButton.setEnabled(false);
            firstBirdButton.setText("kupione");
        }

        cowNumbersTextView2.setText(Integer.toString(cowNumbers));
        catNumbersTextView2.setText(Integer.toString(catNumbers));
        birdNumberTextView2.setText(Integer.toString(birdNumbers));

        cowInGlassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockCowInGlasses();
                cow.setCowNumbers(cowNumbers);
                tutorial2();
            }
        });
        cowPurpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockCowPurple();
                cow.setCowNumbers(cowNumbers);
            }
        });

        catInHatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockCatInHat();
                cat.setCatNumbers(catNumbers);
            }
        });
        firstCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("cat");
            }
        });
        firstBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog("bird");
            }
        });
    }


    public void tutorial2 () {


        introducingCloud2.setVisibility(VISIBLE);
        introducingCow2.setVisibility(VISIBLE);
        introducingTextView2.setVisibility(VISIBLE);
        cowPurpleButton.setEnabled(false);
        firstCatButton.setEnabled(false);
        catInHatButton.setEnabled(false);
        firstBirdButton.setEnabled(false);

        switch (tutorialNumbers2) {
            case 0:
                introducingTextView2.setText("Teraz możesz kupić nową krowę\n\nKUP TERAZ!");
                break;
            case 1:
                introducingTextView2.setText("Wspaniale!\nWróc teraz na łąke");
                break;
            case 2:
                introducingCloud2.setVisibility(INVISIBLE);
                introducingCow2.setVisibility(INVISIBLE);
                introducingTextView2.setVisibility(INVISIBLE);
                cowPurpleButton.setEnabled(true);
                firstCatButton.setEnabled(true);
                catInHatButton.setEnabled(true);
                firstBirdButton.setEnabled(true);
                break;
        }

    }

    public void unlockFirstCat () {
        if (cowNumbers >= 10) {
            if (cat.isCatlocked()) {
                cowNumbers -= 10;
                firstCatButton.setEnabled(false);
                firstCatButton.setText("Kupione");
                cowNumbersTextView2.setText(Integer.toString(cowNumbers));
                cat.setCatLocked(false);
            }
        }else {
            Toast.makeText(this, "za malo krów:(\nWyklikaj jeszcze kilka", Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockFirstBird () {
        if (catNumbers >= 20) {
            if (!bird.isBirdUnlocked()) {
                catNumbers -= 20;
                firstBirdButton.setEnabled(false);
                firstBirdButton.setText("Kupione");
                catNumbersTextView2.setText(Integer.toString(catNumbers));
                bird.setBirdUnlocked(true);
            }
        } else {
            Toast.makeText(this, "za malo ptaków:(\nWyklikaj jeszcze kilka", Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockCowPurple () {
        if (cowNumbers >= 15) {
            if (!cow.isCowPurpleUnlocked()) {
                cowNumbers -= 15;
                cowPurpleButton.setEnabled(false);
                cowPurpleButton.setText("Kupione");
                cowNumbersTextView2.setText(Integer.toString(cowNumbers));
                cow.setCowPurpleUnlocked(true);
            }
        }
        else {
            Toast.makeText(UpgradeActivity.this, "Za mało krów :(\n wyklikaj wiecej", Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockCowInGlasses() {

        if (cowNumbers >= 10) {
            if (!cow.isCowInGlassesUnlocked()) {
                cowNumbers -= 10;
                cowInGlassesButton.setEnabled(false);
                cowInGlassesButton.setText("Kupione");
                cowNumbersTextView2.setText(Integer.toString(cowNumbers));
                cow.setCowInGlassesUlkocked(true);
                tutorialNumbers2 = 1;
            }
        } else {
            Toast.makeText(UpgradeActivity.this, "Za mało krów :(\n wyklikaj wiecej", Toast.LENGTH_SHORT).show();
        }
    }

    public void unlockCatInHat () {

        if (catNumbers >=10) {
            if (!cat.isCatInHatUnlocked()) {
                catNumbers -= 10;
                catInHatButton.setEnabled(false);
                catInHatButton.setText("kupione");
                catNumbersTextView2.setText(Integer.toString(catNumbers));
                cat.setCatInHatUnlocked(true);
            }
        } else {
            Toast.makeText(UpgradeActivity.this, "za malo kotów :(\n wyklikaj wiecej", Toast.LENGTH_SHORT).show();
        }
    }

    public void createDialog (String animal) {
        switch (animal) {
            case "cat" :
                AlertDialog catDialog = new AlertDialog.Builder(this)
                        .setTitle("Odblokuj Nowego Zwierzaka")
                        .setMessage("Chcesz odblokować teraz?\nkoszt: 10 krów")
                        .setCancelable(false)
                        .setIcon(R.drawable.cat)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unlockFirstCat();
                            }
                        })
                        .setNeutralButton("Później", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cat.setCatLocked(true);
                            }
                        })
                        .show();
                break;

            case "bird" :
                AlertDialog birdDialod = new AlertDialog.Builder(this)
                        .setTitle("Odblokuj Nowego Zwierzaka")
                        .setMessage("Chcesz odblokować teraz?\nkoszt 20 kotów")
                        .setCancelable(false)
                        .setIcon(R.drawable.bird)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unlockFirstBird();
                            }
                        })
                        .setNeutralButton("Później", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bird.setBirdUnlocked(false);
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putInt("cow", cowNumbers);
        if (cow.isCowInGlassesUnlocked()) {
            bundle.putInt("cowInGlasses", 1);
            bundle.putInt("tutorialNumbers", 3);
        }
        if (cow.isCowPurpleUnlocked()) {
            bundle.putInt("cowPurple", 1);
        }

        bundle.putInt("cat", catNumbers);
        if (!cat.isCatlocked()) {
            bundle.putInt("firstCat", 1);
        }
        if (cat.isCatInHatUnlocked()) {
            bundle.putInt("catInHat", 1);
        }

        bundle.putInt("bird", birdNumbers);
        if (bird.isBirdUnlocked()) {
            bundle.putInt("firstBird", 1);
        }

        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        super.onBackPressed();  // optional depending on your needs
    }


}
