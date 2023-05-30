package com.example.converso.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.converso.R
import com.example.converso.models.Conversation
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.viewModels.ConversationsViewModel

@Composable
fun ConversationsScreen(
    viewModel: ConversationsViewModel = ConversationsViewModel(),
    onNavToProfile: () -> Unit,
    modifier: Modifier = Modifier){

    val  allConversations = viewModel?.convoList ?: listOf<Conversation>()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp))
    {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier
                    .padding(5.dp)
                    .background(Color.Gray)
                    .clickable { onNavToProfile.invoke() }){

                Image(painter = painterResource(id = R.drawable.ic_profile_btn), contentDescription = null,
                modifier = Modifier.size(24.dp))
            }
        }

        Text(text = "Chats", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        LazyColumn(){
            items(allConversations){conversation ->
                ConverstationCard(
                    Conversation(
                        title = conversation.title,
                        image = conversation.image)
                )
            }
        }



    }
}

@Composable
fun ConverstationCard(
    conversation: Conversation,
    modifier: Modifier = Modifier){
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Column() {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(conversation.image)
                    .crossfade(true)
                    .build(),
                contentDescription = conversation.title,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
            contentScale = ContentScale.Crop
            )


            Text(
                text = conversation.title,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(16.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun prevConversationsScreen(){
    ConversoTheme {
        ConversationsScreen(onNavToProfile = {})
    }
}