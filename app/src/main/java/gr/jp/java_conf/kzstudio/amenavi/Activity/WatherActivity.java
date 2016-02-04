package gr.jp.java_conf.kzstudio.amenavi.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.DirectionalViewPager;

import java.util.Arrays;
import java.util.List;

import gr.jp.java_conf.kzstudio.amenavi.Fragment.SettingFragment;
import gr.jp.java_conf.kzstudio.amenavi.Fragment.TodaysWeatherFragment;
import gr.jp.java_conf.kzstudio.amenavi.Fragment.TodaysWeatherListFragment;
import gr.jp.java_conf.kzstudio.amenavi.Fragment.TodaysWeatherPagerFragment;
import gr.jp.java_conf.kzstudio.amenavi.Fragment.WeatherPagerFragment;
import gr.jp.java_conf.kzstudio.amenavi.R;

/**
 * Created by kiyokazu on 16/01/27.
 */
public class WatherActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity_layout);

        final DirectionalViewPager pager = (DirectionalViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);

            fragments = Arrays.asList(new TodaysWeatherFragment(), new WeatherPagerFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
