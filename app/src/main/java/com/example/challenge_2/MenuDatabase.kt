package com.example.challenge_2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartChart::class], version = 1, exportSchema = false)
abstract class MenuDatabase : RoomDatabase() {
    abstract val simpleCartDao: SimpleCartDao

    companion object{
    @Volatile
    private var INSTANCE: MenuDatabase? = null

        fun getInstance(context: Context): MenuDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MenuDatabase::class.java,
                        "simple_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}