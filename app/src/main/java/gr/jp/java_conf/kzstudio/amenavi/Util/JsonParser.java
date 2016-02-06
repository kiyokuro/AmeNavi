package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

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
    private final String _VALUE = "value";


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public  String parseJson(JSONObject jsonObject, String element) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonObject);//dataをarrayにする
        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject subJsonObj = jsonArray.getJSONObject(i);//data配列のi番目をjsonobjとして取り出す
                String cloudCover = subJsonObj.getString("cloudcover");//jsonObjならStringで取れる。jsonAryならintで取れる。
                JSONArray subJsonAry = new JSONArray(subJsonObj);//weatherがある階層がjsonObjで入る

                for(int j = 0; j < subJsonAry.length(); j++){
                    JSONObject thirdJsonObj = subJsonAry.getJSONObject(j);//weatherの中身。hourlyとか入る
                    String nowWetherJa = thirdJsonObj.getString("value");
                    JSONArray thirdJsonAry = new JSONArray(thirdJsonObj);

                    for (int k = 0; k < thirdJsonAry.length(); k++){
                        JSONObject forthJsonObj = thirdJsonAry.getJSONObject(k);
                        String value = forthJsonObj.getString("value");//hourlyの中のvalue
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return element;
    }


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

                //Log.v("jsonArray",elements.get(i));
            }
            /*JSONObject weatherJa = new JSONObject(elements.get(1));//周りより階層が1つ深いから取り出す
            elements.add(4,weatherJa.getString(_VALUE));
            JSONObject weatherImg = new JSONObject(elements.get(13));//周りより階層が1つ深いから取り出す
            elements.add(13,weatherImg.getString(_VALUE));
            */
        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }

    public ArrayList<String> getFutureWeather(JSONObject jsonObject, int allayNumber) throws JSONException{
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

            //Log.v("jsonArray", elements.toString());

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }

    public String getRainChance(JSONObject jsonObject, int time){
        String rainChance = "";
        int arrayNum = 0;
        arrayNum = time / 3 + 1;
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

            //Log.v("jsonArray", elements.toString());

        }catch (JSONException e){
            e.printStackTrace();
        }
        return elements;
    }
}
