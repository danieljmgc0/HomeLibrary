package com.knighttech.homelibrary

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.usecases.ManageBookDatabase
import com.knighttech.homelibrary.ui.theme.White


var allBooks: List<Book> = listOf()

class BookListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        allBooks = ManageBookDatabase().getAllBooks(getApplicationContext())
        setContent {
            HomeLibraryTheme {
                BookListScreen()
            }
        }
    }
}

@Composable
fun BookListScreen() {
    var query by remember { mutableStateOf("") }

    // Estado observable de la lista de libros
    val context = LocalContext.current
    val books = remember {
        mutableStateListOf<Book>().apply {
            addAll(ManageBookDatabase().getAllBooks(context))
       }
    }

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
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Buscar libro por ISBN") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

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

/*
@Composable
fun BookListScreen() {
    var query by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_imagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // ajusta para cubrir toda la pantalla
        )

        // Contenido encima del fondo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // margen interior para no pegar a los bordes
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Caja superior → Input de búsqueda
            TextField(
                value = query,
                onValueChange = {
                    val it = ""
                    query = it
                },
                label = { Text("Buscar libro por ISBN") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(allBooks) { book ->
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
                                modifier = Modifier.weight(1f) // ocupa todo el espacio disponible
                            ) {
                                Text("ISBN: ${book.isbn_13}")
                                Spacer(Modifier.height(4.dp))
                                Text("Título: ${book.title}")
                                Spacer(Modifier.height(4.dp))
                                Text("Autor: ${book.author1}")
                            }

                            // ❌ Botón eliminar
                            Button(
                                onClick = {
                                    // Aquí llamas al DAO/UseCase para eliminar el libro
                                    //ManageBookDatabase().deleteBook(context = this, book)
                                    println("SE QUIERE ELIMINAR EL LIRBOOOO")
                                },
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }

        }
    }
}
*/