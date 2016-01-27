package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FileOutput;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonParser;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonReader;


/**
 * Created by kiyokazu on 16/01/09.
 */
public class TodaysWeatherFragment extends Fragment implements View.OnClickListener{
    private ImageView _weatherImg;
    private TextView _currentWeather;
    private TextView _cloudCover;
    private TextView _temperature;
    private ImageView _todayWeatherBg;

    private ArrayList<String> currentWeatherData;

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

        showCurrentWeather();
        return view;
    }

    public void showCurrentWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        try {
            JSONObject object = jsonReader.getJson("WeatherData", FileOutput._outputDir);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
