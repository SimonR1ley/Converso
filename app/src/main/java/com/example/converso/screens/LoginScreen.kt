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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel? = null,
    navToRegister:() -> Unit,
    navToHome:() -> Unit,
    modifier: Modifier = Modifier) {


    val authUiState = authViewModel?.authUiState
    val error = authUiState?.errorMessage != null
    val context = LocalContext.current

//    State Variables


//    var email by remember {
//        mutableStateOf("")
//    }
//
//    var password by remember {
//        mutableStateOf("")
//    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {


//        Spacer(modifier = Modifier.size(40.dp))
//
//        Text(text = "Login",
//            style = MaterialTheme.typography.displaySmall,
//            fontWeight = FontWeight.Medium,
//            color = MaterialTheme.colorScheme.primary)
//
//        Spacer(modifier = Modifier.size(100.dp))

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(300.dp))

        if(error){
            Text(text = authUiState?.errorMessage ?: "",
            color = Color.Red)
        }

    Spacer(modifier = Modifier.size((20.dp)))

   TextField(
        value = authUiState?.loginEmail ?: "",
        onValueChange = {authViewModel?.handleInputStateChanges("LoginEmail", it)},
    label = {Text("Email")},
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Transparent,
            Color(0xFF2A2D2E),
//            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    leadingIcon = {
        Icon(
        imageVector = Icons.Default.Email,
        contentDescription = null
        )},
       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
       modifier = Modifier
           .padding(0.dp)
           .fillMaxWidth()
   )


        Spacer(modifier = Modifier.size((20.dp)))

    TextField(
        value = authUiState?.loginPassword ?: "",
        onValueChange = {authViewModel?.handleInputStateChanges("loginPassword", it)},
        label = {Text("Password")},
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            disabledTextColor = Color.Transparent,
            Color(0xFF2A2D2E),
//            backgroundColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )},
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )

    Spacer(modifier = Modifier.size(30.dp))

    Button(onClick = { authViewModel?.loginUser(context) },
    modifier = Modifier
        .width(250.dp)
        .padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            Color(0xFF4AC789),
            contentColor = Color.White),
        shape = RoundedCornerShape(12.dp),
    ){
        Text(text = "Login",
        fontSize = 18.sp,
        modifier = Modifier
//            .fillMaxWidth()
            .padding(5.dp))
    }

    Spacer(modifier = Modifier.size(10.dp))

    Row(modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
       Text(text = "Need an account?")
        TextButton(
            colors = ButtonDefaults.textButtonColors(
                contentColor = Color(0xFF4AC789)),
            onClick = { navToRegister.invoke()}) {
            Text(text = "Register")
        }
    }
    }

    LaunchedEffect(key1 = authViewModel?.hasUser){
        if(authViewModel?.hasUser == true){
            navToHome.invoke()
        }
    }

}

@Preview(
    showSystemUi = true
)
@Composable
fun PreviewLoginScreen() {
    ConversoTheme {
        LoginScreen(navToRegister = {}, navToHome = {}, authViewModel = AuthViewModel())
    }
}