package uz.gita.luis.puzzle15.Puzzle15;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefHard;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefnumbersHard;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedSound;
import uz.gita.luis.puzzle15.Puzzle15.dialogs.RecordDialog;
import uz.gita.luis.puzzle15.R;

public class HardActivity extends AppCompatActivity {
    private SharedSound sharedSound;
    private SharedPrefHard sharedPrefHard;
    private SharedPrefnumbersHard sharedPrefnumbersHard;
    private MediaPlayer gta;
    private MediaPlayer pst;
    private MediaPlayer sound;
    private Chronometer time;
    private TextView textScore;
    private Button[][] items;
    private List<Integer> numbers;
    private Coordinate emptySpace;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
        loadView();
        loadData();
        dataToView();
    }

    private void loadView() {
        sharedSound = SharedSound.getInstance();
        gta = MediaPlayer.create(this, R.raw.sound10gta);
        sharedPrefHard = SharedPrefHard.getInstance();
        textScore = findViewById(R.id.text_score_hard);
        sharedPrefnumbersHard = SharedPrefnumbersHard.getInstance();
        time = findViewById(R.id.time_hard);
        if (sharedPrefnumbersHard.getLastTime() == 0) {
            time.setBase(SystemClock.elapsedRealtime());
        } else {
            time.setBase(sharedPrefnumbersHard.getLastTime());
        }

        findViewById(R.id.btn_finish_hard).setOnClickListener(v -> finish());

        findViewById(R.id.btn_restart_hard).setOnClickListener(v -> restart());
        final ViewGroup group = findViewById(R.id.container_hard);
        final int count = group.getChildCount();

        items = new Button[5][5];
        for (int i = 0; i < count; i++) {
            final View view = group.getChildAt(i);
            final Button button = (Button) view;
            final int y = i / 5;
            final int x = i % 5;
            button.setOnClickListener(v -> onItemClick(button, x, y));
            items[y][x] = button;
        }
        emptySpace = new Coordinate(4, 4);
    }

    private void loadData() {
        numbers = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            numbers.add(i);
        }
    }

    private void dataToView() {
        getAll();
    }

    private void restart() {
        time.setBase(SystemClock.elapsedRealtime());
        time.start();
        score = 0;
        textScore.setText(String.valueOf(score));
        do {
            Collections.shuffle(numbers);
        }
        while (!isSolvable());
        items[emptySpace.y][emptySpace.x].setBackgroundResource(R.drawable.back_button);
        items[emptySpace.y][emptySpace.x].setVisibility(View.VISIBLE);

        emptySpace.x = 4;
        emptySpace.y = 4;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                final int index = 5 * i + j;
                if (index < 24) {
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

            if (sharedSound.getSoundOn()) {
                sound = MediaPlayer.create(this, R.raw.soun2);
                sound.start();}
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
                if (sharedSound.getSoundOn()) {
                    gta.start();
                }
                int steps = Integer.parseInt(textScore.getText().toString());

                if (sharedPrefHard.getFirstSteps() != 0) {
                    if (steps < sharedPrefHard.getFirstSteps()) {

                        sharedPrefHard.putThirdSteps(sharedPrefHard.getSecondSteps());
                        sharedPrefHard.putSecondSteps(sharedPrefHard.getFirstSteps());
                        sharedPrefHard.putFirstSteps(steps);
                        record(String.valueOf(steps),1,"Best score");
                        return;
                    }
                } else {
                    sharedPrefHard.putFirstSteps(steps);
                    record(String.valueOf(steps),1,"Best score");
                    return;
                }

                if (sharedPrefHard.getSecondSteps() != 0) {
                    if (steps < sharedPrefHard.getSecondSteps()) {
                        if (sharedPrefHard.getThirdSteps()!=0){
                        sharedPrefHard.putThirdSteps(sharedPrefHard.getSecondSteps());}
                        sharedPrefHard.putSecondSteps(steps);
                        record(String.valueOf(steps),2,"Second");
                        return;
                    }
                } else {
                    sharedPrefHard.putSecondSteps(steps);
                    record(String.valueOf(steps),2,"Second");
                    return;
                }

                if (sharedPrefHard.getThirdSteps() != 0) {
                    if (steps < sharedPrefHard.getThirdSteps()) {
                        sharedPrefHard.putThirdSteps(steps);
                        record(String.valueOf(steps),3,"Third");
                        return;
                    }
                } else {
                    sharedPrefHard.putThirdSteps(steps);
                    record(String.valueOf(steps),3,"Third");
                    return;
                }
                restart();
            }
        } else {
            if (sharedSound.getSoundOn()) {
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
            time.setBase(SystemClock.elapsedRealtime());
            time.start();
            restart();
        });
        time.stop();
    }

 /*   private void closeAndGo() {
        FancyToast.makeText(this,"You have updated your record",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
        Intent intent = new Intent(this, BestScoreActivity.class);
        startActivity(intent);
        finish();
    }*/

    private boolean isWin() {
        if (emptySpace.x != 4 || emptySpace.y != 4) return false;
        for (int i = 0; i < 24; i++) {
            final int y = i / 5;
            final int x = i % 5;
            final String text = items[y][x].getText().toString();
            if (!text.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    boolean isSolvable() {
        int[] puzzle15 = new int[25];

        for (int i = 0; i < numbers.size(); i++) {
            puzzle15[i] = numbers.get(i);
        }

        int countInversions = 0;
        for (int i = 0; i < 25; i++) {
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
        putAll();}

    private void putAll() {
        if (sharedSound.getSoundOn()) {
        gta.stop();
        }
        sharedPrefnumbersHard.putLastTime(SystemClock.elapsedRealtime() - time.getBase());
        sharedPrefnumbersHard.putLastStep(String.valueOf(score));
        time.stop();
        for (int i = 0; i < 25; i++) {
            final int y = i / 5;
            final int x = i % 5;
            final String text = items[y][x].getText().toString();
            sharedPrefnumbersHard.putNumbers(i, text);
        }
    }

    private void getAll() {

        if (sharedPrefnumbersHard.getNumbers(0).equals("0") ||
                sharedPrefnumbersHard.getNumbers(0).equals("1") &&
                        sharedPrefnumbersHard.getNumbers(1).equals("2") &&
                        sharedPrefnumbersHard.getNumbers(2).equals("3") &&
                        sharedPrefnumbersHard.getNumbers(3).equals("4") &&
                        sharedPrefnumbersHard.getNumbers(4).equals("5") &&
                        sharedPrefnumbersHard.getNumbers(5).equals("6") &&
                        sharedPrefnumbersHard.getNumbers(6).equals("7") &&
                        sharedPrefnumbersHard.getNumbers(7).equals("8") &&
                        sharedPrefnumbersHard.getNumbers(8).equals("9") ||
                        sharedPrefnumbersHard.getNumbers(9).equals("10") &&
                        sharedPrefnumbersHard.getNumbers(10).equals("11") &&
                        sharedPrefnumbersHard.getNumbers(11).equals("12") &&
                        sharedPrefnumbersHard.getNumbers(12).equals("13") &&
                        sharedPrefnumbersHard.getNumbers(13).equals("14") &&
                        sharedPrefnumbersHard.getNumbers(14).equals("15") &&
                        sharedPrefnumbersHard.getNumbers(15).equals("16") &&
                        sharedPrefnumbersHard.getNumbers(16).equals("17") &&
                        sharedPrefnumbersHard.getNumbers(17).equals("18") &&
                        sharedPrefnumbersHard.getNumbers(18).equals("19") &&
                        sharedPrefnumbersHard.getNumbers(19).equals("20") &&
                        sharedPrefnumbersHard.getNumbers(20).equals("21") &&
                        sharedPrefnumbersHard.getNumbers(21).equals("22") &&
                        sharedPrefnumbersHard.getNumbers(22).equals("23") &&
                        sharedPrefnumbersHard.getNumbers(23).equals("24")) {
            restart();
        } else {
            score = Integer.parseInt(sharedPrefnumbersHard.getLastStep());
            textScore.setText(String.valueOf(score));
            time.setBase(SystemClock.elapsedRealtime() - sharedPrefnumbersHard.getLastTime());
            time.start();

            for (int i = 0; i < 25; i++) {
                final int y = i / 5;
                final int x = i % 5;
                String text = sharedPrefnumbersHard.getNumbers(i);      // items[y][x].getText().toString();
                if (text == "") {
                    emptySpace.x = x;
                    emptySpace.y = y;
                    items[y][x].setVisibility(View.INVISIBLE);
                    items[y][x].setText(text);
                } else {
//                    int number = Integer.parseInt(text)+1;
                    items[y][x].setText(String.valueOf(text));
                    items[y][x].setVisibility(View.VISIBLE);
                }
            }
        }
    }
}

