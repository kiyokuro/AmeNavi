package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * 現在の天気の画面を提供する。
 */
public class CurrentWeatherFragment extends Fragment{
    private TextView _currentWeather;
    private TextView _rainChance;
    private TextView _temperature;
    private View _backGround;
    private NetworkImageView _networkImageView;
    private TextView _yearMonth;
    private TextView _day;
    private TextView _dayOfWeek;

    private ArrayList<String> currentWeatherData;
    private RequestQueue _queue;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.todays_weather_fragment_layout,null);//layoutを返す
        _backGround = view.findViewById(R.id.back_ground);
        _currentWeather = (TextView)view.findViewById(R.id.current_weather);
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

    /**
     * JSONデータから現在の天気情報を取得して、Viewにセットする。
     */
    public void showCurrentWeather(){
        MyDate date = new MyDate();
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonReader.getJson("WeatherData", FileOutput._outputDir);
            currentWeatherData = jsonParser.getCurrentWeather(jsonObject);
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }

        //通信には成功しても、天気の情報がない場所だとデータ取れない
        if(currentWeatherData.size()==0){
            _currentWeather.setTextColor(Color.parseColor("#000000"));
            _currentWeather.setText("No Data");
            _rainChance.setTextColor(Color.parseColor("#000000"));
            _rainChance.setText("右にスクロールして再取得");
            return;
        }

        //画面に情報をセットしていく
        //天気
        _currentWeather.setText(currentWeatherData.get(1));
        //気温
        _temperature.setText(currentWeatherData.get(2));
        //年と月
        _yearMonth.setText(date.getDate()[0]+"/"+date.getDate()[1]);
        //日
        _day.setText(date.getDate()[2]);
        //曜日
        _dayOfWeek.setText("["+date.getDate()[3]+"]");
        //降水確率
        _rainChance.setText("降水確率 : " + jsonParser.getRainChance(jsonObject,Integer.parseInt(date.getDate()[4])) + "%");
        //天気のアイコン
        String imgUrl = currentWeatherData.get(3);
        _networkImageView.setImageUrl(imgUrl, new ImageLoader(_queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
            //アイコンの端の1ピクセルをとってその色を背景にセット
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                int pixecColor = bitmap.getPixel(0, 0);
                _backGround.setBackgroundColor(pixecColor);
            }
        }));

    }
}
