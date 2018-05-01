package pl.pafc.dzwikizwierzt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpgradeActivity extends AppCompatActivity {

    ImageView cowInGlasses;

    TextView cowNumbersTextView2;
    TextView catNumbersTextView2;
    TextView birdNumberTextView2;

    Cow  cow  = new Cow();
    Cat  cat  = new Cat();
    Bird bird = new Bird();

    Button cowInGlassesButton;
    
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

        final Bundle bundle = getIntent().getExtras();

        cowNumbers  = bundle.getInt("cow");
        catNumbers  = bundle.getInt("cat");
        birdNumbers = bundle.getInt("bird");

        if (bundle.getBoolean("cowInGlasses")) {
            cow.setCowInGlassesUlkocked(true);
            cowInGlassesButton.setEnabled(false);
            cowInGlassesButton.setText("Kupione");
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

    }
    public void unlockCowInGlasses() {

        if (cowNumbers >= 10) {
            if (!cow.isCowInGlassesUnlkocked()) {
                cowNumbers -= 10;
                cowInGlassesButton.setEnabled(false);
                cowInGlassesButton.setText("Kupione");
                cowNumbersTextView2.setText(Integer.toString(cowNumbers));
                cow.setCowNumbers(cowNumbers);
                cow.setCowInGlassesUlkocked(true);
            }
        }
        else {
            Toast.makeText(UpgradeActivity.this, "Za mało krów :(\n wyklikaj wiecej", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();

        bundle.putInt("cow", cowNumbers);
        if (cow.isCowInGlassesUnlkocked()) {
            bundle.putInt("cowInGlasses", 1);
        }
        bundle.putInt("cat", catNumbers);
        bundle.putInt("bird", birdNumbers);
        intent.putExtras(bundle);
        setResult(RESULT_OK,intent);

        super.onBackPressed();  // optional depending on your needs
    }

}
