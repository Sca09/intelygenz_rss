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
import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.net.ApiInterface;
import es.intelygenz.rss.data.net.ConnectionManagerUtils;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository.OnRequestListener;
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

public class FeedRespositoryUnitTest {

    public static final String FAKE_ERROR_MESSAGE = "fakeErrorMessage";

    @Mock
    Context mMockContext;

    @Mock
    ApiInterface mMockApiService = Mockito.mock(ApiInterface.class);

    @Mock
    ConnectionManagerUtils connectionManagerUtils = Mockito.mock(ConnectionManagerUtils.class);

    @Mock
    OnRequestListener listener = Mockito.mock(OnRequestListener.class);

    FeedsRepositoryImpl repository = new FeedsRepositoryImpl(mMockApiService, mMockContext) {
        @Override
        public void saveArticleListToDB(List<Article> articleList) {
            dataSaved = true;
        }

        @Override
        public void getArticleListFromDB() {
            dataRetrieved = true;
        }
    };

    private boolean dataSaved;
    private boolean dataRetrieved;

    @Before
    public void setUp() {
        dataSaved = false;
        dataRetrieved = false;

        repository.setConnectionManagerUtils(connectionManagerUtils);
        repository.setListener(listener);
    }

    @Test
    public void getFeedsForMainScreen_RepositoryCalled() {
        when(connectionManagerUtils.isNetworkAvailable(mMockContext)).thenReturn(true);
        when(mMockApiService.getFeeds(any(Map.class))).thenReturn(Mockito.mock(Call.class));

        repository.getFeeds(listener);

        verify(mMockApiService, timeout(1)).getFeeds(any(Map.class));
    }

    @Test
    public void getFeedsForMainScreen_NoInternet_DataRetrieved() {
        when(connectionManagerUtils.isNetworkAvailable(mMockContext)).thenReturn(false);

        repository.getFeeds(listener);

        assertTrue(dataRetrieved);
    }

    @Test
    public void onResponse_ResponseSuccessful_DataSavedInDB() {
        Response<FeedsResponse> response = Response.success(new FeedsResponse());

        repository.onResponse(Mockito.mock(Call.class), response);

        assertTrue(dataSaved);
    }

    @Test
    public void onResponse_ResponseSuccessful_ListenerCalled() {
        Response<FeedsResponse> response = Response.success(new FeedsResponse());

        repository.onResponse(Mockito.mock(Call.class), response);

        verify(listener, times(1)).onSucceed(response.body());
    }

    @Test
    public void onResponse_ResponseError_ListenerCalled() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(FAKE_ERROR_MESSAGE);
        Response<FeedsResponse> response = Response.error(403, ResponseBody.create(MediaType.parse("application/json"), new Gson().toJson(errorResponse)));

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