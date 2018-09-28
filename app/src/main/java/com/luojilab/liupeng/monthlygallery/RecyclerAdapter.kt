package com.luojilab.liupeng.monthlygallery

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.signature.MediaStoreSignature
import com.bumptech.glide.util.Preconditions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_month_item.view.*

internal class RecyclerAdapter(context: Context, private val data: List<MediaStoreData>,
                               glideRequests: GlideRequests) : RecyclerView.Adapter<RecyclerAdapter.ListViewHolder>(),
        ListPreloader.PreloadSizeProvider<MediaStoreData>, ListPreloader.PreloadModelProvider<MediaStoreData> {
    private val screenWidth: Int
    private val requestBuilder: GlideRequest<Drawable>

    private var actualDimensions: IntArray? = null

    init {
        requestBuilder = glideRequests.asDrawable().fitCenter()

        setHasStableIds(true)

        screenWidth = getScreenWidth(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(R.layout.activity_month_item, viewGroup, false)
        view.layoutParams.width = screenWidth

        if (actualDimensions == null) {
            view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    if (actualDimensions == null) {
                        actualDimensions = intArrayOf(view.width, view.height)
                    }
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
        }

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        val current = data[position]

        val signature = MediaStoreSignature(current.mimeType, current.dateModified, current.orientation)

        requestBuilder
                .clone()
                .signature(signature)
                .load(current.uri)
                .into(viewHolder.itemView.item_img)
    }

    override fun getItemId(position: Int): Long {
        return data[position].rowId
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun getPreloadItems(position: Int): List<MediaStoreData> {
        return listOf(data[position])
    }

    override fun getPreloadRequestBuilder(item: MediaStoreData): RequestBuilder<Drawable>? {
        val signature = MediaStoreSignature(item.mimeType, item.dateModified, item.orientation)
        return requestBuilder
                .clone()
                .signature(signature)
                .load(item.uri)
    }

    override fun getPreloadSize(item: MediaStoreData, adapterPosition: Int,
                                perItemPosition: Int): IntArray? {
        return actualDimensions
    }

    // Display#getSize(Point)
    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = Preconditions.checkNotNull(wm).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    /**
     * ViewHolder containing views to display individual [ ].
     */
    internal class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image: ImageView

        init {
            image = itemView.findViewById(R.id.item_img)
        }
    }
}
