package com.trippl3dev.listlibrary.implementation

import android.arch.lifecycle.LifecycleObserver
import com.trippl3dev.listlibrary.interfaces.IListCallback

abstract class SubBaseListVM<T,V:IListCallback> : FullListVM<T, T, V>(), LifecycleObserver {
    override fun mapFrom(it: T): T {
        return it
    }


}