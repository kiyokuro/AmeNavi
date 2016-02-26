package gr.jp.java_conf.kzstudio.amenavi.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.List;

import gr.jp.java_conf.kzstudio.amenavi.R;

/**
 * Created by kiyokazu on 16/01/25.
 */
public class ListAdapter extends ArrayAdapter<FutureWeather>{
    private List<FutureWeather> _item;
    private int _resourceId;
    private LayoutInflater _inflater;
    private Context _context;
    RequestQueue queue;
    private ImageLoader imageLoader;
    private LruCache<String, Bitmap> mCache;

    public ListAdapter(Context context, int resourceId, List<FutureWeather> item) {
        super(context, resourceId, item);
        this._context = context;
        this._item = item;
        this._resourceId = resourceId;
        this._inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        queue = Volley.newRequestQueue(getContext());
        imageLoader = new ImageLoader(queue, new BitmapCache());
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = this._inflater.inflate(this._resourceId, null);
        }

        FutureWeather item = this._item.get(position);
        TextView weather = (TextView)view.findViewById(R.id.weather);
        NetworkImageView image = (NetworkImageView)view.findViewById(R.id.list_network_image);
        TextView ampm = (TextView)view.findViewById(R.id.ampm);
        TextView time = (TextView)view.findViewById(R.id.time);
        TextView temp = (TextView)view.findViewById(R.id.temp);
        TextView rainChance = (TextView)view.findViewById(R.id.rain_cance_list);
        final View listBackground = view.findViewById(R.id.listview_background);

        //天気のテキストをセット
        if(item.get_weather().equals("No Data")){
            weather.setTextColor(Color.parseColor("#000000"));
            weather.setText(item.get_weather());
        }else {
            weather.setText(item.get_weather());
        }

        //アイコンをセット
        String url = item.get_imgUrl();
        image.setImageUrl(url, new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                //Bitmap bitmap = ((BitmapDrawable) _networkImageView.getDrawable()).getBitmap();
                int pixecColor = bitmap.getPixel(0, 0);
                listBackground.setBackgroundColor(pixecColor);
            }
        }));

        // 画像取得処理
        //ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
        //imageLoader.get(url, listener);



        //時間をセット
        switch (item.get_time()){
            case "0":
                ampm.setText("AM");
                time.setText("0");
                break;
            case "300":
                ampm.setText("AM");
                time.setText("3");
                break;
            case "600":
                ampm.setText("AM");
                time.setText("6");
                break;
            case "900":
                ampm.setText("AM");
                time.setText("9");
                break;
            case "1200":
                ampm.setText("PM");
                time.setText("0");
                break;
            case "1500":
                ampm.setText("PM");
                time.setText("3");
                break;
            case "1800":
                ampm.setText("PM");
                time.setText("6");
                break;
            case "2100":
                ampm.setText("PM");
                time.setText("9");
                break;
            default:
                ampm.setText("??");
                time.setText("?");
                break;
        }

        //気温をセット
        temp.setText(item.get_temperture());

        //降水確率をセット
        rainChance.setText("降水確率 : "+item.get_rain()+"%");

        return view;
    }
}
