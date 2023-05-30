package com.example.converso

import android.provider.ContactsContract.Profile
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.converso.screens.RegisterScreen
import com.example.converso.screens.LoginScreen
import com.example.converso.screens.ConversationsScreen
import com.example.converso.screens.ProfileScreen
import com.example.converso.viewModels.AuthViewModel

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

}
}