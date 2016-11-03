package es.intelygenz.rss.presentation.view;

import java.util.List;

import es.intelygenz.rss.presentation.internal.di.components.ActivityComponent;
import es.intelygenz.rss.presentation.internal.di.components.ApplicationComponent;
import es.intelygenz.rss.presentation.model.FeedModel;

/**
 * Created by davidtorralbo on 02/11/16.
 */

public interface MainView {

    void showProgress();

    void hideProgress();

    void refreshFeeds(List<FeedModel> feedModelList);

    void showErrorMessage(String errorMessage);

    void showDefaultErrorMessage();

    boolean cleanSearchBox();

    boolean closeDrawer();

    ApplicationComponent getApplicationComponentFromApplication();

    ActivityComponent getActivityComponentFromBaseActivity();

}
