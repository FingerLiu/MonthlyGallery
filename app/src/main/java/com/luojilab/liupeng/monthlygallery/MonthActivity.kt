package com.luojilab.liupeng.monthlygallery

import android.Manifest
import android.app.PendingIntent.getActivity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.LoaderManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.MemoryCategory
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.luojilab.liupeng.monthlygallery.MonthActivity.Companion.DATE_REPR
import com.luojilab.liupeng.monthlygallery.R.id.galleryRecyclerView
import com.luojilab.liupeng.monthlygallery.R.id.monthTextView
import kotlinx.android.synthetic.main.activity_month.*


class MonthActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<List<MediaStoreData>> {


    private val REQUEST_READ_STORAGE = 0

    companion object {
        const val DATE_REPR = "MONTH YEAR"
    }

    val pics: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)

        // 显示标题
        showDate()

        GlideApp.get(this).setMemoryCategory(MemoryCategory.HIGH)

        // 暂时只支持 API 23 及以上
        requestStoragePermission()
        loadPics()

        galleryRecyclerView.setHasFixedSize(true)
        galleryRecyclerView.layoutManager = GridLayoutManager(this, 3)
        galleryRecyclerView.adapter = MonthAdapter(pics, this)

        LoaderManager.getInstance(this).initLoader(R.id.loader_id_media_store_data, null, this)
    }

    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<List<MediaStoreData>> {
        return MediaStoreDataLoader(this)
    }

    override fun onLoadFinished(loader: Loader<List<MediaStoreData>>,
                                mediaStoreData: List<MediaStoreData>) {
        val glideRequests = GlideApp.with(this)
        val adapter = RecyclerAdapter(this, mediaStoreData, glideRequests)
        val preloader = RecyclerViewPreloader(glideRequests, adapter, adapter, 3)
        galleryRecyclerView.addOnScrollListener(preloader)
        galleryRecyclerView.adapter = adapter
    }

    override fun onLoaderReset(loader: Loader<List<MediaStoreData>>) {
        // Do nothing.
    }

    fun showDate() {
        val date = intent.getStringExtra(DATE_REPR)
        monthTextView.text = date
    }

    fun loadPics() {
        pics.add("pic1")
        pics.add("pic2")
        pics.add("pic3")
        pics.add("pic4")
        pics.add("pic5")
        pics.add("pic6")
        pics.add("pic7")
        pics.add("pic8")
        pics.add("pic9")
        pics.add("pic10")
        pics.add("pic11")
        pics.add("pic12")
        pics.add("pic13")
        pics.add("pic14")
        pics.add("pic15")
        pics.add("pic16")
        pics.add("pic17")
        pics.add("pic18")
        pics.add("pic19")
        pics.add("pic20")
        pics.add("pic21")
        pics.add("pic22")
        pics.add("pic23")
        pics.add("pic24")
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_STORAGE)
    }
}