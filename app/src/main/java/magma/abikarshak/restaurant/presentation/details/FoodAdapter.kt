package magma.abikarshak.restaurant.presentation.details

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import magma.abikarshak.restaurant.R
import magma.abikarshak.restaurant.databinding.TestBinding
import magma.abikarshak.restaurant.model.Restaurant
import magma.abikarshak.restaurant.utils.listeners.RecyclerItemFoodListener

class FoodAdapter :
    ListAdapter<Restaurant, FoodAdapter.MyViewHolder>(NewsDiffCallBacks()) {

    lateinit var context: Context
    //var dynamicView = false

    private lateinit var listener: RecyclerItemFoodListener<Restaurant>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = TestBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    fun setListener(listener: RecyclerItemFoodListener<Restaurant>) {
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

    inner class MyViewHolder(val binding: TestBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(item: Restaurant) {
            binding.item = item
            binding.executePendingBindings()
            Log.d("TAG", "AAA onClick: " + item.deletedDate)
            if (item.deletedDate!! > 0L) {
                binding.btnAdd.visibility = View.GONE
                binding.cardQuantity.visibility = View.VISIBLE
            } else {
                binding.btnAdd.visibility = View.VISIBLE
                binding.cardQuantity.visibility = View.GONE
            }
        }

        init {
            itemView.setOnClickListener(this)
            binding.btnAdd.setOnClickListener(this)
            binding.btnInc.setOnClickListener(this)
            binding.btnDec.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val item = getItem(bindingAdapterPosition)
            when (p0?.id) {
                R.id.btn_add -> {
                    item.deletedDate = item.deletedDate?.plus(1L)
                    binding.btnAdd.visibility = View.GONE
                    binding.cardQuantity.visibility = View.VISIBLE
                    binding.txtQuantity.text = item.deletedDate.toString()
                    listener.onAddClicked(item, bindingAdapterPosition)
                }
                R.id.btn_inc -> {
                    if (item.deletedDate!! < 100) {
                        item.deletedDate = item.deletedDate?.plus(1L)
                    }
                    binding.txtQuantity.text = item.deletedDate.toString()
                    listener.onIncClicked(item, bindingAdapterPosition)
                }
                R.id.btn_dec -> {
                    if (item.deletedDate!! > 0) {
                        item.deletedDate = item.deletedDate?.minus(1L)
                    }
                    if (item.deletedDate!! <= 0L) {
                        binding.btnAdd.visibility = View.VISIBLE
                        binding.cardQuantity.visibility = View.GONE
                    }
                    binding.txtQuantity.text = item.deletedDate.toString()
                    listener.onDecClicked(item, bindingAdapterPosition)
                }
                else -> {
                    listener.onItemClicked(item, bindingAdapterPosition)
                }
            }
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

    fun getItemsCount() : Int {
        var count = 0
        for (item in currentList){
            if (item.deletedDate!! > 0){
                count = count.plus(item.deletedDate!!.toInt())
            }
        }
        return count
    }

}

