package com.app.coffeeshopatwo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Menu(
    @PrimaryKey(autoGenerate = true)
    var menu_id: Int? = null,

    @ColumnInfo("Nama Menu")
    var nama: String? = null,

    @ColumnInfo("Harga")
    var harga: Int? = null,

    @ColumnInfo("Deskripsi")
    var deskripsi: String? = null,

    @ColumnInfo("Foto")
    var foto: String? = null
)