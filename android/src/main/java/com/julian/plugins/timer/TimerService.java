package simple.workout.log;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.R;

public class TimerService extends Service {
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;
    private Handler handler = new Handler();
    private long startTime;
    private long elapsedTime = 0;
    private boolean isPaused = false;
    private long pausedAt = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();  // Create the notification channel when the service is created
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "START_TIMER":
                    if (isPaused) {
                        // Resume from pause
                        startTime = System.currentTimeMillis() - pausedAt;
                        isPaused = false;
                    } else {
                        // Start fresh timer
                        startTime = System.currentTimeMillis();
                    }

                    // Start service in the foreground
                    Notification notification = createNotification(0, "Starting");
                    startForeground(NOTIFICATION_ID, notification);

                    handler.post(updateNotificationTask);
                    break;
                case "PAUSE_TIMER":
                    isPaused = true;
                    pausedAt = System.currentTimeMillis() - startTime;
                    handler.removeCallbacks(updateNotificationTask);
                    updateNotification(elapsedTime, "Paused");
                    break;
                case "STOP_TIMER":
                    stopForeground(true); // Stop foreground service
                    stopSelf();
                    break;
            }
        }

        return START_STICKY;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Timer Notifications";
            String channelDescription = "Channel for timer updates";
            int importance = NotificationManager.IMPORTANCE_NONE;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);
            channel.setShowBadge(false);  // Disable the notification badge
            channel.setSound(null, null); // Disable the notification sound

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification(long elapsedTime, String statusText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)  // Use the CHANNEL_ID
            .setContentTitle("Timer Running")
            .setContentText(statusText + ": " + elapsedTime + " seconds")
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(0xff000000)
            .setColorized(true)
            .setSound(null) // Disable sound for Android versions below 8.0
            .setDefaults(0); 
        return builder.build();
    }

    private PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(this, TimerService.class);
        intent.setAction(action);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Runnable updateNotificationTask = new Runnable() {
        @Override
        public void run() {
            elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            updateNotification(elapsedTime, "Running");
            handler.postDelayed(this, 1000);
        }
    };

    private void updateNotification(long elapsedTime, String statusText) {
        Notification notification = createNotification(elapsedTime, statusText);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(updateNotificationTask);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
