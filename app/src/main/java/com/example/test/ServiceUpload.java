package com.example.test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class ServiceUpload extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("AAA", intent.getStringExtra("mode"));

        int ID = new Random().nextInt();
        String title = "Modeus";
        String body = "Gura-san OK";
        String channel = "my_cloud";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Cloud";
            String description = "This is notification";
            NotificationChannel channel1 = new NotificationChannel(channel, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(description);
            getSystemService(NotificationManager.class).createNotificationChannel(channel1);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        //notificationManagerCompat.notify(ID, builder.build());
        int i;
        for (i = 0; i < 100; i += 5) {
            builder.setProgress(100, i , false);
            notificationManagerCompat.notify(ID, builder.build());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.d("TAG", "sleep failure");
            }
        }
        builder.setContentText("Download completed")
                .setProgress(0,0,false)
                .setOngoing(false);
        notificationManagerCompat.notify(ID, builder.build());

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("AAA", "Nono");
        int ID = new Random().nextInt();
        String title = "Modeus";
        String body = "Gura-san OK";
        String channel = "my_cloud";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setOngoing(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Cloud";
            String description = "This is notification";
            NotificationChannel channel1 = new NotificationChannel(channel, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(description);
            getSystemService(NotificationManager.class).createNotificationChannel(channel1);
        }
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        //notificationManagerCompat.notify(ID, builder.build());

        new Thread(() -> {
            int i;
            for (i = 0; i < 100; i += 5) {
                builder.setProgress(100, i , false);
                notificationManagerCompat.notify(ID, builder.build());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("TAG", "sleep failure");
                }
            }
            builder.setContentText("Download completed")
                    .setProgress(0,0,false)
                    .setOngoing(false);
            notificationManagerCompat.notify(ID, builder.build());
        }).start();

        return null;
    }
}
