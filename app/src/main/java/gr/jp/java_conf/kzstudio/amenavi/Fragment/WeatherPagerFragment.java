package gr.jp.java_conf.kzstudio.amenavi.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.DirectionalViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import gr.jp.java_conf.kzstudio.amenavi.R;

/**
 * MyPagerAdapter内で左右にスクロールするFragmentListに設定する。
 */
public class WeatherPagerFragment extends Fragment {
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.weather_pager_layout, null);
        final DirectionalViewPager pager = (DirectionalViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> fragments;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            //横に並べるフラグメントをしてい
            fragments = Arrays.asList(new TodaysWeatherListFragment(), new TomorrowWeatherListFragment());
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
