package project.oop.ashesi_news;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchEditText;
    private RecyclerView searchRecyclerView;
    private CardGridAdapter adapter; // Custom adapter for grid cards
    private DatabaseReference databaseReference; // Firebase database reference
    private List<Card> allCards = new ArrayList<>(); // Full list of cards
    private List<Card> filteredCards = new ArrayList<>(); // Filtered search results

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        searchEditText = view.findViewById(R.id.search_edit_text);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);

        // Set up RecyclerView with GridLayoutManager
        searchRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // 2 columns
        adapter = new CardGridAdapter(filteredCards); // Initially empty filtered list
        searchRecyclerView.setAdapter(adapter);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("cards");

        // Fetch all cards from Firebase (for initial explore page)
        fetchAllCardsFromFirebase();

        // Listen for user input in the search bar
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch(s.toString()); // Filter cards as user types
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void fetchAllCardsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allCards.clear();
                for (DataSnapshot cardSnapshot : snapshot.getChildren()) {
                    String title = cardSnapshot.child("title").getValue(String.class);
                    String description = cardSnapshot.child("description").getValue(String.class);
                    String imageUrl = cardSnapshot.child("imageUrl").getValue(String.class);

                    allCards.add(new Card(title, description, imageUrl));
                }
                filteredCards.clear();
                filteredCards.addAll(allCards); // Show all cards initially
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void performSearch(String query) {
        filteredCards.clear();
        if (query.isEmpty()) {
            // Show all cards if search is empty
            filteredCards.addAll(allCards);
        } else {
            // Filter cards based on title or description
            for (Card card : allCards) {
                if (card.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        card.getDescription().toLowerCase().contains(query.toLowerCase())) {
                    filteredCards.add(card);
                }
            }
        }
        adapter.notifyDataSetChanged(); // Update RecyclerView
    }
}
