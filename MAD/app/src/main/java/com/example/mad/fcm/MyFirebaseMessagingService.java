package com.example.mad.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.mad.MainActivity;
import com.example.mad.MyApplication;
import com.example.mad.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        RemoteMessage.Notification notification = message.getNotification();

        if (notification == null) {
            return;
        }
        String title = notification.getTitle();
        String strMessage = notification.getBody();

        sendNotification(title, strMessage);
    }

    private void sendNotification(String title, String strMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID);
        noBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        noBuilder.setContentTitle(title);
        noBuilder.setContentText(strMessage);
        noBuilder.setContentIntent(pendingIntent);

        Notification notification = noBuilder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}
