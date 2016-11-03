package es.intelygenz.rss.presentation.presenter;

import java.util.ArrayList;
import java.util.List;

import es.intelygenz.rss.data.entity.response.entity.Source;
import es.intelygenz.rss.domain.preferences.PreferencesInteractor;
import es.intelygenz.rss.domain.preferences.PreferencesInteractor.OnPreferencesRequestListener;
import es.intelygenz.rss.domain.preferences.PreferencesInteractorImpl;
import es.intelygenz.rss.presentation.model.SourceModel;
import es.intelygenz.rss.presentation.utils.Constants;
import es.intelygenz.rss.presentation.view.PreferencesView;

/**
 * Created by davidtorralbo on 03/11/16.
 */

public class PreferencesScreenPresenterImpl implements PreferencesScreenPresenter, OnPreferencesRequestListener {

    private PreferencesView view;
    private PreferencesInteractor interactor;

    public PreferencesScreenPresenterImpl(PreferencesView view) {
        this.view = view;

        if(this.view != null) {
            interactor = new PreferencesInteractorImpl(this, this.view.getActivityComponentFromBaseActivity());
        }
    }

    @Override
    public void retrieveSources() {
        view.showProgress();
        interactor.getSourcesForPreferences();
    }

    @Override
    public void setInteractor(PreferencesInteractor interactor) {
        this.interactor = interactor;
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
    public void onPreferencesRequestSucceed(List<SourceModel> sourceModelList) {
        view.hideProgress();

        List<SourceModel> sourceModelListFiltered = new ArrayList<>();
        for(SourceModel sourceModel : sourceModelList) {
            if(sourceModel.getSortBysAvailable().contains(Constants.WEB_SERVICE_DEFAULT_SORT_BY)) {
                sourceModelListFiltered.add(sourceModel);
            }
        }

        view.showAvailableSource(sourceModelListFiltered);
    }

    @Override
    public void onPreferencesRequestError(String errorMessage) {
        view.hideProgress();
        view.showErrorMessage(errorMessage);
    }

    @Override
    public void onPreferencesRequestException(Throwable t) {
        view.hideProgress();
        view.showDefaultErrorMessage();
    }
}
