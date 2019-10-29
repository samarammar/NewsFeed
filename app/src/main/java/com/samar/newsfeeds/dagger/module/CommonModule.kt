package com.samar.newsfeeds.dagger.module



import com.newsdata.JobExecutor
import com.newsdata.datasource.INewsRemote
import com.newsdata.repository.NewsRepositoryImp
import com.newsdomain.diinterfaces.excuter.PostExecutionThread
import com.newsdomain.diinterfaces.excuter.ThreadExecutor
import com.newsdomain.repository.INewsRepository
import com.newsremote.datasource.NewsRemoteDataSource
import com.newsremote.service.newsService
import com.samar.newsfeeds.ui.UiThread

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CommonModule {

    @Provides
    fun getCommonRepository(commonRepositoryImp: NewsRepositoryImp): INewsRepository {
        return commonRepositoryImp
    }

//    @Provides
//    fun provideCommonCache(commonCache: CommonCacheDataSource): ICommonCach {
//        return commonCache
//    }

    @Provides
    fun provideRemote(commonRemote: NewsRemoteDataSource): INewsRemote {
        return commonRemote
    }
    @Provides
    fun provideService(retrofit: Retrofit): newsService {
        return retrofit.create(newsService::class.java)
    }

    @Provides
     fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
     fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }


}