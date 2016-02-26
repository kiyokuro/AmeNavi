package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kiyokazu on 2015/12/24.
 * JSONデータをパースする
 */
public class JsonParser{
    private final String _DATA = "data";
    private final String _CURRENTCONDITION = "current_condition";
    private final String _WEATHER = "weather";
    private final String _HOURLY = "hourly";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public ArrayList<String> getCurrentWeather(JSONObject jsonObject) throws JSONException {
        ArrayList<String> elements = new ArrayList<String>();
        try{
            if(jsonObject.getJSONObject(_DATA).getJSONArray("nearest_area")==null){
                elements.add("null");
                return elements;
            }
            JSONArray jsonArray = jsonObject.getJSONObject(_DATA).getJSONArray(_CURRENTCONDITION);//dataをarrayにする
            for(int i = 0; i < jsonArray.length(); i++){
                elements.add(jsonArray.getJSONObject(i).getString("cloudcover"));
                elements.add(jsonArray.getJSONObject(i).getJSONArray("lang_ja").getJSONObject(i).getString("value"));
                elements.add(jsonArray.getJSONObject(i).getString("temp_C"));
                elements.add(jsonArray.getJSONObject(i).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }

    public ArrayList<String> getTodayWeather(JSONObject jsonObject, int allayNumber) throws JSONException{
        ArrayList<String> elements = new ArrayList<String>();
        try{
            JSONArray jsonArray = jsonObject.getJSONObject(_DATA).getJSONArray(_WEATHER).getJSONObject(0).getJSONArray(_HOURLY);//dataをarrayにする

            elements.add(jsonArray.getJSONObject(allayNumber).getString("cloudcover"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("lang_ja").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("tempC"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("time"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("chanceofrain"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("chanceofsnow"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }

    public String getRainChance(JSONObject jsonObject, int time){
        String rainChance = "";
        int arrayNum = time / 3 + 1;
        try {
            rainChance = jsonObject.getJSONObject(_DATA).
                    getJSONArray(_WEATHER).getJSONObject(0).
                    getJSONArray(_HOURLY).getJSONObject(arrayNum).
                    getString("chanceofrain");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rainChance;
    }

    public ArrayList<String> getTomorrowWeather(JSONObject jsonObject, int allayNumber) throws JSONException{
        ArrayList<String> elements = new ArrayList<String>();
        try{
            JSONArray jsonArray = jsonObject.getJSONObject(_DATA).getJSONArray(_WEATHER).getJSONObject(1).getJSONArray(_HOURLY);//dataをarrayにする

            elements.add(jsonArray.getJSONObject(allayNumber).getString("cloudcover"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("lang_ja").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("tempC"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("time"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("chanceofrain"));
            elements.add(jsonArray.getJSONObject(allayNumber).getString("chanceofsnow"));
            elements.add(jsonArray.getJSONObject(allayNumber).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }
}
