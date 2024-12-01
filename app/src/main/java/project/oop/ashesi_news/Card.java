package project.oop.ashesi_news;

import java.util.List;

public class Card {
    private String title;
    private String description;
    private String imageUrl;
    private List<String> comments;

    public Card(String title, String description, String imageUrl) {
        // Default constructor required for calls to DataSnapshot.getValue(CardModel.class)
        this.title= title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Card(String title, String description, String imageUrl, List<String> comments) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.comments = comments;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
