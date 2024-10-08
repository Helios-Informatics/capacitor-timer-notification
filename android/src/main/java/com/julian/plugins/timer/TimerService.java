package simple.workout.log;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.R;
import android.app.PendingIntent;

public class TimerService extends Service {
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "START_TIMER":
                    long duration = intent.getLongExtra("TIMER_DURATION", 0);
                    Notification notification = createNotification(duration, "Running");
                    startForeground(NOTIFICATION_ID, notification);
                    break;

                case "UPDATE_NOTIFICATION":
                    long remainingTime = intent.getLongExtra("TIMER_DURATION", 0);
                    String statusText = intent.getStringExtra("STATUS_TEXT");
                    updateNotification(remainingTime, statusText);
                    break;

                case "STOP_TIMER":
                    stopForeground(true);
                    stopSelf();
                    break;
            }
        }
        return START_STICKY;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, "Timer Notifications", NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createNotification(long remainingTime, String statusText) {
        // Create an intent for start/pause
        Intent startPauseIntent = new Intent(this, TimerService.class);
        startPauseIntent.setAction("PAUSE_TIMER");
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 0, startPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create an intent for stop
        Intent stopIntent = new Intent(this, TimerService.class);
        stopIntent.setAction("STOP_TIMER");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Build notification
        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer Running")
            .setContentText(statusText + ": " + convertSecondsToMS(remainingTime))
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(new NotificationCompat.Action(R.drawable.ic_media_play, "Pause", pausePendingIntent))
            .addAction(new NotificationCompat.Action(R.drawable.ic_media_pause, "Stop", stopPendingIntent))
            .setDefaults(0)
            .build();
    }

    private void updateNotification(long remainingTime, String statusText) {
        Notification notification = createNotification(remainingTime, statusText);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private String convertSecondsToMS(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
