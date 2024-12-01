package project.oop.ashesi_news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private final MutableLiveData<List<Card>> bookmarkedCards = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Card>> getBookmarkedCards() {
        return bookmarkedCards;
    }

    public void addBookmark(Card card) {
        List<Card> currentBookmarks = bookmarkedCards.getValue();
        if (currentBookmarks != null && !currentBookmarks.contains(card)) {
            currentBookmarks.add(card);
            bookmarkedCards.setValue(currentBookmarks);
        }
    }
}
