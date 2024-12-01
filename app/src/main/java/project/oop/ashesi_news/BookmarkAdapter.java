package project.oop.ashesi_news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private List<Card> bookmarkList;
    private Context context;

    // Constructor with context parameter
    public BookmarkAdapter(List<Card> bookmarkList, Context context) {
        this.bookmarkList = bookmarkList;
        this.context = context;  // Initialize context
    }

    // ViewHolder class for each card item
    public static class BookmarkViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView imageUrl;

        public BookmarkViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmark_title);
            imageUrl = itemView.findViewById(R.id.bookmark_image);
            description = itemView.findViewById(R.id.bookmark_description);
        }
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get context from the parent view group
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmark_item, parent, false);
        return new BookmarkViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Card card = bookmarkList.get(position);
        holder.title.setText(card.getTitle());
        holder.description.setText(card.getDescription());

        // Load image using Glide
        Glide.with(holder.itemView.getContext())  // Use context from itemView for Glide
                .load(card.getImageUrl())  // Assuming bookmark has an image URL
                .into(holder.imageUrl);  // The ImageView in your ViewHolder
    }

    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }

    // Method to update the data in the adapter
    public void updateData(List<Card> newBookmarkList) {
        this.bookmarkList = newBookmarkList;
        notifyDataSetChanged();
    }
}
