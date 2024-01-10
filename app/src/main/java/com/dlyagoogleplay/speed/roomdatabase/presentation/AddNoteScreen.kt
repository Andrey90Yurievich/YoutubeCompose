package com.dlyagoogleplay.speed.roomdatabase.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
//компонуемая функция
fun AddNoteScreen( //AddNoteScreen - добавить заметку на старницу
    state: NoteState,
    navController: NavController, //ссылка на библиотеку навигации
    onEvent: (NotesEvents) -> Unit
) {
    Scaffold (  //каркас заметки
        floatingActionButton = { //плавающая кнопка снизу
            FloatingActionButton(onClick = {
                onEvent(NotesEvents.SaveNote( //при клике создается класс с заоловком и описанием
                    title = state.title.value,
                    description = state.description.value,
                ))
                navController.popBackStack()
             }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Note"
                )

            }
        }
    )



    { paddingValues ->



        Column( //колонка
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize() //по все размерам родителя экрана
        ){
            //поле ввода для заголовка
            TextField( //TextFieldпозволяет пользователям вводить и изменять текст.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                value = state.title.value,//присвоится значение введенное к value
                onValueChange = { //при изменении значения
                    state.title.value = it //эти значения присобачить к заголовку
                                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = { //заполнитель
                    Text(text = "Title")
                }
            )

            //поле ввода для описания
            TextField( //TextFieldпозволяет пользователям вводить и изменять текст.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = state.description.value,//присвоится значение введенное к value
                onValueChange = { //при изменении значения
                    state.description.value = it //эти значения присобачить к заголовку
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                ),
                placeholder = { //заполнитель
                    Text(text = "Description")
                }
            )

        }
    }
}