package com.app.coffeeshopatwo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.coffeeshopatwo.dao.MenuDao
import com.app.coffeeshopatwo.dao.TransactionDao
import com.app.coffeeshopatwo.dao.UserDao
import com.app.coffeeshopatwo.entity.Menu
import com.app.coffeeshopatwo.entity.Transaction
import com.app.coffeeshopatwo.entity.User

@Database(
    entities = [User::class, Menu::class, Transaction::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun menuDao(): MenuDao
    abstract fun transactionDao(): TransactionDao

    companion object{
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(instance==null){
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "DatabaseAtwo")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}