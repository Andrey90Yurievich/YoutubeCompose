package com.dlyagoogleplay.speed.roomdatabase.presentation

//МОДЕЛЬ ПРЕДСТАВЛЕНИЯ ЗАМЕТОК


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlyagoogleplay.speed.roomdatabase.data.Note
import com.dlyagoogleplay.speed.roomdatabase.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel (
    //передаем в конструктор ДАО интерфейс
    private val dao: NoteDao
): ViewModel() {
    //чтобы список сортировался по дате добавления
    private val isSortedByDAdded = MutableStateFlow(true) //сортируем список измняемое состояние потоков



    //состояние заметок
    private var notes =
        //сортировка по датам и вывод последнего
        isSortedByDAdded.flatMapLatest { sort -> //в пользовательский интерфейс будут возвращены только самые последние результаты.
            //если отсортирован по датам
            if (sort) {
                dao.getNotesOrderByDateAdded() //упорядочивать заметки по дате добавления
            } else {
                dao.getNotesOrderByTitle() //упорядочивать заметки по названию
            }
            //корутинские фишки
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
            //stateIn промежуточный оператор - Чтобы преобразовать поток
            //определить CoroutineScope, который поможет вам управлять тем, когда ваши сопрограммы должны запускаться.
    //LiveData— это наблюдаемый класс держателя данных.
    //viewModelScope - получить доступ к объекту ViewModel
    //SharingStarted.WhileSubscribed() сохраняет активность вышестоящего производителя, пока есть активные подписчики.
    //emptyList()) - пустой список



    //MutableStateFlow — это часть библиотеки Kotlin Flow, которая представляет собой платформу для построения асинхронных и потоковых конвейеров данных в Kotlin.
    //MutableStateFlow — это поток, который можно как собирать, так и преобразовывать, и он имеет изменяемое состояние, которое может быть изменено сборщиком. Это означает, что вы можете использовать MutableStateFlow для представления состояния представления и обновлять его на основе пользовательского ввода или других событий.
    //MutableStateFlow для хранения текущего изменения
    val _state = MutableStateFlow(NoteState()) //текущее состояние заметки
    val state =
        //объединяем если в 3 этих заметках состояния будут изменения то обновим
        combine(_state, isSortedByDAdded, notes) {state, isSortedByDateAdded, notes ->
            //combine - объединять
            //скопируем эту заметку и присвоим к списку заметок
            state.copy(
                notes = notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())
    //stateIn - создается поток
    //viewModelScope - доступ к объекту ViewModel
    //SharingStarted.WhileSubscribed() - последнее состояние
    //время выполнения 5 сек
    //



    //своя функция для майн активити
    //параметром принимаем событие которое совершил пользователь удалил добавил пересохранил обновил
    fun onEvent(event: NotesEvents) {
        when (event) { //when - пока, переборка
            //событие когда удалил ветку
            is NotesEvents.DeleteNote -> {  //если вызвана функция удалить, то
                viewModelScope.launch { //создать поток и
                    //передаем заметку которую он пытается удалить
                    dao.deleteNote(event.note) //через интерфейс дао удалить из БД заметку
                }
            }
            //событие когда сохранил ветку
            is NotesEvents.SaveNote -> { //если нажали сохранить
                val note = Note( //созданный объект присвоить к списку note
                        title = state.value.title.value,
                        description = state.value.description.value,
                        dateAdded = System.currentTimeMillis()
                )
                viewModelScope.launch { //зпустить поток
                    dao.upsertNote(note) //вставить или обновить изменения которые получились
                }

                //обновить заметку
                _state.update { //update – обновление пакетов
                    //копируем состояние
                    it.copy(
                        title = mutableStateOf(""),
                        description = mutableStateOf("")
                    )
                }
            }
            //событие когда он обновлякт ветку
            //если последнее состояние не равно существующему то отсортировать
            NotesEvents.SortNotes -> {
                isSortedByDAdded.value = !isSortedByDAdded.value
            }
        }
    }

}