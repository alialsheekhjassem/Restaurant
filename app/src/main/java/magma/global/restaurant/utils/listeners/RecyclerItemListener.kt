package magma.global.restaurant.utils.listeners

import android.graphics.Bitmap

interface RecyclerItemListener<T> {

    fun onItemClicked(item : T, index : Int)
    fun onShareClicked(item: T, bitmapFromView: Bitmap?)
}