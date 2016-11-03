package es.intelygenz.rss.data.repository.sources;

import es.intelygenz.rss.data.entity.response.SourcesResponse;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public interface SourcesRepository {

    interface OnSourcesRequestListener {

        void onSucceed(SourcesResponse response);

        void onError(String errorMessage);

        void onException(Throwable t);

    }

    void getSources(OnSourcesRequestListener listener);

}
