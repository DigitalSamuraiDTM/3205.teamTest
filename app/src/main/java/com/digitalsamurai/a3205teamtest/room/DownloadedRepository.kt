package com.digitalsamurai.a3205teamtest.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DownloadedRepository (
    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val fileName : String,
    val userName : String
    )