package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import gr.jp.java_conf.kzstudio.amenavi.R;
import gr.jp.java_conf.kzstudio.amenavi.Util.FileOutput;
import gr.jp.java_conf.kzstudio.amenavi.Util.FutureWeather;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonParser;
import gr.jp.java_conf.kzstudio.amenavi.Util.JsonReader;
import gr.jp.java_conf.kzstudio.amenavi.Util.ListAdapter;
import gr.jp.java_conf.kzstudio.amenavi.Util.MyDate;

/**
 * Created by kiyokazu on 16/02/03.
 */
public class TomorrowWeatherFragment extends Fragment {
    private ListView _listVIew;
    private TextView _dayLabel;

    private ArrayList<String> tomorrowWeatherData;
    private ArrayList<FutureWeather> list;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.tomorrow_weather_list_layout, null);//layoutを返す
        _listVIew = (ListView)view.findViewById(R.id.tomorrow_list);
        _dayLabel = (TextView)view.findViewById(R.id.day_label);

        list = new ArrayList<FutureWeather>();

        showTomorrowWeather();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showTomorrowWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        tomorrowWeatherData = new ArrayList<String>();
        ListAdapter adapter;
        try {
            JSONObject object = jsonReader.getJson("WeatherData", FileOutput._outputDir);
            for(int i=1;i<9;i++) {
                tomorrowWeatherData = jsonParser.getTomorrowWeather(object, i);
                if(tomorrowWeatherData.get(0)==null){
                    break;
                }
                //ここにlistViewを作る処理を書く
                list.add(new FutureWeather(
                        tomorrowWeatherData.get(7),
                        tomorrowWeatherData.get(1),
                        tomorrowWeatherData.get(5),
                        tomorrowWeatherData.get(6),
                        tomorrowWeatherData.get(2),
                        tomorrowWeatherData.get(0),
                        tomorrowWeatherData.get(4)));
            }
            adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.future_weather_list, list);
            _listVIew.setAdapter(adapter);

            MyDate myDate = new MyDate();
            _dayLabel.setText(myDate.getTomorrowDate()[1]+"/"+myDate.getTomorrowDate()[2]+"["+myDate.getTomorrowDate()[3]+"]");
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }
}