package com.example.challenge_2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart_Table")
data class CartChart(
    @PrimaryKey(autoGenerate = true)
    var itemId: Long? = null,

    @ColumnInfo(name = "item_menu")
    var itemMenu: String? = null,

    @ColumnInfo(name = "item_harga")
    var itemHarga: String? = null,

    @ColumnInfo(name = "item_harga_satuan")
    var itemHargaSatuan: Int? = -1,

    @ColumnInfo(name = "item_gambar")
    var itemGambar: String? = null,

    @ColumnInfo(name = "item_quantity")
    var itemQuantity: Int = -1,

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false
){
    companion object {
        const val TABLE_NAME = "Cart_Table"
    }
}
