package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
 * 緯度と軽度から天気を検索して取得する。
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
        String requestCode = data.getAccessUrl(intent.getDoubleExtra("lat",999.0),
                intent.getDoubleExtra("lon",999.0));
        connectApi(requestCode);
    }

    /**
     * APIからデータ取得して端末のファイルに書き出す。
     * @param requestUrl APIにアクセスするURL
     */
    private void connectApi(String requestUrl){
        RequestQueue _requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(
                Request.Method.GET,
                requestUrl,
                null,
                new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                //通信成功
                JsonWritter jsonWritter = new JsonWritter();
                jsonWritter.fileMaker(response.toString(), "WeatherData", FileOutput._outputDir);

                changeActivity();
            }
        }
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 通信失敗
                Log.e("VolleyError", error.toString());
            }
        }
        );
        _requestQueue.add(jsonObjReq);
    }

    /**
     * WeatherActivityに画面遷移する。
     */
    private void changeActivity(){
        findViewById(R.id.loadview).setVisibility(View.GONE);
        Intent intent = new Intent(this,WatherActivity.class);
        startActivity(intent);
        finish();
    }
}
