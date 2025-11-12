package com.dev.devmath

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.devmath.core.designsystem.KptMaterialTheme
import com.dev.devmath.core.feature.home.camera.CameraScreen
import com.dev.devmath.core.feature.home.main.MainScreen
import com.dev.devmath.core.feature.home.math.MathSolveScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

object Routes {
    const val MAIN = "main"
    const val CAMERA = "camera"
    const val MATH_SOLVE = "math_solve"
}

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    
    KptMaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Routes.MAIN
        ) {
            composable(Routes.MAIN) {
                MainScreen(
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
                        // Handle captured photo if needed
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