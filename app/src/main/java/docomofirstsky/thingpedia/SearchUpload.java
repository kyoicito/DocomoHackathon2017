package docomofirstsky.thingpedia;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Hello on 2017/02/22.
 */

public class SearchUpload extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageButton yesButton = (ImageButton)findViewById(R.id.search_uoload_yesButton);
        ImageButton noButton = (ImageButton)findViewById(R.id.search_upload_noButton);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http通信の後クイズ画面
            }
        });
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //トップ画面に戻る
            }
        });
    }
}
