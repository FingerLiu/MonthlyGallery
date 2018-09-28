package com.luojilab.liupeng.monthlygallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.MonthDisplayHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_item.view.*
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GalleryAdapter(val items: ArrayList<GalleryDate>, val context:
Context): RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of pics in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item, parent, false))
    }

    // Binds each pic  in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.month?.text = items[position].month
        holder?.year?.text = items[position].year
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val month = view.tv_month
    val year = view.tv_year

  init {
        view.setOnClickListener {v: View ->
            Toast.makeText(v.context,
                    "month " + month.text.toString() + " of year " + year.text.toString(),
                    Toast.LENGTH_SHORT).show()
//            val dateString = String.format("%s-%s-01", year.text.toString(), month.text.toString())
//            val startDate = LocalDate.parse(dateString)
//            val endDate = startDate.plusMonths(1)
//            val  DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//            val startTimestap : Timestamp = Timestamp.valueOf(startDate.atStartOfDay().format(DATE_TME_FORMATTER))
//            val endTimestap : Timestamp = Timestamp.valueOf(endDate.atStartOfDay().format(DATE_TME_FORMATTER))

            val fromDateVar = Calendar.getInstance()
            fromDateVar.set(year.text.toString().toInt(), month.text.toString().toInt() - 1, 1)

            val toDateVar = Calendar.getInstance()
            if (month.text.toString().toInt()<12) {
                toDateVar.set(year.text.toString().toInt(), month.text.toString().toInt(), 1)
            }
            else {
                toDateVar.set(year.text.toString().toInt() + 1, 0, 1)
            }

            // Create an Intent to start the second activity
            val monthIntent = Intent(v.context, MonthActivity::class.java)
            monthIntent.putExtra(MonthActivity.DATE_REPR, month.text.toString()+"."+year.text.toString())
            monthIntent.putExtra(MonthActivity.DATE_FROM, fromDateVar.timeInMillis.toString())
            monthIntent.putExtra(MonthActivity.DATE_TO, toDateVar.timeInMillis.toString())

            // Start the new activity.
            startActivity(v.context, monthIntent, null)
        }
    }
}
