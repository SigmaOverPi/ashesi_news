package project.oop.ashesi_news;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CardGridAdapter extends RecyclerView.Adapter<CardGridAdapter.CardViewHolder> {
    private final List<Card> cards;

    public CardGridAdapter(List<Card> cards) {
        this.cards = cards;
    }

    public void updateData(List<Card> newCards) {
        cards.clear();
        cards.addAll(newCards);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);

        holder.titleTextView.setText(card.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(card.getImageUrl())
                .into(holder.imageView);

        // Set click listener to navigate to FullScreenCardActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FullScreenCardActivity.class);
            intent.putExtra("title", card.getTitle());
            intent.putExtra("description", card.getDescription());
            intent.putExtra("imageUrl", card.getImageUrl());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.card_title);
            imageView = itemView.findViewById(R.id.card_image);
        }
    }
}
