package es.intelygenz.rss.data.repository.feeds;

import es.intelygenz.rss.data.entity.response.FeedsResponse;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface FeedsRepository {

    interface OnRequestListener {

        void onSucceed(FeedsResponse response);

        void onError(String errorMessage);

        void onException(Throwable t);

    }

    void getFeeds(String source, OnRequestListener listener);

}
