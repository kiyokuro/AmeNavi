package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
 * 位置情報の取得とAPIにアクセスを行う
 */
public class MainActivity extends FragmentActivity {
    private final int _REQUEST_PERMISSION_GPS = 10;
    private Context _context;

    private LocationManager _locationManager;
    private LocationListener _locationListener;
    private String _provider;
    private double _lat = 0.00;
    private double _lon = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _context = this;
        FileOutput._outputDir = getDir("KokoTen", MODE_PRIVATE);
        findViewById(R.id.loadview).setVisibility(View.VISIBLE);

        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        } else {
            getLocation();
        }
    }

    /**
     * 位置情報の権限が許可されているか確認する。許可されてなければ許可を求める。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, _REQUEST_PERMISSION_GPS);

            } else {
                Toast toast = Toast.makeText(this,
                        "現在地の天気を検索するために、GPSの使用を許可してください", Toast.LENGTH_LONG);
                toast.show();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, _REQUEST_PERMISSION_GPS);
            }
        } else {
            getLocation();
        }
    }

    /**
     * checkPermissionで権限を求めた結果を受け取る。
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == _REQUEST_PERMISSION_GPS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast toast = Toast.makeText(this, "アプリは実行できません", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /**
     * GPSから位置情報を取得を開始する。位置情報の取得を終了するまで位置情報を取得し続ける。
     * 位置情報がONになっていなければダイアログを表示してONにするように促す。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void getLocation() {
        _locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        _provider = _locationManager.getBestProvider(criteria, true);
        //GPSがONかチェック
        String gpsStatus = android.provider.Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        //GPSがOFFならダイアログを表示
        if (gpsStatus.indexOf("gps", 0) < 0) {
            new AlertDialog.Builder(this)
                    .setTitle("位置情報がONになっていません")
                    .setMessage("位置情報がONに設定されていません。位置情報をONにしてください。")
                    .setPositiveButton("GPS設定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //GPSの設定画面を開く
                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("閉じる", null)
                    .show();
        }

        //リスナーの設定
        _locationListener = new LocationListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onLocationChanged(Location location) {
                _lat = location.getLatitude();
                _lon = location.getLongitude();
                //Log.v("現在地", "Lat=" + _lat + "Lon=" + _lon);

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //APIにアクセスして位置情報の取得を終了する。
                Data data = new Data(_context);
                String requestUrl = data.getAccessUrl(_lat, _lon);
                connectApi(requestUrl);
                _locationManager.removeUpdates(_locationListener);
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
        };

        //現在地取得開始
        _locationManager.requestLocationUpdates(_provider, 100, 1, _locationListener);
    }

    /**
     * APIからデータ取得して端末のファイルに書き出す。
     * @param requestUrl APIにアクセスするURL
     */
    public void connectApi(String requestUrl) {
        RequestQueue _requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
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
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 通信失敗
                        Log.e("VolleyError", error.toString());
                        new AlertDialog.Builder(_context)
                                .setTitle("情報が取得できませんでした")
                                .setMessage("情報を再取得しますか？")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @TargetApi(Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED) {
                                            return;
                                        }
                                        _locationManager.requestLocationUpdates(_provider, 100, 1, _locationListener);
                                    }
                                })
                                .setNegativeButton("キャンセル", null)
                                .show();
                }
            }
        );
        _requestQueue.add(jsonObjReq);
        _requestQueue.start();
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
