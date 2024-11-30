package project.oop.ashesi_news;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private String title;
    private String description;
    private List<String> comments;

    public Card() {
        // Default constructor required for Firebase
    }

    public Card(String title, String description) {
        this.title = title;
        this.description = description;
        this.comments = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
