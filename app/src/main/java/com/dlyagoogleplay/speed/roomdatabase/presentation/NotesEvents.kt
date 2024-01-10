package com.dlyagoogleplay.speed.roomdatabase.presentation



//СОБЫТИЯ ЗАМЕТОК

import com.dlyagoogleplay.speed.roomdatabase.data.Note

sealed interface NotesEvents { //отмечаются события
    object SortNotes: NotesEvents //SortNotes - сортировка событий если в существующей заметке появились изменения то их добавляют

    data class DeleteNote(val note: Note): NotesEvents

    data class SaveNote(
        val title: String,
        val description: String
    ): NotesEvents
}