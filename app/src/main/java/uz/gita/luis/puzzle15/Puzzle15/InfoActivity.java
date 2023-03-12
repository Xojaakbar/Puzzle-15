package uz.gita.luis.puzzle15.Puzzle15;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.luis.puzzle15.R;

public class InfoActivity extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(view -> {
                finish();
        });
    }
}