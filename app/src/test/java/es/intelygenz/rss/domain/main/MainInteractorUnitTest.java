package es.intelygenz.rss.domain.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository;
import es.intelygenz.rss.domain.main.MainInteractor.OnMainRequestListener;
import es.intelygenz.rss.domain.mapper.FeedModelDataMapper;
import es.intelygenz.rss.presentation.AndroidApplication;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.DaggerApplicationComponent;
import es.intelygenz.rss.presentation.internal.di.modules.ActivityModule;
import es.intelygenz.rss.presentation.internal.di.modules.ApplicationModule;
import es.intelygenz.rss.presentation.view.activity.BaseActivity;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MainInteractorUnitTest {

    public static final String FAKE_ERROR_MESSAGE = "fakeErrorMessage";

    @Mock
    FeedsRepository repository = Mockito.mock(FeedsRepository.class);

    @Mock
    FeedModelDataMapper mapper = Mockito.mock(FeedModelDataMapper.class);

    @Mock
    OnMainRequestListener listener = Mockito.mock(OnMainRequestListener.class);

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new AndroidApplication())).build();
    ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent).activityModule(new ActivityModule(new BaseActivity())).build();

    MainInteractorImpl interactor = new MainInteractorImpl(listener, activityComponent);

    @Before
    public void setUp() {
        interactor.setRepository(repository);
        interactor.setMapper(mapper);
    }

    @Test
    public void getFeedsForMainScreen_RepositoryCalled() {
        interactor.getFeedsForMainScreen();
        verify(repository, timeout(1)).getFeeds(interactor);
    }

    @Test
    public void onSuccess_MapperCalled() {
        FeedsResponse response = new FeedsResponse();
        List<Article> articleList = new ArrayList<>();
        articleList.add(new Article());
        response.setArticles(articleList);

        interactor.onSucceed(response);
        verify(mapper, times(1)).transform(articleList);
    }

    @Test
    public void onSuccess_ListenerCalled() {
        FeedsResponse response = new FeedsResponse();
        List<Article> articleList = new ArrayList<>();
        articleList.add(new Article());
        response.setArticles(articleList);

        interactor.onSucceed(response);
        verify(listener, times(1)).onMainRequestSucceed(any(ArrayList.class));
    }

    @Test
    public void onSuccess_ResponseEmpty_MapperNotCalled() {
        interactor.onSucceed(new FeedsResponse());
        verify(mapper, never()).transform(any(ArrayList.class));
    }

    @Test
    public void onError_ListenerCalled() {
        interactor.onError(FAKE_ERROR_MESSAGE);
        verify(listener, times(1)).onMainRequestError(Mockito.matches(FAKE_ERROR_MESSAGE));
    }

    @Test
    public void onException_ListenerCalled() {
        Throwable t = new Throwable();
        interactor.onException(t);
        verify(listener, times(1)).onMainRequestException(Mockito.eq(t));
    }

}