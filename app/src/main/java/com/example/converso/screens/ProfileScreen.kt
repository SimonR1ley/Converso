package com.example.converso.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.converso.R
import com.example.converso.reposities.AuthRepository
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.viewModels.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(),
    navOnSignOut: () -> Unit,
    navBack:() -> Unit

){

    val profileUiState = viewModel?.profileUiState



    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia(),
    onResult = { uri ->
        if (uri != null) {
            viewModel.handleProfileImageChange(uri)
        }
    }
    )


Column(
    horizontalAlignment = Alignment.CenterHorizontally,
modifier = modifier.padding(20.dp)){

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

     Text(
         text = "My Profile",
     fontWeight = FontWeight.Bold,
     fontSize = 21.sp,
         modifier = modifier.padding(bottom = 15.dp)
     )

    Spacer(modifier = Modifier.size((30.dp)))


    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(profileUiState?.profileImage ?: "/")
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.ic_profile_btn),
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.LightGray, shape = CircleShape)
    )

    Spacer(modifier = Modifier.size((20.dp)))

    Button(onClick = {
        singlePhotoPickerLauncher.launch(
          PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )},
            modifier = Modifier
            .width(160.dp),
        colors = ButtonDefaults.buttonColors(
       Color(0xFF2A2D2E)),
        ) {
//        Icon(  imageVector = Icons.Default.Edit,
//            contentDescription = null,
//         )
        Text(text = "Set Profile Picture",
            fontSize = 13.sp,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.padding(5.dp)
        )
    }



    Spacer(modifier = Modifier.size((30.dp)))

    TextField(
        value = profileUiState?.username ?: "",
        onValueChange = {viewModel.handleUsernameStateChanges(it)},
        label = {Text("Username")},
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
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.size((20.dp)))

    TextField(
        value = profileUiState?.email ?: "",
        onValueChange = {},
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
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        enabled = false
    )
    Spacer(modifier = Modifier.size((20.dp)))



    Button(onClick = {
            viewModel.saveProfileData()
    },
        modifier = Modifier
            .width(250.dp)
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            Color(0xFF4AC789),
            contentColor = Color.White),
        shape = RoundedCornerShape(12.dp),) {
        Text(text = "Save Profile",
            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
    }


    Spacer(modifier = Modifier.size((20.dp)))

    
    Button(onClick = { AuthRepository().signOffUser(); navOnSignOut.invoke();},
        border = BorderStroke(3.dp,
            Color(0xFF4AC789)),
        modifier = Modifier
            .width(250.dp)
            .padding(5.dp),
        colors = ButtonDefaults.buttonColors(
            Color.White,
            contentColor = Color.White),
        shape = RoundedCornerShape(12.dp),) {
        Text(text = "Sign Out",
            fontSize = 18.sp,
            color = Color(0xFF4AC789),
//            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
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