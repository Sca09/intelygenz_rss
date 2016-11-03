package es.intelygenz.rss.domain.main;

import java.util.List;

import javax.inject.Inject;

import es.intelygenz.rss.data.entity.response.FeedsResponse;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository.OnRequestListener;
import es.intelygenz.rss.domain.mapper.FeedModelDataMapper;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.model.FeedModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class MainInteractorImpl implements MainInteractor, OnRequestListener {

    @Inject
    protected FeedsRepository repository;

    @Inject
    protected FeedModelDataMapper mapper;

    private OnMainRequestListener listener;

    public MainInteractorImpl(OnMainRequestListener listener, ActivityComponent activityComponent) {
        this.listener = listener;

        activityComponent.inject(this);
    }

    @Override
    public void getFeedsForMainScreen() {
        repository.getFeeds(this);
    }

    @Override
    public void onSucceed(FeedsResponse response) {
        if(response.getArticles() != null) {
            List<FeedModel> feedModelList = mapper.transform(response.getArticles());

            if (listener != null) {
                listener.onMainRequestSucceed(feedModelList);
            }
        }
    }

    @Override
    public void onError(String errorMessage) {
        if(listener != null) {
            listener.onMainRequestError(errorMessage);
        }
    }

    @Override
    public void onException(Throwable t) {
        if(listener != null) {
            listener.onMainRequestException(t);
        }
    }

    public void setRepository(FeedsRepository repository) {
        this.repository = repository;
    }

    public void setMapper(FeedModelDataMapper mapper) {
        this.mapper = mapper;
    }
}
