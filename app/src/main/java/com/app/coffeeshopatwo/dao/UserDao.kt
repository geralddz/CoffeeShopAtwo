package com.app.coffeeshopatwo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.coffeeshopatwo.entity.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username LIKE :username AND password LIKE :password")
    fun loginUser(username: String, password: String) : LiveData<User>

    @Insert
    fun register(user: User)
}