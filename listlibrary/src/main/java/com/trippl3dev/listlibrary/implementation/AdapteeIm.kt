package com.trippl3dev.listlibrary.implementation

import com.trippl3dev.listlibrary.BaseAdapter
import com.trippl3dev.listlibrary.interfaces.IAdaptee

abstract class  AdapteeIm<T>(list: ArrayList<T>) : IAdaptee<T> {

    val list: ArrayList<T> = ArrayList()
    private lateinit var baseadapter:BaseAdapter<T>


    override fun setAdapter(adapter: BaseAdapter<T>) {
        baseadapter  = adapter
    }

    override fun getAdapter(): BaseAdapter<T> {
       return baseadapter
    }
    override fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
    }

    override fun getList(): List<T> {
        return list
    }


    init {
        list.clear()
        list.addAll(list)
    }
}