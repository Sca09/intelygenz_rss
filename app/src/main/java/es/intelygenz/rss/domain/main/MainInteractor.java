package es.intelygenz.rss.domain.main;

import java.util.List;

import es.intelygenz.rss.data.repository.feeds.FeedsRepository;
import es.intelygenz.rss.domain.mapper.FeedModelDataMapper;
import es.intelygenz.rss.presentation.model.FeedModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface MainInteractor {

    interface OnMainRequestListener {

        void onMainRequestSucceed(List<FeedModel> feedModelList);

        void onMainRequestError(String errorMessage);

        void onMainRequestException(Throwable t);

    }

    void getFeedsForMainScreen();

}
