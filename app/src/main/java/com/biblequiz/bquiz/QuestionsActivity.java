package com.biblequiz.bquiz;

//Activity for setting the questions accessed from the database.

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    private int quesAttempted, totalQues=10, setScore, totalScore, totalQuesAttempt;
    private TextView txt_question, txt_optionA, txt_optionB, txt_optionC, txt_optionD,
            txt_que_count, txt_score, txt_correct, txt_wrong, txt_set_completed;
    private Button btn_next;
    private ArrayList<QuestionModel> arrayList=new ArrayList();
    private SharedPreferences.Editor editor;
    private View answerView, selectedView;
    private String setDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        txt_question=findViewById(R.id.txt_question);
        txt_optionA=findViewById(R.id.txt_optionA);
        txt_optionB=findViewById(R.id.txt_optionB);
        txt_optionC=findViewById(R.id.txt_optionC);
        txt_optionD=findViewById(R.id.txt_optionD);
        txt_que_count=findViewById(R.id.txt_que_count);
        txt_score=findViewById(R.id.txt_score);
        txt_correct=findViewById(R.id.txt_correct);
        txt_wrong=findViewById(R.id.txt_wrong);
        txt_set_completed=findViewById(R.id.txt_set_completed);
        btn_next=findViewById(R.id.btn_next);
        btn_next.setVisibility(View.INVISIBLE);

        setDetails=getIntent().getStringExtra("setDetails");
        SharedPreferences sharedPreferences = getSharedPreferences("scoresFile", MODE_PRIVATE);
        editor= sharedPreferences.edit();

        //accessing the scores and attempted questions data from the shared preference file.
        setScore= sharedPreferences.getInt(setDetails+"score", 0);
        quesAttempted= sharedPreferences.getInt(setDetails+"attempted", 0);
        totalScore= sharedPreferences.getInt("totalScore", 0);
        totalQuesAttempt= sharedPreferences.getInt("totalQuesAttempt", 0);

        //calling the database helper class for obtaining the questions using cursor.
        databaseHelper helper=new databaseHelper(this);
        Cursor cursor=helper.getSetQuestions(setDetails);

        //do-while loop for adding questions to an array list.
        do{
            arrayList.add(new QuestionModel(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)));
        }while (cursor.moveToNext());

        //showing dialog if the set is already completed
        if(quesAttempted==10){
            AlertDialog builder=new AlertDialog.Builder(this)
                    .setTitle("This section is completed")
                    .setMessage("Your score: "+setScore+" / 10")
                    .setCancelable(false)
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            restartQuizSet(null);
                        }
                    })
                    .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .show();
        }else{
            //setting the question for first time
            setQuestion();
        }
    }

    //method for setting the question and the score in text fields
    public void setQuestion(){
        txt_question.setText(arrayList.get(quesAttempted).getQuestion());
        txt_optionA.setText(arrayList.get(quesAttempted).getOptionA());
        txt_optionB.setText(arrayList.get(quesAttempted).getOptionB());
        txt_optionC.setText(arrayList.get(quesAttempted).getOptionC());
        txt_optionD.setText(arrayList.get(quesAttempted).getOptionD());
        txt_que_count.setText("Que: "+(quesAttempted+1)+"/"+totalQues);
        txt_score.setText("Score: "+setScore);
    }


    public void btnQuit(View view) {
        finish();
    }

    public void btnNextQuestion(View view) {

        txt_optionA.setEnabled(true);
        txt_optionB.setEnabled(true);
        txt_optionC.setEnabled(true);
        txt_optionD.setEnabled(true);
        btn_next.setVisibility(View.INVISIBLE);
        txt_correct.setVisibility(View.INVISIBLE);
        txt_wrong.setVisibility(View.INVISIBLE);
        try {
            answerView.setBackground(getDrawable(R.drawable.set_layout));
            selectedView.setBackground(getDrawable(R.drawable.set_layout));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setQuestion();
    }


    public void checkAnswer(View view) {

        txt_optionA.setEnabled(false);
        txt_optionB.setEnabled(false);
        txt_optionC.setEnabled(false);
        txt_optionD.setEnabled(false);
        selectedView=view;
        String selectedAnswer=selectedView.getTag().toString();
        String correctAnswer=arrayList.get(quesAttempted).getAnswerKey();
        String answerViewId= "txt_option"+correctAnswer;

        int answerViewIdInt=getResources().getIdentifier(answerViewId, "id", getPackageName());
        answerView = findViewById(answerViewIdInt);
        answerView.setBackground(getDrawable(R.drawable.ans_correct_layout));
        if(selectedAnswer.equals(correctAnswer)){
            txt_correct.setVisibility(View.VISIBLE);
            setScore++;
            totalScore++;
            txt_score.setText("Score: "+setScore);
            editor.putInt(setDetails+"score",+setScore);
            editor.putInt("totalScore",+totalScore);
            editor.apply();
        }else{
            selectedView.setBackground(getDrawable(R.drawable.ans_wrong_layout));
            txt_wrong.setVisibility(View.VISIBLE);

        }
        quesAttempted++;
        totalQuesAttempt++;
        editor.putInt(setDetails+"attempted", quesAttempted);
        editor.putInt("totalQuesAttempt", totalQuesAttempt);
        editor.apply();

        if(quesAttempted==totalQues){
            txt_set_completed.setVisibility(View.VISIBLE);
        }else{
            btn_next.setVisibility(View.VISIBLE);
        }

    }

    public void restartQuizSet(View view) {
        txt_set_completed.setVisibility(View.INVISIBLE);
        totalQuesAttempt-=quesAttempted;
        totalScore-=setScore;
        quesAttempted=0;
        setScore=0;
        editor.putInt(setDetails+"attempted", quesAttempted);
        editor.putInt(setDetails+"score",+setScore);
        editor.putInt("totalQuesAttempt",+totalQuesAttempt);
        editor.putInt("totalScore",+totalScore);
        editor.apply();
        btnNextQuestion(view);
    }
}