package com.forgetsky.wanandroid.main.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forgetsky.wanandroid.R;
import com.forgetsky.wanandroid.app.Constants;
import com.forgetsky.wanandroid.base.activity.BaseActivity;
import com.forgetsky.wanandroid.base.fragment.BaseFragment;
import com.forgetsky.wanandroid.hierarchy.ui.KnowledgeHierarchyFragment;
import com.forgetsky.wanandroid.homepager.ui.HomePagerFragment;
import com.forgetsky.wanandroid.main.contract.MainContract;
import com.forgetsky.wanandroid.main.presenter.MainPresenter;
import com.forgetsky.wanandroid.navigation.ui.NavigationFragment;
import com.forgetsky.wanandroid.project.ui.ProjectFragment;
import com.forgetsky.wanandroid.wxarticle.ui.WxArticleFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.main_floating_action_btn)
    FloatingActionButton mFloatingActionButton;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.fragment_group)
    FrameLayout mFrameGroup;

    //fragments
    private ArrayList<BaseFragment> mFragments;
    private HomePagerFragment mHomePagerFragment;
    private KnowledgeHierarchyFragment mKnowledgeHierarchyFragment;
    private NavigationFragment mNavigationFragment;
    private WxArticleFragment mWxArticleFragment;
    private ProjectFragment mProjectFragment;
    private int mLastFgIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNavigationView();
        initPager();
//        NavigationFragment targetFg = findFragment(NavigationFragment.class);
//        if (targetFg == null) {
//            targetFg = NavigationFragment.getInstance();
//        }

    }

    private void initPager() {
        initFragments();
        initBottomNavigationView();
        switchFragment(0);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mHomePagerFragment = HomePagerFragment.getInstance();
        mKnowledgeHierarchyFragment = KnowledgeHierarchyFragment.getInstance();
        mNavigationFragment = NavigationFragment.getInstance();
        mWxArticleFragment = WxArticleFragment.getInstance();
        mProjectFragment = ProjectFragment.getInstance();

        mFragments.add(mHomePagerFragment);
        mFragments.add(mKnowledgeHierarchyFragment);
        mFragments.add(mNavigationFragment);
        mFragments.add(mWxArticleFragment);
        mFragments.add(mProjectFragment);

    }

    private void switchFragment(int position) {
        if (position >= mFragments.size()) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment targetFg = mFragments.get(position);
        if (mLastFgIndex >= 0) {
            Fragment lastFg = mFragments.get(mLastFgIndex);
            ft.hide(lastFg);
        }
        mLastFgIndex = position;
        if (!targetFg.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(targetFg).commitAllowingStateLoss();
            ft.add(R.id.fragment_group, targetFg);
        }
        ft.show(targetFg);
        ft.commitAllowingStateLoss();
    }

    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tab_main_pager:
                    loadPager(getString(R.string.home_pager), 0,
                            mHomePagerFragment, Constants.TYPE_MAIN_PAGER);
                    break;
                case R.id.tab_knowledge_hierarchy:
                    loadPager(getString(R.string.knowledge_hierarchy), 1,
                            mKnowledgeHierarchyFragment, Constants.TYPE_KNOWLEDGE);
                    break;
                case R.id.tab_navigation:
                    loadPager(getString(R.string.navigation), 2,
                            mNavigationFragment, Constants.TYPE_NAVIGATION);
                    break;
                case R.id.tab_wx_article:
                    loadPager(getString(R.string.wx_article), 3,
                            mWxArticleFragment, Constants.TYPE_WX_ARTICLE);
                    break;
                case R.id.tab_project:
                    loadPager(getString(R.string.project), 4,
                            mProjectFragment, Constants.TYPE_PROJECT);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void loadPager(String title, int position, BaseFragment mFragment, int pagerType) {
        mTitle.setText(title);
        switchFragment(position);
//        mFragment.reload();
        mPresenter.setCurrentPage(pagerType);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            mTitle.setText(R.string.home_pager);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    //TODO navigation item
                    case R.id.nav_item_my_collect:
                        Toast.makeText(MainActivity.this,"you click nav_item_my_collect", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_item_about_us:
                        break;
                    case R.id.nav_item_logout:
                        break;
                    case R.id.nav_item_setting:
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO toolbar button
            case R.id.action_usage:
                Toast.makeText(this,"you click usage", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_search:
                Toast.makeText(this,"you click search", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showLogoutSuccess() {

    }
}
