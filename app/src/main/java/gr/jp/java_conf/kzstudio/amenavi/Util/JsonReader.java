package gr.jp.java_conf.kzstudio.amenavi.Util;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 端末内のファイルを読み込む。
 */
public class JsonReader {
    /**
     * 端末のファイルに保存されているJSONデータを取得する。
     * @param fileName 端末のファイルの名前
     * @param outputDir ファイルのパス
     * @return 取得したJSONObject
     * @throws FileNotFoundException
     */
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
