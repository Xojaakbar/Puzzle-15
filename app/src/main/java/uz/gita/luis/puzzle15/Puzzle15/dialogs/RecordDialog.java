package uz.gita.luis.puzzle15.Puzzle15.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import uz.gita.luis.puzzle15.R;


public class RecordDialog extends AlertDialog {
    private OnPositiveClickListener onPositiveClickListener;
    private ImageView imageOfPlace;
    private TextView whichPlace;
    private TextView okay;
    private TextView steps;

    public RecordDialog(@NonNull Context context) {
        super(context);
    }

    public RecordDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected RecordDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_dialog);
        imageOfPlace = findViewById(R.id.imageOfPlace);
        whichPlace = findViewById(R.id.which_place);
        okay = findViewById(R.id.okay);
        okay.setOnClickListener(view -> {
            if (hasListener()){
            onPositiveClickListener.onclick();
            dismiss();}});
        steps = findViewById(R.id.steps_of_record);
    }

    public void setWhichPlace(String place){
        whichPlace.setText(place);
    }

    public void setSteps(String steps_text) {
        steps.setText(steps_text);
    }

    public void setImageOfPlace(int place) {
        switch (place){
            case 1:
            imageOfPlace.setImageResource(R.drawable.cup);
                break;
            case 2:
                imageOfPlace.setImageResource(R.drawable.second_place);
                break;
            case 3:
            imageOfPlace.setImageResource(R.drawable.img_5);
                break;
        }
    }

    public boolean hasListener(){
        return onPositiveClickListener != null;
    }


    public void setOnPositiveClickListener(OnPositiveClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }
}
