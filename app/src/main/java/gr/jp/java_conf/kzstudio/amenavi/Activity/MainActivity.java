package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

import gr.jp.java_conf.kzstudio.amenavi.API.Data;
import gr.jp.java_conf.kzstudio.amenavi.Fragment.TodaysWeatherFragment;
import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FileOutput;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonWritter;

public class MainActivity extends FragmentActivity {
    private final int _REQUEST_PERMISSION_GPS = 10;
    private Context _context;

    private LocationManager _locationManager;
    private String _provider;
    private double _lat = 0.00;
    private double _lon = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;
        File outputDir = getDir("KokoTen", MODE_PRIVATE);
        FileOutput._outputDir = outputDir;
        findViewById(R.id.loadview).setVisibility(View.VISIBLE);

        if(Build.VERSION.SDK_INT>=23){
            checkPermission();
        }else {
            getLocation();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, _REQUEST_PERMISSION_GPS);

            }else {
                Toast toast = Toast.makeText(this, "現在地の天気を検索するために、GPSの使用を許可してください", Toast.LENGTH_LONG);
                toast.show();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, _REQUEST_PERMISSION_GPS);
            }

        } else {
            getLocation();
        }
    }

    // 結果の受け取り
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == _REQUEST_PERMISSION_GPS) {
            // 使用が許可された
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
                return;

            } else {
                // それでも拒否された時の対応
                Toast toast = Toast.makeText(this, "アプリは実行できません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getLocation() {
        //GPSの準備と接続確認
        _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Criteriaオブジェクトを生成
        Criteria criteria = new Criteria();
        // Accuracyを指定(低精度)
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        // PowerRequirementを指定(低消費電力)
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // ロケーションプロバイダの取得
        _provider = _locationManager.getBestProvider(criteria, true);
        //GPSが使えるかチェック
        boolean gpsFlg = _locationManager.isProviderEnabled(_provider);
        Log.d("GPS Enabled", gpsFlg ? "OK" : "NG");

        //現在地取得開始
        _locationManager.requestLocationUpdates(
                _provider, //LocationManager.NETWORK_PROVIDER,
                5000, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onLocationChanged(Location location) {
                        _lat = location.getLatitude();
                        _lon = location.getLongitude();
                        String msg = "Lat=" + _lat + "\nLng=" + _lon;
                        Log.v("現在地", msg);

                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        //位置情報の取得終了
                        _locationManager.removeUpdates(this);

                        changeActivity();

                        //天気のデータを取得
                        /*Data data = new Data(_context);
                        String requestUrl = data.getAccessUrl(_lat, _lon);

                        connectApi(requestUrl);
                        */
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }

    /**
     * APIからデータ取得して端末のファイルに書き出す
     * @param requestUrl APIにアクセスするURL
     */
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
                Log.v("★ getdata", response.toString());
                JsonWritter jsonWritter = new JsonWritter();
                jsonWritter.fileMaker(response.toString(), "WeatherData", FileOutput._outputDir);

                /*FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment todayWeatherFm = new TodaysWeatherFragment();
                //Bundle bundle = new Bundle();
                //bundle.putString("outputDir", outputDir.toString());
                //todayWeatherFm.setArguments(bundle);
                ft.replace(R.id.container, todayWeatherFm);
                ft.commit();*/

                changeActivity();
            }
        }
                // 通信失敗時のリスナーを設定する
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // 通信失敗時の処理
                Log.e("VolleyError",error.toString());
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
