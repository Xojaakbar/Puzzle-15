package uz.gita.luis.puzzle15.Puzzle15;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefHard;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefMedium;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefeasy;
import uz.gita.luis.puzzle15.R;

public class BestScoreActivity extends AppCompatActivity {
    private SharedPrefMedium sharedPrefMedium;
    private SharedPrefeasy sharedPrefeasy;
    private SharedPrefHard sharedPrefHard;
    private LinearLayout clear;
    private ImageView back;

    private TextView firstTextEasy;
    private TextView secondTextEasy;
    private TextView thirdTextEasy;

    private TextView firstTextMedium;
    private TextView secondTextMedium;
    private TextView thirdTextMedium;

    private TextView firstTextHard;
    private TextView secondTextHard;
    private TextView thirdTextHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_score);
        loadViews();
        setTexts();
    }


    private void loadViews() {
        back = findViewById(R.id.button_nazad);

        firstTextEasy = findViewById(R.id.first_easy);
        secondTextEasy = findViewById(R.id.second_easy);
        thirdTextEasy = findViewById(R.id.third_easy);

        firstTextMedium = findViewById(R.id.first_medium);
        secondTextMedium = findViewById(R.id.second_medium);
        thirdTextMedium = findViewById(R.id.third_medium);

        firstTextHard = findViewById(R.id.first_hard);
        secondTextHard = findViewById(R.id.second_hard);
        thirdTextHard = findViewById(R.id.third_hard);

        sharedPrefeasy = SharedPrefeasy.getInstance();
        sharedPrefMedium = SharedPrefMedium.getInstance();
        sharedPrefHard = SharedPrefHard.getInstance();

        clear = findViewById(R.id.clear);
        back.setOnClickListener(view -> {
            finish();
        });
        clear.setOnClickListener(view -> {
            sharedPrefeasy.clear();
            sharedPrefMedium.clear();
            sharedPrefHard.clear();
            Toast.makeText(this,"tozalandi",Toast.LENGTH_SHORT).show();
            setTexts();});
    }

    private void setTexts() {
        int firstEasy = sharedPrefeasy.getFirstSteps();
        int secondEasy = sharedPrefeasy.getSecondSteps();
        int thirdEasy = sharedPrefeasy.getThirdSteps();
        firstTextEasy.setText(String.valueOf(firstEasy));
        secondTextEasy.setText(String.valueOf(secondEasy));
        thirdTextEasy.setText(String.valueOf(thirdEasy));

        int firstMedium = sharedPrefMedium.getFirstSteps();
        int secondMedium = sharedPrefMedium.getSecondSteps();
        int thirdMedium = sharedPrefMedium.getThirdSteps();
        firstTextMedium.setText(String.valueOf(firstMedium));
        secondTextMedium.setText(String.valueOf(secondMedium));
        thirdTextMedium.setText(String.valueOf(thirdMedium));

        int firstHard = sharedPrefHard.getFirstSteps();
        int secondHard = sharedPrefHard.getSecondSteps();
        int thirdHard = sharedPrefHard.getThirdSteps();
        firstTextHard.setText(String.valueOf(firstHard));
        secondTextHard.setText(String.valueOf(secondHard));
        thirdTextHard.setText(String.valueOf(thirdHard));
    }
}