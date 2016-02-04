package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import gr.jp.java_conf.kzstudio.amenavi.API.Data;
import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FileOutput;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonWritter;

/**
 * Created by kiyokazu on 16/02/03.
 */
public class ConnectAPIActivity extends Activity {
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_api_activity_layout);
        _context = this;
        findViewById(R.id.loadview).setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Data data = new Data(_context);
        String requestCode = data.getAccessUrl(intent.getDoubleExtra("lat",35.681382),intent.getDoubleExtra("lon",139.766084));
        connectApi(requestCode);
    }

    private void connectApi(String requestUrl){
        RequestQueue _requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(
                // HTTPメソッド名を設定する。GETかPOSTか等
                Request.Method.GET
                // リクエスト先のURLを設定する
                , requestUrl
                // リクエストパラメーターを設定する
                ,null
                // 通信成功時のリスナーを設定する
                ,new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                //通信成功時の処理
                //Log.v("★ getdata", response.toString());
                JsonWritter jsonWritter = new JsonWritter();
                jsonWritter.fileMaker(response.toString(), "WeatherData", FileOutput._outputDir);

                changeActivity();
            }
        }
                // 通信失敗時のリスナーを設定する
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 通信失敗時の処理
                Log.e("VolleyError", error.toString());
            }
        }
        );
        _requestQueue.add(jsonObjReq);
    }
    private void changeActivity(){
        findViewById(R.id.loadview).setVisibility(View.GONE);
        Intent intent = new Intent(this,WatherActivity.class);
        startActivity(intent);
        finish();
    }
}
