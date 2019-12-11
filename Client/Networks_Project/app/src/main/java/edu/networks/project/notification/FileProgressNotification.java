package edu.networks.project.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

import androidx.core.app.NotificationCompat;

public class FileProgressNotification {
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private int NOTIFICATION_ID;

    private final String fileName;
    private final int fileSize;
    private final boolean isDownload;

    public FileProgressNotification(Context context, String fileName, int fileSize, boolean isDownload) {
        this.context = context;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.isDownload = isDownload;
        NOTIFICATION_ID = fileName.hashCode();

        builder = new NotificationCompat.Builder(context, context.getPackageName())
                .setContentTitle(fileName)
                .setContentText((isDownload ? "downloading " : "uploading ") + " 0/" + fileSize + "B")
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

    public void updateNotification(int size) {
        builder.setContentText((isDownload ? "downloading" : "uploading") + " " + size + "/" + fileSize + "B")
                .setProgress(100, Math.min(size, fileSize) * 100 / fileSize, false)
                .setOngoing(true);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void failNotification() {
        builder.setContentTitle("Error: " + fileName)
                .setContentTitle("There was a problem transferring your file")
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setOngoing(false)
                .setAutoCancel(true);
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void finishNotification(File file) {
        builder.setContentTitle("Complete: " + fileName)
                .setContentTitle("The file transfer completed successfully")
                .setSmallIcon(isDownload ? android.R.drawable.stat_sys_download_done : android.R.drawable.stat_sys_upload_done)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentIntent(getIntent(file));
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private PendingIntent getIntent(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(file));
        Intent chooserIntent = Intent.createChooser(intent, "Choose an application to open");
        context.startActivity(chooserIntent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}
