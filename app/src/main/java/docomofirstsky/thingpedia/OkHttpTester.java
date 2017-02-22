package docomofirstsky.thingpedia;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by igbt3 on 2017/02/22.
 */

public class OkHttpTester {
    private static String URL="http://www.yahoo.co.jp";
    private static String serverip="http://153.127.200.187:8080/api/hello";

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
                    result = response.body().string();
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返す
                if(result != null)
                    return result;
                return "response is null";
            }

            @Override
            protected void onPostExecute(String result) {
                Log.e("OKHTTPGTEST", result);
            }

        }.execute();
        return true;
    }

    public boolean testpost(){
        final RequestBody requestBody = RequestBody.create(
                MediaType.parse("text/plain"), "test");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = null;

                // リクエストオブジェクトを作って
                Request request = new Request.Builder()
                        .url(serverip)
                        .post(requestBody)
                        .build();

                // クライアントオブジェクトを作って
                OkHttpClient client = new OkHttpClient();

                // リクエストして結果を受け取って
                try {
                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 返す
                if(result != null)
                    return result;
                return "response is null";
            }

            //非同期処理が終わったらUIスレッドが実行する処理
            //引数はdoInBackgroundの戻り値
            @Override
            protected void onPostExecute(String result) {
                Log.e("OKHTTPPTEST", result);
            }

        }.execute();
        return true;
    }


}
