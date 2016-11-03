package es.intelygenz.rss.domain.main;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.SourcesResponse;
import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.entity.response.entity.Source;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository;
import es.intelygenz.rss.data.repository.sources.SourcesRepository;
import es.intelygenz.rss.domain.main.MainInteractor.OnMainRequestListener;
import es.intelygenz.rss.domain.mapper.FeedModelDataMapper;
import es.intelygenz.rss.domain.mapper.SourceModelDataMapper;
import es.intelygenz.rss.domain.preferences.PreferencesInteractor;
import es.intelygenz.rss.domain.preferences.PreferencesInteractor.OnPreferencesRequestListener;
import es.intelygenz.rss.domain.preferences.PreferencesInteractorImpl;
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

public class PreferencesInteractorUnitTest {

    public static final String FAKE_ERROR_MESSAGE = "fakeErrorMessage";

    @Mock
    SourcesRepository repository = Mockito.mock(SourcesRepository.class);

    @Mock
    SourceModelDataMapper mapper = Mockito.mock(SourceModelDataMapper.class);

    @Mock
    OnPreferencesRequestListener listener = Mockito.mock(OnPreferencesRequestListener.class);

    ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(new AndroidApplication())).build();
    ActivityComponent activityComponent = DaggerActivityComponent.builder().applicationComponent(applicationComponent).activityModule(new ActivityModule(new BaseActivity())).build();

    PreferencesInteractorImpl interactor = new PreferencesInteractorImpl(listener, activityComponent);

    @Before
    public void setUp() {
        interactor.setRepository(repository);
        interactor.setMapper(mapper);
    }

    @Test
    public void getSourcesForPreferences_RepositoryCalled() {
        interactor.getSourcesForPreferences();
        verify(repository, timeout(1)).getSources(interactor);
    }

    @Test
    public void onSuccess_MapperCalled() {
        SourcesResponse response = createFakeSourcesResponse();

        interactor.onSucceed(response);
        verify(mapper, times(1)).transform(response.getSources());
    }

    @Test
    public void onSuccess_ListenerCalled() {
        SourcesResponse response = createFakeSourcesResponse();

        interactor.onSucceed(response);
        verify(listener, times(1)).onPreferencesRequestSucceed(any(ArrayList.class));
    }

    @Test
    public void onSuccess_ResponseEmpty_MapperNotCalled() {
        interactor.onSucceed(new SourcesResponse());
        verify(mapper, never()).transform(any(ArrayList.class));
    }

    @Test
    public void onError_ListenerCalled() {
        interactor.onError(FAKE_ERROR_MESSAGE);
        verify(listener, times(1)).onPreferencesRequestError(Mockito.matches(FAKE_ERROR_MESSAGE));
    }

    @Test
    public void onException_ListenerCalled() {
        Throwable t = new Throwable();
        interactor.onException(t);
        verify(listener, times(1)).onPreferencesRequestException(Mockito.eq(t));
    }

    private SourcesResponse createFakeSourcesResponse() {
        SourcesResponse response = new SourcesResponse();
        List<Source> sourceList = new ArrayList<>();
        sourceList.add(new Source());
        response.setSources(sourceList);

        return response;
    }

}