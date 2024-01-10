package com.dlyagoogleplay.speed.roomdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database( // эта аннотация помечает класс как базу данных. Это должен быть абстрактный класс, расширяющий RoomDatabase

    //Данный класс помечен аннотацией @Database, в которой необходимо обязательно описать два свойства: entities и version.
    entities = [Note::class], //entities - сущности //свойство принимает все сущности, которые были описаны выше
    version = 1 //свойство задаёт версию базы данных.
)
//этот абсьрактный класс и связывает базу данных с помощью интерфейса DAO
abstract class NotesDatabase: RoomDatabase() { //Это должен быть абстрактный класс, расширяющий RoomDatabase
    abstract val dao: NoteDao //В самом классе создан абстрактный метод, который возвращает dao-интерфейс.
}