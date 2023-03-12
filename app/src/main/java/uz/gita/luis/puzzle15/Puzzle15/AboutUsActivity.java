package uz.gita.luis.puzzle15.Puzzle15;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import uz.gita.luis.puzzle15.R;
import uz.gita.luis.puzzle15.databinding.ActivityAboutUsBinding;

public class AboutUsActivity extends AppCompatActivity {
    ActivityAboutUsBinding binding;
    private ImageView telegram;
    private String link = "http://t.me/@luis_101/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.telegramLuis.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://t.me/luis_101");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}