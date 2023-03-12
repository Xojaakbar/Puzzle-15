package uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefMedium {
    private static SharedPrefMedium sharedPrefMedium;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final String s = "Medium";

    private SharedPrefMedium(Context context) {
        pref = context.getSharedPreferences(s, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public static SharedPrefMedium getInstance(){
        return sharedPrefMedium;
    }
    public static void init(Context context) {
        if (sharedPrefMedium == null) {
            sharedPrefMedium = new SharedPrefMedium(context);
        }
    }

    public void clear(){
        editor.clear().apply();
    }

    public void putFirstSteps(int steps){
        editor.putInt("first",steps).apply();
    }

    public int getFirstSteps(){
        return pref.getInt("first",0);
    }

    public void putSecondSteps(int steps){
        editor.putInt("second",steps).apply();
    }

    public int getSecondSteps(){
        return pref.getInt("second",0);
    }

    public void putThirdSteps(int steps){
        editor.putInt("third",steps).apply();
    }

    public int getThirdSteps(){
        return pref.getInt("third",0);
    }
}
