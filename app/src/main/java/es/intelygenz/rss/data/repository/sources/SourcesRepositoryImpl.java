package es.intelygenz.rss.data.repository.sources;

import java.util.HashMap;
import java.util.Map;

import es.intelygenz.rss.BuildTypeConfiguration;
import es.intelygenz.rss.data.entity.response.ErrorResponse;
import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.SourcesResponse;
import es.intelygenz.rss.data.entity.response.utils.ErrorConversor;
import es.intelygenz.rss.data.net.ApiClient;
import es.intelygenz.rss.data.net.ApiInterface;
import es.intelygenz.rss.presentation.internal.di.PerActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by davidtorralbo on 03/11/16.
 */

@PerActivity
public class SourcesRepositoryImpl implements SourcesRepository, Callback<SourcesResponse> {

    private ApiInterface apiService;
    private OnSourcesRequestListener listener;

    public SourcesRepositoryImpl(ApiInterface apiService) {
        this.apiService = apiService;
    }


    @Override
    public void getSources(OnSourcesRequestListener listener) {
        this.listener = listener;

        Map<String, String> data = new HashMap<>();
        data.put("apiKey", BuildTypeConfiguration.WEB_SERVICE_API_KEY);

        Call<SourcesResponse> call = apiService.getSources(data);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
        if(listener != null) {
            if(response.isSuccessful()) {
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
    public void onFailure(Call<SourcesResponse> call, Throwable t) {
        if(listener != null) {
            listener.onException(t);
        }
    }
}
