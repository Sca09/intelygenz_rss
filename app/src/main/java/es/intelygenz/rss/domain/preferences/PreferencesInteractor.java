package es.intelygenz.rss.domain.preferences;

import java.util.List;

import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.model.SourceModel;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public interface PreferencesInteractor {

    interface OnPreferencesRequestListener {

        void onPreferencesRequestSucceed(List<SourceModel> sourceModelList);

        void onPreferencesRequestError(String errorMessage);

        void onPreferencesRequestException(Throwable t);

    }

    void getSourcesForPreferences();

}
