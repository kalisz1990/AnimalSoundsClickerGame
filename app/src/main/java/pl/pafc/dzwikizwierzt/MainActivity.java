package pl.pafc.dzwikizwierzt;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.annotation.DrawableRes;
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
    ImageView cow1Small;
    ImageView cow2Small;
    ImageView cow3Small;
    ImageView catImage;
    ImageView cat1Small;
    ImageView cat2Small;
    ImageView birdImage;
    ImageView bird1Small;
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

    @DrawableRes
    int drawableResCow = R.drawable.cow_animation;
    @DrawableRes
    int drawableResCat = R.drawable.cat_anim;
    @DrawableRes
    int drawableResBird = R.drawable.bird;

    Cow cow   = new Cow();
    Cat cat   = new Cat();
    Bird bird = new Bird();

    int tutorialNumbersOfCow = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer cowSound  = MediaPlayer.create(this, R.raw.cow_moo);
        final MediaPlayer catSound  = MediaPlayer.create(this, R.raw.cat_meow);
        final MediaPlayer birdSound = MediaPlayer.create(this, R.raw.bird);

        catImage  = findViewById(R.id.catID);
        catMini   = findViewById(R.id.catMiniID);
        cat1Small = findViewById(R.id.cat1ID);
        cat2Small = findViewById(R.id.cat2ID);

        birdImage  = findViewById(R.id.birdID);
        birdMini   = findViewById(R.id.birdMiniMainID);
        bird1Small = findViewById(R.id.bird1ID);

        cowImage  = findViewById(R.id.cowID);
        cow1Small = findViewById(R.id.cow1ID);
        cow2Small = findViewById(R.id.cow2ID);
        cow3Small = findViewById(R.id.cow3ID);

        soundOff = findViewById(R.id.soundOnID);
        soundOn  = findViewById(R.id.soundOffID);

        introducingCow      = findViewById(R.id.introductionCowID);
        introducingCloud    = findViewById(R.id.introductionCloudID);
        introducingTextView = findViewById(R.id.introductiongTextViewID);

        cowNumberTextView  = findViewById(R.id.cowNumberTextViewID);
        catNumbersTextView = findViewById(R.id.catNumberTextViewID);
        birdNumberTextView = findViewById(R.id.birdNumberTextView);

        upgradeButton = findViewById(R.id.upgradeID);

        tutorial();

        //todo animacja tła

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
                    if (!cat.isCatlocked()) {
                        bundle.putBoolean("firstCat", true);
                    }
                    if (cat.isCatInHatUnlocked()) {
                        bundle.putBoolean("catInHat", true);
                    }
                    if (bird.isBirdUnlocked()) {
                        bundle.putBoolean("firstBird", true);
                    }
                } catch (NullPointerException npe) {
                    npe.getMessage();
                }

                bundle.putInt("cow", cow.getCowNumbers());
                bundle.putInt("cat", cat.getCatNumbers());
                bundle.putInt("bird", bird.getBirdNumbers());

                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });


        soundOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundOffOn("on", cowSound, catSound, birdSound);
            }
        });

        soundOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundOffOn("off", cowSound, catSound, birdSound);
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

                if (tutorialNumbersOfCow < 2) {
                    if (cow.getCowNumbers() == 1) {
                        tutorialNumbersOfCow = 1;
                        tutorial();
                    }
                    if (cow.getCowNumbers() == 10) {
                        tutorialNumbersOfCow = 2;
                        tutorial();
                    }
                } else {
                    tutorial();
                }
            }
        });

        // KOT
        //todo kot ma wskakakuje na drzewo

        catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));

        animalSmall(cat1Small, catImage, R.drawable.cat_anim);
        animalSmall(cat2Small, catImage, R.drawable.cat_in_hat_anim);

        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catSound.start();
                animalAnimation(catImage, drawableResCat);
                catNumbersTextView.setVisibility(VISIBLE);
                cat.setCatNumbers(cat.getCatNumbers() + 1);
                catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
            }
        });

        // PTAK
        //TODO animacja ptaka

        birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));

        animalSmall(bird1Small, birdImage, R.drawable.bird);

        birdImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birdSound.start();
                birdNumberTextView.setVisibility(VISIBLE);
                bird.setBirdNumbers(bird.getBirdNumbers() + 1);
                birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
            }
        });
    }

    public void tutorial() {

        upgradeButton.setEnabled(false);

        introducingCow.setVisibility(VISIBLE);
        introducingCloud.setVisibility(VISIBLE);
        introducingTextView.setVisibility(VISIBLE);

        switch (tutorialNumbersOfCow) {
            case 0:
                introducingTextView.setText("Hej Przyjacielu !!\nWidzisz te krowę na łące?\n\nKLIKNIJ W NIA");
                break;
            case 1:
                introducingTextView.setText("Świetnie!!\nUzbieraj 10 krów");
                break;
            case 2:
                upgradeButton.setEnabled(true);
                introducingTextView.setText("Doskonale!!\nTeraz kliknij w przycisk\nULEPSZENIE");
                break;
            case 3:
                introducingTextView.setText("Masz teraz nową krowę w okularach.\nZBIERAJ WIĘCEJ!");
                break;
            case 4:
                introducingTextView.setVisibility(INVISIBLE);
                introducingCloud.setVisibility(INVISIBLE);
                introducingCow.setVisibility(INVISIBLE);
                upgradeButton.setEnabled(true);
                break;
        }

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

                    if (animalImage == birdImage) {
                        birdImage.setImageResource(resID);
                        if (resID == R.drawable.bird) {
                            drawableResCat = R.drawable.bird;
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
                    bird.setBirdUnlocked(false);
                    birdImage.setVisibility(VISIBLE);
                    birdMini.setVisibility(VISIBLE);
                    birdNumberTextView.setVisibility(VISIBLE);
                    catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
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
                   cowNumberTextView.setText(Integer.toString(cow.getCowNumbers()));
                   if (bundle.getInt("cowInGlasses") == 1) {
                       cowImage.setImageDrawable(getDrawable(R.drawable.cow_in_glasses_anim));
                       drawableResCow = R.drawable.cow_in_glasses_anim;
                       cow.setCowInGlassesUlkocked(true);
                       cow2Small.setVisibility(VISIBLE);
                   }
                   if (bundle.getInt("tutorialNumbers") == 3) {
                       tutorialNumbersOfCow = 3;
                       introducingTextView.setText("Świetni!!\nMasz nową krowę!\nTAK TRZYMAJ!");
                       tutorialNumbersOfCow ++;
                   }
                   if (bundle.getInt("cowPurple") == 1) {
                       cowImage.setImageDrawable(getDrawable(R.drawable.cow_purple_anim));
                       drawableResCow = R.drawable.cow_purple_anim;
                       cow.setCowPurpleUnlocked(true);
                       cow3Small.setVisibility(VISIBLE);
                   }

                   cat.setCatNumbers(bundle.getInt("cat"));
                   catNumbersTextView.setText(Integer.toString(cat.getCatNumbers()));
                   if (bundle.getInt("firstCat") == 1) {
                       catImage.setImageDrawable(getDrawable(R.drawable.cat_anim));
                       drawableResCat = R.drawable.cat_anim;
                       cat.setCatLocked(false);
                       catImage.setVisibility(VISIBLE);
                       catMini.setVisibility(VISIBLE);
                       cat1Small.setVisibility(VISIBLE);
                   }
                   if (bundle.getInt("catInHat") == 1) {
                       catImage.setImageDrawable(getDrawable(R.drawable.cat_in_hat_anim));
                       drawableResCat = R.drawable.cat_in_hat_anim;
                       cat.setCatInHatUnlocked(true);
                       cat2Small.setVisibility(VISIBLE);
                   }

                   bird.setBirdNumbers(bundle.getInt("bird"));
                   birdNumberTextView.setText(Integer.toString(bird.getBirdNumbers()));
                   if (bundle.getInt("firstBird") == 1) {
                       birdImage.setImageDrawable(getDrawable(R.drawable.bird));
                       drawableResBird = R.drawable.bird;
                       bird.setBirdUnlocked(true);
                       birdImage.setVisibility(VISIBLE);
                       birdMini.setVisibility(VISIBLE);
                       bird1Small.setVisibility(VISIBLE);
                   }

               }
               catch (NullPointerException npe) {
                   npe.getMessage();
               }
            }
        }
    }

}
