package project.oop.ashesi_news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private List<Card> cards;

    public CardStackAdapter(List<Card> cards) {
        this.cards = (cards != null) ? cards : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.title.setText(card.getTitle());
        holder.description.setText(card.getDescription());
        if (card.getComments() != null) {
            holder.comments.setText(String.join("\n", card.getComments()));
        } else {
            holder.comments.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, comments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_description);
            comments = itemView.findViewById(R.id.card_comments);
        }
    }

    public void updateData(List<Card> newCards) {
        if (newCards != null) {
            this.cards.clear();
            this.cards.addAll(newCards);
            notifyDataSetChanged();
        }
    }
}
