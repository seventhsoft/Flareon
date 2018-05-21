package com.seventhsoft.kuni.recompensas;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.seventhsoft.kuni.R;
import com.seventhsoft.kuni.game.BottomNavigationFragment;
import com.seventhsoft.kuni.game.MainActivity;
import com.seventhsoft.kuni.utils.ToolbarFragment;

import java.util.ArrayList;
import java.util.List;

public class RecompensasActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recompensas);
        setToolbar();
        setBottomNavigation();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * Defines the number of tabs by setting appropriate fragment and tab name.
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RecompensasJugadorFragment(), "Jugador");
        adapter.addFragment(new RecompensasConcursoFragment(), "Concurso");
        viewPager.setAdapter(adapter);
    }


    /**
     * Set the toolbar for the activity
     */
    private void setToolbar() {
        FragmentManager fm = RecompensasActivity.this.getSupportFragmentManager();
        Fragment fragment;
        //if (fragment == null) {
        fragment = ToolbarFragment.newInstance(1);
        fm.beginTransaction()
                .add(R.id.toolbar_fragment, fragment)
                .commit();
        //}
    }
    private void setBottomNavigation() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment;
        fragment = BottomNavigationFragment.newInstance(2);
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment, "bottom_navegation").commit();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
