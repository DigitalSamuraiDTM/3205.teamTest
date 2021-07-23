package com.digitalsamurai.a3205teamtest.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.digitalsamurai.a3205teamtest.StartApplication

@Database(entities = [DownloadedRepository::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {
    abstract fun daoDownloadedRepository() : DaoDownloadedRepository
    companion object Database{
        private var instance : AppDatabase? = null
        fun create() : AppDatabase{
            if (instance==null){
                 instance = Room.databaseBuilder(StartApplication.getContext(),
                    AppDatabase::class.java, "Database").build()
            }
            return instance!!
        }
    }
}