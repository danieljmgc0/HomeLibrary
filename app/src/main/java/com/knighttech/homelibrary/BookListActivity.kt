package com.knighttech.homelibrary

import android.os.Bundle
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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.knighttech.homelibrary.domain.model.SampleData
import com.knighttech.homelibrary.ui.theme.White

class BookListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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

            // Caja inferior → Lista de libros
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(SampleData.books) { book ->
                    Card(
                        elevation = CardDefaults.cardElevation(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("ISBN: ${book.isbn}")
                            Spacer(Modifier.height(4.dp))
                            Text("Título: ${book.title}")
                            Spacer(Modifier.height(4.dp))
                            Text("Autor: ${book.author}")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BookListPreview() {
    HomeLibraryTheme {
        BookListScreen()
    }
}