package project.oop.ashesi_news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullScreenCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_card);

        // Get references to views
        TextView titleTextView = findViewById(R.id.full_screen_card_title);
        TextView descriptionTextView = findViewById(R.id.full_screen_card_description);
        ImageView imageView = findViewById(R.id.full_screen_card_image);

        // Extract data from intent
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String imageUrl = intent.getStringExtra("imageUrl");

        // Bind data to views
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        Glide.with(this).load(imageUrl).into(imageView);
    }
}
