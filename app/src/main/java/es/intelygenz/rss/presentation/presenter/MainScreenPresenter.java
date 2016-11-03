package es.intelygenz.rss.presentation.presenter;

import es.intelygenz.rss.domain.main.MainInteractor;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface MainScreenPresenter extends Presenter {

    void loadFeeds(String source);

    void setInteractor(MainInteractor interactor);

}
