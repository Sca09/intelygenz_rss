/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.intelygenz.rss.presentation.internal.di.components;

import android.app.Activity;

import dagger.Component;
import es.intelygenz.rss.domain.main.MainInteractorImpl;
import es.intelygenz.rss.presentation.internal.di.PerActivity;
import es.intelygenz.rss.presentation.internal.di.modules.ActivityModule;

/**
 * Created by davidtorralbo on 02/11/16.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainInteractorImpl mainInteractor);

    //Exposed to sub-graphs.
    Activity activity();
}
