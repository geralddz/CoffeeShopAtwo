package com.app.coffeeshopatwo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.coffeeshopatwo.entity.Transaction

@Dao
interface TransactionDao  {
    @Query("SELECT * FROM `Transaction`  WHERE user_id = :userid")
    fun getTransaction(userid : Int): List<Transaction>

    @Insert
    fun insertTransaction(transaction: Transaction)
}