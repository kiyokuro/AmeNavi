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
 * Created by kiyokazu on 16/01/27.
 */
public class TodaysWeatherListFragment extends Fragment {
    private ListView _listVIew;
    private TextView _dayLabel;

    private ArrayList<String> futureWeatherData;
    private ArrayList<FutureWeather> list;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.todays_weather_list_fragment_layout, null);//layoutを返す
        _listVIew = (ListView)view.findViewById(R.id.lsitview);
        _dayLabel = (TextView)view.findViewById(R.id.day_label);

        list = new ArrayList<FutureWeather>();

        showFutureWeather();
        return view;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showFutureWeather(){
        JsonReader jsonReader = new JsonReader();
        JsonParser jsonParser = new JsonParser();
        futureWeatherData = new ArrayList<String>();
        ListAdapter adapter;

        try {
            JSONObject object = jsonReader.getJson("WeatherData", FileOutput._outputDir);
            futureWeatherData = jsonParser.getTodayWeather(object, 0);
            if(futureWeatherData.size()==0){
                list.add(new FutureWeather("","No Data","","","","",""));
                adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.weather_list_item, list);
                _listVIew.setAdapter(adapter);
                return;
            }
            for(int i=1;i<9;i++) {
                futureWeatherData = jsonParser.getTodayWeather(object, i);

                //ここにlistViewを作る処理を書く
                list.add(new FutureWeather(
                        futureWeatherData.get(7),
                        futureWeatherData.get(1),
                        futureWeatherData.get(5),
                        futureWeatherData.get(6),
                        futureWeatherData.get(2),
                        futureWeatherData.get(0),
                        futureWeatherData.get(4)));
            }
            adapter = new ListAdapter(getActivity().getApplicationContext(), R.layout.weather_list_item, list);
            _listVIew.setAdapter(adapter);

            MyDate myDate = new MyDate();
            _dayLabel.setText(myDate.getDate()[1]+"/"+myDate.getDate()[2]+"["+myDate.getDate()[3]+"]");
        }catch (JSONException | IOException e){
            e.printStackTrace();
        }
    }
}
