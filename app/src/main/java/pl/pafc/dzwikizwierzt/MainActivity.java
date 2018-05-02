package pl.pafc.dzwikizwierzt;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
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
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    //TODO tutorial gry, jak nalezy grac w gre
    //todo zrobic baze danych SQL, czy konieczne?

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

        final MediaPlayer cowSound  = MediaPlayer.create(this, R.raw.cow_moo);
        final MediaPlayer catSound  = MediaPlayer.create(this, R.raw.cat_meow);
        final MediaPlayer birdSound = MediaPlayer.create(this, R.raw.bird);

        sayingCloud = findViewById(R.id.mooCloudID);
        catMini     = findViewById(R.id.catMiniID);
        catImage    = findViewById(R.id.catID);
        birdMini    = findViewById(R.id.birdMiniMainID);
        birdImage   = findViewById(R.id.birdID);
        soundOff    = findViewById(R.id.soundOffID);
        soundOn     = findViewById(R.id.soundOnID);
        cowImage    = findViewById(R.id.cowID);

        animalText         = findViewById(R.id.AnimalSoundTextViewID);
        cowNumberTextView  = findViewById(R.id.cowNumberTextViewID);
        catNumbersTextView = findViewById(R.id.catNumberTextViewID);
        birdNumberTextView = findViewById(R.id.birdNumberTextView);

        upgradeButton = findViewById(R.id.upgradeID);


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

        //todo krowa ma jesc trawe
        cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
        cowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cowSound.start();
                sayingCloud.setVisibility(VISIBLE);
                animalText.setText("moooo");
                animalText.setVisibility(VISIBLE);
                cow.setCowNumbers(cow.getCowNumbers() + 1);
                cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
                handler.postDelayed(new Runnable() {
                    public void run() {
                        sayingCloud.setVisibility(INVISIBLE);
                        animalText.setVisibility(INVISIBLE);
                    }
                }, 3500);

                if (cow.getCowNumbers() >= 10) {
                    catImage.setVisibility(VISIBLE);
                }
            }
        });

        //todo kot ma wskakakuje na drzewo
        catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cat.isCatlocked()) {
                    createDialog("cat");
                } else {
                    catSound.start();
                    catNumbersTextView.setVisibility(VISIBLE);
                    sayingCloud.setVisibility(VISIBLE);
                    animalText.setText("Meow");
                    animalText.setVisibility(VISIBLE);
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
                    birdImage.setVisibility(VISIBLE);
                }
            }
        });

        birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
        birdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bird.isBirdLocked()) {
                    createDialog("bird");
                } else {
                    birdSound.start();
                    birdNumberTextView.setVisibility(VISIBLE);
                    sayingCloud.setVisibility(VISIBLE);
                    animalText.setText("Cwir, Cwir");
                    animalText.setVisibility(VISIBLE);
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

        soundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundOffOn("off", cowSound,catSound,birdSound);
            }
        });

        soundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundOffOn("on", cowSound,catSound,birdSound);
            }
        });

    }

    public void soundOffOn (String onOff, final MediaPlayer cowSound, final MediaPlayer catSound, final MediaPlayer birdSound ) {
        switch (onOff) {
            case "on" :
                cowSound.setVolume(1,1);
                catSound.setVolume(1,1);
                birdSound.setVolume(1,1);
                soundOn.setVisibility(INVISIBLE);
                soundOn.setEnabled(false);
                soundOff.setVisibility(VISIBLE);
                soundOff.setEnabled(true);
                Toast.makeText(MainActivity.this, "dzwiek włączony", Toast.LENGTH_SHORT).show();
                break;

            case "off" :
                cowSound.setVolume(0,0);
                catSound.setVolume(0,0);
                birdSound.setVolume(0,0);
                soundOff.setVisibility(INVISIBLE);
                soundOff.setEnabled(false);
                soundOn.setVisibility(VISIBLE);
                soundOn.setEnabled(true);
                Toast.makeText(MainActivity.this, "dzwiek wyciszony", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void unlockAnimal (String animalToUnlock) {
        switch (animalToUnlock) {
            case "cat":
                    cat.setCatLocked(false);
                    catImage.setVisibility(VISIBLE);
                    catMini.setVisibility(VISIBLE);
                    catNumbersTextView.setVisibility(VISIBLE);
                    cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
                break;

            case "bird" :
                    bird.setBirdLocked(false);
                    birdImage.setVisibility(VISIBLE);
                    birdMini.setVisibility(VISIBLE);
                    birdNumberTextView.setVisibility(VISIBLE);
                    catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
                break;
        }
    }

    public void createDialog (String animal) {
        switch (animal) {
            case "cat" :
                AlertDialog catDialog = new AlertDialog.Builder(this)
                        .setTitle("Odblokuj Nowego Zwierzaka")
                        .setMessage("Chcesz odblokować teraz?")
                        .setCancelable(false)
                        .setIcon(R.drawable.cat)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unlockAnimal("cat");
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
                break;

            case "bird" :
                AlertDialog birdDialod = new AlertDialog.Builder(this)
                        .setTitle("Odblokuj Nowego Zwierzaka")
                        .setMessage("Chcesz odblokować teraz?")
                        .setCancelable(false)
                        .setIcon(R.drawable.cat)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                unlockAnimal("bird");
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
                break;
        }
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
