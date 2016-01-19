package com.zky.zkyutilsdemo.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.zky.zkyutilsdemo.R;
import com.zky.zkyutilsdemo.app.customView.TabView;

import java.util.ArrayList;
import java.util.List;


public class MaterialDesignActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) this.findViewById(R.id.tool_bar);
        mTabLayout = (TabLayout) this.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) this.findViewById(R.id.view_pager);

        mToolbar.setTitle("Material");
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        CustomActionView view = new CustomActionView(getApplicationContext());
        actionBar.setCustomView(view);
//        Toolbar parent =(Toolbar) view.getParent();
//        parent.setContentInsetsAbsolute(0, 0);

        //初始化TabLayout的title数据集
        titles = new ArrayList<>();
        titles.add("details");
        titles.add("share");
        titles.add("agenda23143243243214144");
        titles.add("1");
        titles.add("2");
        titles.add("test3");
        titles.add("4");
        //初始化TabLayout的title
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //初始化ViewPager的数据集
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        fragments.add(new InfoDetailsFragment());
        //创建ViewPager的adapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        //千万别忘了，关联TabLayout与ViewPager
        //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(getTabView(i));
        }
        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
    }

    @NonNull
    private TabLayout.Tab getTab(int index) {
        TabLayout.Tab tab = mTabLayout.newTab();
        TabView tabView = getTabView(index);
        tab.setCustomView(tabView);
        return tab;
    }

    @NonNull
    private TabView getTabView(int index) {
        TabView tabView = new TabView(getApplication());
        tabView.setTitle(titles.get(index));
        return tabView;
    }
}
