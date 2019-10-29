package com.samar.newsfeeds.utils.networking
import io.reactivex.subjects.PublishSubject

class NetworkUtils  private constructor(){

    @Volatile var mNetworkStatus: Int = CONNECTED
    @Volatile var network: PublishSubject<Int> = PublishSubject.create()

    companion object {
        @Volatile
        var instance = NetworkUtils()
        const val CONNECTED = -90
        const val DISCONNECTED = -92
        const val CONNECTING = -94


        fun getNetworkUtils(): NetworkUtils {
            return instance
        }

    }


    fun getNetworkStatus(): PublishSubject<Int> {
        return network
    }

    fun setNetworkStatus(networkStatus: Int) {
        mNetworkStatus = networkStatus
        network.onNext(mNetworkStatus)
    }

    fun isConnected(): Boolean {
            mNetworkStatus = if(com.blankj.utilcode.util.NetworkUtils.isConnected()){
                CONNECTED
            }else{
                DISCONNECTED
            }
            return mNetworkStatus == CONNECTED
    }

    fun isNotConnected(): Boolean {
        return mNetworkStatus == DISCONNECTED
    }


    interface ConnectionStatusCB {
        fun onConnected() {}
        fun onDisconnected() {}
    }
}