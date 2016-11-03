package es.intelygenz.rss.presentation.view;

import java.util.List;

import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.model.SourceModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface PreferencesView {

    void showProgress();

    void hideProgress();

    void showAvailableSource(List<SourceModel> sourceModelList);

    void showErrorMessage(String errorMessage);

    void showDefaultErrorMessage();

    ApplicationComponent getApplicationComponentFromApplication();

    ActivityComponent getActivityComponentFromBaseActivity();

}
