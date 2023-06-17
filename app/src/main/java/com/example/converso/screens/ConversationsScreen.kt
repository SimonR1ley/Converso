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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.converso.R
import com.example.converso.models.Conversation
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.viewModels.ConversationsViewModel

@Composable
fun ConversationsScreen(
    viewModel: ConversationsViewModel = viewModel(),
    onNavToProfile: () -> Unit,
    onNavToChat: (chatId: String) -> Unit,
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
                modifier = Modifier.size(35.dp))
            }
        }

        Text(text = "Group Chats", fontSize = 25.sp, fontWeight = FontWeight.Bold)

        LazyColumn(){
            items(allConversations){conversation ->
                Card(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .clickable { onNavToChat.invoke(conversation.id) }){
                    ConversationCard(
                        Conversation(
                            title = conversation.title,
                            image = conversation.image)
                    )
                }


            }
        }



    }
}

@Composable
fun ConversationCard(
    conversation: Conversation,
    modifier: Modifier = Modifier){

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
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp),
//                style = MaterialTheme.typography.displayLarge,

            )

    }
}

@Preview(showSystemUi = true)
@Composable
fun prevConversationsScreen(){
    ConversoTheme() {
        ConversationsScreen(onNavToProfile = {}, onNavToChat = {} )
    }
}