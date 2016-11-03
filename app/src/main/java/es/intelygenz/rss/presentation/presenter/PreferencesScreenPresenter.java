package es.intelygenz.rss.presentation.presenter;

import es.intelygenz.rss.domain.preferences.PreferencesInteractor;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface PreferencesScreenPresenter extends Presenter {

    void retrieveSources();

    void setInteractor(PreferencesInteractor interactor);

}
