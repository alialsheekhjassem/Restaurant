package magma.abikarshak.restaurant.presentation.home.ui.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.ItemFoodTypeBinding
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemFoodListener

class FoodTypeAdapter :
    ListAdapter<Restaurant, FoodTypeAdapter.MyViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    //var dynamicView = false

    private lateinit var listener: RecyclerItemFoodListener<Restaurant>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemFoodTypeBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    fun setListener(listener: RecyclerItemFoodListener<Restaurant>) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(val binding: ItemFoodTypeBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Restaurant) {
            binding.item = item
            val colorPrimary = ContextCompat.getColor(binding.root.context, R.color.colorPrimary)
            val colorYellow = ContextCompat.getColor(binding.root.context, R.color.yellow)
            if (bindingAdapterPosition % 2 == 0)
                binding.cardParent.setCardBackgroundColor(colorPrimary)
            else binding.cardParent.setCardBackgroundColor(colorYellow)
            binding.executePendingBindings()
            Log.d("TAG", "AAA onClick: " + item.deletedDate)
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val item = getItem(bindingAdapterPosition)
            listener.onItemClicked(item, bindingAdapterPosition)
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

