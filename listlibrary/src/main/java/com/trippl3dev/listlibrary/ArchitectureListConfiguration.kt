package com.trippl3dev.listlibrary

import android.content.Context

class ArchitectureListConfiguration(val context: Context?){

    companion object {
        fun getInstance(context: Context?): ArchitectureListConfiguration {
            return ArchitectureListConfiguration(context = context)
        }
    }
    private var preferenceModule: PreferenceModule? = null
    fun setLoadingViewId(id:Int):ArchitectureListConfiguration{
        if (preferenceModule == null){
            preferenceModule = PreferenceModule.getInstance(context = context!!)
        }
        preferenceModule?.loadingId = id
        return this
    }

    fun setErrorView(id:Int):ArchitectureListConfiguration{
        if (preferenceModule == null){
            preferenceModule = PreferenceModule.getInstance(context = context!!)
        }
        preferenceModule?.loadingId = id
        return this
    }
    fun setEmptyView(id:Int):ArchitectureListConfiguration{
        if (preferenceModule == null){
            preferenceModule = PreferenceModule.getInstance(context = context!!)
        }
        preferenceModule?.emptyId = id
        return this
    }
}