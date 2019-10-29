package com.trippl3dev.listlibrary.implementation


import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.trippl3dev.listlibrary.GenericListK
import java.io.Serializable

@Suppress("unused")
class ArchitectureList(internal val fragmentManger: FragmentManager) {
    private var listBundle: ListBundle = ListBundle()
    private var genericList = GenericListK()
    var isReady:Boolean = false

    companion object {
         fun get(fragmentManager: FragmentManager): ArchitectureList {
            return ArchitectureList(fragmentManager)
        }
    }



    fun init(): ListData {
        return ListData(this)
    }

    fun addListener(callback: ListReadyCallbak){
        genericList.setListReadyCallback(callback)
        if(isReady) {
            genericList.prepareCallback()
        }
    }




    class ListData(private val listBuilder: ArchitectureList) {


        fun setVM(className: String): FragmentBuilder {
            this.listBuilder.listBundle.className = className
            return FragmentBuilder(listBuilder)
        }

        fun setVM(className: String, list: ArrayList<*>): FragmentBuilder {
            this.listBuilder.listBundle.className = className
            this.listBuilder.listBundle.list = list
            return FragmentBuilder(listBuilder)
        }
    }


    class FragmentBuilder(private val listBuilder: ArchitectureList) {
        val bundle = Bundle()
        fun putListInContainerWithId(containerId: Int,action: (it: Boolean) -> Unit): ArchitectureList {
            if (listBuilder.fragmentManger.findFragmentByTag("$containerId") == null) {
                listBuilder.isReady = false
                action(listBuilder.isReady)
                bundle.putSerializable(GenericListK.dataKey, listBuilder.listBundle)
                listBuilder.genericList.arguments = bundle
                listBuilder.fragmentManger.beginTransaction().replace(containerId, listBuilder.genericList, "$containerId").commit()
            }else{
                listBuilder.isReady = true
                action(listBuilder.isReady)
                listBuilder.genericList = listBuilder.fragmentManger.findFragmentByTag("$containerId") as GenericListK
            }
            return listBuilder
        }

        fun putListInContainerWithId(containerId: Int): ArchitectureList {
            if (listBuilder.fragmentManger.findFragmentByTag("$containerId") == null) {
                bundle.putSerializable(GenericListK.dataKey, listBuilder.listBundle)
                listBuilder.genericList.arguments = bundle
                listBuilder.fragmentManger.beginTransaction().replace(containerId, listBuilder.genericList, "$containerId").commit()
            }else{
                listBuilder.genericList = listBuilder.fragmentManger.findFragmentByTag("$containerId") as GenericListK
            }
            return listBuilder
        }
    }




    interface ListReadyCallbak:Serializable{
        fun onListReady(baseListVM: RecyclerListIm?)
    }

    class ListBundle : Serializable {
         var className: String = ""
        var list: List<Any>? = null

    }
}
