import com.google.firebase.Timestamp

data class Message(
    val id: String = "",
    val from: String = "",
    val fromUserId: String = "",
    val fromUserProfilePic: String = "",
    val message: String = "",
    val timestamp: Timestamp = Timestamp.now()
)