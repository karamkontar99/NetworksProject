package edu.networks.project.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class FileProgressNotification {
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private int NOTIFICATION_ID;

    private final String fileName;
    private final boolean isDownload;

    public FileProgressNotification(Context context, final String fileName, boolean isDownload) {
        this.context = context;
        this.fileName = fileName;
        this.isDownload = isDownload;
        NOTIFICATION_ID = fileName.hashCode();

        builder = new NotificationCompat.Builder(context, context.getPackageName())
                .setContentTitle(fileName)
                .setContentText((isDownload ? "downloading " : "uploading ") + "...")
                .setSmallIcon(isDownload ? android.R.drawable.stat_sys_download : android.R.drawable.stat_sys_upload)
                .setProgress(100, 0, false)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(context.getPackageName(),"Networks Project File Transfer", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
            builder.setChannelId(channel.getId());
        }
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void updateNotification(int size, int total) {
        Log.e("SOCKETS", "updating notification");

        builder.setContentText((isDownload ? "downloading" : "uploading") + " " + size + "/" + total + "B")
                .setProgress(100, size * 100 / total, false)
                .setOngoing(true);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void failNotification() {
        Log.e("SOCKETS", "failing notification");

        builder.setContentTitle("Error: " + fileName)
                .setContentTitle("There was a problem transferring your file")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setOngoing(false)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void finishNotification() {
        Log.e("SOCKETS", "finishing notification");

        builder.setContentTitle("Complete: " + fileName)
                .setContentTitle("The file transfer completed successfully")
                .setSmallIcon(isDownload ? android.R.drawable.stat_sys_download_done : android.R.drawable.stat_sys_upload_done)
                .setOngoing(false)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
