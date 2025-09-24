package com.knighttech.homelibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.knighttech.homelibrary.domain.model.Book
import com.knighttech.homelibrary.domain.usecases.ManageBookDatabase
import com.knighttech.homelibrary.ui.theme.Purple40
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme
import com.knighttech.homelibrary.ui.theme.White

var save = 0

class AddBookFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var theBook: Book = Book("", "" ,"" ,"None", "", "", "None" )
        setContent {
            HomeLibraryTheme {
                ManualBookForm(
                    onSave = { book ->
                        // Aquí guardas en Room
                        //println("Libro manual: $book")
                        save = 1
                        theBook = book
                        try{
                            ManageBookDatabase().saveBook( getApplicationContext(), theBook!!)
                            Toast.makeText(applicationContext, "Se ha ha añadido el libro a la biblioteca", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            this.onDestroy()
                        } catch (e: IllegalStateException) {
                            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        }
                    },
                    onCancel = {
                        save = 2
                        startActivity(Intent(this, MainActivity::class.java))
                        this.onDestroy()
                    }
                )
            }
            if (save == 1) {
                BookPreviewDialog(
                    theBook,
                    onDismiss = {
                        startActivity(Intent(this, MainActivity::class.java))
                        this.onDestroy()
                    },
                    onConfirm = {
                        ManageBookDatabase().saveBook( getApplicationContext(), theBook)

                        startActivity(Intent(this, MainActivity::class.java))
                        this.onDestroy()
                    }
                )
            }
        }
    }
}


@Composable
fun ManualBookForm(
    onSave: (Book) -> Unit,
    onCancel: () -> Unit
) {
    var isbn by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var publisher by remember { mutableStateOf("") }
    var publishedDate by remember { mutableStateOf("") }
    var thumbnail by remember { mutableStateOf("") }

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

            Text(
                "Introduce los datos del libro",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Purple40
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextField(
                value = isbn,
                onValueChange = { isbn = it },
                label = { Text("ISBN-13") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            TextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            TextField(
                value = publisher,
                onValueChange = { publisher = it },
                label = { Text("Editorial") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            TextField(
                value = publishedDate,
                onValueChange = { publishedDate = it },
                label = { Text("Fecha de publicación") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            TextField(
                value = thumbnail,
                onValueChange = { thumbnail = it },
                label = { Text("URL Thumbnail") },
                modifier = Modifier.fillMaxWidth().padding(vertical=10.dp),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(0.9f),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                    onClick = onCancel) {
                    Text("Cancelar", color = White)
                }
                Button(
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(0.9f),
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                    onClick = {
                    if (isbn.isNotBlank() && title.isNotBlank()) {
                        onSave(
                            Book(
                                isbn_13 = isbn,
                                title = title,
                                author1 = author,
                                author2 = "NOAUTOR",
                                publisher = publisher,
                                published_date = publishedDate,
                                thumbnail = thumbnail
                            )
                        )
                    }
                }) {
                    Text("Guardar", color = White)
                }
            }
        }
    }
}

