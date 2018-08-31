package com.luojilab.liupeng.monthlygallery

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.AsyncTaskLoader

import java.util.ArrayList
import java.util.Collections

/**
 * Loads metadata from the media store for images and videos.
 */
class MediaStoreDataLoader internal constructor(context: Context) : AsyncTaskLoader<List<MediaStoreData>>(context) {

    private var cached: List<MediaStoreData>? = null
    private var observerRegistered = false
    private val forceLoadContentObserver = ForceLoadContentObserver()

    override fun deliverResult(data: List<MediaStoreData>?) {
        if (!isReset && isStarted) {
            super.deliverResult(data)
        }
    }

    override fun onStartLoading() {
        if (cached != null) {
            deliverResult(cached)
        }
        if (takeContentChanged() || cached == null) {
            forceLoad()
        }
        registerContentObserver()
    }

    override fun onStopLoading() {
        cancelLoad()
    }

    override fun onReset() {
        super.onReset()

        onStopLoading()
        cached = null
        unregisterContentObserver()
    }

    override fun onAbandon() {
        super.onAbandon()
        unregisterContentObserver()
    }

    override fun loadInBackground(): List<MediaStoreData>? {
        val data = queryImages()
        data.addAll(queryVideos())
        Collections.sort<MediaStoreData>(data) { mediaStoreData, mediaStoreData2 -> java.lang.Long.valueOf(mediaStoreData2.dateTaken).compareTo(mediaStoreData.dateTaken) }
        return data
    }

    private fun queryImages(): MutableList<MediaStoreData> {
        return query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_MODIFIED,
                MediaStore.Images.ImageColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.IMAGE).toMutableList()
    }

    private fun queryVideos(): MutableList<MediaStoreData> {
        return query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, VIDEO_PROJECTION,
                MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns.DATE_MODIFIED,
                MediaStore.Video.VideoColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION,
                MediaStoreData.Type.VIDEO).toMutableList()
    }

    private fun query(contentUri: Uri, projection: Array<String>, sortByCol: String,
                      idCol: String, dateTakenCol: String, dateModifiedCol: String, mimeTypeCol: String,
                      orientationCol: String, type: MediaStoreData.Type): List<MediaStoreData> {
        val data = ArrayList<MediaStoreData>()
        val cursor = context.contentResolver
                .query(contentUri, projection, null, null, "$sortByCol DESC") ?: return data

        try {
            val idColNum = cursor.getColumnIndexOrThrow(idCol)
            val dateTakenColNum = cursor.getColumnIndexOrThrow(dateTakenCol)
            val dateModifiedColNum = cursor.getColumnIndexOrThrow(dateModifiedCol)
            val mimeTypeColNum = cursor.getColumnIndex(mimeTypeCol)
            val orientationColNum = cursor.getColumnIndexOrThrow(orientationCol)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColNum)
                val dateTaken = cursor.getLong(dateTakenColNum)
                val mimeType = cursor.getString(mimeTypeColNum)
                val dateModified = cursor.getLong(dateModifiedColNum)
                val orientation = cursor.getInt(orientationColNum)

                data.add(MediaStoreData(id, Uri.withAppendedPath(contentUri, java.lang.Long.toString(id)),
                        mimeType, dateTaken, dateModified, orientation, type))
            }
        } finally {
            cursor.close()
        }

        return data
    }

    private fun registerContentObserver() {
        if (!observerRegistered) {
            val cr = context.contentResolver
            cr.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver)
            cr.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver)

            observerRegistered = true
        }
    }

    private fun unregisterContentObserver() {
        if (observerRegistered) {
            observerRegistered = false

            context.contentResolver.unregisterContentObserver(forceLoadContentObserver)
        }
    }

    companion object {
        private val IMAGE_PROJECTION = arrayOf(MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATE_TAKEN, MediaStore.Images.ImageColumns.DATE_MODIFIED, MediaStore.Images.ImageColumns.MIME_TYPE, MediaStore.Images.ImageColumns.ORIENTATION)

        private val VIDEO_PROJECTION = arrayOf(MediaStore.Video.VideoColumns._ID, MediaStore.Video.VideoColumns.DATE_TAKEN, MediaStore.Video.VideoColumns.DATE_MODIFIED, MediaStore.Video.VideoColumns.MIME_TYPE, "0 AS " + MediaStore.Images.ImageColumns.ORIENTATION)
    }
}
