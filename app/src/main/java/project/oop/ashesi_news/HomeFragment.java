package project.oop.ashesi_news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private CardStackView cardStackView;
    private CardStackAdapter adapter;
    private DatabaseReference databaseReference;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("cards");

        // Initialize CardStackView and Adapter
        cardStackView = view.findViewById(R.id.card_stack_view);
        CardStackLayoutManager layoutManager = new CardStackLayoutManager(requireContext());
        cardStackView.setLayoutManager(layoutManager);

        adapter = new CardStackAdapter(new ArrayList<>());
        cardStackView.setAdapter(adapter);

        // Fetch cards from Firebase
        fetchCardsFromFirebase();
    }

    private void fetchCardsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Card> cards = new ArrayList<>();
                for (DataSnapshot cardSnapshot : snapshot.getChildren()) {
                    String title = cardSnapshot.child("title").getValue(String.class);
                    String description = cardSnapshot.child("description").getValue(String.class);
                    String imageUrl = cardSnapshot.child("imageUrl").getValue(String.class);

                    Log.d("HomeFragment", "Fetched Card: Title = " + title + ", Description = " + description + ", Image URL = " + imageUrl);
                    // Create a card model and add it to the list
                    cards.add(new Card(title, description, imageUrl));
                }

                // Pass the updated list of cards to the adapter
                adapter.updateData(cards);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("HomeFragment", "Database error: " + error.getMessage());
            }
        });
    }
}
