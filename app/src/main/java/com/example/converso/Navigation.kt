package com.example.converso

import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.converso.screens.ChatScreen
import com.example.converso.screens.RegisterScreen
import com.example.converso.screens.LoginScreen
import com.example.converso.screens.ConversationsScreen
import com.example.converso.screens.ProfileScreen
import com.example.converso.viewModels.AuthViewModel
import android.Manifest

enum class AuthRoutes {

    Login,
    Register
}

enum class HomeRoutes {
    Conversations,
    Chat,
    Profile
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            Log.d("notification request", it.toString())
        })




     val startingScreen: String = if(authViewModel.hasUser){
        HomeRoutes.Conversations.name
    } else {
        AuthRoutes.Login.name
    }


NavHost(
    navController = navController,
//    startDestination = AuthRoutes.Login.name
    startDestination = startingScreen)
{
    composable(route = AuthRoutes.Login.name){
        LoginScreen(
            navToRegister = {
                navController.navigate(AuthRoutes.Register.name){
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Login.name){
                        inclusive = true
                    }
        }
        },
            navToHome = {
                navController.navigate(HomeRoutes.Conversations.name){
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Login.name){
                        inclusive = true
                    }
                }
            },
            authViewModel = authViewModel)
    }

    composable(route = AuthRoutes.Register.name){
        RegisterScreen(
            navToLogin = {
                navController.navigate(AuthRoutes.Login.name){
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Register.name){
                        inclusive = true
                    }
                }
            }, navToHome = {
                navController.navigate(HomeRoutes.Conversations.name){
                    launchSingleTop = true
                    popUpTo(route = AuthRoutes.Register.name){
                        inclusive = true
                    }
                }
            },
            authViewModel = authViewModel)

        }

    composable(route = HomeRoutes.Conversations.name){
        ConversationsScreen(
            onNavToProfile = {
                navController.navigate(HomeRoutes.Profile.name){
                    launchSingleTop = true
                }
            },
            onNavToChat = {
                navController.navigate("${HomeRoutes.Chat.name}/${it}"){
                    launchSingleTop = true
                }
            }
        )
    }

    composable(route = HomeRoutes.Profile.name) {
        ProfileScreen(navOnSignOut = { navController.navigate(AuthRoutes.Login.name){
            launchSingleTop = true
            popUpTo(route = HomeRoutes.Conversations.name){
                inclusive = true
            }
        } }, navBack = { navController.navigate(HomeRoutes.Conversations.name){
            launchSingleTop = true
            popUpTo(route = HomeRoutes.Conversations.name)
        } })
    }


    composable(route = "${HomeRoutes.Chat.name}/{chatId}",
        arguments = listOf(navArgument("chatId") { type = NavType.StringType; defaultValue = "chat1234" }))
    {
        ChatScreen(chatId = it.arguments?.getString("chatId"))
    }

}

    LaunchedEffect(key1 = permissionLauncher ) {
        Log.d("launch permission", "YES")
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        }
    }

}