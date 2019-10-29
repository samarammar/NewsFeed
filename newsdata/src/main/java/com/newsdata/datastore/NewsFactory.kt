package com.newsdata.datastore

import com.newsdata.datasource.INewsDataStore
import javax.inject.Inject

class NewsFactory @Inject constructor(val commonRemoteDataStore: NewsRemoteDataStore) {

    fun retrieveDataStore(): INewsDataStore {
        return commonRemoteDataStore
    }
}