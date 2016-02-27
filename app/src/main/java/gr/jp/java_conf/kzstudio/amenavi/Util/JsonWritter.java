package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 端末にファイルを作りデータを書き込む。
 */
public class JsonWritter {

    /**
     * APIから取得したJSONデータを端末にファイルを作成し書き込む。
     * @param jsonData 端末に保存するJSONデータ
     * @param fileName 作成するファイルの名前
     * @param outputDir 作成するファイルのパス
     */
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

