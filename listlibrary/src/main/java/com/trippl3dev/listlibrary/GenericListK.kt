package com.trippl3dev.listlibrary


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trippl3dev.listlibrary.implementation.FullListVM
import com.trippl3dev.listlibrary.implementation.ArchitectureList
import com.trippl3dev.listlibrary.implementation.RecyclerListIm
import com.trippl3dev.listlibrary.implementation.VMFactory
import com.trippl3dev.listlibrary.interfaces.IAdaptee
import com.trippl3dev.listlibrary.interfaces.IListCallback
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*


class GenericListK : Fragment(), RecyclerListIm.ListCallbackFunctionality ,IEmpty{

    companion object {
        const val dataKey = "dataKey"
        val Bundle: String = "Bundle"
    }

    /***************** List Helper ****************************/
    override fun getAdaptee(): IAdaptee<Any>? {
        return fullListVM?.getAdaptee()
    }
    override fun getState(): Int {
        return adapter.getState()
    }
    override fun getScrollListener(): android.support.v7.widget.RecyclerView.OnScrollListener {
        return fullListVM?.getScrollListener(recyclerView.layoutManager!!)!!
    }
    override fun setState(state: Int) {
        adapter.setState(state)
    }
    override fun resetVMData() {
        fullListVM?.resetData()
    }
    override fun setListVMCallback(listCallback: IListCallback) {
        fullListVM?.setIListCallback(listCallback)
    }
    override fun filter(value: Any): Boolean {
        val list = fullListVM?.filter(value)!!
        fullListVM?.getAdaptee()?.setList(list)
        adapter.notifyDataSetChanged()
        return list.isEmpty()
    }

    override fun swapperCallback(className: String) {
        currentVMclassName = className
        clearEveryThing()
        fullListVM = ViewModelProviders.of(this, VMFactory())
            .get<FullListVM<Any, Any, IListCallback>>(getVMClass(className)!!)
        prepareData()
        recyclerCallback?.operation = fullListVM?.getListOp()!!
        recyclerView.clearOnScrollListeners()
        prepareList()
        listReadyCallback?.onListReady(recyclerCallback)
        fullListVM?.fetchData()
    }

    private fun clearEveryThing(){
        fullListVM?.getLiveDataList()?.removeObservers(this)
        fullListVM?.getListOp()?.setList(ArrayList())
        fullListVM?.getAdaptee()?.setList(ArrayList())
        resetVMData()
        fullListVM = null
        disposable?.dispose()
    }

    override fun getRecycler(): android.support.v7.widget.RecyclerView {
        return recyclerView
    }

    /************************ Helper End *********************************/

    /****************** Variables***********************/
    private var currentVMclassName: String = ""
    private lateinit var bundle: ArchitectureList.ListBundle
    private var fullListVM: FullListVM<Any, Any, IListCallback>? = null
    private var listReadyCallback: ArchitectureList.ListReadyCallbak? = null
        get() = field
    private lateinit var adapter: BaseAdapter<Any>
    private var disposable: Disposable? = null
    private var recyclerCallback: RecyclerListIm? = null
    private lateinit var recyclerView: RecyclerView

    /**********************Variables End********************/


