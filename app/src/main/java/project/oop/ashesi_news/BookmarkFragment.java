package project.oop.ashesi_news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookmarkFragment extends Fragment {

    private BookmarkAdapter bookmarkAdapter;
    private BookmarkViewModel bookmarkViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView for displaying bookmarks
        RecyclerView recyclerView = view.findViewById(R.id.bookmark_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false));

        // Initialize the adapter for the RecyclerView
        bookmarkAdapter = new BookmarkAdapter(new ArrayList<>(),getActivity());
        recyclerView.setAdapter(bookmarkAdapter);

        // Initialize BookmarkViewModel
        bookmarkViewModel = new ViewModelProvider(requireActivity()).get(BookmarkViewModel.class);

        // Observe the list of bookmarked cards
        bookmarkViewModel.getBookmarkedCards().observe(getViewLifecycleOwner(), bookmarks -> {
            // Update the adapter when bookmarks are updated
            bookmarkAdapter.updateData(bookmarks);
        });
    }
}
