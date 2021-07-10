package com.biblequiz.bquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView txt_total_score;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        int i;
        ConstraintLayout layout = findViewById(R.id.layout);
        drawerLayout=findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        txt_total_score=findViewById(R.id.txt_total_score);

        //Initializing the shared preference variable to access the user's scores from the file "scoresFile".
        sharedPreferences=getSharedPreferences("scoresFile", MODE_PRIVATE);

        //For loop for setting click listeners to all chapters.
        for(i =2; i <=11; i++){
            View view= layout.getChildAt(i);
            final int chapterNo= i -1;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //intent to open the sets activity as per user click on a chapter.
                    Intent intent=new Intent(HomeActivity.this, SetsActivity.class);
                    //setting the chapter name using tag of the selected chapter
                    intent.putExtra("chapter", view.getTag().toString());
                    intent.putExtra("chapterNo", chapterNo);
                    startActivity(intent);
                }
            });
        }

        //setting listeners for menu of the slider navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                Uri uri;
                switch (item.getItemId()){
                    case R.id.about:
                        //intent for opening the about activity.
                            intent=new Intent(HomeActivity.this, ActivityAbout.class);
                            startActivity(intent);
                        break;

                    case R.id.feedback:
                        //intent for opening source code page on Github
                            uri=Uri.parse("https://form.jotform.me/201464936644460");
                            intent=new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        break;

                    case R.id.other_apps:
                        //intent for opening play store profile
                            uri=Uri.parse("https://play.google.com/store/apps/developer?id=Ebenezer+Softwares");
                            intent=new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        break;

                    case R.id.download:
                        //intent for opening github profile
                            uri=Uri.parse("https://drive.google.com/file/d/1DcKqdDhFxGXARCJx0H8v7Jw5Qm6swskl/view?usp=sharing");
                            intent=new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        break;

                    case R.id.rate:
                        //intent for opening play store app page for rating and review.
                        uri=Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());
                        intent=new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //accessing the total score of user using the shared preference.
        int totalScore = sharedPreferences.getInt("totalScore", 0);
        int totalQuesAttempt = sharedPreferences.getInt("totalQuesAttempt", 0);
        String scoreDetails= totalScore +" / "+ totalQuesAttempt;
        txt_total_score.setText(scoreDetails);
    }

    //method for opening the navigation drawer
    public void openNavBar(View view) {
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
