package pl.pafc.dzwikizwierzt;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {

    //TODO tutorial gry, jak nalezy grac w gre
    //todo zrobic baze danych SQL

    ImageView cowImage;
    ImageView catImage;
    ImageView birdImage;
    ImageView catMini;
    ImageView birdMini;
    ImageView sayingCloud;
    ImageView soundOff;
    ImageView soundOn;

    TextView animalText;
    TextView cowNumberTextView;
    TextView catNumbersTextView;
    TextView birdNumberTextView;

    Button upgradeButton;

    Cow  cow  = new Cow();
    Cat  cat  = new Cat();
    Bird bird = new Bird();

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        upgradeButton = (Button) findViewById(R.id.upgradeID);
        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
                Bundle bundle = new Bundle();

                try {
                    if (cow.isCowInGlassesUnlkocked()) {
                        bundle.putBoolean("cowInGlasses", true);
                    }
                }
                catch (NullPointerException npe) {
                    npe.getMessage();
                }

                bundle.putInt("cow", cow.getCowNumbers());
                bundle.putInt("cat", cat.getCatNumbers());
                bundle.putInt("bird", bird.getBirdNumbers());

                intent.putExtras(bundle);
                startActivityForResult(intent,1);
            }
        });

        final MediaPlayer cowSound = MediaPlayer.create(this, R.raw.cow_moo);

        sayingCloud = (ImageView) findViewById(R.id.mooCloudID);
        animalText  = (TextView) findViewById(R.id.AnimalSoundTextViewID);

        cowNumberTextView = (TextView) findViewById(R.id.cowNumberTextViewID);
        cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
        //todo krowa ma jesc trawe

        cowImage = (ImageView) findViewById(R.id.cowID);
        cowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cowSound.start();
                cow.getCowNumbers();
                sayingCloud.setVisibility(v.VISIBLE);
                animalText.setText("Mooo");
                animalText.setVisibility(v.VISIBLE);
                cow.setCowNumbers(cow.getCowNumbers() + 1);
                cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
                handler.postDelayed(new Runnable() {
                    public void run() {
                        sayingCloud.setVisibility(INVISIBLE);
                        animalText.setVisibility(INVISIBLE);
                    }
                }, 3500);

                if (cow.getCowNumbers() >= 10) {
                    catImage.setVisibility(v.VISIBLE);
                }
            }
        });

        final MediaPlayer catSound = MediaPlayer.create(this, R.raw.cat_meow);
        //todo kot ma wskakakuje na drzewo
        //todo jak dam ze pozniej odblokowac to zeby obrazek kota/ptaka po klikniecu mowil ze nie jest odblokowany

        catNumbersTextView = (TextView) findViewById(R.id.catNumberTextViewID);
        catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
        catMini = (ImageView) findViewById(R.id.catMiniID);
        catImage = (ImageView) findViewById(R.id.catID);
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cat.isCatlocked()) {
                    createDialogCat("Nowe zwierze ", "Odblokuj kota. Koszt 10 krów");
                } else {
                    catSound.start();
                    catNumbersTextView.setVisibility(v.VISIBLE);
                    sayingCloud.setVisibility(v.VISIBLE);
                    animalText.setText("Meow");
                    animalText.setVisibility(v.VISIBLE);
                    cat.setCatNumbers(cat.getCatNumbers() + 1);
                    catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            sayingCloud.setVisibility(INVISIBLE);
                            animalText.setVisibility(INVISIBLE);
                        }
                    }, 3000);
                }
                if (cat.getCatNumbers() >= 10) {
                    birdImage.setVisibility(v.VISIBLE);
                }
            }
        });

        final MediaPlayer birdSound = MediaPlayer.create(this, R.raw.bird);
        birdNumberTextView = (TextView) findViewById(R.id.birdNumberTextView);
        birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
        birdMini  = (ImageView) findViewById(R.id.birdMiniMainID);
        birdImage = (ImageView) findViewById(R.id.birdID);
        birdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bird.isBirdLocked()) {
                    createDialogBird("Nowe zwierze ", "Odblokuj ptaka. Koszt 10 kotów");
                } else {
                    birdSound.start();
                    birdNumberTextView.setVisibility(v.VISIBLE);
                    sayingCloud.setVisibility(v.VISIBLE);
                    animalText.setText("Cwir, Cwir");
                    animalText.setVisibility(v.VISIBLE);
                    bird.setBirdNumbers(bird.getBirdNumbers() + 1);
                    birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));

                    handler.postDelayed(new Runnable() {
                        public void run() {
                            sayingCloud.setVisibility(INVISIBLE);
                            animalText.setVisibility(INVISIBLE);
                        }
                    }, 3600);
                }
            }
        });

        soundOff = (ImageView) findViewById(R.id.soundOffID);
        soundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cowSound.setVolume(0,0);
                catSound.setVolume(0,0);
                birdSound.setVolume(0,0);
                soundOff.setVisibility(v.INVISIBLE);
                soundOff.setEnabled(false);
                soundOn.setVisibility(View.VISIBLE);
                soundOn.setEnabled(true);
                Toast.makeText(MainActivity.this, "dzwiek wyciszony", Toast.LENGTH_SHORT).show();
            }
        });

        soundOn = (ImageView) findViewById(R.id.soundOnID);
        soundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cowSound.setVolume(1,1);
                catSound.setVolume(1,1);
                birdSound.setVolume(1,1);
                soundOn.setVisibility(INVISIBLE);
                soundOn.setEnabled(false);
                soundOff.setVisibility(View.VISIBLE);
                soundOff.setEnabled(true);
                Toast.makeText(MainActivity.this, "dzwiek włączony", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void unlockCat() {
        //todo zrobic metode ogolna

        if (cow.getCowNumbers() >= 10) {
            cat.setCatLocked(false);
            catImage.setVisibility(View.VISIBLE);
            catMini.setVisibility(View.VISIBLE);
            catNumbersTextView.setVisibility(View.VISIBLE);
            cow.setCowNumbers(cow.getCowNumbers()-10);
            cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
        }
    }

    public void unlockBird() {

        if (cat.getCatNumbers() >= 10) {
            bird.setBirdLocked(false);
            birdImage.setVisibility(View.VISIBLE);
            birdMini.setVisibility(View.VISIBLE);
            birdNumberTextView.setVisibility(View.VISIBLE);
            cat.setCatNumbers(cat.getCatNumbers()-10);
            catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
        }
    }
    public void createDialogCat (String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.drawable.cat)
                .setPositiveButton("Odblokuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unlockCat();
                    }
                })
                .setNeutralButton("Później", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        catImage.setEnabled(true);
                        cat.setCatLocked(true);
                    }
                })
                .show();
    }

    public void createDialogBird (String title, String message) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.drawable.cat)
                .setPositiveButton("Odblokuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        unlockBird();
                        upgradeButton.setEnabled(true);
                    }
                })
                .setNeutralButton("Później", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        birdImage.setEnabled(true);
                        bird.setBirdLocked(true);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle bundle;
                bundle = data.getExtras();
                cow.setCowNumbers(bundle.getInt("cow"));
                cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
               try {
                   if (bundle.getInt("cowInGlasses") == 1) {
                       cowImage.setImageDrawable(getDrawable(R.drawable.cow_in_glasses));
                       cow.setCowInGlassesUlkocked(true);
                   }
               }
               catch (NullPointerException npe) {
                   npe.getMessage();
               }
            }
        }
    }

}
