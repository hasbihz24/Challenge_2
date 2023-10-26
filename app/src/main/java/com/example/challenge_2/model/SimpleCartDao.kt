package com.example.challenge_2.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.challenge_2.model.CartChart

@Dao
interface SimpleCartDao {
    @Insert
    fun insert(chart: CartChart)

    @Query("SELECT * FROM Cart_Table ORDER BY itemId DESC")
    fun getAllItem(): LiveData<List<CartChart>>

    @Query("SELECT * FROM Cart_Table WHERE item_menu = :newName ORDER BY itemId DESC")
    fun getAllItemByName(newName: String?): LiveData<List<CartChart>>

    @Delete
    fun delete(chart: CartChart)

    @Query("DELETE FROM Cart_Table WHERE itemId = :itemIdParams")
    fun delteByItemId(itemIdParams: Long?)

    @Update
    fun update(chart: CartChart)

    @Query("UPDATE Cart_Table SET item_quantity = :newQuantity, item_harga = :newHarga where itemId = :itemIdParams")
    fun  updateQuantityByItemId(newQuantity: Int,newHarga: String, itemIdParams: Long?)

    @Query("SELECT SUM(item_harga) FROM Cart_Table")
    fun  getPriceCount(): LiveData<Int>
}