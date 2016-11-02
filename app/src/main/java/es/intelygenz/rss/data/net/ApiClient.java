package es.intelygenz.rss.data.net;

import es.intelygenz.rss.BuildTypeConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class ApiClient {

    public static final String BASE_URL = BuildTypeConfiguration.WEB_SERVICE_URL;
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        return getClient(BASE_URL);
    }

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            if(baseUrl == null || baseUrl.equals("")) {
                baseUrl = BASE_URL;
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
