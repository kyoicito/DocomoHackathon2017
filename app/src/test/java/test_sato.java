/**
 * Created by satou on 2017/02/22.
 */

package sample.basic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class test_sato extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(res.layout.quiz_start);

        String hello="Hello";
        TextView textView1=(TextView)findViewById(res.layout.id.textView1);
        textView1.setText(hello);
    }
}
