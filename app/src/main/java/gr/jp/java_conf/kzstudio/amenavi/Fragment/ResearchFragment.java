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
 * Research画面を提供する
 */
public class ResearchFragment extends Fragment{
    private Button _reload;
    private Button _search;
    private EditText _lat;
    private EditText _lon;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.research_fragment_layout, null);//layoutを返す
        _reload = (Button)view.findViewById(R.id.reload);
        _reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        _search = (Button)view.findViewById(R.id.search);
        _search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConnectAPIActivity.class);
                //入力データが空なら999をセットする。999で検索をかければNoDataになる
                if (_lat.getText().toString().equals("")) {
                    intent.putExtra("lat", 999.0);
                } else {
                    intent.putExtra("lat", Double.parseDouble(_lat.getText().toString()));
                }
                if (_lon.getText().toString().equals("")) {
                    intent.putExtra("lon", 999.0);
                } else {
                    intent.putExtra("lon", Double.parseDouble(_lon.getText().toString()));
                }
                startActivity(intent);
                getActivity().finish();
            }
        });
        _lat = (EditText)view.findViewById(R.id.lat);
        _lon = (EditText)view.findViewById(R.id.lon);

        return view;
    }
}
