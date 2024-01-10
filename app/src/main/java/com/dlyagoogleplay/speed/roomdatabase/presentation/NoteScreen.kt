package com.dlyagoogleplay.speed.roomdatabase.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Sort
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dlyagoogleplay.speed.R


//отрисовка экрана для заметок с верхним баром,
@Composable
fun NotesScreen(  //NotesScreen - экран заеток
    state: NoteState, //парметр состояния заметка заголовок и описание
    navController: NavController, //ссылка на библиотеку навигации
    onEvent: (NotesEvents) -> Unit   //событие при
)  {
    Scaffold( //каркас В Material Design каркас — это фундаментальная структура, обеспечивающая стандартизированную платформу для сложных пользовательских интерфейсов. Он объединяет различные части пользовательского интерфейса, такие как панели приложений и плавающие кнопки действий, придавая приложениям целостный внешний вид.

        //Scaffoldпринимает несколько компонуемых объектов в качестве параметров. Среди них следующие:
        //topBar: панель приложений в верхней части экрана.
        //bottomBar: панель приложений в нижней части экрана.
        //floatingActionButton: кнопка, находящаяся в правом нижнем углу экрана, которую можно использовать для отображения ключевых действий.

        //Панель сверху
        topBar = {
            Row( //Ряд по ширине экрана по высоте 55, залит цветом, выровнен по центру внутри текст и кнопка
                modifier = Modifier //Модификаторы — это стандартные объекты Kotlin. Создайте модификатор, вызвав одну из Modifierфункций класса:
                    .fillMaxWidth() //делает составную заливку максимальной шириной, заданной ей от ее родителя. максимальная ширина заполнения
                    .height(55.dp) //высота
                    .background(MaterialTheme.colorScheme.primary) //цвет заливки коричневый
                    .padding(16.dp), //paddingпомещает пространство вокруг элемента.
                verticalAlignment = Alignment.CenterVertically //установить положение по цетру
            ) {
                Text( //
                    text = stringResource(id = R.string.app_name), //надпись Speed
                    modifier = Modifier.weight(1f), // вес, показатель важности, 1f - 1 типа float
                    fontSize = 17.sp, //размер шрифта
                    fontWeight = FontWeight.SemiBold, //вес шрифта, SemiBold - полужирный
                    color = MaterialTheme.colorScheme.onPrimary //цвет светлый
                    )
                //кнопка значек которая сортирует, если изменения есть то их добавляет в существующую заметку
                IconButton(onClick = { onEvent(NotesEvents.SortNotes) }) { //при нажатии на кнопку вызывается функция сортировки
                    //onClick: функция, вызываемая, когда пользователь нажимает кнопку.
                    //
                    Icon(
                        imageVector = Icons.Rounded.Sort, //иконка из библиотеки иконок
                        contentDescription = "Sote Notes", //описание надпись
                        modifier = Modifier.size(35.dp), //размер 35 на 35
                        tint = MaterialTheme.colorScheme.onPrimary //оттенок светлый
                    )
                }
            }
        },

        //Кнопка справа снизу добавить заметку
        floatingActionButton = {  //floatingActionButton: кнопка, находящаяся в правом нижнем углу экрана, которую можно использовать для отображения ключевых действий.
            //Плавающая кнопка действия (FAB) — это выделенная кнопка, которая позволяет пользователю выполнить основное действие в приложении.
            //Создать новый элемент : в приложении для создания заметок FAB можно использовать для быстрого создания новой заметки.
            //Добавить новый контакт . В приложении чата FAB может открыть интерфейс, позволяющий пользователю добавлять кого-либо в разговор.
            //Расположение по центру : в интерфейсе карты FAB может центрировать карту по текущему местоположению пользователя.
            FloatingActionButton(onClick = { //при нажатии добавляется заметка данные берет с полей
                //onClick: функция, вызываемая, когда пользователь нажимает кнопку.
                //containerColor: Цвет кнопки.
                //contentColor: цвет значка.
                state.title.value = "" //state- параметр состояния сверху value-ценность - то что как назовут заметку
                state.description.value = "" //то что напишут в заметку
                navController.navigate("AddNoteScreen") //перейти на окно, AddNoteScreen route - маршрут, перемещение между экранами
                //создаете контроллер навигации с помощью NavControllerкласса.
                //NavControllerэто центральный навигационный API.
                //navigate - Чтобы передать аргумент пункту назначения
            }) {
                Icon( //иконка добавить заметку
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add new note"
                )
            }
        }


    ) { paddingValues -> //paddingValues - значение заполнения, установил Scaffold
        //колонка с заметками
        LazyColumn( //Если вам нужно отобразить большое количество элементов
            contentPadding = paddingValues, //отступы по краям содержимого.
            modifier = Modifier
                .fillMaxSize() //растягивает компонент по всей длине и ширине контейнера
                .padding(8.dp), //внутренние отступы
            verticalArrangement = Arrangement.spacedBy(16.dp) //разнесено по. Размещайте дочерние элементы так, чтобы каждые два соседних элемента находились на фиксированном spaceрасстоянии по главной оси.
        ) {
            items(state.notes.size) { index -> //items - элементы ленивой колонки, в него принимаются параметр дляна списка
                NoteItem( // функция элемент заметки карточка
                    state = state, //ссфлка на данные состояния заголовой описание и индекс карточки порядковый
                    index = index, //индекс карточки с заметкой число
                    onEvent = onEvent
                )
            }
        }
    }
}





//карточка заметки
@Composable
fun NoteItem(
    state: NoteState,
    index: Int,
    onEvent: (NotesEvents) -> Unit
) {
    Row( //ряд карточки
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)) //закругленные края
            .background(MaterialTheme.colorScheme.primaryContainer) //поносный цвет
            .padding(12.dp) //внутренние отступы
    ) {
        //колонка ряда карточки заметки c заголовком и описанием
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.notes[index].title, //заголовок карточки
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer//темный цвет
                )

            Spacer(modifier = Modifier.height(8.dp)) //Spacer — это пустой элемент, который используется для создания пробела между двумя элементами пользовательского интерфейса.

            Text(
                text = state.notes[index].description,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer,//описание из заметки
            )
        }

        //после колонки с заголовкои м описанием идет кнопка иконка удалить заметку
        IconButton(onClick = {
            onEvent(NotesEvents.DeleteNote(state.notes[index]))
        }
        ) {
            //иконка удалить
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Note",
                modifier = Modifier.size(35.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}
