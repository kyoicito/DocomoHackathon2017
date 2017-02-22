package docomofirstsky.thingpedia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

/**
 * Created by Hello on 2017/02/22.
 */

public class QuizStart extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_judge);

        // クイズデータを受け取る
        Intent intent = getIntent();
        String question = intent.getStringExtra("QUESTION");
        String ans1 = intent.getStringExtra("CHOOSE1");
        String ans2 = intent.getStringExtra("CHOOSE2");
        String ans3 = intent.getStringExtra("CHOOSE3");
        String ans4 = intent.getStringExtra("CHOOSE4");
        Integer ansnum = intent.getIntExtra("ANSNUM", 0);

        //問題文代入を書く

        //ボタンを押したら該当番号が正解か判定
        Button ans1Button = (Button)findViewById(R.id.quiz_choose_no1);
        Button ans2Button = (Button)findViewById(R.id.quiz_choose_no2);
        Button ans3Button = (Button)findViewById(R.id.quiz_choose_no3);
        Button ans4Button = (Button)findViewById(R.id.quiz_choose_no4);

        final Boolean isCorrect = false;
        ans1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ansnum == 1) {
                    isCorrect = true;
                }
                Intent intent = new Intent(getApplication(), QuizJudge.class);
                intent.putExtra("ISCORRECT", isCorrect);
                //int requestCode = RESULT_SUBACTIVITY;
                //startActivityForResult( intent, requestCode );
            }
        });
        ans2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ansnum == 2) {
                    isCorrect = true;
                }
                Intent intent = new Intent(getApplication(), QuizJudge.class);
                intent.putExtra("ISCORRECT", isCorrect);
                //int requestCode = RESULT_SUBACTIVITY;
                //startActivityForResult( intent, requestCode );
            }
        });
        ans3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ansnum == 3) {
                    isCorrect = true;
                }
                Intent intent = new Intent(getApplication(), QuizJudge.class);
                intent.putExtra("ISCORRECT", isCorrect);
                //int requestCode = RESULT_SUBACTIVITY;
                //startActivityForResult( intent, requestCode );
            }
        });
        ans4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ansnum == 4) {
                    isCorrect = true;
                }
                Intent intent = new Intent(getApplication(), QuizJudge.class);
                intent.putExtra("ISCORRECT", isCorrect);
                //int requestCode = RESULT_SUBACTIVITY;
                //startActivityForResult( intent, requestCode );
            }
        });
    }
}
