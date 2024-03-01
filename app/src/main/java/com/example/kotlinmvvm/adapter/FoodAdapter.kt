package com.example.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinmvvm.OnItemClickListener
import com.example.kotlinmvvm.databinding.ItemFoodBinding
import com.example.kotlinmvvm.model.Food

class FoodAdapter(
    var listFood: ArrayList<Food>,
    private var itemClick: OnItemClickListener
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(private val binding: ItemFoodBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(food : Food){
            binding.txtFood.text = food.nameFood
            binding.txtPrice.text = food.price.toString()
            Glide.with(itemView.context).load(food.image).into(binding.imgFood)
        }

        init{
            binding.rlFood.setOnClickListener {
                itemClick.onItemClick(listFood[adapterPosition])
            }
            binding.btnDelete.setOnClickListener {
                itemClick.onItemDeleteClick(listFood[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFood.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(listFood[position])
    }

    fun removeItem(position: Int) {
        listFood.removeAt(position)
        notifyItemRemoved(position)
    }

}