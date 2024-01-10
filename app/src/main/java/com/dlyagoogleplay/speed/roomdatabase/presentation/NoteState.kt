package com.dlyagoogleplay.speed.roomdatabase.presentation

//СОСТОЯНИЕ ЗАМЕТКИ



import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dlyagoogleplay.speed.roomdatabase.data.Note

data class NoteState ( //Состояние заметки
    val notes: List<Note> = emptyList(), //список заметок, сначало он пустой
    val title: MutableState<String> = mutableStateOf(""), //то что пишут в заголовок
    val description: MutableState<String> = mutableStateOf(""), //то что пишут в описание заметки
)