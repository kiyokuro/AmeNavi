package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FileOutput;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonParser;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonReader;
import gr.jp.java_conf.kzstudio.amenavi.Util.MyDate;


/**
 * Created by kiyokazu on 16/01/09.
 */
public class TodaysWeatherFragment extends Fragment implements View.OnClickListener{
    private TextView _currentWeather;
    private TextView _cloudCover;
    private TextView _rainChance;
    private TextView _temperature;
    private View _backGround;
    private NetworkImageView _networkImageView;
    private TextView _yearMonth;
    private TextView _day;
    private TextView _dayOfWeek;

    private ArrayList<String> currentWeatherData;
    private String _url;
    RequestQueue _queue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.todays_weather_fragment_layout,null);//layoutを返す
        _backGround = view.findViewById(R.id.back_ground);
        _currentWeather = (TextView)view.findViewById(R.id.current_weather);
        //_cloudCover = (TextView)view.findViewById(R.id.cloud_cover);
        _rainChance = (TextView)view.findViewById(R.id.rain_chance);
        _temperature = (TextView)view.findViewById(R.id.temperature);
        _yearMonth = (TextView)view.findViewById(R.id.year_month);
        _day = (TextView)view.findViewById(R.id.day);
        _dayOfWeek = (TextView)view.findViewById(R.id.day_of_week);

        _queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        _networkImageView = (NetworkImageView) view.findViewById(R.id.network_image_view);

        showCurrentWeather();
        return view;
    }

    public void showCurrentWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        JSONObject object = null;
        try {
            object = jsonReader.getJson("WeatherData", FileOutput._outputDir);
            currentWeatherData = jsonParser.getCurrentWeather(object);
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }

        if(currentWeatherData.get(0)==null){
            _currentWeather.setTextColor(000);
            _currentWeather.setText("No Data");
        }

        //画面の情報をセットしていく
        _currentWeather.setText(currentWeatherData.get(1));
        //_cloudCover.setText("雲量 : "+currentWeatherData.get(0)+"%");
        _temperature.setText(currentWeatherData.get(2));
        _url = currentWeatherData.get(3);
        MyDate date = new MyDate();
        _yearMonth.setText(date.getDate()[0]+"/"+date.getDate()[1]);
        _day.setText(date.getDate()[2]);
        _dayOfWeek.setText("["+date.getDate()[3]+"]");
        _rainChance.setText("降水確率 : " + jsonParser.getRainChance(object,Integer.parseInt(date.getDate()[4])) + "%");

        _networkImageView.setImageUrl(_url, new ImageLoader(_queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                //Bitmap bitmap = ((BitmapDrawable) _networkImageView.getDrawable()).getBitmap();
                int pixecColor = bitmap.getPixel(0, 0);
                _backGround.setBackgroundColor(pixecColor);
            }
        }));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
