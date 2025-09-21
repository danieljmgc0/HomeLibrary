package com.knighttech.homelibrary

import android.Manifest
import android.content.Intent
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.compose.material3.Button


import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.knighttech.homelibrary.ui.theme.HomeLibraryTheme
import kotlinx.coroutines.launch


class ScanScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            HomeLibraryTheme {
                ScanScreen(
                    onResult = { code ->
                        val resultIntent = Intent().apply {
                            putExtra("scanned_isbn", code)
                        }
                        setResult(RESULT_OK, resultIntent)
                        finish() // cerrar y devolver a AddBookActivity
                    }
                )
            }
        }
    }
}


@androidx.annotation.OptIn(ExperimentalGetImage::class)
@OptIn(ExperimentalPermissionsApi::class, ExperimentalGetImage::class)
@Composable
fun ScanScreen(onResult: (String) -> Unit) {
    var mycode : String = "Hola"
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderF = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Estados
    var detected by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    // Inicializar cámara cuando se concede permiso
    if (cameraPermissionState.status.isGranted) {
        LaunchedEffect(cameraProviderF) {
            val cameraProvider = cameraProviderF.get()

            val previewUseCase = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(previewView.surfaceProvider) }

            val analysisUseCase = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy ->
                        try {
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val input = InputImage.fromMediaImage(
                                    mediaImage,
                                    imageProxy.imageInfo.rotationDegrees
                                )
                                BarcodeScanning.getClient()
                                    .process(input)
                                    .addOnSuccessListener { barcodes ->
                                        barcodes.firstOrNull()?.rawValue?.let { code ->
                                            if (code != detected) {
                                                detected = code
                                                mycode = code
                                                onResult(code)
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("ScanScreen", "Error leyendo código: $e")
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        } catch (e: Exception) {
                            imageProxy.close()
                            Log.e("ScanScreen", "Analysis error: $e")
                        }
                    }
                }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                previewUseCase,
                analysisUseCase
            )
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            Text(
                "Se necesita permiso de cámara\ny reinicia la pantalla.",
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
    }


    Box(Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if(mycode == null) mycode = "NULLO"
            Log.e("SCANEAO ", mycode)
            Text(mycode)
        }
    }
}
