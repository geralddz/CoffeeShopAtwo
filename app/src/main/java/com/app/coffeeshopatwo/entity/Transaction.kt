package com.app.coffeeshopatwo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var trans_id: Int? = null,

    @ColumnInfo("User_id")
    var uid: String? = null,

    @ColumnInfo("Tanggal")
    var date: String? = null,

    @ColumnInfo("Menu")
    var nama: String? = null,

    @ColumnInfo("Qty")
    var qty: Int? = null,

    @ColumnInfo("Total")
    var total: Int? = null,

    @ColumnInfo("Status")
    var status: String? = null,

    @ColumnInfo("Photo")
    var foto: String? = null

)
