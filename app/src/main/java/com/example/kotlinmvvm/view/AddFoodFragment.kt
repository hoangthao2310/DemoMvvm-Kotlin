package com.example.kotlinmvvm.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlinmvvm.base.BaseFragment
import com.example.kotlinmvvm.databinding.FragmentAddFoodBinding
import com.example.kotlinmvvm.model.Food
import com.example.kotlinmvvm.model.FoodType
import com.example.kotlinmvvm.viewmodel.FoodViewModel

class AddFoodFragment : BaseFragment<FragmentAddFoodBinding>() {
    private lateinit var viewModel: FoodViewModel
    private lateinit var foodType: FoodType
    private lateinit var image: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FoodViewModel::class.java]
    }
    override fun getLayout(container: ViewGroup?): FragmentAddFoodBinding =
        FragmentAddFoodBinding.inflate(layoutInflater, container, false)

    override fun initViews() {

        foodType = data as FoodType

        binding.tvAddFood.text = "Thêm mới " + foodType.name

        binding.btnAdd.setOnClickListener {
            val foodNew = Food(
                nameFood = binding.edtNameFood.text.toString(),
                price = binding.edtPrice.text.toString().toDouble(),
            )
            viewModel.addFood(foodNew, image, foodType.typeId.toString())
            viewModel.isCheckAddFood.observe(viewLifecycleOwner){isCheck ->
                if(isCheck){
                    callback.showFragment(
                        AddFoodFragment::class.java,
                        FoodFragment::class.java,
                        0,
                        0,
                        foodType,
                        false
                    )
                    notify("Them thanh cong")
                }
            }
        }

        binding.btnSelectImage.setOnClickListener {
            pickImageGallery()
        }

        binding.btnBack.setOnClickListener {
            callback.backToPrevious()
        }
    }

    companion object{
        private const val REQUEST_CODE_PICK_IMAGE = 100
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK){
            image = data?.data!!
            Glide.with(this).load(image).into(binding.ivImageFood)
        }
    }

}