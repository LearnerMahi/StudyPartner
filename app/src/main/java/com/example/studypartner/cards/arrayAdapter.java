package com.example.studypartner.cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studypartner.R;
import com.example.studypartner.cards.cards;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<cards> {
    Context context;

    public arrayAdapter(Context context, int resourceId, List<cards> items) {
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        cards card_item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        ImageView image = convertView.findViewById(R.id.image);
        name.setText(card_item.getName());
        // Clear the existing image


        if (card_item.getProfileImageUrl().equals("default")) {
            Glide.with(convertView.getContext()).load(R.mipmap.ic_launcher).into(image);
        } else {
            Glide.with(convertView.getContext()).clear(image);
            Glide.with(convertView.getContext()).load(card_item.getProfileImageUrl()).into(image);
        }

        return convertView;
    }
}
