package com.luojilab.liupeng.monthlygallery

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_month.*


class MonthActivity : AppCompatActivity() {

    companion object {
        const val DATE_REPR = "MONTH YEAR"
    }

    val pics: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_month)
        showDate()
        loadPics()
        galleryRecyclerView.setHasFixedSize(true)
        galleryRecyclerView.layoutManager = GridLayoutManager(this, 3)

        galleryRecyclerView.adapter = MonthAdapter(pics, this)
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
}