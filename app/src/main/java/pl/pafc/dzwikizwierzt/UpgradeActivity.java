package pl.pafc.dzwikizwierzt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {

    TextView cowNumbersTextView2;
    TextView catNumbersTextView2;
    TextView birdNumberTextView2;

    Cow  cow  = new Cow();
    Cat  cat  = new Cat();
    Bird bird = new Bird();

    Button cowInGlassesButton;
    Button cowPurpleButton;
    Button catInHatButton;

    int cowNumbers;
    int catNumbers;
    int birdNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        cowNumbersTextView2 = findViewById(R.id.cowNumberTextViewID2);
        catNumbersTextView2 = findViewById(R.id.catNumberTextViewID2);
        birdNumberTextView2 = findViewById(R.id.birdNumberTextViewID2);
        cowInGlassesButton  = findViewById(R.id.cow_in_glasses_buttonID);
        cowPurpleButton  = findViewById(R.id.cowPurpleButtonID);
        catInHatButton = findViewById(R.id.cat_in_hatButtonID);

        final Bundle bundle = getIntent().getExtras();

        cowNumbers  = bundle.getInt("cow");
        catNumbers  = bundle.getInt("cat");
        birdNumbers = bundle.getInt("bird");

        if (bundle.getBoolean("cowInGlasses")) {
            cow.setCowInGlassesUlkocked(true);
            cowInGlassesButton.setEnabled(false);
            cowInGlassesButton.setText("Kupione");
        }
        if (bundle.getBoolean("cowPurple")){
            cow.setCowPurpleUnlocked(true);
            cowPurpleButton.setEnabled(false);
            cowPurpleButton.setText("kupione");
        }
        if (bundle.getBoolean("catInHat")) {
            cat.setCatInHatUnlocked(true);
            catInHatButton.setEnabled(false);
            catInHatButton.setText("kupione");
        }

        cowNumbersTextView2.setText(Integer.toString(cowNumbers));
        catNumbersTextView2.setText(Integer.toString(catNumbers));
        birdNumberTextView2.setText(Integer.toString(birdNumbers));

        cowInGlassesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unlockCowInGlasses();
                cow.setCowNumbers(cowNumbers);
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
    }

    public void unlockCowPurple () {
        if (cowNumbers >= 5) {
            if (!cow.isCowPurpleUnlocked()) {
                cowNumbers -= 5;
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

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putInt("cow", cowNumbers);
        if (cow.isCowInGlassesUnlocked()) {
            bundle.putInt("cowInGlasses", 1);
        }
        if (cow.isCowPurpleUnlocked()) {
            bundle.putInt("cowPurple", 1);
        }

        bundle.putInt("cat", catNumbers);
        if (cat.isCatInHatUnlocked()) {
            bundle.putInt("catInHat", 1);
        }

        bundle.putInt("bird", birdNumbers);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        super.onBackPressed();  // optional depending on your needs
    }

}
