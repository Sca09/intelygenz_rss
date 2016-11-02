package es.intelygenz.rss.presentation.presenter;

import java.util.List;

import es.intelygenz.rss.domain.main.MainInteractor;
import es.intelygenz.rss.domain.main.MainInteractor.OnMainRequestListener;
import es.intelygenz.rss.domain.main.MainInteractorImpl;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.view.MainView;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public class MainScreenPresenterImpl implements MainScreenPresenter, OnMainRequestListener {

    private MainView view;
    private MainInteractor interactor;

    public MainScreenPresenterImpl(MainView view) {
        this.view = view;

        if(this.view != null) {
            interactor = new MainInteractorImpl(this, this.view.getActivityComponentFromBaseActivity());
        }
    }

    @Override
    public void loadFeeds() {
        view.showProgress();
        interactor.getFeedsForMainScreen();
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void onMainRequestSucceed(List<FeedModel> feedModelList) {
        view.hideProgress();
        view.refreshFeeds(feedModelList);
    }

    @Override
    public void onMainRequestError(String errorMessage) {
        view.hideProgress();
        view.showErrorMessage(errorMessage);
    }

    @Override
    public void onMainRequestException(Throwable t) {
        view.hideProgress();
        view.showDefaultErrorMessage();
    }
}
