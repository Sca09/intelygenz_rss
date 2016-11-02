package es.intelygenz.rss.presentation.view.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.AndroidApplication;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerActivityComponent;
import es.intelygenz.rss.presentation.internal.di.modules.ActivityModule;
import es.intelygenz.rss.presentation.navigation.Navigator;
import es.intelygenz.rss.presentation.utils.Preferences;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class BaseActivity extends AppCompatActivity {

    @Inject
    protected
    Navigator navigator;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
        this.initializeInjector();
        this.setTextSizeStyle();
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.setTextSizeStyle();
    }

    private void initializeInjector() {
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    public void setTextSizeStyle() {
        getTheme().applyStyle(new Preferences(this).getFontStyle().getResId(), true);
    }

    public void showSnackbar(String message, View view) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        snackbar.show();
    }

    public ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
