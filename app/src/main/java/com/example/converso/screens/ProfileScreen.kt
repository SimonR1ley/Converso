package com.example.converso.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.converso.R
import com.example.converso.reposities.AuthRepository
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.viewModels.AuthViewModel

@Composable
fun ProfileScreen(
    navOnSignOut: () -> Unit,
    navBack:() -> Unit,
    modifier: Modifier = Modifier
){
Column{

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ){
        Box(
            modifier
                .padding(5.dp)
                .clickable { navBack.invoke() }){

            Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = null,
                modifier = Modifier.size(24.dp))
        }
    }

     Text(text = "My Profile")


    
    Button(onClick = { AuthRepository().signOffUser(); navOnSignOut.invoke();},
    border = BorderStroke(3.dp,
        MaterialTheme.colorScheme.primary),
        colors =
        ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
    modifier = Modifier
        .fillMaxWidth()
        .padding(15.dp)) {
        Text(text = "Sign Out",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )
    }
}
}

@Preview(showSystemUi = true)
@Composable
fun PreviewProfileScreen() {
    ConversoTheme {
        ProfileScreen(navBack = {}, navOnSignOut = {})
    }
}