package com.luojilab.liupeng.monthlygallery

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable


/**
 * A data model containing data for a single media item.
 */
class MediaStoreData : Parcelable {

    internal val rowId: Long
    internal val uri: Uri
    internal val mimeType: String
    internal val dateModified: Long
    internal val orientation: Int
    private val type: Type
    internal val dateTaken: Long

    override fun describeContents(): Int {
        return 0
    }

    internal constructor(rowId: Long, uri: Uri, mimeType: String, dateTaken: Long, dateModified: Long,
                         orientation: Int, type: Type) {
        this.rowId = rowId
        this.uri = uri
        this.dateModified = dateModified
        this.mimeType = mimeType
        this.orientation = orientation
        this.type = type
        this.dateTaken = dateTaken
    }

    private constructor(`in`: Parcel) {
        rowId = `in`.readLong()
        uri = Uri.parse(`in`.readString())
        mimeType = `in`.readString()
        dateTaken = `in`.readLong()
        dateModified = `in`.readLong()
        orientation = `in`.readInt()
        type = Type.valueOf(`in`.readString())
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeLong(rowId)
        parcel.writeString(uri.toString())
        parcel.writeString(mimeType)
        parcel.writeLong(dateTaken)
        parcel.writeLong(dateModified)
        parcel.writeInt(orientation)
        parcel.writeString(type.name)
    }

    override fun toString(): String {
        return ("MediaStoreData{"
                + "rowId=" + rowId
                + ", uri=" + uri
                + ", mimeType='" + mimeType + '\''.toString()
                + ", dateModified=" + dateModified
                + ", orientation=" + orientation
                + ", type=" + type
                + ", dateTaken=" + dateTaken
                + '}'.toString())
    }

    /**
     * The type of data.
     */
    enum class Type {
        VIDEO,
        IMAGE
    }

    companion object {
        val CREATOR: Parcelable.Creator<MediaStoreData> = object : Parcelable.Creator<MediaStoreData> {
            override fun createFromParcel(parcel: Parcel): MediaStoreData {
                return MediaStoreData(parcel)
            }

            override fun newArray(i: Int): Array<MediaStoreData?> {
                // TODO
                return arrayOfNulls(i)
            }
        }
    }
}
