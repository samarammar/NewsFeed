package com.samar.newsfeeds.base.fragment

//import com.common.ui.base.fragment.BaseListBehavior
//import com.common.ui.base.fragment.SelectedItemCallback


//import kotlin.math.absoluteValue
//
//class BaseListFragment : SBaseFragment() ,IListListenerSetter, SelectedItemCallback,IDialog {
//    override fun onItemSelected(position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun setDialogFragment(dialog: DialogFragment) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun setListener(listener: IListItemSelected<*>) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//    private lateinit var dialoge: DialogeFragment
//
//    override fun setDialogFragment(dialog: DialogFragment) {
//        this.dialoge = dialog as DialogeFragment
//    }
//
//
//    private lateinit var listListener: IListItemSelected<KeyValue<Int,String>>
//
//    override fun setListener(listener: IListItemSelected<*>) {
//        this.listListener = listener as IListItemSelected<KeyValue<Int,String>>
//    }
//
//    companion object {
//        val BASELIST = "BASELIST"
//        val BASELISTLAYOUT = "BASELISTLAYOUT"
//    }
//    var listLayout:FrameLayout? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//    override fun onItemSelected(position: Int) {
//        listListener.onItemSelected(listHolder?.operation?.getList()?.get(position) as KeyValue<Int, String>)
//    }
//
//    @SuppressLint("ResourceType")
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view  = inflater.inflate(getContainer(),container,false)
//       listLayout = view.findViewById(R.id.list)
//        addList()
//        return view
//    }
//
//
//    private var listHolder: RecyclerListIm? = null
//
//    fun getDataList():List<KeyValue<Int,String>>{
//        return arguments?.getSerializable(BASELIST) as List<KeyValue<Int, String>>
//    }
//    fun getContainer():Int{
//        return arguments?.getInt(BASELISTLAYOUT,-1)!!
//    }
//
//    override fun onResume() {
//        super.onResume()
//        (dialoge as DialogeFragment).prepareAttributes(view?.width!!,view?.height!!)
//    }
//    fun addList(){
//        PrettyList.get(childFragmentManager)
//                .init()
//                .setVM(BaseListBehavior::class.java.name)
//                .putListInContainerWithId(listLayout?.id!!)
//                .addListener(object : PrettyList.ListReadyCallbak {
//                    override fun onListReady(baseListVM: RecyclerListIm?) {
//                        listHolder = baseListVM
//                        listHolder?.setListVMCallback(this@BaseListFragment)
//                        listHolder?.addItemDecoration(
//                                DividerItemDecoration(context!!, R.drawable.line))
//                        listHolder?.operation?.setList(getDataList())
////                        dialoge.view?.visibility = View.GONE
//                        Handler().postDelayed({
//                            if(dialoge.y()!! + view?.height!! < DimensionUtils.getScreenHeight(context!!,true)){
//                                dialoge.view?.findViewById<BubbleLinearLayout>(R.id.bubble)?.setmArrowLocation(BubbleDrawable.ArrowLocation.TOP)
//                            }else{
//                                dialoge.view?.findViewById<BubbleLinearLayout>(R.id.bubble)?.setmArrowLocation(BubbleDrawable.ArrowLocation.BOTTOM)
//
//                            }
////                            dialoge.view?.visibility = View.VISIBLE
//                            (dialoge as DialogeFragment).prepareAttributes(view?.width!!,view?.height!!)
//                        },100)
//
//                    }
//                })
//    }

//}