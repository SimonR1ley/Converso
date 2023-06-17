package com.example.converso.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.converso.MainActivity
import android.Manifest
import com.example.converso.R

class MyNotification(
    private val context: Context,
    private val title: String,
    private val body: String
) {

    val channelId = "Notification100"
    val channelName = "MyNotification"


    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun showNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            notificationBuilder = NotificationCompat.Builder(context, channelId)
            notificationBuilder.setSmallIcon(R.drawable.ic_profile_btn)
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setContentText(body)
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setContentIntent(pendingIntent)


            with(NotificationManagerCompat.from(context)){

                if(ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED){
                    Log.d("AA Notification permission", "Do not have permission to show")

                }

                notify(100, notificationBuilder.build())

            }

        }




    }

}