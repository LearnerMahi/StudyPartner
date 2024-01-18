package com.example.studypartner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studypartner.FieldAdapter.FieldViewHolder;
import com.example.studypartner.R;

import java.util.List;
public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.FieldViewHolder> {

    private List<Field> fields;

    public FieldAdapter(List<Field> fields) {
        this.fields = fields;
    }

    @NonNull
    @Override
    public FieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_field, parent, false);
        return new FieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FieldViewHolder holder, int position) {
        Field field = fields.get(position);
        holder.fieldName.setText(field.getName());

        // Handle book list (example: display the first book's title)
        if (!field.getBooks().isEmpty()) {
            holder.bookTitle.setText(field.getBooks().get(0).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }

    static class FieldViewHolder extends RecyclerView.ViewHolder {

        TextView fieldName;
        TextView bookTitle;  // Add this line

        FieldViewHolder(View itemView) {
            super(itemView);
            fieldName = itemView.findViewById(R.id.f);
            bookTitle = itemView.findViewById(R.id.bookTitle);  // Add this line
        }
    }
}
