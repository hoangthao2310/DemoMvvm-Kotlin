package com.example.kotlinmvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinmvvm.OnItemClickListener
import com.example.kotlinmvvm.databinding.ItemFoodTypeBinding
import com.example.kotlinmvvm.model.FoodType
import com.bumptech.glide.Glide

class FoodTypeAdapter(
    private var listFoodType: List<FoodType>,
    private var itemClick: OnItemClickListener
) : RecyclerView.Adapter<FoodTypeAdapter.FoodTypeViewHolder>(){

    inner class FoodTypeViewHolder(private val binding: ItemFoodTypeBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(foodType : FoodType){
            binding.txtFoodType.text = foodType.name
            Glide.with(itemView.context).load(foodType.image).into(binding.imgFoodType)
        }

        init {
            binding.rlType.setOnClickListener {
                itemClick.onItemClick(listFoodType[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodTypeViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = ItemFoodTypeBinding.inflate(inflate, parent, false)
        return FoodTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodTypeViewHolder, position: Int) {
        holder.bind(listFoodType[position])
    }

    override fun getItemCount(): Int {
        return listFoodType.size
    }

}