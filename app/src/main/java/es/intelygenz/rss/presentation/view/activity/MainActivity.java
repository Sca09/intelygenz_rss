package es.intelygenz.rss.presentation.view.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.presenter.MainScreenPresenter;
import es.intelygenz.rss.presentation.presenter.MainScreenPresenterImpl;
import es.intelygenz.rss.presentation.utils.Utils;
import es.intelygenz.rss.presentation.view.MainView;
import es.intelygenz.rss.presentation.view.adapter.FeedListAdapter;
import es.intelygenz.rss.presentation.view.component.FeedCard.OnFeedCardListener;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class MainActivity extends BaseActivity implements MainView, OnFeedCardListener {

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.input_text) EditText searchEditText;
    @BindView(R.id.feed_recycler_view) RecyclerView feedRecyclerView;

    private MainScreenPresenter presenter;
    private List<FeedModel> feedModelList = new ArrayList<>();
    private FeedListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenter = new MainScreenPresenterImpl(this);

        showToolBarActionButton();
        setDrawer();

        setLayout();
    }

    private void setLayout() {
        adapter = new FeedListAdapter(this, feedModelList);
        adapter.setOnFeedCardListener(this);
        feedRecyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        feedRecyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();

        presenter.loadFeeds();

        setSearchButtonListener();
    }

    private void setSearchButtonListener() {
        searchEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= searchEditText.getRight() - searchEditText.getTotalPaddingRight()) {
                        if(!Strings.isNullOrEmpty(searchEditText.getText().toString())) {
                            closeSoftKeyboard();
                            filterByWords(searchEditText.getText().toString());

                            return true;
                        }

                        return false;
                    }
                }
                return false;
            }
        });
    }

    private void filterByWords(String words) {
        List<FeedModel> filteredFeedModelList = Utils.filterByWords(words, feedModelList);

        refreshFeeds(filteredFeedModelList);
    }

    private void closeSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void showToolBarActionButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @OnClick(R.id.settings_menu_layout)
    public void onSettingsClicked(View view) {
        closeDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(presenter != null) {
            presenter.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(presenter != null) {
            presenter.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(presenter != null) {
            presenter.destroy();
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshFeeds(List<FeedModel> feedModelList) {
        this.feedModelList.clear();
        this.feedModelList.addAll(feedModelList);
        adapter.notifyDataSetChanged();
        feedRecyclerView.scrollToPosition(0);
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        if(Strings.isNullOrEmpty(errorMessage)) {
            showDefaultErrorMessage();
        } else {
            showSnackbar(errorMessage, coordinatorLayout);
        }
    }

    @Override
    public void showDefaultErrorMessage() {
        showSnackbar(getString(R.string.something_went_wrong), coordinatorLayout);
    }

    @Override
    public boolean closeDrawer() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

            return true;
        }

        return false;
    }

    @Override
    public void showSearchBox() {
        searchEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean cleanSearchBox() {
        if(!Strings.isNullOrEmpty(searchEditText.getText().toString())) {
            searchEditText.setText("");
            searchEditText.clearFocus();

            presenter.loadFeeds();

            return true;
        }

        return false;
    }

    @Override
    public ApplicationComponent getApplicationComponentFromApplication() {
        return getApplicationComponent();
    }

    @Override
    public ActivityComponent getActivityComponentFromBaseActivity() {
        return getActivityComponent();
    }

    @Override
    public void onFeedCardClicked(FeedModel feedModel, ImageView sharedView) {
        navigator.navigateToFeedDetails(MainActivity.this, feedModel, sharedView);
    }

    @Override
    public void onBackPressed() {
        if (!closeDrawer() && !cleanSearchBox()){
            super.onBackPressed();
        }
    }
}