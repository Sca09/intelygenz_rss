package es.intelygenz.rss.domain.preferences;

import java.util.List;

import javax.inject.Inject;

import es.intelygenz.rss.data.entity.response.SourcesResponse;
import es.intelygenz.rss.data.repository.sources.SourcesRepository;
import es.intelygenz.rss.data.repository.sources.SourcesRepository.OnSourcesRequestListener;
import es.intelygenz.rss.domain.mapper.SourceModelDataMapper;
import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.model.SourceModel;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public class PreferencesInteractorImpl implements PreferencesInteractor, OnSourcesRequestListener {

    @Inject
    protected SourcesRepository repository;

    @Inject
    protected SourceModelDataMapper mapper;

    private OnPreferencesRequestListener listener;

    public PreferencesInteractorImpl(OnPreferencesRequestListener listener, ActivityComponent activityComponent) {
        this.listener = listener;

        activityComponent.inject(this);
    }

    @Override
    public void getSourcesForPreferences() {
        repository.getSources(this);
    }

    @Override
    public void onSucceed(SourcesResponse response) {
        if(response.getSources() != null) {
            List<SourceModel> sourceModelList = mapper.transform(response.getSources());

            if (listener != null) {
                listener.onPreferencesRequestSucceed(sourceModelList);
            }
        }
    }

    @Override
    public void onError(String errorMessage) {
        if(listener != null) {
            listener.onPreferencesRequestError(errorMessage);
        }
    }

    @Override
    public void onException(Throwable t) {
        if(listener != null) {
            listener.onPreferencesRequestException(t);
        }
    }

    public void setRepository(SourcesRepository repository) {
        this.repository = repository;
    }

    public void setMapper(SourceModelDataMapper mapper) {
        this.mapper = mapper;
    }
}
