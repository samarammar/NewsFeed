package com.trippl3dev.listlibrary

import android.support.v7.util.DiffUtil


@Suppress("unused")
 class GenericlistDiffUtils<T>(private var oldList: ArrayList<T>, private var newList: ArrayList<T>) : DiffUtil.Callback() {


    private lateinit var itemDifferents: ListItemsDiffs

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return if (this::itemDifferents.isInitialized) {
            itemDifferents.itemChanges()
        } else {
            newList[newItemPosition] == oldList[oldItemPosition]
        }
    }

    fun setItemsDifferencesListener(itemDiffs: ListItemsDiffs) {
        itemDifferents = itemDiffs
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        try {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            if (oldItem == null) return false
            if (newItem == null) return false
            return oldItem == newItem
        } catch (e: IndexOutOfBoundsException) {
            return false
        }
    }


    interface ListItemsDiffs {
        fun itemChanges(): Boolean
    }
}