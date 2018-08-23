package com.luojilab.liupeng.monthlygallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_item.*

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
        galleries.add(GalleryDate("Jan", "2018"))
        galleries.add(GalleryDate("Feb", "2018"))
        galleries.add(GalleryDate("Mar", "2018"))
        galleries.add(GalleryDate("Apr", "2018"))
        galleries.add(GalleryDate("May", "2018"))
        galleries.add(GalleryDate("Jun", "2018"))
        galleries.add(GalleryDate("Jul", "2018"))
        galleries.add(GalleryDate("Aug", "2018"))
        galleries.add(GalleryDate("Sep", "2018"))
        galleries.add(GalleryDate("Oct", "2018"))
        galleries.add(GalleryDate("Nov", "2018"))
        galleries.add(GalleryDate("Dec", "2018"))
        galleries.add(GalleryDate("Jan", "2017"))
        galleries.add(GalleryDate("Feb", "2017"))
        galleries.add(GalleryDate("Mar", "2017"))
        galleries.add(GalleryDate("Apr", "2017"))
        galleries.add(GalleryDate("May", "2017"))
        galleries.add(GalleryDate("Jun", "2017"))
        galleries.add(GalleryDate("Jul", "2017"))
        galleries.add(GalleryDate("Aug", "2017"))
        galleries.add(GalleryDate("Sep", "2017"))
        galleries.add(GalleryDate("Oct", "2017"))
        galleries.add(GalleryDate("Nov", "2017"))
        galleries.add(GalleryDate("Dec", "2017"))
    }
}
