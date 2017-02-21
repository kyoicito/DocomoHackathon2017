package docomofirstsky.thingpedia;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by igbt3 on 2017/02/22.
 */

public class OkHttpTester {
    private static String URL="www.yahoo.co,jp";
    public boolean testget(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;

                // リクエストオブジェクトを作って
                Request request = new Request.Builder()
                        .url(URL)
                        .get()
                        .build();

                // クライアントオブジェクトを作って
                OkHttpClient client = new OkHttpClient();

                // リクエストして結果を受け取って
                try {
                    Response response = client.newCall(request).execute();
                    response.close();
                    result = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返す
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e("OKHTTPGTEST", result);
            }

        }.execute();
        return true;
    }


}
