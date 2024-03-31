package com.app.coffeeshopatwo.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.coffeeshopatwo.entity.Menu

@Dao
interface MenuDao {

    @Query("SELECT * FROM Menu ORDER BY menu_id ASC")
    fun getMenu(): List<Menu>

    @Query("SELECT * FROM Menu WHERE menu_id = :mid")
    fun getMenuId(mid:Int) : Menu

    @Insert
    fun insertMenu(menu: Menu)

    @Update
    fun updateMenu(menu: Menu)

    @Delete
    fun deleteMenu(menu: Menu)
}