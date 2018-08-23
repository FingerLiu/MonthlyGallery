package com.luojilab.liupeng.monthlygallery
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_month_item.view.*
import java.time.Month

class MonthAdapter(val items: ArrayList<String>, val context:
Context): RecyclerView.Adapter<MonthViewHolder>() {

    // Gets the number of pics in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_month_item, parent, false))
    }

    // Binds each pic  in the ArrayList to a view
    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        holder?.pic?.text = items[position]
    }
}

class MonthViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val pic = view.tv_pic_name

    init {
        view.setOnClickListener { v: View ->
            Toast.makeText(v.context, "pic" + pic.text.toString() + " clicked.",
                    Toast.LENGTH_SHORT).show()
        }
    }
}
