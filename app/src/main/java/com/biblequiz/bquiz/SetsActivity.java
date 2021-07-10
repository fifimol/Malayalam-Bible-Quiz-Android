package com.biblequiz.bquiz;

//Activity for displaying the sets of a given C++ topic. Users can choose a set from this activity.

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SetsActivity extends AppCompatActivity {

    //Hardcoded values for number or sets per topic.
    private int[] setsCounts={4,4,4,4,4,4,4,4,4,4};
    private String chapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int[] setsScores, setsQueAttempted;
    int i, chapterNo, chapterSetsCount;
    private RecyclerView recycler;
    private TextView txt_chapter_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        recycler=findViewById(R.id.recycler);
        txt_chapter_title=findViewById(R.id.txt_chapter_title);

        //accessing chapter name and chapter number passed by previous activity.
        chapter=getIntent().getStringExtra("chapter");
        chapterNo=getIntent().getIntExtra("chapterNo", 0);
        txt_chapter_title.setText(chapter);
        //obtaining the number of sets of the chapter from the array above.
        chapterSetsCount=setsCounts[chapterNo-1];
        recycler.setLayoutManager(new LinearLayoutManager(this));
        setsScores=new int[chapterSetsCount];
        setsQueAttempted=new int[chapterSetsCount];
        sharedPreferences=getSharedPreferences("scoresFile", MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //assigning values to setScores and setsQueAttempted array which stores the chapter sets user score.
        //This data is accessed from shared preference.
        for(i=0; i<chapterSetsCount; i++){
            setsScores[i]=sharedPreferences.getInt(chapter+"set"+(i+1)+"score", 0);
            setsQueAttempted[i]=sharedPreferences.getInt(chapter+"set"+(i+1)+"attempted", 0);
        }

        //setting adapter to recyclerview
        recycler.setAdapter(new SetsAdapter(setsScores, setsQueAttempted, getApplicationContext(), chapter));
    }

    //Function for the back image
    public void goBack(View view) {
        finish();
    }

}
