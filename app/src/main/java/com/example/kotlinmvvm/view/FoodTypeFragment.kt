package com.example.kotlinmvvm.view

import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm.OnItemClickListener
import com.example.kotlinmvvm.adapter.FoodAdapter
import com.example.kotlinmvvm.adapter.FoodTypeAdapter
import com.example.kotlinmvvm.base.BaseFragment
import com.example.kotlinmvvm.databinding.FragmentFoodTypeBinding
import com.example.kotlinmvvm.model.FoodType
import com.example.kotlinmvvm.viewmodel.FoodViewModel
import java.lang.Appendable


class FoodTypeFragment : BaseFragment<FragmentFoodTypeBinding>() {

    private lateinit var viewModel: FoodViewModel

    override fun getLayout(container: ViewGroup?): FragmentFoodTypeBinding =
        FragmentFoodTypeBinding.inflate(layoutInflater, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        viewModel.getFoodType()
    }

    override fun initViews() {
        viewModel.listFoodType.observe(viewLifecycleOwner){ listFoodType->
            if(listFoodType != null){
                val onItemClickListener = object : OnItemClickListener{
                    override fun onItemClick(data: Any?) {
                        val foodType = data as FoodType
                        callback.showFragment(
                            FoodTypeFragment::class.java,
                            FoodFragment::class.java,
                            0,
                            0,
                            foodType,
                            true
                        )
                    }

                    override fun onItemDeleteClick(data: Any?) {
                    }
                }
                binding.rcvType.adapter = FoodTypeAdapter(listFoodType, onItemClickListener)
            }
        }
    }


}