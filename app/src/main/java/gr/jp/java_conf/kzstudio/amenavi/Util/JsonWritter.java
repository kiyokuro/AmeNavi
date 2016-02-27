package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by kiyokazu on 2015/12/24.
 * webAPIから取得したJSONデータを端末のファイルに書き込む
 */
public class JsonWritter {

    public void fileMaker(String jsonData, String fileName, File outputDir) {
        try {
            File file = new File(outputDir + "/" + fileName+".json");
            FileWriter filewriter;

            filewriter = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(filewriter);
            PrintWriter pw = new PrintWriter(bw);
            pw.write(jsonData);
            pw.close();
            //Log.v("write data","write date");
        } catch (IOException e) {
            e.printStackTrace();
         }
    }
}

