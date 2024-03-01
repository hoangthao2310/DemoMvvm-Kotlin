package com.example.kotlinmvvm.repo

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlinmvvm.model.Food
import com.example.kotlinmvvm.model.FoodType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class FoodRepository(_application: Application) {
    private var listFoodType : MutableLiveData<ArrayList<FoodType>> = MutableLiveData()
    private var listFood : MutableLiveData<ArrayList<Food>> = MutableLiveData()

    private var isCheckFood: MutableLiveData<Boolean> = MutableLiveData(false)

    private lateinit var foodType: FoodType
    private lateinit var food: Food
    private var application: Application
    private var storageReference: StorageReference

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    val getFirebaseFoodType: MutableLiveData<ArrayList<FoodType>>
        get() = listFoodType

    val getFirebaseFood: MutableLiveData<ArrayList<Food>>
        get() = listFood

    val isCheckFirebaseFood: MutableLiveData<Boolean>
        get() = isCheckFood

    init {
        application = _application

        storageReference = FirebaseStorage.getInstance().getReference()
    }

    fun listFoodType(){
        db.collection("foodtype")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null){
                    val list = ArrayList<FoodType>()
                    for(document in task.result){
                        val typeId = document.id
                        val name = document.getString("name")
                        val image = document.getString("image")
                        foodType = FoodType(typeId, name, image)
                        list.add(foodType)
                    }
                    Log.d("Food Type", list.toString())
                    listFoodType.postValue(list)
                }
            }
            .addOnFailureListener {exception->
                Log.e("Food Type", "Error getting food types", exception)
            }
    }

    fun listFood(idType: String? = null){
        db.collection("foodtype/$idType/food")
            .get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful && task.result != null){
                    val list = ArrayList<Food>()
                    for(document in task.result){
                        val foodId = document.id
                        val foodName = document.getString("name")
                        val price = document.getDouble("price")
                        val image = document.getString("image")
                        food = Food(foodId, foodName, price, image)
                        list.add(food)
                    }
                    Log.d("Food", list.toString())
                    listFood.postValue(list)
                }
            }
            .addOnFailureListener {exception->
                Log.e("Food", "Error getting food", exception)
            }
    }

    fun addFood(food: Food, image: Uri, idType: String){
        val reference = storageReference.child("images/" + UUID.randomUUID().toString())
        reference.putFile(image).addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener {uri ->
                val addF: HashMap<String, Any> = HashMap()
                addF["name"] = food.nameFood.toString()
                addF["price"] = food.price.toString().toDouble()
                addF["image"] = uri.toString()
                db.collection("foodtype/$idType/food")
                    .add(addF)
                    .addOnSuccessListener {
                        isCheckFood.postValue(true)
                        Toast.makeText(application, "upload image thành công", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        isCheckFood.postValue(false)
                        Toast.makeText(application, "upload image không thành công", Toast.LENGTH_LONG).show()
                    }
            }
        }.addOnFailureListener{
            Toast.makeText(application, "upload image thành công", Toast.LENGTH_LONG).show()
        }
    }

    fun deleteFood(id: String, idType: String){
        db.collection("foodtype").document(idType).collection("food").document(id)
            .delete()
            .addOnCompleteListener {
                Log.d("Delete Food", "DocumentSnapshot successfully deleted!")
                Toast.makeText(application, "Successfully deleted", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
            }
    }
}