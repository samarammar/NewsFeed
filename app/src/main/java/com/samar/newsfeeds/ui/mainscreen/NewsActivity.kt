package com.samar.newsfeeds.ui.mainscreen

import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import com.google.gson.Gson
import com.newsdomain.model.Article
import com.newsdomain.state.BaseVS
import com.newsdomain.statesresult.ArticlesResult
import com.samar.newsfeeds.R
import com.samar.newsfeeds.base.IBaseMVI
import com.samar.newsfeeds.base.activity.BaseActivity
import com.samar.newsfeeds.base.activity.BaseMVIActivity
import com.samar.newsfeeds.databinding.ActivityMainBinding
import com.samar.newsfeeds.ui.newsdetails.DetailsActivity
import com.samar.newsfeeds.utils.getActivity
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState
import com.trippl3dev.listlibrary.implementation.ArchitectureList
import com.trippl3dev.listlibrary.implementation.RecyclerListIm
import com.trippl3dev.listlibrary.interfaces.States
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsActivity : BaseMVIActivity<IBaseMVI, NewsPresenter>(),IBaseMVI,INewsFeedsList, NavigationView.OnNavigationItemSelectedListener{

//    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.action_explore -> Toast.makeText(this, "Explore", Toast.LENGTH_SHORT).show()
            R.id.action_msg -> Toast.makeText(this, "Live Chat", Toast.LENGTH_SHORT).show()
            R.id.action_gallary -> Toast.makeText(this, "Gallary", Toast.LENGTH_SHORT).show()
            R.id.action_heart -> Toast.makeText(this, "Wish List", Toast.LENGTH_SHORT).show()
            R.id.action_magazine -> Toast.makeText(this, "E-Magazine", Toast.LENGTH_SHORT).show()
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onItemClick(positon: Int, item: Article) {
        val gson = Gson()
        DetailsActivity.open(getActivity()!!, gson.toJson(item))
    }

    @Inject
    lateinit var newsPresenter: NewsPresenter

    override fun createPresenter(): NewsPresenter = newsPresenter

    private var newsFeedsHolder: RecyclerListIm? = null


    override fun render(baseVS: BaseVS) {
        when (baseVS) {
            is BaseVS.Loading -> {
                newsFeedsHolder?.setState(States.LOADING)
            }
            is BaseVS.Error->{
                newsFeedsHolder?.setState(States.ERROR)
            }
            is ArticlesResult -> {
                newsFeedsHolder?.setState(States.NORMAL)

                newsFeedsHolder?.operation?.setList(
                        baseVS.articleState.map {
                            Article("By "+it.author, it.description,
                                    if(it.publishedAt.contains("T")){
                                        formatDateFromDateString("yyyy-MM-dd","MMMM dd, yyyy",it.publishedAt.split("T").get(0))
                                    }else{it.publishedAt}, it.title, it.url,it.urlToImage)
                        }
                )
            }
        }
    }

    @Throws(ParseException::class)
    fun formatDateFromDateString(inputDateFormat: String, outputDateFormat: String, inputDate: String): String {
        val mParsedDate: Date
        val mOutputDateString: String
        val mInputDateFormat = SimpleDateFormat(inputDateFormat, Locale.getDefault())
        val mOutputDateFormat = SimpleDateFormat(outputDateFormat, java.util.Locale.ENGLISH)
        mParsedDate = mInputDateFormat.parse(inputDate)
        mOutputDateString = mOutputDateFormat.format(mParsedDate)
        return mOutputDateString

    }
    lateinit var binding: ActivityMainBinding
//    private val toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        application().applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(binding.appbar.toolbar)

        toggle = ActionBarDrawerToggle(this, binding.drawer,binding.appbar.toolbar, R.string.app_name, R.string.app_name)
        binding.drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        binding.navView.setNavigationItemSelectedListener(this)

        setRegionsList()

    }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setRegionsList() {
        ArchitectureList.get(supportFragmentManager)
                .init()
                .setVM(newsfeedsVM::class.java.name)
                .putListInContainerWithId(R.id.listContainer)
                .addListener(object : ArchitectureList.ListReadyCallbak {
                    override fun onListReady(baseListVM: RecyclerListIm?) {
                        baseListVM?.setListVMCallback(this@NewsActivity)
                        this@NewsActivity.newsFeedsHolder = baseListVM
                    }

                })
    }
}