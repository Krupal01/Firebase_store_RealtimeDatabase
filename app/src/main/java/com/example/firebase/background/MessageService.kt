package com.example.firebase.background

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.firebase.R
import com.example.firebase.ui.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageService : FirebaseMessagingService() {


    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        sendRegistrationToServer(p0)
    }

    private fun sendRegistrationToServer(p0: String) {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val title = p0.notification?.title
        val message = p0.notification?.body

        val myProcess = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(myProcess)
        val isInForeground = (myProcess.importance ==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
        if(isInForeground) {
            customNotification(title.toString(), message.toString())
        } else{
            notification(title.toString(), message.toString())
        }
    }

    @SuppressLint("ServiceCast")
    private fun notification(title: String, message: String) {
        val notificationIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = Notification.Builder(applicationContext)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentText(message)

        notificationManager.notify(1, builder.build())
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("RemoteViewLayout")
    private fun customNotification(title: String, message: String) {

        val notificationIntent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationLayout = RemoteViews(packageName,R.layout.custom_notification_layout)
        notificationLayout.setTextViewText(R.id.NotTitle, title)
        notificationLayout.setImageViewResource(R.id.NotIcon,R.drawable.ic_launcher_foreground)
        notificationLayout.setTextViewText(R.id.NotMsg, message)

        val builder = Notification.Builder(applicationContext)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .setCustomContentView(notificationLayout)
        notificationManager.notify(1, builder.build())
    }

}