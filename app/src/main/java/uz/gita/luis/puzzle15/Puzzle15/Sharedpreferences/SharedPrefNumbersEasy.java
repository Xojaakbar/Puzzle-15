package uz.gita.luis.puzzle15.Puzzle15.Sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefNumbersEasy {

    private static SharedPrefNumbersEasy sharedPrefNumbersEasy;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String FILE_NAME = "NUMBERSEASY";


    private SharedPrefNumbersEasy(Context context){
        pref = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    public static SharedPrefNumbersEasy getInstance() {
          return sharedPrefNumbersEasy;
    }

    public static void init(Context context){
        if (sharedPrefNumbersEasy ==null){
            sharedPrefNumbersEasy = new SharedPrefNumbersEasy(context);
        }
    }
    public void putNumbers(int i,String num){
        editor.putString(String.valueOf(i),num).apply();
    }
    public String getNumbers(int i){
     return pref.getString(String.valueOf(i), String.valueOf(i));
    }
    public void putLastStep(String step){
        editor.putString("LastStep",step).apply();
    }
    public String getLastStep(){
      return   pref.getString("LastStep","0");
    }


    public void putLastTime(Long time){
        editor.putLong("LastTime",time).apply();
    }


    public Long getLastTime(){
        return pref.getLong("LastTime",0);
    }
}
