package docomofirstsky.thingpedia;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Hello on 2017/02/22.
 */

public class QuizJudge extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_judge);

        // 正解だったかどうか受け取る
        Intent intent = getIntent();
        Boolean isCorrect = intent.getBooleanExtra("ISCORRECT", false);

        //正解なら正解，不正解なら不正解と表示


        //図鑑登録orトップ画面に戻る機能もいる

    }
}
