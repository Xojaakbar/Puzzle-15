package uz.gita.luis.puzzle15.Puzzle15;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedSound;
import uz.gita.luis.puzzle15.R;


public class MenuActivity extends AppCompatActivity {

    private SharedSound sharedSound;
    private ImageView aboutUs;
    private ImageView info;
    private ImageView bestScore;
    private ImageView soundImage;
    private TextView medium;
    private TextView easy;
    private TextView hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        loadView();
        onClickList();
    }

    private void onClickList() {
        info.setOnClickListener(about -> {
            Intent intent = new Intent(this,InfoActivity.class);
            startActivity(intent);
        });

        bestScore.setOnClickListener(view -> {
            Intent intent = new Intent(this,BestScoreActivity.class);
            startActivity(intent);
        });
        easy.setOnClickListener(view -> {
            Intent intent = new Intent(this,EasyActivity.class);
            startActivity(intent);
        });
        hard.setOnClickListener(view -> {
            Intent intent = new Intent(this,HardActivity.class);
            startActivity(intent);
        });

        medium.setOnClickListener(view -> {
            Intent intent = new Intent(this, MediumActivity.class);
            startActivity(intent);
        });
        aboutUs.setOnClickListener(view -> {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        });
        soundImage.setOnClickListener(view -> {
            if (sharedSound.getSoundOn()) {
                soundImage.setImageResource(R.drawable.angry2);
                sharedSound.putSoundOn(false);
            } else {
                soundImage.setImageResource(R.drawable.imageofsound);
                sharedSound.putSoundOn(true);
            }
        });
    }

    private void loadView() {
        sharedSound = SharedSound.getInstance();
        info = findViewById(R.id.info);
        bestScore = findViewById(R.id.best_scores);
        easy = findViewById(R.id.easy);
        hard = findViewById(R.id.hard);
        medium = findViewById(R.id.medium);
        soundImage = findViewById(R.id.sound);
        if (sharedSound.getSoundOn()) {
                soundImage.setImageResource(R.drawable.imageofsound);
            } else {
                soundImage.setImageResource(R.drawable.angry2);
            }
        aboutUs = findViewById(R.id.aboutus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedSound.getSoundOn()) {
            soundImage.setImageResource(R.drawable.imageofsound);
        } else {
            soundImage.setImageResource(R.drawable.angry2);
        }
    }
}