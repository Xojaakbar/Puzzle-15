package uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedSound {
        private static SharedSound sharedSound;
        private SharedPreferences pref;
        private SharedPreferences.Editor editor;
        private final String s = "SOUND";

        private SharedSound(Context context) {
            pref = context.getSharedPreferences(s, Context.MODE_PRIVATE);
            editor = pref.edit();
        }
        public static SharedSound getInstance(){
            return sharedSound;
        }
        public static void init(Context context) {
            if (sharedSound == null) {
                sharedSound = new SharedSound(context);
            }
        }
        public void putSoundOn(boolean soundOn){
            editor.putBoolean("Sound",soundOn).apply();
        }

        public boolean getSoundOn(){
            return pref.getBoolean("Sound",true);
        }
    }
