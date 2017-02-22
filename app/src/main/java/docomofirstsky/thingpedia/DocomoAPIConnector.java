package docomofirstsky.thingpedia;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileLockInterruptionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by igbt3 on 2017/02/22.
 */

public class DocomoAPIConnector extends AsyncTask<String,Void ,String>{
    public static final String PRODUCT_SEARCH="https://api.apigw.smt.docomo.ne.jp/imageRecognition/v1/recognize";
    public static final String SEARCHPARAM="?APIKEY=6150436e4e496d4b4f493170723374436530495552373477486e6c2f474d6250443953784336416e555835&recog=product-all&numOfCandidates=10";


    private CallBackTask callbacktask;

    //params[0]:url
    //params[1]:imagepath
    @Override
    protected String doInBackground(String... params) {
        String result=null;
        String url=params[0]+SEARCHPARAM;
        File image= new File(params[1]) ;

        Log.e("DOCOMO",params[1]);

        final RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/octet-stream"), image);
        // リクエストオブジェクトを作って
        Request request = new Request.Builder()
                .url(url)
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


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        callbacktask.CallBack(result);
    }

    public void setOnCallBack(CallBackTask _cbj) {
        callbacktask = _cbj;
    }


    /**
     * コールバック用のstaticなclass
     */
    public static class CallBackTask {
        public void CallBack(String result) {
        }
    }

}
