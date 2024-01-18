package com.example.studypartner.Matches;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studypartner.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesViewHolder> {
    private List<MatchesObject> matchesList;
    private Context context;
    public MatchesAdapter(List<MatchesObject> matchesList, Context context){
        this.matchesList=matchesList;
        this.context=context;
    }
    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_item,null,false);
        ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        MatchesViewHolder rov=new MatchesViewHolder(layoutView);

        return rov;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
         holder.mMatchId.setText(matchesList.get(position).getUserId());
         holder.mMatchName.setText(matchesList.get(position).getName());
         if (!matchesList.get(position).getProfileImageUrl().equals("default")) {
             Glide.with(context).load(matchesList.get(position).getName()).into(holder.mMatchImage);
         }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
