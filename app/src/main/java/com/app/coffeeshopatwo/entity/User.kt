package com.app.coffeeshopatwo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null,

    @ColumnInfo("Username")
    var username: String? = null,

    @ColumnInfo("Password")
    var password: String? = null
)
