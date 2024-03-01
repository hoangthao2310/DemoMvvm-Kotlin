package com.example.kotlinmvvm.view


import android.os.Bundle
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvm.FoodDetailFragment
import com.example.kotlinmvvm.OnItemClickListener
import com.example.kotlinmvvm.adapter.FoodAdapter
import com.example.kotlinmvvm.base.BaseFragment
import com.example.kotlinmvvm.databinding.FragmentFoodBinding
import com.example.kotlinmvvm.model.Food
import com.example.kotlinmvvm.model.FoodType
import com.example.kotlinmvvm.view.dialog.DeleteDialog
import com.example.kotlinmvvm.view.dialog.OnClickListener
import com.example.kotlinmvvm.viewmodel.FoodViewModel

class FoodFragment : BaseFragment<FragmentFoodBinding>() {

    private lateinit var viewModel: FoodViewModel
    private lateinit var foodType: FoodType
    private lateinit var foodAdapter: FoodAdapter

    override fun getLayout(container: ViewGroup?): FragmentFoodBinding =
        FragmentFoodBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodType = data as FoodType
        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        viewModel.getFood(foodType.typeId)
    }

    override fun initViews() {
        binding.tvFood.text = foodType.name

        binding.btnAdd.setOnClickListener {
            callback.showFragment(
                FoodFragment::class.java,
                AddFoodFragment::class.java,
                0,
                0,
                foodType,
                true
            )
        }

        viewModel.listFood.observe(viewLifecycleOwner){ listFood ->
            if(listFood != null){
                val onItemClickListener = object : OnItemClickListener{
                    override fun onItemClick(data: Any?) {
                        val food = data as Food
                        callback.showFragment(
                            FoodFragment::class.java,
                            FoodDetailFragment::class.java,
                            0,
                            0,
                            food,
                            true
                        )
                    }

                    override fun onItemDeleteClick(data: Any?) {
                        if (data is Food) {
                            val food = data
                            val dialog = DeleteDialog(object: OnClickListener {
                                override fun onClick() {
                                    viewModel.deleteFood(food.foodId.toString(), foodType.typeId.toString())
                                    val position = foodAdapter.listFood.indexOf(food)
                                    foodAdapter.removeItem(position)
                                }

                                override fun onClick(name: String, price: Double, image: String) {

                                }
                            })
                            dialog.show(requireActivity().supportFragmentManager, "delete_food_dialog")
                        } else {
                            notify("Lỗi dữ liệu")
                        }
                    }
                }
                foodAdapter = FoodAdapter(listFood, onItemClickListener)
                binding.rcvFood.adapter = foodAdapter
            }
        }

        binding.btnBack.setOnClickListener {
            callback.showFragment(
                FoodFragment::class.java,
                FoodTypeFragment::class.java,
                0,
                0,
                null,
                false
            )
        }
    }
}