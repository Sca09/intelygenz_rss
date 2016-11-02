package es.intelygenz.rss.presentation.navigation;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;

import javax.inject.Inject;
import javax.inject.Singleton;

import es.intelygenz.rss.R;
import es.intelygenz.rss.presentation.model.FeedModel;
import es.intelygenz.rss.presentation.utils.Constants;
import es.intelygenz.rss.presentation.view.activity.FeedDetailsActivity;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@Singleton
public class Navigator {

    @Inject
    public Navigator() {
        //empty
    }

    public void navigateToFeedDetails(Activity activity, FeedModel feedModel, ImageView sharedView) {
        if(activity != null) {
            Intent intentToLaunch = FeedDetailsActivity.getCallingIntent(activity);
            intentToLaunch.putExtra(Constants.INTENT_EXTRA_FEED_MODEL, feedModel);

            String transitionName = activity.getString(R.string.transition_feed_detail);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && sharedView != null) {
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(activity, sharedView, transitionName);
                activity.startActivity(intentToLaunch, transitionActivityOptions.toBundle());
            } else {
                activity.startActivity(intentToLaunch);
            }
        }
    }
}
