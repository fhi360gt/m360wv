package com.org.fhi360.m360wv;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.org.fhi360.m360wv.utils.CustomPagerAdapter;

import java.util.List;
import java.util.Vector;

/**
 * Created by jlgarcia on 06/01/2017.
 */

public class Reach_pg_we_0 extends FragmentActivity {

    private CustomPagerAdapter mPagerAdapter;

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.reach_pgviewer);
        // initialsie the pager
        this.initialisePaging();
    }

    /**
     * Initialise the fragments to be paged
     */
    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Reach_pg_we_1.class.getName()));
        fragments.add(Fragment.instantiate(this, Reach_pg_we_2.class.getName()));
        fragments.add(Fragment.instantiate(this, Reach_pg_we_3.class.getName()));

        this.mPagerAdapter = new CustomPagerAdapter(
                super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager) super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

    }
}

