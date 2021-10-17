package magma.global.restaurant.utils.listeners

interface RecyclerItemListener<T> {

    fun onItemClicked(item : T, index : Int)
}