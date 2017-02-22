package docomofirstsky.thingpedia;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final static int RESULT_CAMERA = 1001;
    private final static int REQUEST_PERMISSION = 1002;
    public static final int USERID=100;

    private Uri curImgUri;
    private ImageView imageView;
    private Uri cameraUri;
    private File  cameraFile;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OkHttpTester ok=new OkHttpTester();
        ok.testpost();

        if (savedInstanceState != null){
            cameraUri = savedInstanceState.getParcelable("CaptureUri");
        }

        ImageButton cameraButton = (ImageButton)findViewById(R.id.findButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Android 6, API 23以上でパーミッションの確認
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermission();
                }
                else {
                    cameraIntent();
                    //画像を送信する部分を書く
                }
            }
        });
    }


    protected void onSaveInstanceState(Bundle outState){
        outState.putParcelable("CaptureUri", cameraUri);
    }

    private void cameraIntent(){
        // 保存先のフォルダーを作成
        File cameraFolder = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IMG"
        );
        cameraFolder.mkdirs();

        // 保存ファイル名決定
        String fileName = new SimpleDateFormat("ddHHmmss").format(new Date());
        filePath = cameraFolder.getPath() +"/" + fileName + ".jpg";
        Log.d("debug","filePath:"+filePath);

        // capture画像のファイルパス
        cameraFile = new File(filePath);
//        cameraUri = Uri.fromFile(cameraFile);
        cameraUri = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName() + ".provider", cameraFile);
        //カメラアクティビティ起動
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        curImgUri = cameraUri;
        startActivityForResult(intent, RESULT_CAMERA);

    }

    //戻ってきた時
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CAMERA) {

            if(cameraUri != null){
               /*DocomoAPIConnector dc=new DocomoAPIConnector();
                dc.setOnCallBack(new DocomoAPIConnector.CallBackTask(){
                    //
                });
                dc.execute();*/
                String url="http://153.127.200.187:8080/api/getref";
                String jsondata=null;
                try {
                    JSONObject jo = new JSONObject();
                    jo.put("userId", Integer.toString(USERID));
                    jo.put("name", "じゃがりこ");
                    JSONArray ja=new JSONArray();
                    ja.put(jo);
                    jsondata=ja.toString();
                }catch (JSONException e){}
                Log.e("API","post start");
                postapi(url,jsondata);
            }
            else{
                Log.d("debug","cameraUri == null");
            }
        }
    }


    public boolean postapi(String url,String postdata) {
        final RequestBody requestBody = RequestBody.create(
                MediaType.parse("text/plain"), postdata);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result = null;
                String serverip = params[0];
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
                if (result != null)
                    return result;
                return "response is null";
            }

            //非同期処理が終わったらUIスレッドが実行する処理
            //引数はdoInBackgroundの戻り値
            @Override
            protected void onPostExecute(String result) {
                Log.e("OKHTTPPTEST", result);
            }

        }.execute(url);
        return true;
    }
    // Runtime Permission check
    private void checkPermission(){
        // 既に許可している
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            cameraIntent();
        }
        // 拒否していた場合
        else{
            requestLocationPermission();
        }
    }

    // 許可を求める
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        } else {
            Toast toast = Toast.makeText(this, "許可されないとアプリが実行できません", Toast.LENGTH_SHORT);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, REQUEST_PERMISSION);

        }
    }

    //POSTするファイルのパスを引数として貰っている
    protected void sendImage(String ImagePath) {
        //ポスト先のURL
        String url = "http://153.127.200.187:8080/api/getref/";

        File file = new File(ImagePath);

        //ここでPOSTする内容を設定 "image/jpg"の部分は送りたいファイルの形式に合わせて変更する
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userID", "1")
                .addFormDataPart("img", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        String result="";
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            {
                result = response.body().string();
            }
        } catch (Exception e) {}
        Log.d("Response:", result);
        //return result;
        return;
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraIntent();
                return;

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "これ以上なにもできません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}