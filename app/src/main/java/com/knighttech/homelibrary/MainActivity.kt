package com.knighttech.homelibrary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.knighttech.homelibrary.ui.theme.Purple40


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MenuScreen(
                onAddBookClick = {
                    // Aquí lanzas tu actividad para añadir libro
                    startActivity(Intent(this, AddBookActivity::class.java))
                },
                onViewListClick = {
                    // Aquí lanzas tu actividad para ver lista
                    startActivity(Intent(this, BookListActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun MenuScreen(
    onAddBookClick: () -> Unit,
    onViewListClick: () -> Unit

) {

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_imagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // ajusta para cubrir toda la pantalla
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Home",
                fontFamily = FontFamily(Font(R.font.handwritten)),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(top = 5.dp, start = 10.dp)
                    .align(Alignment.Start),
                color = Purple40
            )

            Text(
                text = "Library",
                fontFamily = FontFamily(Font(R.font.handwritten)),
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp).align(Alignment.Start),
                color = Purple40
            )
            //Spacer(modifier = Modifier.height((-40).dp))

            Spacer(modifier = Modifier.height(230.dp))
            Button(
                onClick = onAddBookClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)
                    .size(width = 60.dp, height = 60.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Añadir libro",
                    modifier = Modifier.padding(end = 15.dp)
                )
                Text("Nuevo libro", Modifier.padding(start = 10.dp, end=0.dp))
            }

            Button(
                onClick = onViewListClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 7.dp)
                    .size(width = 60.dp, height = 60.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.book_icon),
                    contentDescription = "Ver biblioteca",
                    modifier = Modifier.padding(end = 18.dp)
                )
                Text("Ver biblioteca", Modifier.padding(start = 0.dp))
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPrev(){
    MenuScreen(
        onAddBookClick = {},
        onViewListClick = {}
    )
}