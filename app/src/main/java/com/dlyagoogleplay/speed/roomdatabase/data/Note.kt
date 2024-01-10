package com.dlyagoogleplay.speed.roomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //создание сущности
//название таблицы не переопределяли, значит она будет называться Note
data class Note(
    val title: String,
    val description: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true) //автоматическая генерация ключа, самотоятельная
    val id: Int = 0
)
