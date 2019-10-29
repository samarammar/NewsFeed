package com.samar.newsfeeds.base.fragment

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View


//class BaseListBehavior : SubBaseListVM<KeyValue<Int, String>,SelectedItemCallback>() {
//    override fun getViewId(type: Int): Int {
//          return R.layout.base_list_item
//    }
//
//    override fun getLayoutManager(context: Context): RecyclerView.LayoutManager {
//        return LinearLayoutManager(context,RecyclerView.VERTICAL,false)
//    }
//    override fun onBindView(root: View?, position: Int) {
//        super.onBindView(root, position)
//        root?.setOnClickListener {
//            listCallback?.onItemSelected(position)
//        }
//    }
//}
//interface SelectedItemCallback:ListCallback{
//    fun onItemSelected(position:Int)
//}