    /******************** Create View ****************************/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState == null)
            getBundle()

        fullListVM = ViewModelProviders.of(this, VMFactory())
            .get<FullListVM<Any, Any, IListCallback>>(getVMClass(currentVMclassName)!!)
        lifecycle.addObserver(fullListVM!!)
        if (savedInstanceState != null)
            listState = savedInstanceState.getParcelable(listStateKey)
        val view: View?
        if (fullListVM?.hasSwipeToRefresh()!!) {
            view = getRefreshRecycler(inflater,container)
        } else {
            view = getRecyclerOnly(inflater,container)
        }
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        prepareData()

        retainInstance = true
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareList()
        prepareCallback()
    }
    fun prepareCallback(){
        if (listReadyCallback != null) {
            listReadyCallback!!.onListReady(recyclerCallback)
        }
    }
    override fun onResume() {

        super.onResume()
        if (recyclerCallback?.getLayoutManager() != null)
            recyclerView.layoutManager = recyclerCallback?.getLayoutManager()
        if (listState != null && recyclerView.layoutManager != null) {
            recyclerView.layoutManager!!.onRestoreInstanceState(listState)
            if (fullListVM?.hasLoadMore()!! && !fullListVM?.isInNestedScroll()!!) {
                recyclerView.clearOnScrollListeners()
                recyclerView.addOnScrollListener(fullListVM?.getScrollListener(recyclerView.layoutManager!!)!!)
            }
        }
    }


    /***************************** prepare view ******************************/
    fun getRecyclerOnly(inflater: LayoutInflater, container: ViewGroup?):View{
        val view = inflater.inflate(R.layout.list_layout_view2, container, false)
        this.recyclerView = view.findViewById(R.id.list)
        return view
    }

    fun getRefreshRecycler(inflater: LayoutInflater, container: ViewGroup?):View{
        val view = inflater.inflate(R.layout.list_layout_view, container, false)
        this.recyclerView = view.findViewById(R.id.list)
        val refreshLayout: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout)
        refreshLayout.isEnabled = true
        refreshLayout.setOnRefreshListener {
            resetVMData()
            fullListVM?.onRefresh()
            refreshLayout.isRefreshing = false
        }
        return view
    }

    private fun prepareData() {
        fullListVM?.getLiveDataList()?.observe(this, Observer<ArrayList<Any>> { objects ->

            val oldList: ArrayList<Any> = ArrayList(fullListVM?.getAdaptee()?.getList())
            fullListVM?.getAdaptee()?.setList(ArrayList(objects))
            disposable = Flowable.fromArray<ArrayList<Any>>(objects)
                .map<DiffUtil.DiffResult> { it -> DiffUtil.calculateDiff(GenericlistDiffUtils(oldList, it)) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe { diffResult ->
                    if (oldList.size <= 0) {
                        if (fullListVM?.hasLoadMore()!! && !fullListVM?.isInNestedScroll()!!) {
                            recyclerView.clearOnScrollListeners()
                            recyclerView.addOnScrollListener(fullListVM?.getScrollListener(recyclerView.layoutManager!!)!!)
                        }
                        adapter.notifyDataSetChanged()
                    } else {
                        recyclerView.recycledViewPool.clear()
                        diffResult.dispatchUpdatesTo(adapter)
                    }
                    this.fullListVM?.isLoading = false
                }
        })
        if (listState == null) {
            recyclerCallback = RecyclerListIm(this, fullListVM?.getListOp()!!)
            recyclerCallback?.setLayoutManager(this.fullListVM?.getListLayoutManager(context!!)!!)
        }

    }

    private fun prepareList() {
        if (fullListVM?.attachSnapHelper()!!) {
            fullListVM?.getSnapHelper()
                ?.attachToRecyclerView(recyclerView)
        }
        adapter = BaseAdapter(fullListVM?.getAdaptee(), context)
        adapter.setIEmpty(this)
        recyclerView.adapter = adapter


        if (bundle.list != null)
            fullListVM?.getListOp()?.setList(bundle.list!!)
        if (recyclerCallback?.getLayoutManager() != null)
            recyclerView.layoutManager = recyclerCallback?.getLayoutManager()
    }

    private fun getBundle() {
        bundle = arguments!!.getSerializable(dataKey) as ArchitectureList.ListBundle
        currentVMclassName = bundle.className


    }

    @Suppress("UNCHECKED_CAST")
    private fun getVMClass(name: String): Class<FullListVM<Any, Any, IListCallback>>? {
        val clazz: Class<*>?
        try {
            clazz = Class.forName(name)

            return clazz as Class<FullListVM<Any, Any, IListCallback>>?
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


    fun setListReadyCallback(callback: ArchitectureList.ListReadyCallbak) {
        this.listReadyCallback = callback
    }

    private var isEmpty = false;
    override fun setEmpty(isEmpty: Boolean?) {
//        recyclerView.visibility = if(isEmpty!!)View.GONE else View.VISIBLE
//        val error  = PreferenceModule.getInstance(context!!).errorId
//        if(isEmpty) {
//            val errorView :View
//            if (error != -1) {
//                errorView = LayoutInflater.from(context).inflate(error!!, container, false)
//            }else{
//                errorView = TextView(context).apply { text = "Empty" }
//            }
//                if (fullListVM?.hasSwipeToRefresh()!!) {
//                    errorView.layoutParams = refreshLayout.layoutParams
//                } else {
//                    errorView.layoutParams = recyclerView.layoutParams
//                }
//                errorView.id = 555
//                container.addView(errorView)
//                this.isEmpty = true
//        }else{
//            val errorView  = container.findViewById<View>(555)
//            container.removeView(errorView)
//            this.isEmpty = false
//        }
    }

    override fun isEmpty(): Boolean? {
        return isEmpty
    }



    override fun onStop() {
        disposable?.dispose()
        recyclerView.layoutManager = null
        super.onStop()
    }

    /*************************************************************/
    //Save List State
    private val listStateKey = "recycler_list_state"
    private var listState: Parcelable? = null

    override fun onSaveInstanceState(outState: Bundle) {
        if (recyclerView.layoutManager == null) {
            super.onSaveInstanceState(outState)
            return
        }
        listState = recyclerView.layoutManager?.onSaveInstanceState()
        outState.putParcelable(listStateKey, listState)
        super.onSaveInstanceState(outState)

    }
    /*************************************************/
}

interface  IEmpty{
    fun setEmpty(isEmpty: Boolean?)
    fun isEmpty():Boolean?
}

