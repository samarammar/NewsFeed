package com.trippl3dev.listlibrary.implementation

import android.arch.lifecycle.MutableLiveData
import com.trippl3dev.listlibrary.interfaces.IListOp
import java.util.ArrayList

 abstract class ListOperationIm<T>(private val currentList: MutableLiveData<ArrayList<T>>) : IListOp<T> {
     override fun addItem(position: Int, t: T) {
         currentList.value?.add(position,map(t!!))
         currentList.postValue(currentList.value)
     }

     override fun addList(position: Int, list: List<T>) {
         currentList.value?.addAll(position,list.map { map(it!!)})
         currentList.postValue(currentList.value)
     }

     override fun getList(): ArrayList<T>? {
        return currentList.value
    }

    abstract fun map(it:Any):T

    override fun removeAll() {
        currentList.value?.removeAll(currentList.value!!)
        currentList.postValue(currentList.value)
    }

    override fun addItem(t: T) {

        currentList.value?.add(map(t!!))
        currentList.postValue(currentList.value)
    }

    override fun removeItem(t: T) {
        currentList.value?.remove(t)
        currentList.postValue(currentList.value)
    }

    override fun removeItemat(position: Int) {
        currentList.value?.removeAt(position)
        currentList.postValue(currentList.value)
    }

    override fun addList(list: List<T>) {
        currentList.value?.addAll(list.map { map(it!!)})
        currentList.postValue(currentList.value)
    }

    override fun setList(list: List<T>) {
        currentList.value?.clear()
        currentList.value?.addAll(list.map { map(it!!) })
        currentList.postValue(currentList.value)
    }
    override fun removeList(list: List<T>) {
        currentList.value?.removeAll(list)
        currentList.postValue(currentList.value)
    }

    override fun updateItem(t: T, position: Int) {
        currentList.value?.set(position,map(t!!))
        currentList.postValue(currentList.value)
    }



}