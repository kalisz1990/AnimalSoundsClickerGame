package pl.pafc.dzwikizwierzt;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
    ImageView cow1Small;
    ImageView cow2Small;
    ImageView cow3Small;
    ImageView catImage;
    ImageView cat1Small;
    ImageView cat2Small;
    ImageView birdImage;
    ImageView catMini;
    ImageView birdMini;
    ImageView soundOff;
    ImageView soundOn;
    ImageView introducingCow;
    ImageView introducingCloud;

    TextView cowNumberTextView;
    TextView catNumbersTextView;
    TextView birdNumberTextView;
    TextView introducingTextView;

    Button upgradeButton;

    @DrawableRes int drawableResCow = R.drawable.cow_animation;
    @DrawableRes int drawableResCat = R.drawable.cat_anim;

    Cow  cow  = new Cow();
    Cat  cat  = new Cat();
    Bird bird = new Bird();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer cowSound  = MediaPlayer.create(this, R.raw.cow_moo);
        final MediaPlayer catSound  = MediaPlayer.create(this, R.raw.cat_meow);
        final MediaPlayer birdSound = MediaPlayer.create(this, R.raw.bird);

        catImage = findViewById(R.id.catID);
        catMini = findViewById(R.id.catMiniID);
        cat1Small = findViewById(R.id.cat1ID);
        cat2Small = findViewById(R.id.cat2ID);
        birdMini = findViewById(R.id.birdMiniMainID);
        birdImage = findViewById(R.id.birdID);
        soundOff = findViewById(R.id.soundOffID);
        soundOn = findViewById(R.id.soundOnID);
        cowImage = findViewById(R.id.cowID);
        cow1Small = findViewById(R.id.cow1ID);
        cow2Small = findViewById(R.id.cow2ID);
        cow3Small = findViewById(R.id.cow3ID);
        introducingCow = findViewById(R.id.introducingCowID);
        introducingCloud = findViewById(R.id.introducingCloudID);
        introducingTextView = findViewById(R.id.introductiongTextViewID);

        cowNumberTextView  = findViewById(R.id.cowNumberTextViewID);
        catNumbersTextView = findViewById(R.id.catNumberTextViewID);
        birdNumberTextView = findViewById(R.id.birdNumberTextView);

        upgradeButton = findViewById(R.id.upgradeID);

//        tutorial();

        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpgradeActivity.class);
                Bundle bundle = new Bundle();

                try {
                    if (cow.isCowInGlassesUnlocked()) {
                        bundle.putBoolean("cowInGlasses", true);
                    }
                    if (cow.isCowPurpleUnlocked()) {
                        bundle.putBoolean("cowPurple", true);
                    }
                    if (cat.isCatInHatUnlocked()) {
                        bundle.putBoolean("catInHat", true);
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

        // PTAK

        birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
        birdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bird.isBirdLocked()) {
                    createDialog("bird");
                } else {
                    birdSound.start();
                    birdNumberTextView.setVisibility(VISIBLE);
                    bird.setBirdNumbers(bird.getBirdNumbers() + 1);
                    birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
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

        // KROWA
        //todo krowa ma jesc trawe

        cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));

        animalSmall(cow1Small, cowImage, R.drawable.cow_animation);
        animalSmall(cow2Small, cowImage, R.drawable.cow_in_glasses_anim);
        animalSmall(cow3Small, cowImage, R.drawable.cow_purple_anim);

        cowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cowSound.start();
                animalAnimation(cowImage, drawableResCow);
                cow.setCowNumbers(cow.getCowNumbers() + 1);
                cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));

                if (cow.getCowNumbers() >= 10) {
                    catImage.setVisibility(VISIBLE);
                }
            }
        });

        // KOT
        //todo kot ma wskakakuje na drzewo

        animalSmall(cat1Small, catImage, R.drawable.cat_anim);
        animalSmall(cat2Small, catImage, R.drawable.cat_in_hat_anim);

        catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cat.isCatlocked()) {
                    createDialog("cat");
                } else {
                    catSound.start();
                    animalAnimation(catImage, drawableResCat);
                    catNumbersTextView.setVisibility(VISIBLE);
                    cat.setCatNumbers(cat.getCatNumbers() + 1);
                    catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
                }
                if (cat.getCatNumbers() >= 10) {
                    birdImage.setVisibility(VISIBLE);
                }
            }
        });

    }

    public void tutorial () {

        cowImage.setEnabled(false);
        final Animation animation = new AlphaAnimation(1f,0f);

        introducingTextView.setText("Hej!\nJestem Pan Krowa");
        introducingTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animation.setDuration(1500);
                introducingTextView.setText("Powiem Tobie jak grać");
            }
        });

        Animation tutorialAnim = new AlphaAnimation(1f,0f);
        tutorialAnim.setDuration(2000);

    }

    public void animalAnimation(ImageView animal, @DrawableRes final int resID) {

        animal.setImageResource(resID);
        AnimationDrawable animationDrawable = (AnimationDrawable) animal.getDrawable();
        animationDrawable.start();
    }

    public void animalSmall(final ImageView smallAnimal, final ImageView animalImage, @DrawableRes final int resID) {

        smallAnimal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animalImage == cowImage) {
                        cowImage.setImageResource(resID);
                        if (resID == R.drawable.cow_animation) {
                            drawableResCow = R.drawable.cow_animation;
                        }
                        if (resID == R.drawable.cow_in_glasses_anim) {
                            drawableResCow = R.drawable.cow_in_glasses_anim;
                        }
                        if (resID == R.drawable.cow_purple_anim) {
                            drawableResCow = R.drawable.cow_purple_anim;
                        }
                    }

                    if (animalImage == catImage) {
                        catImage.setImageResource(resID);
                        if (resID == R.drawable.cat_anim) {
                            drawableResCat = R.drawable.cat_anim;
                        }
                        if (resID == R.drawable.cat_in_hat_anim) {
                            drawableResCat = R.drawable.cat_in_hat_anim;
                        }
                    }
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
                        .setIcon(R.drawable.bird)
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
               try {
                   cow.setCowNumbers(bundle.getInt("cow"));
                   cat.setCatNumbers(bundle.getInt("cat"));
                   bird.setBirdNumbers(bundle.getInt("bird"));
                   cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
                   if (bundle.getInt("cowInGlasses") == 1) {
                       cowImage.setImageDrawable(getDrawable(R.drawable.cow_in_glasses_anim));
                       drawableResCow = R.drawable.cow_in_glasses_anim;
                       cow.setCowInGlassesUlkocked(true);
                       cow2Small.setVisibility(VISIBLE);
                   }

                   if (bundle.getInt("cowPurple") == 1) {
                       cowImage.setImageDrawable(getDrawable(R.drawable.cow_purple_anim));
                       drawableResCow = R.drawable.cow_purple_anim;
                       cow.setCowPurpleUnlocked(true);
                       cow3Small.setVisibility(VISIBLE);
                   }
                   if (bundle.getInt("catInHat") == 1) {
                       catImage.setImageDrawable(getDrawable(R.drawable.cat_in_hat_anim));
                       drawableResCat = R.drawable.cat_in_hat_anim;
                       cat.setCatInHatUnlocked(true);
                       cat2Small.setVisibility(VISIBLE);
                   }
               }
               catch (NullPointerException npe) {
                   npe.getMessage();
               }
            }
        }
    }

}
