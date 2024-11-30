package project.oop.ashesi_news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;
import java.util.List;

import project.oop.ashesi_news.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
//
//    // David's Part incase it fucks up-------------------------------------------
//    private CardStackView cardStackView;
//    private CardStackAdapter adapter;
//    private DatabaseReference databaseReference;
//
//    // End of David's Part for variables --------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.search) {
                replaceFragment(new SearchFragment());
            } else if (item.getItemId() == R.id.bookmark) {
                replaceFragment(new bookmarkFragment());
            }
            return true;
        });
//
//        // Start of card stuff ------------------------------------------------------------
//
//        // Initialize Firebase Database
//        databaseReference = FirebaseDatabase.getInstance().getReference("cards");
//        // Initialize CardStackView and Adapter
//        cardStackView = findViewById(R.id.card_stack_view);
//        CardStackLayoutManager layoutManager = new CardStackLayoutManager(this);
//        cardStackView.setLayoutManager(layoutManager);
//
//        adapter = new CardStackAdapter(new ArrayList<>());
//        cardStackView.setAdapter(adapter);
//
//        // Fetch cards from Firebase
//        fetchCardsFromFirebase();
//
//        // Add new card button listener
//        findViewById(R.id.add_card_button).setOnClickListener(view -> addNewCard());
//
//        // ---------------------------------------------------------------------------------
//    }
//
//    // Another fucken part ---------------------------------------------------------
//    private void fetchCardsFromFirebase() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                List<Card> cards = new ArrayList<>();
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    Card card = child.getValue(Card.class);
//                    cards.add(card);
//                }
//                adapter.updateData(cards);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.e("MainActivity", "Database error: " + error.getMessage());
//            }
//        });
//    }
//
//    private void addNewCard() {
//        String title = "New Card Title";
//        String description = "New Card Description";
//        Card newCard = new Card(title, description);
//
//        // Push new card to Firebase
//        databaseReference.push().setValue(newCard).addOnSuccessListener(aVoid ->
//                Toast.makeText(this, "Card added!", Toast.LENGTH_SHORT).show()
//        ).addOnFailureListener(e ->
//                Toast.makeText(this, "Failed to add card!", Toast.LENGTH_SHORT).show()
//        );
    }
    // End of that shit --------------------------------------------------------

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}