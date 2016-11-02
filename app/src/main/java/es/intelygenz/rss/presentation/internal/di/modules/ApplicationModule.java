package es.intelygenz.rss.presentation.internal.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.intelygenz.rss.presentation.AndroidApplication;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@Module
public class ApplicationModule {
    private final AndroidApplication application;
    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
    return this.application;
  }
}
