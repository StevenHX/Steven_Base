package com.hx.stevenbase.ui.Set.talk

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.animation.AlphaInAnimation
import com.google.gson.Gson
import com.hx.steven.fragment.BaseLazyFragment
import com.hx.stevenbase.R
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.talk_fragment.*
import kotlinx.android.synthetic.main.talk_fragment.view.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class talkFragmentKt : BaseLazyFragment() {
    init {
        setEnableMultiple(false)
    }

    override fun getContentId(): Int {
        return R.layout.talk_fragment
    }

    override fun onFirstUserVisible() {
    }

    override fun onUserVisible() {
    }

    override fun onFirstUserInvisible() {
    }

    override fun onUserInvisible() {
    }

    override fun initView(view: View?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBanner()
        initRecycleView()
        initArticleData()
    }

    var images: MutableList<String> = mutableListOf()

    private fun initBanner() {
        requireView().home_banner.setIndicatorGravity(BannerConfig.CENTER)
        home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        images.add("https://wanandroid.com/blogimgs/8690f5f9-733a-476a-8ad2-2468d043c2d4.png")
        images.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png")
        images.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png")
        home_banner.setImages(images)
                .setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                        Glide.with(context!!)
                                .load(path)
                                .into(imageView!!)
                    }
                })
                .start()
    }

    private var homeAdapter: TalkAdapter? = null
    private var articleList = mutableListOf<Data>()
    private fun initRecycleView() {
        rvHomeList.layoutManager = LinearLayoutManager(getContext())
        homeAdapter = TalkAdapter(R.layout.home_recycle_item, articleList)
        homeAdapter?.adapterAnimation = AlphaInAnimation()
        rvHomeList.adapter = homeAdapter
    }

    private fun file2JsonStr(fileName: String): String? {
        val stringBuilder = StringBuilder()
        try {
            val isr = InputStreamReader(resources.assets.open(fileName))
            val bf = BufferedReader(isr)
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
            bf.close()
            isr.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun initArticleData() {
        val originStr = file2JsonStr("home_articles.json")
        val data: ArticleBean
        data = Gson().fromJson<ArticleBean>(originStr, ArticleBean::class.java)
        articleList.addAll(data.datas)
        println(articleList)
        homeAdapter?.addData(articleList)
    }
}