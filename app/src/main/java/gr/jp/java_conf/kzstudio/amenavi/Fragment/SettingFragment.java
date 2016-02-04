package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import gr.jp.java_conf.kzstudio.amenavi.Activity.ConnectAPIActivity;
import gr.jp.java_conf.kzstudio.amenavi.Activity.MainActivity;
import gr.jp.java_conf.kzstudio.amenavi.R;

/**
 * Created by kiyokazu on 16/02/03.
 */
public class SettingFragment extends Fragment{
    private Button reload;
    private Button search;
    private EditText lat;
    private EditText lon;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.setting_fragment_layout, null);//layoutを返す
        reload = (Button)view.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        search = (Button)view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConnectAPIActivity.class);
                intent.putExtra("lat",Double.parseDouble(lat.getText().toString()));
                intent.putExtra("lon",Double.parseDouble(lon.getText().toString()));
                startActivity(intent);
                getActivity().finish();
            }
        });
        lat = (EditText)view.findViewById(R.id.lat);
        lon = (EditText)view.findViewById(R.id.lon);

        return view;
    }
}
