package gr.jp.java_conf.kzstudio.amenavi.Util;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kiyokazu on 2015/12/24.
 * 端末のファイルに保存されているJSONデータを読み込む
 */
public class JsonReader {
    public JSONObject getJson(String fileName, File outputDir) throws FileNotFoundException {

        InputStream input;
        JSONObject jsonObject = null;
        try {
            input = new FileInputStream(outputDir + "/" + fileName+".json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            String json = new String(buffer);
            jsonObject = new JSONObject(json);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
