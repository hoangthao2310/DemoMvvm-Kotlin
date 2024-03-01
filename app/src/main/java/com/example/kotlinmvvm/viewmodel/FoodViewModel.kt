package com.example.kotlinmvvm.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmvvm.model.Food
import com.example.kotlinmvvm.model.FoodType
import com.example.kotlinmvvm.repo.FoodRepository

class FoodViewModel(application: Application): AndroidViewModel(application) {
    private var repository: FoodRepository

    val listFoodType: MutableLiveData<ArrayList<FoodType>>
        get() = repository.getFirebaseFoodType

    val listFood: MutableLiveData<ArrayList<Food>>
        get() = repository.getFirebaseFood

    val isCheckAddFood : MutableLiveData<Boolean>
        get() = repository.isCheckFirebaseFood

    init {
        repository = FoodRepository(application)
    }

    fun getFoodType(){
        repository.listFoodType()
    }

    fun getFood(idType: String? = null){
        repository.listFood(idType)
    }

    fun addFood(food: Food, image: Uri, idType: String){
        repository.addFood(food, image, idType)
    }

    fun deleteFood(id: String, idType: String){
        repository.deleteFood(id, idType)
    }
}