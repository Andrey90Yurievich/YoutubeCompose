package com.dlyagoogleplay.speed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room

import com.dlyagoogleplay.speed.roomdatabase.data.NotesDatabase
import com.dlyagoogleplay.speed.roomdatabase.presentation.AddNoteScreen
import com.dlyagoogleplay.speed.roomdatabase.presentation.NotesScreen
import com.dlyagoogleplay.speed.roomdatabase.presentation.NotesViewModel


class MainActivity : ComponentActivity() {

    //создание базы данных "notes.db"
    private val database by lazy {  // создается база данных appDatabase с помощью специального
        // билдера: Room.databaseBuilder, который принимает контекст, класс, содержащий описание
        // нашей базы данных, и название самой базы.
        Room.databaseBuilder(
            applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    //использовать эту фабрику при получении экземпляра ViewModel:
    private val viewModel by viewModels<NotesViewModel>(
        factoryProducer = { //завод-производитель
            //класс ViewModel предназначен для хранения и управления UI-related dataс учетом жизненного цикла.
            object : ViewModelProvider.Factory { //Factoryинтерфейса отвечают за создание экземпляров ViewModels
                override fun <T: ViewModel> create(modelClass: Class<T>):T {
                    return NotesViewModel(database.dao) as T
                    //вернуть вью модель с данными из БД по запросам DAO
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                    Surface (
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background //белый
                    ) {
                        val state by viewModel.state.collectAsState()
                        //создание навконтроллероа
                        val navController = rememberNavController()



                        NavHost(
                            //переход на экран заметок
                            navController=navController, startDestination = "NoteScreen") {
                            composable("NoteScreen") {
                                NotesScreen(
                                    state = state,
                                    navController = navController,
                                    onEvent = viewModel::onEvent
                                )
                            }
                            composable("AddNoteScreen") {
                                AddNoteScreen(
                                    state = state,
                                    navController = navController,
                                    onEvent = viewModel::onEvent
                                )
                            }
                        }

                }
            }
        }
    }
}

