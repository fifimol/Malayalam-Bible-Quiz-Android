package com.biblequiz.bquiz;

//Adapter for the recyclerview. It will set the sets data and score to all sets of the selected chapter.

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetsAdapter extends RecyclerView.Adapter<SetsAdapter.myViewHolder> {

    private int[] setsScores;
    private int[] setsQueAttempted;
    private Context context;
    private String chapter;

    //assigning the class members using data passed while setting adapter to recycler.
    public SetsAdapter(int[] setsScores, int[] setsQueAttempted, Context context, String chapter) {
        this.setsScores = setsScores;
        this.setsQueAttempted = setsQueAttempted;
        this.chapter = chapter;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout for set.
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.select_set, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, final int position) {
        //binding the data for the set.
        final String setNo=" Quiz Set "+(position+1);
        final String setScore="Score :"+setsScores[position]+"/"+setsQueAttempted[position];
        holder.txt_set_no.setText(setNo);
        holder.txt_set_score.setText(setScore);
        //setting a listener for all sets to open the questions activity as per the set selected.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent=new Intent(context, QuestionsActivity.class);
                    intent.putExtra("setDetails", chapter+"set"+(position+1));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return setsQueAttempted.length;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView txt_set_no, txt_set_score;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_set_no=itemView.findViewById(R.id.txt_set_no);
            txt_set_score=itemView.findViewById(R.id.txt_set_score);
        }
    }
}
