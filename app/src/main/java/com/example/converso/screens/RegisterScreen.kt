package com.example.converso.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.converso.R
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.viewModels.AuthViewModel

//import androidx.compose.material.ButtonDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navToLogin: () -> Unit,
    navToHome: () -> Unit,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier) {

//    State Variables

 val authUiState = authViewModel?.authUiState
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        )
    {

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(300.dp))

//        Spacer(modifier = Modifier.size(40.dp))

//        Text(text = "Register",
//            style = MaterialTheme.typography.displaySmall,
//            fontWeight = FontWeight.Medium,
//            color = MaterialTheme.colorScheme.primary)





    Spacer(modifier = Modifier.size((20.dp)))

    TextField(
        value = authUiState?.registerUsername ?: "",
        onValueChange = {authViewModel?.handleInputStateChanges("registerUsername", it)},
        label = {Text("Username")},
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
//            backgroundColor = Color.White,
            Color(0xFF2A2D2E),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
//        colors = Color.Black,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )},
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )

        Spacer(modifier = Modifier.size((20.dp)))

    TextField(
        value = authUiState?.registerEmail ?: "",
        onValueChange = {authViewModel.handleInputStateChanges("registerEmail", it)},
        label = {Text("Email")},
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
//            backgroundColor = Color.White,
            Color(0xFF2A2D2E),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )},
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )

        Spacer(modifier = Modifier.size((20.dp)))

    TextField(
        value = authUiState?.registerPassword ?: "",
        onValueChange = {authViewModel.handleInputStateChanges("registerPassword", it)},
        label = {Text("Password")},
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
//            backgroundColor = Color.White,
            Color(0xFF2A2D2E),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null
            )},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.size(30.dp))

    Button(
        onClick = { authViewModel.createNewUser(context = context)},
        modifier = Modifier
//            .fillMaxWidth()
            .width(250.dp)
            .padding(5.dp)

        ,
        colors = ButtonDefaults.buttonColors(
            Color(0xFF4AC789),
            contentColor = Color.White),
        shape = RoundedCornerShape(12.dp),

        ) {
        Text(text = "Register",
            fontSize = 18.sp,
            modifier = Modifier
//                .fillMaxWidth()
                .padding(5.dp))
    }

    Spacer(modifier = Modifier.size(10.dp))

    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        Text(text = "Already have an Account??")
        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF4AC789)),
            onClick = { navToLogin.invoke() }) {
            Text(text = "Login")
        }
    }
    }

    LaunchedEffect(key1 = authViewModel.hasUser){
        if(authViewModel.hasUser){
            navToHome.invoke()
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewRegisterScreen() {
    ConversoTheme {
        RegisterScreen(navToLogin = {}, navToHome = {}, authViewModel = AuthViewModel() )
    }
}