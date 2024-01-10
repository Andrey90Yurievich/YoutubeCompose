package com.dlyagoogleplay.speed.roomdatabase.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao //эта аннотация помечает интерфейс как объект доступа к данным.
//Объекты доступа к данным — это основные компоненты Room, которые отвечают за определение методов доступа к базе данных.
interface NoteDao {
    @Upsert //метод upsert (вставка или обновление).
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    //заказать по датам обновления
    @Query("SELECT * FROM note ORDER BY dateAdded") //@Query, принимает строку с SQL-запросом.
    //Именно благодаря данному запросу выполняется получение всех элементов или удаление какого-то конкретного элемента.
    fun getNotesOrderByDateAdded() : Flow<List<Note>> //упорядочивать заметки по дате добавления
    //Flow<List<Note>> - вернет поток


    //
    @Query("SELECT * FROM note ORDER BY title ASC") //@Query, которая принимает строку с SQL-запросом.
    fun getNotesOrderByTitle() : Flow<List<Note>> //упорядочивать заметки по названию
    //Команда ASC команда используется для сортировки возвращаемых данных в порядке возрастания.
}