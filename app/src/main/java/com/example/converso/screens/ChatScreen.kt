package com.example.converso.screens

import Message
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.converso.R
import com.example.converso.models.Conversation
import com.example.converso.ui.theme.ConversoTheme
import com.example.converso.ui.theme.Purple40
import com.example.converso.ui.theme.Purple80
import com.example.converso.viewModels.ChatViewModel
import com.example.converso.viewModels.ConversationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    chatId: String?,
    modifier: Modifier = Modifier
){

    var newMessage: String by remember {
        mutableStateOf("")
    }

    val currentUserFrom = "Simon"

    val allMessages = viewModel?.messageList ?: listOf<Message>()



    val isChatIdNotBlank = chatId.isNullOrBlank()

    LaunchedEffect(key1 = Unit){
        if (!isChatIdNotBlank) {
            viewModel.getRealtimeMessages(chatId ?: "")
            Log.d("AAA launch effect attempt", ".......")
        } else {
            Log.d("AAA chat id error", ".......")
        }

    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),

    ) {
        Text(
//            text = viewModel.currentUserId ?: "" ,
            text = "Development" ,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )


        LazyColumn(
            modifier = modifier.weight(1f),
            reverseLayout = true) {
            items(allMessages) { message ->

                if (viewModel.currentUserId == message.fromUserId) {
//                if (currentUserFrom == message.fromUserId) {
                    MessageToBubble(message)
                } else {
                    MessageFromBubble(message)
                }
            }
        }


        Row(modifier= modifier
            .padding(8.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly) {

            OutlinedTextField(
                value = newMessage,
                onValueChange = {newMessage = it },
                shape = RoundedCornerShape(12.dp),
                label = {Text("New Message")}
            )

            Spacer(modifier = modifier.size(8.dp))

            Button(
                onClick = {
                    viewModel.sendNewMessage(newMessage, chatId ?: "");newMessage = ""
                },
                modifier = modifier.height(50.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF4AC789))
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_profile_btn), contentDescription = null)
            }
        }
    }

}


@Composable
fun MessageFromBubble(
    message: Message, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.Bottom
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_profile_btn),
            contentDescription = null,
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Column(modifier = modifier.padding(start = 8.dp, end = 16.dp)) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
//                            topEnd = 15.dp,
                            bottomStart = 15.dp
                        )
                    )
            ) {
                Text(
                    text = message.from,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = modifier.padding(start = 10.dp, top = 10.dp)
                )

                Text(
                    text = message.message,
                    color = Color.White,
                    modifier = modifier.padding(start = 10.dp, top = 2.dp)
                )
                Text(
                    fontSize = 10.sp,
                    text = message.timestamp.toDate().toString(),
                    color = Color.White,
                    modifier = modifier.padding(start = 10.dp, top = 2.dp, bottom = 10.dp)
                )

            }
        }
    }
}

@Composable
fun MessageToBubble(
    message: Message, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(10.dp),
        verticalAlignment = Alignment.Bottom
    ) {


        Column(modifier = modifier.padding(start = 16.dp, end = 8.dp)) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFF4AC789),
                        shape = RoundedCornerShape(
                            topStart = 15.dp,
                            topEnd = 15.dp,
                            bottomStart = 15.dp
                        )
                    )
            ) {


                Text(
                    text = message.message,
                    color = Color.Black,
                    modifier = modifier.padding(start = 10.dp, top = 10.dp)
                )
                Text(
                    text = message.timestamp.toDate().toString(),
                    color = Color.Gray,
                    modifier = modifier
                        .padding(all = 10.dp)
                        .align(Alignment.End)
                )

            }
        }
    }
}

            @Preview(showSystemUi = true)
            @Composable
            fun PrevChatScreen() {
                ConversoTheme() {
                    ChatScreen(chatId = "chat1234")
                }
            }
