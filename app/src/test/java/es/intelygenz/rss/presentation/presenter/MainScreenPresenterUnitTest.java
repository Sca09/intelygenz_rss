package es.intelygenz.rss.presentation.presenter;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import es.intelygenz.rss.domain.main.MainInteractor;
import es.intelygenz.rss.presentation.AndroidApplication;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.modules.ActivityModule;
import es.intelygenz.rss.presentation.internal.di.modules.ApplicationModule;
import es.intelygenz.rss.presentation.view.MainView;
import es.intelygenz.rss.presentation.view.activity.BaseActivity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainScreenPresenterUnitTest {

    public static final String FAKE_SOURCE = "fakeSource";

    @Mock
    Context mMockContext;

    @Mock
    MainView mainView = Mockito.mock(MainView.class);

    @Mock
    MainInteractor interactor = Mockito.mock(MainInteractor.class);

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new AndroidApplication())).build();
    ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent).activityModule(new ActivityModule(new BaseActivity())).build();

    MainScreenPresenterImpl presenter;

    @Before
    public void setUp() {
        when(mainView.getApplicationComponentFromApplication()).thenReturn(applicationComponent);
        when(mainView.getActivityComponentFromBaseActivity()).thenReturn(activityComponent);
        presenter = new MainScreenPresenterImpl(mainView);
        presenter.setInteractor(interactor);
    }

    @Test
    public void loadFeeds_ProgressShown() {
        presenter.loadFeeds(FAKE_SOURCE);
        verify(mainView, times(1)).showProgress();
    }

    @Test
    public void loadFeeds_InteractorCalled() {
        presenter.loadFeeds(FAKE_SOURCE);
        verify(interactor, times(1)).getFeedsForMainScreen(FAKE_SOURCE);
    }

    @Test
    public void onMainRequestSuccess_ProgressHidden() {
        presenter.onMainRequestSucceed(null);
        verify(mainView, times(1)).hideProgress();
    }

    @Test
    public void onMainRequestSuccess_RefreshViewCalled() {
        presenter.onMainRequestSucceed(null);
        verify(mainView, times(1)).refreshFeeds(null);
    }

    @Test
    public void onMainRequestError_ProgressHidden() {
        presenter.onMainRequestError(null);
        verify(mainView, times(1)).hideProgress();
    }

    @Test
    public void onMainRequestError_ShowErrorMessageCalled() {
        presenter.onMainRequestError(null);
        verify(mainView, times(1)).showErrorMessage(null);
    }

    @Test
    public void onMainRequestException_ProgressHidden() {
        presenter.onMainRequestException(null);
        verify(mainView, times(1)).hideProgress();
    }

    @Test
    public void onMainRequestException_ShowDefaultErrorMessageCalled() {
        presenter.onMainRequestException(null);
        verify(mainView, times(1)).showDefaultErrorMessage();
    }

}