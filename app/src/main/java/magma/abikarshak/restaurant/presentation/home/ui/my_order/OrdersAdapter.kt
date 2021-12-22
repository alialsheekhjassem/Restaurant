package magma.abikarshak.restaurant.presentation.home.ui.my_order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import magma.abikarshak.restaurant.databinding.ItemOrderBinding
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemListener

class OrdersAdapter :
    ListAdapter<Restaurant, OrdersAdapter.MyViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    //var dynamicView = false

    private lateinit var listener: RecyclerItemListener<Restaurant>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemOrderBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)

    }

    fun setListener(listener: RecyclerItemListener<Restaurant>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        /*Glide.with(context).load(currentList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop()
            .into(holder.binding.imgRestaurant).getSize { width, height ->
                val aspectRatio = width.toFloat() / height.toFloat()
                holder.binding.imgRestaurant.setAspectRatio(aspectRatio)
                if (dynamicView) {
                    val lp = ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    holder.binding.imgRestaurant.layoutParams = lp
                } else {
                    val imageHeight = context.resources.getDimension(R.dimen.image_height)
                    val lp = ConstraintLayout.LayoutParams(MATCH_PARENT, imageHeight.toInt())
                    holder.binding.imgRestaurant.layoutParams = lp
                }
            }*/
        holder.bind(item)
    }

    inner class MyViewHolder(val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Restaurant) {
            binding.item = item
            binding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClicked(getItem(bindingAdapterPosition), bindingAdapterPosition)
        }
    }

    class NewsDiffCallBacks : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(
            oldItem: Restaurant,
            newItem: Restaurant
        ): Boolean {
            return newItem.description == oldItem.description
        }
    }


    override fun getItemCount() = currentList.size

}

