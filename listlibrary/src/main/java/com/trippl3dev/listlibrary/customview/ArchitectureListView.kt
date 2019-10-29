package com.trippl3dev.listlibrary.customview

import android.content.Context
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import android.widget.FrameLayout
import com.trippl3dev.listlibrary.R
import com.trippl3dev.listlibrary.implementation.ArchitectureList
import com.trippl3dev.listlibrary.implementation.RecyclerListIm
import android.content.ContextWrapper
import android.support.v7.app.AppCompatActivity


class ArchitectureListView : FrameLayout, ArchitectureList.ListReadyCallbak {

    private var listHelper: RecyclerListIm? = null
    private var isReady = false
    var onListReady: OnListReady? = null
    override fun onListReady(baseListVM: RecyclerListIm?) {
        this.listHelper = baseListVM
        isReady = true
        onListReady?.onListReady()
    }

    fun Do(action: (it: RecyclerListIm) -> Unit?) {
        if (isReady){
            action(listHelper!!)
        }
        else {
            this.onListReady = object : OnListReady {
                override fun onListReady() {
                    action(listHelper!!)
                }

            }
        }
    }


    private var classVM: String? = null
    private lateinit var fragmentManager: FragmentManager


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setAttrs(context, attrs, defStyleAttr)
    }

    private var fragmentId: Int? = null
    private var fragmentTag: String? = null

    private fun setAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ArchitectureListView, defStyleAttr, 0)
        classVM = a.getString(R.styleable.ArchitectureListView_vmClassName)
        fragmentId = a.getResourceId(R.styleable.ArchitectureListView_fragmentId, -1)
        fragmentTag = a.getString(R.styleable.ArchitectureListView_fragmentTag)

        init(getFragmentManager()!!)
    }


    private fun getActivity(): AppCompatActivity? {
        var context = context
        while (context is ContextWrapper) {
            if (context is AppCompatActivity) {
                return context
            }
            context = context.baseContext
        }
        return null
    }

    private fun getFragmentManager(): FragmentManager? {
        val activity = getActivity()
        if (fragmentId != -1) {
            var fragment = activity?.supportFragmentManager
                ?.findFragmentById(fragmentId!!)
            if(fragment == null){
                fragment = activity?.supportFragmentManager?.fragments?.get(0)
                    ?.childFragmentManager?.findFragmentById(fragmentId!!)
            }
            if (fragment != null)
                return fragment?.childFragmentManager
        }

        if (fragmentTag != null) {
            val fragment = activity?.supportFragmentManager
                ?.findFragmentByTag(fragmentTag!!)
            if (fragment != null)
                return fragment?.childFragmentManager
        }
        return activity?.supportFragmentManager
    }

    private fun init(manager: FragmentManager) {
        this.fragmentManager = manager
        initView()
    }

    fun init(manager: FragmentManager, onListReady: OnListReady) {
        this.fragmentManager = manager
        this.onListReady = onListReady
        initView()
    }

    fun setClassVMName(name: String) {
        this.classVM = name
    }

    private fun initView() {
        ArchitectureList.get(fragmentManager)
            .init().setVM(classVM ?: "")
            .putListInContainerWithId(this.id) {
                this@ArchitectureListView.isReady = it
            }
            .addListener(this)
    }

    interface OnListReady {
        fun onListReady()
    }
}