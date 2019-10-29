package com.samar.newsfeeds.dagger.component

import android.app.Application
import com.newsdomain.diinterfaces.ProjectScope
import com.samar.newsfeeds.application.MyApplication
import com.samar.newsfeeds.base.activity.InjectorAll
import com.samar.newsfeeds.dagger.module.ApplicationModule
import com.samar.newsfeeds.dagger.module.CommonModule
import com.samar.newsfeeds.dagger.module.NetworkModule
import com.samar.newsfeeds.dagger.module.PicassoModule
import com.samar.newsfeeds.ui.mainscreen.NewsActivity
import com.samar.newsfeeds.ui.newsdetails.DetailsActivity


import dagger.BindsInstance
import dagger.Component

@ProjectScope
@Component(
    modules = arrayOf(
        ApplicationModule::class,
        PicassoModule::class,
        CommonModule::class,
        NetworkModule::class
    )
)
interface ApplicationComponent {

    fun inject(injectorAll: InjectorAll)
    fun inject(myApplication: MyApplication)

    fun inject(newsActivity: NewsActivity)
    fun inject(detailsActivity: DetailsActivity)


    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent
        @BindsInstance
        fun application(application: Application): Builder
    }
}