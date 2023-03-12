package uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefeasy {

        private static SharedPrefeasy sharedPrefeasy;
        private SharedPreferences pref;
        private SharedPreferences.Editor editor;
        private final String s = "EASY";

        private SharedPrefeasy(Context context) {
            pref = context.getSharedPreferences(s, Context.MODE_PRIVATE);
            editor = pref.edit();
        }
        public static SharedPrefeasy getInstance(){
            return sharedPrefeasy;
        }
        public static void init(Context context) {
            if (sharedPrefeasy == null) {
                sharedPrefeasy = new SharedPrefeasy(context);
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
