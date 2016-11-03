package es.intelygenz.rss.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import es.intelygenz.rss.domain.preferences.PreferencesInteractor;
import es.intelygenz.rss.presentation.AndroidApplication;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.modules.ActivityModule;
import es.intelygenz.rss.presentation.internal.di.modules.ApplicationModule;
import es.intelygenz.rss.presentation.model.SourceModel;
import es.intelygenz.rss.presentation.view.PreferencesView;
import es.intelygenz.rss.presentation.view.activity.BaseActivity;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PreferencesScreenPresenterUnitTest {

    public static final String FAKE_ERROR_MESSAGE = "fakeErrorMessage";

    @Mock
    Context mMockContext;

    @Mock
    PreferencesView mMockView = Mockito.mock(PreferencesView.class);

    @Mock
    PreferencesInteractor mMockInteractor = Mockito.mock(PreferencesInteractor.class);

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new AndroidApplication())).build();
    ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent).activityModule(new ActivityModule(new BaseActivity())).build();

    PreferencesScreenPresenterImpl presenter;

    @Before
    public void setUp() {
        when(mMockView.getApplicationComponentFromApplication()).thenReturn(applicationComponent);
        when(mMockView.getActivityComponentFromBaseActivity()).thenReturn(activityComponent);
        presenter = new PreferencesScreenPresenterImpl(mMockView);
        presenter.setInteractor(mMockInteractor);
    }

    @Test
    public void retrieveSources_ProgressShown() {
        presenter.retrieveSources();
        verify(mMockView, times(1)).showProgress();
    }

    @Test
    public void retrieveSources_InteractorCalled() {
        presenter.retrieveSources();
        verify(mMockInteractor, times(1)).getSourcesForPreferences();
    }

    @Test
    public void onPreferencesRequestSucceed_ProgressHidden() {
        presenter.onPreferencesRequestSucceed(new ArrayList<SourceModel>());
        verify(mMockView, times(1)).hideProgress();
    }

    @Test
    public void onPreferencesRequestSucceed_RefreshViewCalled() {
        presenter.onPreferencesRequestSucceed(new ArrayList<SourceModel>());
        verify(mMockView, times(1)).showAvailableSource(any(ArrayList.class));
    }

    @Test
    public void onPreferencesRequestError_ProgressHidden() {
        presenter.onPreferencesRequestError(FAKE_ERROR_MESSAGE);
        verify(mMockView, times(1)).hideProgress();
    }

    @Test
    public void onPreferencesRequestError_ShowErrorMessageCalled() {
        presenter.onPreferencesRequestError(FAKE_ERROR_MESSAGE);
        verify(mMockView, times(1)).showErrorMessage(FAKE_ERROR_MESSAGE);
    }

    @Test
    public void onPreferencesRequestException_ProgressHidden() {
        presenter.onPreferencesRequestException(null);
        verify(mMockView, times(1)).hideProgress();
    }

    @Test
    public void onPreferencesRequestException_ShowDefaultErrorMessageCalled() {
        presenter.onPreferencesRequestException(null);
        verify(mMockView, times(1)).showDefaultErrorMessage();
    }

}