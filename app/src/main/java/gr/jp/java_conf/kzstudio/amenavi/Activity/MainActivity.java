package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;

import gr.jp.java_conf.kzstudio.amenavi.Fragment.TodaysWeatherFragment;
import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.API.Data;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonWritter;

public class MainActivity extends FragmentActivity {
    private Context _context;

    private LocationManager _locationManager;
    private String _provider;
    private double _lat = 0.00;
    private double _lon = 0.00;
    private File _outputDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;
        _outputDir = getDir("KokoTen", MODE_PRIVATE);

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

        findViewById(R.id.loadview).setVisibility(View.VISIBLE);

        getLocation();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        //現在地取得開始
        _locationManager.requestLocationUpdates(
                _provider, //LocationManager.NETWORK_PROVIDER,
                5000, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        _lat = location.getLatitude();
                        _lon = location.getLongitude();
                        String msg = "Lat=" + _lat + "\nLng=" + _lon;
                        Log.v("現在地", msg);

                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                        //位置情報の取得終了
                        _locationManager.removeUpdates(this);

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment todayWeatherFm = new TodaysWeatherFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("outputDir", _outputDir.toString());
                        todayWeatherFm.setArguments(bundle);
                        ft.replace(R.id.container, todayWeatherFm);
                        ft.commit();

                        //天気のデータを取得
                        /*Data data = new Data(_context);
                        String requestUrl = data.getAccessUrl(_lat, _lon);

                        connectApi(requestUrl, _outputDir);
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
     * @param outputDir ファイルの書き出し先パス
     */
    private void connectApi(String requestUrl, final File outputDir){
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
                jsonWritter.fileMaker(response.toString(), "WeatherData", _outputDir);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment todayWeatherFm = new TodaysWeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("outputDir", outputDir.toString());
                todayWeatherFm.setArguments(bundle);
                ft.replace(R.id.container, todayWeatherFm);
                ft.commit();
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
}
