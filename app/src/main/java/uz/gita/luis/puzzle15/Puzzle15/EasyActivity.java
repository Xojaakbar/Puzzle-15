package uz.gita.luis.puzzle15.Puzzle15;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefNumbersEasy;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefeasy;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedSound;
import uz.gita.luis.puzzle15.Puzzle15.dialogs.RecordDialog;
import uz.gita.luis.puzzle15.R;

public class EasyActivity extends AppCompatActivity {
    private SharedSound sharedSound;
    private SharedPrefeasy sharedPrefeasy;
    private SharedPrefNumbersEasy sharedPrefNumbersEasy;
    private MediaPlayer gta;
    private MediaPlayer pst;
    private MediaPlayer sound;
    private boolean bool = true;
    private Chronometer time;
    private TextView textScore;
    private Button[][] items;
    private List<Integer> numbers;
    private Coordinate emptySpace;
    private int score;
    private boolean isSoundOn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        loadView();
        loadData();
        dataToView();
    }

    private void loadView() {
        sharedSound = SharedSound.getInstance();
        gta = MediaPlayer.create(this, R.raw.sound10gta);
        sharedPrefeasy = SharedPrefeasy.getInstance();
        textScore = findViewById(R.id.text_score_easy);
        sharedPrefNumbersEasy = SharedPrefNumbersEasy.getInstance();
        time = findViewById(R.id.time_easy);
        if (sharedPrefNumbersEasy.getLastTime() == 0) {
            time.setBase(SystemClock.elapsedRealtime());
        } else {
            time.setBase(sharedPrefNumbersEasy.getLastTime());
        }

        findViewById(R.id.btn_finish_easy).setOnClickListener(v -> finish());
        findViewById(R.id.btn_restart_easy).setOnClickListener(v -> restart());
        final ViewGroup group = findViewById(R.id.container_easy);
        final int count = group.getChildCount();

        items = new Button[3][3];
        for (int i = 0; i < count; i++) {
            final View view = group.getChildAt(i);
            final Button button = (Button) view;
            final int y = i / 3;
            final int x = i % 3;
            button.setOnClickListener(v -> onItemClick(button, x, y));
            items[y][x] = button;
        }
        emptySpace = new Coordinate(2, 2);
    }

    private void loadData() {
        numbers = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            numbers.add(i);
        }
    }

    private void dataToView() {
        getAll();
    }

    private void restart() {
        time.stop();
        time.setBase(SystemClock.elapsedRealtime());
        score = 0;
        textScore.setText(String.valueOf(score));
        do {
            Collections.shuffle(numbers);
        }
        while (!isSolvable());
        items[emptySpace.y][emptySpace.x].setVisibility(View.VISIBLE);

        emptySpace.x = 2;
        emptySpace.y = 2;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int index = 3 * i + j;
                if (index < 8) {
                    int number = numbers.get(index);
                    items[i][j].setText(String.valueOf(number));
                    items[i][j].setVisibility(View.VISIBLE);
                } else {
                    items[i][j].setText("");
                    items[i][j].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void onItemClick(Button button, int x, int y) {
        final int dx = Math.abs(emptySpace.x - x);
        final int dy = Math.abs(emptySpace.y - y);
        if (dx + dy == 1) {
            textScore.setText(String.valueOf(++score));

            if (bool) {
                bool = false;
            }
            if (isSoundOn) {
                sound = MediaPlayer.create(this, R.raw.soun2);
                sound.start();
            }
            final String text = button.getText().toString();
            button.setText("");
            button.setVisibility(View.INVISIBLE);

            final Button temp = items[emptySpace.y][emptySpace.x];
            temp.setText(text);
            temp.setVisibility(View.VISIBLE);

            emptySpace.x = x;
            emptySpace.y = y;

            if (isWin()) {
                FancyToast.makeText(this,"You win!!!",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                time.setBase(SystemClock.elapsedRealtime());
                if (isSoundOn) {
                    gta.start();
                }
                int steps = Integer.parseInt(textScore.getText().toString());

                if (sharedPrefeasy.getFirstSteps() != 0) {
                    if (steps < sharedPrefeasy.getFirstSteps()) {

                        sharedPrefeasy.putThirdSteps(sharedPrefeasy.getSecondSteps());
                        sharedPrefeasy.putSecondSteps(sharedPrefeasy.getFirstSteps());
                        sharedPrefeasy.putFirstSteps(steps);
                        record(String.valueOf(steps),1,"Best score");
                        return;
                    }
                } else {
                    sharedPrefeasy.putFirstSteps(steps);
                    record(String.valueOf(steps),1,"Best score");
                    return;
                }

                if (sharedPrefeasy.getSecondSteps() != 0) {
                    if (steps < sharedPrefeasy.getSecondSteps()) {
                        if (sharedPrefeasy.getThirdSteps()!=0){
                        sharedPrefeasy.putThirdSteps(sharedPrefeasy.getSecondSteps());}
                        sharedPrefeasy.putSecondSteps(steps);
                        record(String.valueOf(steps),2,"Second");
                        return;
                    }
                } else {
                    sharedPrefeasy.putSecondSteps(steps);
                    record(String.valueOf(steps),2,"Second");
                    return;
                }

                if (sharedPrefeasy.getThirdSteps() != 0) {
                    if (steps < sharedPrefeasy.getThirdSteps()) {
                        sharedPrefeasy.putThirdSteps(steps);
                        record(String.valueOf(steps),3,"Third");
                        return;
                    }
                } else {
                    sharedPrefeasy.putThirdSteps(steps);
                    record(String.valueOf(steps),3,"Third");
                    return;
                }
                restart();
            }
        } else {
            if (isSoundOn) {
                pst = MediaPlayer.create(this, R.raw.sound13pst);
                pst.start();
            }
        }
    }

    private void record(String steps, int placeForImage, String whichPlace){
        time.stop();
        RecordDialog recordDialog = new RecordDialog(this, R.style.recordDialog);
        recordDialog.setOnPositiveClickListener(() -> {
                restart();
        });
        recordDialog.create();
        recordDialog.setSteps(steps+" steps");
        recordDialog.setImageOfPlace(placeForImage);
        recordDialog.setWhichPlace(whichPlace);
        recordDialog.show();
        recordDialog.setOnCancelListener(dialogInterface -> {
                restart();
        });
        time.stop();
    }

    private boolean isWin() {
        if (emptySpace.x != 2 || emptySpace.y != 2) return false;
        for (int i = 0; i < 8; i++) {
            final int y = i / 3;
            final int x = i % 3;
            final String text = items[y][x].getText().toString();
            if (!text.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    boolean isSolvable() {
        int[] puzzle15 = new int[9];

        for (int i = 0; i < numbers.size(); i++) {
            puzzle15[i] = numbers.get(i);
        }

        int countInversions = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < i; j++) {
                if (puzzle15[j] > puzzle15[i])
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        putAll();
    }


    private void putAll() {
        if (sharedSound.getSoundOn()) {
            gta.stop();
        }
            sharedPrefNumbersEasy.putLastTime(SystemClock.elapsedRealtime() - time.getBase());
        sharedPrefNumbersEasy.putLastStep(String.valueOf(score));
        time.stop();
        for (int i = 0; i < 9; i++) {
            final int y = i / 3;
            final int x = i % 3;
            final String text = items[y][x].getText().toString();
            sharedPrefNumbersEasy.putNumbers(i, text);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAll();
    }


    private void getAll() {
        isSoundOn = sharedSound.getSoundOn();
        if (sharedPrefNumbersEasy.getNumbers(0).equals("0") ||
                sharedPrefNumbersEasy.getNumbers(0).equals("1") &&
                        sharedPrefNumbersEasy.getNumbers(1).equals("2") &&
                        sharedPrefNumbersEasy.getNumbers(2).equals("3") &&
                        sharedPrefNumbersEasy.getNumbers(3).equals("4") &&
                        sharedPrefNumbersEasy.getNumbers(4).equals("5") &&
                        sharedPrefNumbersEasy.getNumbers(5).equals("6") &&
                        sharedPrefNumbersEasy.getNumbers(6).equals("7") &&
                        sharedPrefNumbersEasy.getNumbers(7).equals("8") ) {
            restart();
        } else {
            score = Integer.parseInt(sharedPrefNumbersEasy.getLastStep());
            textScore.setText(String.valueOf(score));
            time.setBase(SystemClock.elapsedRealtime() - sharedPrefNumbersEasy.getLastTime());
            time.start();

            for (int i = 0; i < 9; i++) {
                final int y = i / 3;
                final int x = i % 3;
                String text = sharedPrefNumbersEasy.getNumbers(i);      // items[y][x].getText().toString();
                if (text == "") {
                    emptySpace.x = x;
                    emptySpace.y = y;
                    items[y][x].setVisibility(View.INVISIBLE);
                    items[y][x].setText(text);
                } else {
                    items[y][x].setText(String.valueOf(text));
                    items[y][x].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


