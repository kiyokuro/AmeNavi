package gr.jp.java_conf.kzstudio.amenavi.Util;


import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.DatagramChannel;

/**
 * Created by kiyokazu on 2015/12/24.
 * 端末のファイルに保存されているJSONデータを読み込む
 */
public class JsonReader {
    public JSONObject getJson(String fileName, File outputDir) throws FileNotFoundException {

        InputStream input;
        JSONObject jsonObject = null;
        JSONArray jObj=null;
        try {

            //destinate a file to read
            input = new FileInputStream(outputDir + "/" + fileName+".json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String json = new String(buffer);
            //convert string data to json data
            //jObj= new JSONArray(json);
            jsonObject = new JSONObject(json);
            Log.d("check reading file",json);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
