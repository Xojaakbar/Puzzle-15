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

import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefMedium;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedPrefNumbersMedium;
import uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences.SharedSound;
import uz.gita.luis.puzzle15.Puzzle15.dialogs.RecordDialog;
import uz.gita.luis.puzzle15.R;

public class MediumActivity extends AppCompatActivity {
    private SharedPrefNumbersMedium sharedPrefNumbersMedium;
    private SharedPrefMedium sharedPrefMedium;
    private SharedSound sharedSound;
    private MediaPlayer gta;
    private MediaPlayer sound;
    private MediaPlayer pst;
    private Chronometer time;
    private TextView textScore;
    private Button[][] items;
    private List<Integer> numbers;
    private Coordinate emptySpace;
    private int score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);
        loadView();
        loadData();
        dataToView();
    }

    private void loadView() {
        sharedSound = SharedSound.getInstance();
        gta = MediaPlayer.create(this,R.raw.sound10gta);
        sharedPrefNumbersMedium = SharedPrefNumbersMedium.getInstance();
        textScore = findViewById(R.id.text_score_medium);
        sharedPrefMedium = SharedPrefMedium.getInstance();
        time = findViewById(R.id.time_medium);
        if (sharedPrefNumbersMedium.getLastTime() == 0) {
            time.setBase(SystemClock.elapsedRealtime());
        } else {
            time.setBase(sharedPrefNumbersMedium.getLastTime());
        }

        findViewById(R.id.btn_finish_medium).setOnClickListener(v -> finish());

        findViewById(R.id.btn_restart_medium).setOnClickListener(v -> restart());
        final ViewGroup group = findViewById(R.id.container_medium);
        final int count = group.getChildCount();
        items = new Button[4][4];
        for (int i = 0; i < count; i++) {
            final View view = group.getChildAt(i);
            final Button button = (Button) view;
            final int y = i / 4;
            final int x = i % 4;
            button.setOnClickListener(v -> onItemClick(button, x, y));
            items[y][x] = button;
        }
        emptySpace = new Coordinate(3, 3);
    }

    private void loadData() {
        numbers = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
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

        emptySpace.x = 3;
        emptySpace.y = 3;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final int index = 4 * i + j;
                if (index < 15) {
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

                if (sharedPrefMedium.getFirstSteps() != 0){
                    if (steps < sharedPrefMedium.getFirstSteps()){

                        sharedPrefMedium.putThirdSteps(sharedPrefMedium.getSecondSteps());
                        sharedPrefMedium.putSecondSteps(sharedPrefMedium.getFirstSteps());
                        sharedPrefMedium.putFirstSteps(steps);
                        record(String.valueOf(steps),1,"Best score");
                        return;
                    }
                }

                else { sharedPrefMedium.putFirstSteps(steps);
                    record(String.valueOf(steps),1,"Best score");
                    return;}

                if (sharedPrefMedium.getSecondSteps() != 0){
                    if ( steps < sharedPrefMedium.getSecondSteps() ){
                        if (sharedPrefMedium.getThirdSteps()!=0)
                        sharedPrefMedium.putThirdSteps(sharedPrefMedium.getSecondSteps());

                        sharedPrefMedium.putSecondSteps(steps);
                        record(String.valueOf(steps),2,"Second");
                        return;
                    }
                }

                else { sharedPrefMedium.putSecondSteps(steps);
                    record(String.valueOf(steps),2,"Second");
                    return;}

                if (sharedPrefMedium.getThirdSteps() != 0){
                    if (steps < sharedPrefMedium.getThirdSteps()){
                        sharedPrefMedium.putThirdSteps(steps);
                        record(String.valueOf(steps),3,"Third");
                        return;}
                }
                else { sharedPrefMedium.putThirdSteps(steps);
                    record(String.valueOf(steps),3,"Third");
                    return; }
                restart();
            }
        }
        else {
            if (sharedSound.getSoundOn()){
                pst = MediaPlayer.create(this,R.raw.sound13pst);
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

    private boolean isWin() {
        if (emptySpace.x != 3 || emptySpace.y != 3) return false;
        for (int i = 0; i < 15; i++) {
            final int y = i / 4;
            final int x = i % 4;
            final String text = items[y][x].getText().toString();
            if (!text.equals(String.valueOf(i + 1))) return false;
        }
        return true;
    }

    boolean isSolvable() {
        int[] puzzle15 = new int[15];

        for (int i = 0; i < numbers.size(); i++) {
            puzzle15[i] = numbers.get(i);
        }

        int countInversions = 0;
        for (int i = 0; i < 15; i++) {
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


    @Override
    protected void onResume() {
        super.onResume();
        getAll();
    }
    private void putAll() {
        if (sharedSound.getSoundOn()){
        gta.stop();
        }
        sharedPrefNumbersMedium.putLastTime(SystemClock.elapsedRealtime() - time.getBase());
        sharedPrefNumbersMedium.putLastStep(String.valueOf(score));
        time.stop();
        for (int i = 0; i < 16; i++) {
            final int y = i / 4;
            final int x = i % 4;
            final String text = items[y][x].getText().toString();
            sharedPrefNumbersMedium.putNumbers(i, text);
        }
    }

    private void getAll() {
        if ( sharedPrefNumbersMedium.getNumbers(0).equals("0") ||
             sharedPrefNumbersMedium.getNumbers(0).equals("1") &&
             sharedPrefNumbersMedium.getNumbers(1).equals("2")&&
             sharedPrefNumbersMedium.getNumbers(2).equals("3")&&
             sharedPrefNumbersMedium.getNumbers(3).equals("4")&&
             sharedPrefNumbersMedium.getNumbers(4).equals("5")&&
             sharedPrefNumbersMedium.getNumbers(5).equals("6")&&
             sharedPrefNumbersMedium.getNumbers(6).equals("7")&&
             sharedPrefNumbersMedium.getNumbers(7).equals("8")&&
             sharedPrefNumbersMedium.getNumbers(8).equals("9")&&
             sharedPrefNumbersMedium.getNumbers(9).equals("10")&&
             sharedPrefNumbersMedium.getNumbers(10).equals("11")&&
             sharedPrefNumbersMedium.getNumbers(11).equals("12")&&
             sharedPrefNumbersMedium.getNumbers(12).equals("13")&&
             sharedPrefNumbersMedium.getNumbers(13).equals("14")&&
             sharedPrefNumbersMedium.getNumbers(14).equals("15")) {
            restart();
        } else {
            score = Integer.parseInt(sharedPrefNumbersMedium.getLastStep());
            textScore.setText(String.valueOf(score));
            time.setBase(SystemClock.elapsedRealtime() - sharedPrefNumbersMedium.getLastTime());
            time.start();
            for (int i = 0; i < 16; i++) {
                final int y = i / 4;
                final int x = i % 4;
                String text = sharedPrefNumbersMedium.getNumbers(i);      // items[y][x].getText().toString();
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
}
