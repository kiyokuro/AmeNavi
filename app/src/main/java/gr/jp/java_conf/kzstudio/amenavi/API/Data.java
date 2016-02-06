package gr.jp.java_conf.kzstudio.amenavi.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import gr.jp.java_conf.kzstudio.amenavi.Util.JsonWritter;

/**
 * Created by kiyokazu on 16/01/09.
 */
public class Data extends Volley{
    public static final String _URL = "http://api.worldweatheronline.com/free/v2/weather.ashx?";
    public static final String _apiKey = "fb77e87981a12ca1875459b55b929";

    private Context context;

    public Data(Context context){
        this.context = context;
    }

    /**
     * APIにアクセスするURLを作る
     * @param lat 緯度
     * @param lon 軽度
     * @return APIにアクセスするためのURL
     */
    public String getAccessUrl(double lat, double lon) {
        final String area= lat+"%2C"+lon;
        final AQuery aq = new AQuery(context);
        final Map<String, Object> params = new HashMap<String, Object>();
        StringBuilder requestURL = new StringBuilder();
        
        requestURL.append(_URL);
        requestURL.append("q=" + area);
        requestURL.append("&format=json");
        requestURL.append("&num_of_days=2");
        requestURL.append("&fx24=yes");
        requestURL.append("&includelocation=yes");
        requestURL.append("&lang=ja");
        requestURL.append("&key="+ _apiKey);
        Log.v("requestURL",requestURL.toString());

        return requestURL.toString();
    }
}
