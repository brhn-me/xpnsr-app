package com.brhn.xpnsr.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.brhn.xpnsr.R

class NotificationHandler(private val context: Context) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)!!

    private val channelId = "simple_notification_channel"
    private val channelName = "Simple Notifications"

    @RequiresApi(Build.VERSION_CODES.O)
    fun showSimpleNotification() {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Basic Notification")
            .setContentText("This is a simple notification!")
            .setSmallIcon(R.drawable.baseline_add_home_24)
            .setAutoCancel(true)

        // Create notification channel (if not already created)
        if (notificationManager.getNotificationChannel(channelId) == null) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(1, notificationBuilder.build())
    }
}

class NotificationWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val notificationHandler = NotificationHandler(context)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun doWork(): Result {
        notificationHandler.showSimpleNotification()
        return Result.success()
    }
}