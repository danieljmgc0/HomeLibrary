package com.knighttech.homelibrary

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.usecases.ManageBookDatabase


class BookListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var palabraClave by remember { mutableStateOf("") }
            //var selectedFilter by remember { mutableStateOf("ISBN") }

            HomeLibraryTheme {
                BookListScreen(
                    palabraClave = palabraClave,
                    onQueryChange = { palabraClave = it },
                    onFilterChange = { filter ->
                        //selectedFilter = filter;
                        palabraClave = ""

                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    palabraClave: String,
    onQueryChange: (String) -> Unit,
    onFilterChange: (String) -> Unit
) {
    //var query by remember { mutableStateOf("") }
    //var query_selector by remember { mutableStateOf("") }
    // Estado observable de la lista de libros
    val context = LocalContext.current


    var filtro by remember { mutableStateOf("ISBN") }

    var books = remember {
        mutableStateListOf<Book>().apply {
            addAll(ManageBookDatabase().getAllBooks(context))
        }
    }

    val filters = listOf("ISBN", "Autor", "Título")
    var expanded by remember { mutableStateOf(false) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_imagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Input búsqueda
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TextField para el texto de búsqueda
                TextField(
                    value = palabraClave,
                    onValueChange = onQueryChange,
                    label = { Text("Filtrar") },
                    modifier = Modifier
                        .weight(1f) // ocupa todo el espacio disponible
                        .height(60.dp),
                    shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp), // esquinas redondeadas solo izquierda
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search // acción de "Buscar" en el teclado
                    ),

                    keyboardActions = KeyboardActions(
                        onSearch = {
                            books.clear()
                            books.addAll(getFilterBooks(ManageBookDatabase().getAllBooks(context).toMutableList(), palabraClave, filtro))
                            keyboardController?.hide()    // cierra el teclado
                        },

                    )
                )

                LaunchedEffect(palabraClave) {
                    if (palabraClave.isNotBlank()) {
                        kotlinx.coroutines.delay(400) // espera 0.8s tras dejar de escribir
                        books.clear()
                        books.addAll(getFilterBooks(ManageBookDatabase().getAllBooks(context).toMutableList(), palabraClave, filtro))
                    } else{
                        kotlinx.coroutines.delay(400) // espera 0.8s tras dejar de escribir
                        books.clear()
                        books.addAll(getFilterBooks(ManageBookDatabase().getAllBooks(context).toMutableList(), palabraClave, filtro))
                    }
                }

                // Dropdown para seleccionar filtro
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .width(150.dp) // muy pequeño
                        .height(60.dp)
                ) {
                    TextField(
                        value = filtro,
                        onValueChange = { onQueryChange },
                        readOnly = true,
                        label = { Text("Filtro") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxSize(),
                        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp) // esquinas solo derecha
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        filters.forEach { filter ->
                            DropdownMenuItem(
                                text = { Text(filter) },
                                onClick = {
                                    filtro = filter
                                    expanded = false
                                    onFilterChange(filter)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books, key = { it.isbn_13 }) { book ->
                    Card(
                        elevation = CardDefaults.cardElevation(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("ISBN: ${book.isbn_13}")
                                Spacer(Modifier.height(4.dp))
                                Text("Título: ${book.title}")
                                Spacer(Modifier.height(4.dp))
                                Text("Autor: ${book.author1}")
                            }

                        }
                        // Botón eliminar
                        Button(
                            onClick = {
                                // Eliminar de la base de datos
                                ManageBookDatabase().deleteBook(context, book.isbn_13)

                                // Eliminar de la lista en memoria para refrescar la UI
                                books.remove(book)

                                Toast.makeText(context, "Se ha eliminado el libro", Toast.LENGTH_LONG).show()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}


fun getFilterBooks(theBooks: MutableList<Book>, palabraClave: String, filtro: String): MutableList<Book> {
    var returnBooks: List<Book> = listOf()
    if(filtro == "ISBN"){
        returnBooks = theBooks.filter { it.isbn_13.contains(palabraClave) } as MutableList<Book>
    } else if(filtro == "Autor"){
        returnBooks = theBooks.filter { it.author1.contains(palabraClave) } as MutableList<Book>
    } else if(filtro == "Título"){
        returnBooks = theBooks.filter { it.title.contains(palabraClave) } as MutableList<Book>
    } else{
        returnBooks = theBooks
    }
    Log.d("ReturnBooks", returnBooks.toString())
    return returnBooks
}