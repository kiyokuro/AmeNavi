package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * JSONデータをパースする
 */
public class JsonParser{
    private final String _DATA = "data";
    private final String _CURRENTCONDITION = "current_condition";
    private final String _WEATHER = "weather";
    private final String _HOURLY = "hourly";

    /**
     * JSONデータから現在の天気情報と天気アイコンを取得する。
     * @param jsonObject パースする天気のJSONオブジェクト
     * @return 取得した天気情報。ArrayListには[雲量、天気、気温、天気アイコン]の順で格納。データがない場合は文字列のnullをセットして返す。[null]
     * @throws JSONException
     */
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

    /**
     * 引数のarrayNumber番目の当日の天気を取得する。
     * @param jsonObject パースする天気のJSONオブジェクト
     * @param arrayNumber 0 = 0:00, 1 = 3:00, 2 = 6:00,..., 6 = 18:00, 7 = 21:00, 8 = 24:00
     * @return 取得した天気情報。ArrayListには[雲量、天気、気温、天気アイコン、時間、降水確率、降雪確率]の順で格納。
     * @throws JSONException
     */
    public ArrayList<String> getTodayWeather(JSONObject jsonObject, int arrayNumber) throws JSONException{
        ArrayList<String> elements = new ArrayList<String>();
        try{
            JSONArray jsonArray = jsonObject.getJSONObject(_DATA).getJSONArray(_WEATHER).getJSONObject(0).getJSONArray(_HOURLY);//dataをarrayにする

            elements.add(jsonArray.getJSONObject(arrayNumber).getString("cloudcover"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getJSONArray("lang_ja").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getString("tempC"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getJSONArray("weatherIconUrl").getJSONObject(0).getString("value"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getString("time"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getString("chanceofrain"));
            elements.add(jsonArray.getJSONObject(arrayNumber).getString("chanceofsnow"));

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }

    /**
     * 降水確率を取得する。
     * @param jsonObject パースする天気のJSONオブジェクト
     * @param time 現在の時間hh
     * @return 3時間区切りで現在の時間に最も近い以前の時間の降水確率。
     */
    public String getRainChance(JSONObject jsonObject, int time){
        String rainChance = "";
        //現在の時間を3で割ってあまりを捨てると3時間区切りの一番近い以前の時間になる。＋1は天気予報のarrayが24:00スタートのため。
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

    /**
     * 引数のarrayNumber番目の当日の天気を取得する。
     * @param jsonObject パースする天気のJSONオブジェクト
     * @param allayNumber 0 = 0:00, 1 = 3:00, 2 = 6:00,..., 6 = 18:00, 7 = 21:00, 8 = 24:00
     * @return 取得した天気情報。ArrayListには[雲量、天気、気温、天気アイコン、時間、降水確率、降雪確率]の順で格納。
     * @throws JSONException
     */
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

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }
}
