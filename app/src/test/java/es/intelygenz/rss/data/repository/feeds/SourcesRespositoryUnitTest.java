package es.intelygenz.rss.data.repository.feeds;

import android.content.Context;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import es.intelygenz.rss.data.entity.response.ErrorResponse;
import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.SourcesResponse;
import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.net.ApiInterface;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository.OnRequestListener;
import es.intelygenz.rss.data.repository.sources.SourcesRepository;
import es.intelygenz.rss.data.repository.sources.SourcesRepository.OnSourcesRequestListener;
import es.intelygenz.rss.data.repository.sources.SourcesRepositoryImpl;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SourcesRespositoryUnitTest {

    public static final String FAKE_ERROR_MESSAGE = "fakeErrorMessage";

    @Mock
    Context mMockContext;

    @Mock
    ApiInterface mMockApiService = Mockito.mock(ApiInterface.class);

    @Mock
    OnSourcesRequestListener listener = Mockito.mock(OnSourcesRequestListener.class);

    SourcesRepositoryImpl repository = new SourcesRepositoryImpl(mMockApiService);

    @Before
    public void setUp() {
        repository.setListener(listener);
    }

    @Test
    public void getSources_RepositoryCalled() {
        when(mMockApiService.getSources(any(Map.class))).thenReturn(Mockito.mock(Call.class));

        repository.getSources(listener);

        verify(mMockApiService, timeout(1)).getSources(any(Map.class));
    }

    @Test
    public void onResponse_ResponseSuccessful_ListenerCalled() {
        Response<SourcesResponse> response = Response.success(new SourcesResponse());

        repository.onResponse(Mockito.mock(Call.class), response);

        verify(listener, times(1)).onSucceed(response.body());
    }

    @Test
    public void onResponse_ResponseError_ListenerCalled() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(FAKE_ERROR_MESSAGE);
        Response<SourcesResponse> response = Response.error(403, ResponseBody.create(MediaType.parse("application/json"), new Gson().toJson(errorResponse)));

        repository.onResponse(Mockito.mock(Call.class), response);

        verify(listener, times(1)).onError(FAKE_ERROR_MESSAGE);
    }

    @Test
    public void onFailure_ListenerCalled() {
        Throwable t = new Throwable();

        repository.onFailure(Mockito.mock(Call.class), t);

        verify(listener, times(1)).onException(Mockito.eq(t));
    }
}