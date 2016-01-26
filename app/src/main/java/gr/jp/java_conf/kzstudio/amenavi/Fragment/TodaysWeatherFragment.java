package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FutureWeather;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonParser;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonReader;
import gr.jp.java_conf.kzstudio.amenavi.Util.ListAdapter;


/**
 * Created by kiyokazu on 16/01/09.
 * レイアウトの背景が設定してないからActivityの文字も見えてる
 */
public class TodaysWeatherFragment extends Fragment implements View.OnClickListener{
    private ImageView _weatherImg;
    private TextView _currentWeather;
    private TextView _cloudCover;
    private TextView _temperature;
    private ImageView _todayWeatherBg;
    private ListView _listVIew;

    private String _outputDir;
    private ArrayList<String> currentWeatherData;
    private ArrayList<String> futureWeatherData;
    private ArrayList<FutureWeather> list;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.todays_weather_fragment_layout,null);//layoutを返す
        _weatherImg = (ImageView)view.findViewById(R.id.current_weather_img);
        _todayWeatherBg = (ImageView) view.findViewById(R.id.today_weather);
        _currentWeather = (TextView)view.findViewById(R.id.current_weather);
        _cloudCover = (TextView)view.findViewById(R.id.cloud_cover);
        _temperature = (TextView)view.findViewById(R.id.temperature);
        _listVIew = (ListView)view.findViewById(R.id.lsitview);

        list = new ArrayList<FutureWeather>();

        Bundle bundle = getArguments();
        _outputDir = bundle.getString("outputDir");

        showCurrentWeather();
        showFutureWeather();
        return view;
    }

    public void showCurrentWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        try {
            JSONObject object = jsonReader.getJson("WeatherData", _outputDir);
            currentWeatherData = jsonParser.getCurrentWeather(object);
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }
        if(currentWeatherData.get(1).equals("所により曇り")){
            _todayWeatherBg.setImageResource(R.drawable.sunny);
            _todayWeatherBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        Log.v("showCurrentWeather", currentWeatherData.get(1));
        _currentWeather.setText(currentWeatherData.get(1));
        _cloudCover.setText("雲量 : "+currentWeatherData.get(0)+"%");
        _temperature.setText("気温 : " + currentWeatherData.get(2) + "°C");
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showFutureWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        futureWeatherData = new ArrayList<String>();
        ListAdapter adapter;
        try {
            JSONObject object = jsonReader.getJson("WeatherData", _outputDir);
            for(int i=1;i<9;i++) {
                futureWeatherData = jsonParser.getFutureWeather(object, i);
                //ここにlistViewを作る処理を書く
                list.add(new FutureWeather(
                        futureWeatherData.get(1),
                        futureWeatherData.get(5),
                        futureWeatherData.get(6),
                        futureWeatherData.get(2),
                        futureWeatherData.get(0),
                        futureWeatherData.get(4)));
            }
            adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.future_weather_list, list);
            _listVIew.setAdapter(adapter);
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
