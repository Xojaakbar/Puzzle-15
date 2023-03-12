package uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefMedium.init(this);
        SharedPrefeasy.init(this);
        SharedPrefHard.init(this);
        SharedSound.init(this);
        SharedPrefNumbersEasy.init(this);
        SharedPrefnumbersHard.init(this);
        SharedPrefNumbersMedium.init(this);
    }
}
