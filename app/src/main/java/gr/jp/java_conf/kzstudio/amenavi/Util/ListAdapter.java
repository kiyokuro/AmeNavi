package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gr.jp.java_conf.kzstudio.amenavi.R;

/**
 * Created by kiyokazu on 16/01/25.
 */
public class ListAdapter extends ArrayAdapter<FutureWeather>{
    private List<FutureWeather> _item;
    private int _resourceId;
    private LayoutInflater _inflater;

    public ListAdapter(Context context, int resourceId, List<FutureWeather> item) {
        super(context, resourceId, item);
        this._item = item;
        this._resourceId = resourceId;
        this._inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            //view = this._inflater.inflate(this._resourceId, null);
            view = _inflater.inflate(_resourceId,parent,false);
        }

        FutureWeather item = this._item.get(position);

        //天気のテキストをセット
        TextView weather = (TextView)view.findViewById(R.id.weather);
        weather.setText(item.get_weather());

        //アイコンをセット
        //ImageView image = (ImageView)view.findViewById(R.id.imd);
        //image.setImageURI(item.get_imgUrl());

        //時間をセット
        TextView time = (TextView)view.findViewById(R.id.time);
        time.setText(item.get_time());

        //気温をセット
        TextView temp = (TextView)view.findViewById(R.id.temp);
        temp.setText(item.get_temperture());

        return view;
    }
}
