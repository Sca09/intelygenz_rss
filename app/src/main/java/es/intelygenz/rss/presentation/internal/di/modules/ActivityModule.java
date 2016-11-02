package es.intelygenz.rss.presentation.internal.di.modules;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import es.intelygenz.rss.data.net.ApiClient;
import es.intelygenz.rss.data.net.ApiInterface;
import es.intelygenz.rss.data.repository.feeds.FeedsRepository;
import es.intelygenz.rss.data.repository.feeds.FeedsRepositoryImpl;
import es.intelygenz.rss.presentation.internal.di.PerActivity;

/**
 * Created by davidtorralbo on 02/11/16.
 */

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
    this.activity = activity;
  }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    FeedsRepository provideFeedsRepository() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        return new FeedsRepositoryImpl(apiService, activity);
    }
}
