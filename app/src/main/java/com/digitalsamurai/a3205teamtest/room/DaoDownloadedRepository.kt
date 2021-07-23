package com.digitalsamurai.a3205teamtest.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoDownloadedRepository {
    @Query("SELECT * FROM DownloadedRepository")
    fun getDownloadedRepositories() : List<DownloadedRepository>

    @Insert
    suspend fun insertRepository(repo : DownloadedRepository)

    @Query("SELECT * FROM DownloadedRepository")
    fun observeData() : LiveData<List<DownloadedRepository>>

    @Delete
    suspend fun deleteRepository(repo : DownloadedRepository)
}