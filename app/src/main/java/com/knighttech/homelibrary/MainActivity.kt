package com.knighttech.homelibrary

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme
import com.knighttech.homelibrary.ui.theme.ui.theme.Purple40
import com.knighttech.homelibrary.ui.theme.ui.theme.Purple80
import kotlinx.coroutines.delay

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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "HomeLibrary",
                fontFamily = FontFamily(Font(R.font.handwritten)),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp),
                color = Purple40
            )

            Spacer(modifier = Modifier.height(170.dp))

            Button(
                onClick = onAddBookClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .size(width = 60.dp, height = 60.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Añadir nuevo libro")
            }

            Button(
                onClick = onViewListClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .size(width = 60.dp, height = 60.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Ver biblioteca")
            }
        }
    }
}