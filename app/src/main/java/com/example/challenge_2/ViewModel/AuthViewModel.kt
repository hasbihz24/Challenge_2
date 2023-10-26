package com.example.challenge_2.ViewModel

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge_2.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val curruntUser = MutableLiveData<FirebaseUser>()
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://challenge-binar-152a8-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var userRef: DatabaseReference = database.reference.child("user")
    private val curentUserEmail: String? = auth.currentUser?.email
    private val query = userRef.orderByChild("email").equalTo(curentUserEmail)
    init {
        curruntUser.value = auth.currentUser
    }

    fun getCurrentUser() : LiveData<FirebaseUser>{
        return curruntUser
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun signOut(){
        auth.signOut()
        curruntUser.value = null
    }

   fun fetchData(callback: (User) -> Unit){
       query.addListenerForSingleValueEvent(object: ValueEventListener{
           override fun onDataChange(dataSnapshot: DataSnapshot) {
               if (dataSnapshot.exists()) {
                   for (userSnapshot in dataSnapshot.children) {
                       var user = userSnapshot.getValue(User::class.java)
                       if (user != null) {
                           callback(user)
                           return
                       }

                   }
               }
           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })
   }
    fun editData(myUser : User, callback: (Boolean) -> Unit){
        val id = myUser.userId
        val newUser = myUser.toMap()

        val userRef = userRef.child(id)
        userRef.updateChildren(newUser)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener{
                callback(false)
            }

    }


}