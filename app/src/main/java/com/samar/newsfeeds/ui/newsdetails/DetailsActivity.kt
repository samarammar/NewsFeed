package com.samar.newsfeeds.ui.newsdetails

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviPresenter
import com.newsdomain.state.BaseVS
import com.samar.newsfeeds.R
import com.samar.newsfeeds.base.IBaseMVI
import com.samar.newsfeeds.base.activity.BaseMVIActivity
import com.samar.newsfeeds.base.activity.BasePresenter
import com.samar.newsfeeds.databinding.ActivityMainBinding
import com.samar.newsfeeds.databinding.LayoutNewsFeedDetailsBinding
import com.samar.newsfeeds.ui.mainscreen.INewsFeedsList
import com.samar.newsfeeds.ui.mainscreen.NewsPresenter
import com.google.gson.Gson
import com.newsdomain.model.Article


class DetailsActivity : BaseMVIActivity<IBaseMVI, MviPresenter<IBaseMVI,*>>(), IBaseMVI {
    override fun render(baseVS: BaseVS) {
    }

    companion object {

        fun open(activity: Activity,model:String) {
            val intent = Intent(activity, DetailsActivity::class.java)
            intent.putExtra("model", model)
            activity.startActivity(intent)
        }
    }

    lateinit var binding: LayoutNewsFeedDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        application().applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.layout_news_feed_details)

        val gson = Gson()
        val article = gson.fromJson(intent.getStringExtra("model"), Article::class.java)

        binding.model=article

        binding.openWebsite.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(browserIntent)
        }


    }



}