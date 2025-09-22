package com.knighttech.homelibrary

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.usecases.GetBookByIsbnUsecase
import com.knighttech.homelibrary.ui.theme.AddBookFormActivity
import com.knighttech.homelibrary.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddBookActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            var theBook by remember { mutableStateOf<Book?>(null) }

            SearchScreen(
                query = query,
                onQueryChange = { query = it },
                onSearchClick = { isbn ->
                    GlobalScope.launch(Dispatchers.IO) {
                        println("Buscar: $isbn")
                        val ser = GetBookByIsbnUsecase()
                        ser.getBookByIsbn(isbn) { book ->
                            theBook = book
                            showPreview = true
                        }


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
                            // Aquí guardas en Room
                            showPreview = false
                        }
                    )
                } else {
                    AlertaLibroNoEncontrado(
                        onConfirm = {
                            launcher.launch(Intent(this, AddBookFormActivity::class.java))
                        },
                        onDismiss = {
                            this.onDestroy()
                        },
                        theBook?.isbn_13 ?: "NOISBN"

                    )
                }
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
    Box(modifier = Modifier.fillMaxSize()) {
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            TextField(
                value = query,
                onValueChange = { onQueryChange(it) },
                label = { Text("Buscar libro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { onSearchClick(query) },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Buscar")
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = onScanClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Escanear Código")
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
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("Sí") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("No") }
        },
        title = { Text("Error al buscar libro") },
        text = {
            Column {
                Text("El libro con ISBN no ha sido encontrado. ¿Desea introducir los datos manualmente?")
            }
        }
    )
}

