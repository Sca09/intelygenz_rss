package es.intelygenz.rss.data.repository.feeds;

import android.content.Context;

import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.intelygenz.rss.BuildTypeConfiguration;
import es.intelygenz.rss.data.db.DatabaseHandler;
import es.intelygenz.rss.data.entity.response.ErrorResponse;
import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.entity.Article;
import es.intelygenz.rss.data.entity.response.utils.ErrorConversor;
import es.intelygenz.rss.data.net.ApiClient;
import es.intelygenz.rss.data.net.ApiInterface;
import es.intelygenz.rss.data.net.ConnectionManagerUtils;
import es.intelygenz.rss.presentation.internal.di.PerActivity;
import es.intelygenz.rss.presentation.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@PerActivity
public class FeedsRepositoryImpl implements FeedsRepository, Callback<FeedsResponse> {

    private ApiInterface apiService;
    private OnRequestListener listener;
    private ConnectionManagerUtils connectionManagerUtils;

    private Context context;

    public FeedsRepositoryImpl(ApiInterface apiService, Context context) {
        this.apiService = apiService;
        this.context = context;

        connectionManagerUtils = new ConnectionManagerUtils();
    }

    @Override
    public void getFeeds(String source, OnRequestListener listener) {
        this.listener = listener;

        if(connectionManagerUtils.isNetworkAvailable(context)) {
            Map<String, String> data = new HashMap<>();
            if(Strings.isNullOrEmpty(source)) {
                data.put("source", Constants.WEB_SERVICE_DEFAULT_SOURCE);
            } else {
                data.put("source", source);
            }
            data.put("sortBy", Constants.WEB_SERVICE_DEFAULT_SORT_BY);
            data.put("apiKey", BuildTypeConfiguration.WEB_SERVICE_API_KEY);

            Call<FeedsResponse> call = apiService.getFeeds(data);
            call.enqueue(this);
        } else {
            getArticleListFromDB();
        }
    }

    @Override
    public void onResponse(Call<FeedsResponse> call, Response<FeedsResponse> response) {
        if(listener != null) {
            if(response.isSuccessful()) {
                saveArticleListToDB(response.body().getArticles());
                listener.onSucceed(response.body());
            } else if (response.errorBody() != null ) {
                try {
                    ErrorResponse error = ErrorConversor.parseError(ApiClient.getClient(), response);
                    if (error != null) {
                        listener.onError(error.getMessage());
                    }
                } catch (Exception e) {
                    listener.onException(e);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<FeedsResponse> call, Throwable t) {
        if(listener != null) {
            listener.onException(t);
        }
    }

    public void getArticleListFromDB() {
        DatabaseHandler db = new DatabaseHandler(context);
        List<Article> articleList = db.getLastContacts(10);
        if(listener != null) {
            FeedsResponse response = new FeedsResponse();
            response.setArticles(articleList);

            listener.onSucceed(response);
        }
    }

    public void saveArticleListToDB(List<Article> articleList) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.saveArticleList(articleList);
    }

    public void setConnectionManagerUtils(ConnectionManagerUtils connectionManagerUtils) {
        this.connectionManagerUtils = connectionManagerUtils;
    }

    public void setListener(OnRequestListener listener) {
        this.listener = listener;
    }
}
