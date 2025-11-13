package com.dev.devmath

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.asImage
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.feature.home.camera.CameraScreen
import com.dev.devmath.core.feature.home.main.MainScreen
import com.dev.devmath.core.feature.home.math.MathSolveScreen
import com.dev.devmath.core.ui.toImageBitmap
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.skia.Bitmap

object Routes {
    const val MAIN = "main"
    const val CAMERA = "camera"
    const val MATH_SOLVE = "math_solve"
}

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    var capturedImage: ImageBitmap? by remember { mutableStateOf(null) }
    KptMaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.MAIN
        ) {
            composable(Routes.MAIN) {
                MainScreen(
                    capturedImage = capturedImage,
                    onMathSolveClick = {
                        navController.navigate(Routes.MATH_SOLVE)
                    },
                    onCameraClick = {
                        navController.navigate(Routes.CAMERA)
                    }
                )
            }
            
            composable(Routes.CAMERA) {
                CameraScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onPhotoCaptured = { photoBytes ->
                        // Convert ByteArray to ImageBitmap
                        capturedImage = photoBytes?.toImageBitmap()
                        navController.popBackStack()
                    }
                )
            }
            
            composable(Routes.MATH_SOLVE) {
                MathSolveScreen(
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}