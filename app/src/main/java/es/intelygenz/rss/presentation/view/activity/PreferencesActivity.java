package es.intelygenz.rss.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

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
import es.intelygenz.rss.presentation.model.SourceModel;
import es.intelygenz.rss.presentation.presenter.PreferencesScreenPresenter;
import es.intelygenz.rss.presentation.presenter.PreferencesScreenPresenterImpl;
import es.intelygenz.rss.presentation.utils.PreferencesService;
import es.intelygenz.rss.presentation.view.PreferencesView;
import es.intelygenz.rss.presentation.view.component.FeedCard.OnFeedCardListener;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public class PreferencesActivity extends BaseActivity implements PreferencesView, OnFeedCardListener {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, PreferencesActivity.class);
        return callingIntent;
    }

    @BindView(R.id.coordinator_layout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.source_spinner) Spinner sourceSpinner;

    private PreferencesScreenPresenter presenter;
    private List<SourceModel> sourceModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        ButterKnife.bind(this);

        presenter = new PreferencesScreenPresenterImpl(this);

        showToolBarActionButton();

        setLayout();
    }

    private void setLayout() {
        presenter.retrieveSources();
    }

    private void showToolBarActionButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
    public void showAvailableSource(List<SourceModel> sourceModelList) {
        this.sourceModelList = sourceModelList;
        setGenderSpinner();
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
    public ApplicationComponent getApplicationComponentFromApplication() {
        return getApplicationComponent();
    }

    @Override
    public ActivityComponent getActivityComponentFromBaseActivity() {
        return getActivityComponent();
    }

    @Override
    public void onFeedCardClicked(FeedModel feedModel, ImageView sharedView) {
        navigator.navigateToFeedDetails(PreferencesActivity.this, feedModel, sharedView);
    }

    private void setGenderSpinner() {
        if(sourceModelList.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            for (SourceModel sourceModel : sourceModelList) {
                adapter.add(sourceModel.getName());
            }

            sourceSpinner.setAdapter(adapter);

            String sourceId = PreferencesService.getSource(this);
            if(!Strings.isNullOrEmpty(sourceId)) {
                for (int i = 0; i<sourceModelList.size(); i++) {
                    if(sourceModelList.get(i).getId().equals(sourceId)) {
                        sourceSpinner.setSelection(i);
                    }
                }
            }

            sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("test", "position["+ position +"] - itemSelected["+ sourceModelList.get(position).getName() +"]");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @OnClick(R.id.done_button)
    public void onDoneButtonClicked(View view) {
        SourceModel sourceModel = sourceModelList.get(sourceSpinner.getSelectedItemPosition());
        PreferencesService.setSource(this, sourceModel.getId());

        setResult(RESULT_OK);
        finish();
    }
}