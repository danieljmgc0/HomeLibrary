package com.knighttech.homelibrary

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.usecases.GetBookFromApi
import com.knighttech.homelibrary.domain.usecases.ManageBookDatabase
import com.knighttech.homelibrary.ui.theme.Purple40
import com.knighttech.homelibrary.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var globalIsbn: String = "NOISBN"
        setContent {
            var query by remember { mutableStateOf("") }

            // Launcher para recibir el resultado del ScanScreenActivity
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    val scannedIsbn = result.data?.getStringExtra("scanned_isbn")
                    if (!scannedIsbn.isNullOrEmpty()) {
                        query = scannedIsbn // lo escribimos en el TextField
                    }
                }
            }
            var showPreview by remember { mutableStateOf(false) }
            var showDialog by remember { mutableStateOf(true) }
            var theBook by remember { mutableStateOf<Book?>(null) }
            var isError by remember { mutableStateOf(false) }

            SearchScreen(
                query = query,
                onQueryChange = { query = it },
                onSearchClick = { isbn ->
                    if (!isbn.isBlank()) {
                        GlobalScope.launch(Dispatchers.IO) {
                            println("Buscar: $isbn")
                            val api = GetBookFromApi()
                            api.getBookByIsbn(isbn) { book ->
                                theBook = book
                                showPreview = true
                            }
                            showDialog = true
                            globalIsbn = isbn
                            query = ""
                        }
                    } else{
                        isError = true
                        query = ""
                    }
                },
                onScanClick = {
                    launcher.launch(Intent(this, ScanScreenActivity::class.java))
                }
            )

            if (showPreview) {
                if (theBook != null) {
                    BookPreviewDialog(
                        book = theBook!!,
                        onDismiss = { showPreview = false },
                        onConfirm = {
                            try{
                                ManageBookDatabase().saveBook( getApplicationContext(), theBook!!)
                                Toast.makeText(applicationContext, "Se ha ha añadido el libro a la biblioteca", Toast.LENGTH_LONG).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            } catch (e: IllegalStateException) {
                                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                            } finally {
                                showPreview = false
                            }


                            //this.onDestroy()
                        }
                    )
                } else {
                    if(showDialog) {
                        AlertaLibroNoEncontrado(
                            onConfirm = {
                                launcher.launch(Intent(this, AddBookFormActivity::class.java))
                            },
                            onDismiss = {
                                showDialog = false
                            },
                            globalIsbn

                        )
                    }
                }
            }

            if(isError){
                Toast.makeText(applicationContext, "El ISBN no puede estar vacío", Toast.LENGTH_LONG).show()
                isError = false
            }
        }
    }
}

@Composable
fun SearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    onScanClick: () -> Unit
) {
    var isError by remember { mutableStateOf(false) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.fondo_imagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Nuevo",
                fontFamily = FontFamily(Font(R.font.handwritten)),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 5.dp, start = 10.dp)
                    .align(Alignment.Start),
                color = Purple40
            )

            Text(
                text = "Libro",
                fontFamily = FontFamily(Font(R.font.handwritten)),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp).align(Alignment.Start),
                color = Purple40
            )
            // Barra de búsqueda (un poco encima del centro → con Spacer negativo)
            Spacer(modifier = Modifier.height((146).dp))

            TextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                label = { Text("Buscar libro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White),
                shape = RoundedCornerShape(10.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones debajo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                //horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { onSearchClick(query) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(vertical = 7.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Buscar")
                }

                Button(
                    onClick = onScanClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(vertical = 7.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.barcode_icon),
                        contentDescription = "Escanear",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Escanear")
                }
            }
        }
    }
}

@Composable
fun BookPreviewDialog(
    book: Book,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        title = { Text("Preview del libro") },
        text = {
            Column {
                Text("ISBN: ${book.isbn_13}")
                Spacer(Modifier.height(4.dp))
                Text("Título: ${book.title}")
                Spacer(Modifier.height(4.dp))
                Text("Autor: ${book.author1}")
            }
        }
    )
}

@Composable
fun AlertaLibroNoEncontrado(onConfirm: () -> Unit, onDismiss: () -> Unit, isbn: String) {
    AlertDialog(
        onDismissRequest = { onConfirm },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Sí") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("No") }
        },
        title = { Text("Error al buscar libro") },
        text = {
            Column {
                Text("El libro con el ISBN $isbn no ha sido encontrado. ¿Desea introducir los datos manualmente?")
            }
        }
    )
}

@Composable
@Preview
fun PreviewAddBook() {
    SearchScreen(
        query = "a",
        onQueryChange = {

        },
        onSearchClick ={

        },
        onScanClick = {

        }
    )
}