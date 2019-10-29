package com.newsdata.module

import android.content.Context
import com.newsdomain.diinterfaces.AppContext

import com.newsdomain.diinterfaces.ProjectScope
import java.io.File
import javax.inject.Inject
@ProjectScope
class AssetModule @Inject constructor( @AppContext val appContext: Context){

    fun getData(data:String) :String{
        val json_string = appContext.assets.open("mockdata${File.separator}$data.json").bufferedReader().use{
            it.readText()
        }
        return json_string
    }
}
