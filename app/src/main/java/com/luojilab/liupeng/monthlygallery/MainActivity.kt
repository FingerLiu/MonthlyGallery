package com.luojilab.liupeng.monthlygallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*

data class GalleryDate(val month: String, val year: String)

class MainActivity : AppCompatActivity() {

    val galleries: ArrayList<GalleryDate> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGalleries()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.adapter = GalleryAdapter(galleries, this)
    }

   fun addGalleries() {
        galleries.add(GalleryDate("01", "2018"))
        galleries.add(GalleryDate("02", "2018"))
        galleries.add(GalleryDate("03", "2018"))
        galleries.add(GalleryDate("04", "2018"))
        galleries.add(GalleryDate("05", "2018"))
        galleries.add(GalleryDate("06", "2018"))
        galleries.add(GalleryDate("07", "2018"))
        galleries.add(GalleryDate("08", "2018"))
        galleries.add(GalleryDate("09", "2018"))
        galleries.add(GalleryDate("10", "2018"))
        galleries.add(GalleryDate("11", "2018"))
        galleries.add(GalleryDate("12", "2018"))
        galleries.add(GalleryDate("01", "2017"))
        galleries.add(GalleryDate("02", "2017"))
        galleries.add(GalleryDate("03", "2017"))
        galleries.add(GalleryDate("04", "2017"))
        galleries.add(GalleryDate("05", "2017"))
        galleries.add(GalleryDate("06", "2017"))
        galleries.add(GalleryDate("07", "2017"))
        galleries.add(GalleryDate("08", "2017"))
        galleries.add(GalleryDate("09", "2017"))
        galleries.add(GalleryDate("10", "2017"))
        galleries.add(GalleryDate("11", "2017"))
        galleries.add(GalleryDate("12", "2017"))
    }
}
