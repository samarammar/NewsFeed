package com.trippl3dev.listlibrary

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.trippl3dev.listlibrary.interfaces.IAdaptee
import com.trippl3dev.listlibrary.interfaces.States


class BaseAdapter<T>(holderInterface: IAdaptee<T>?, context: Context?) : android.support.v7.widget.RecyclerView.Adapter<GenericHolder>() {

    private var adaptee: IAdaptee<T>? = holderInterface
    private var mContext: Context? = context
    private var showLoad: Boolean = false
    private var state: Int = States.NORMAL
    private var currentList: List<T>?
        get() = adaptee?.getList()
    private var loadinID: Int? = -1
    private var errorID: Int? = -1

    init {
        currentList = adaptee?.getList()
        loadinID = PreferenceModule.getInstance(context?.applicationContext!!).loadingId
        errorID = PreferenceModule.getInstance(context.applicationContext!!).errorId
        this.adaptee?.setAdapter(this)
    }

    fun <K> setAdaptee(adaptee: IAdaptee<T>?) where K : IAdaptee<Any> {
        this.adaptee = adaptee

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericHolder {
        return if (viewType == adaptee?.getErrorLoadingType(state)) {
            if (state == States.ERROR) {
                if (adaptee?.getErrorViewId() == -1 && errorID == -1) {
                    val v: ImageView = ImageView(parent.context)
                    v.setImageResource(R.drawable.retry)
                    GenericHolder(v)
                } else {
                    val v: View = LayoutInflater.from(mContext).inflate(
                            if (adaptee?.getErrorViewId()!! == -1) errorID!!
                            else adaptee?.getErrorViewId()!!
                            , parent, false)
                    GenericHolder(v)
                }
            } else {
                if (adaptee?.getLoadingViewId() == -1 && loadinID == -1) {
                    val v: ProgressBar = ProgressBar(parent.context)
                    GenericHolder(v)
                } else {
                    val v: View = LayoutInflater.from(mContext).inflate(
                            if (adaptee?.getLoadingViewId()!! == -1) loadinID!!
                            else adaptee?.getLoadingViewId()!!
                            , parent, false)
                    GenericHolder(v)
                }
            }
        } else {
            val v: View = LayoutInflater.from(mContext).inflate(adaptee?.getViewID(viewType)!!, parent, false)
            GenericHolder(v)
        }
    }

    override fun getItemCount(): Int {
        var size = 0
        if (state == 0) {
            if (adaptee?.getIsCircular()!!) return Int.MAX_VALUE
                size = getCurrentGenerictList()?.size ?: 0
        } else {
            if (adaptee?.getIsCircular()!!) Int.MAX_VALUE else
                size = getCurrentGenerictList()?.size!! + 1
        }

        return size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= getCurrentGenerictList()?.size!!) {
            adaptee?.getErrorLoadingType(state)!!
        } else {
            adaptee?.getTheViewType(position)!!
        }
    }

    override fun onBindViewHolder(holder: GenericHolder, position: Int) {
        if (holder.itemViewType == adaptee?.getErrorLoadingType(state)) {
            if (state == States.ERROR)
                holder.itemView.setOnClickListener { adaptee?.onErrorViewClicked() }
        } else {
            val binding: ViewDataBinding? = DataBindingUtil.bind(holder.itemView)
            try {
                binding?.setVariable(BR.model, if (adaptee?.getIsCircular()!!) getCurrentGenerictList()?.get(position % getCurrentGenerictList()?.size!!) else getCurrentGenerictList()?.get(position))
                adaptee?.onBinding(binding, position)
                binding?.executePendingBindings()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            adaptee?.onBindingView(binding?.root, position)
        }
    }

    private fun getCurrentGenerictList(): List<T>? {
        return adaptee?.getList()
    }

    fun getState():Int{
        return this.state
    }
    fun setState(state: Int) {
//        if(getCurrentGenerictList()?.size!! == 0)return
        if (this.state != States.NORMAL) {
            this.state = state
            when (state) {
                States.NORMAL -> {
                    notifyItemRemoved(getCurrentGenerictList()?.size!!)
                }
                else -> {
                    notifyItemRemoved(getCurrentGenerictList()?.size!!)
                    notifyItemInserted(getCurrentGenerictList()?.size!!)
                }
            }
        } else {
            if(state == States.NORMAL) {
                this.state = state
            }else{
                this.state = state
                notifyItemInserted(getCurrentGenerictList()?.size!!)
            }
//            notifyItemInserted(getCurrentGenerictList()?.size!!)
        }
    }

    private lateinit var iEmpty: IEmpty

    fun setIEmpty(iEmpty: IEmpty) {
        this.iEmpty  = iEmpty
    }




}

