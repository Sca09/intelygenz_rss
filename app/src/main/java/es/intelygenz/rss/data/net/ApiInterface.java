package es.intelygenz.rss.data.net;

import java.util.Map;

import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.entity.response.SourcesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface ApiInterface {

    @GET("/v1/articles")
    Call<FeedsResponse> getFeeds(@QueryMap Map<String, String> options);

    @GET("/v1/sources")
    Call<SourcesResponse> getSources(@QueryMap Map<String, String> options);

}
