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
    private long totalDuration = 0;  // Total timer duration in seconds
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
                    // Retrieve duration from the intent
                    totalDuration = intent.getLongExtra("duration", 0);  // Passed from TypeScript

                    if (isPaused) {
                        // Resume from pause
                        startTime = System.currentTimeMillis() - pausedAt;
                        isPaused = false;
                    } else {
                        // Start fresh timer
                        startTime = System.currentTimeMillis();
                    }

                    // Start service in the foreground
                    Notification notification = createNotification(totalDuration, "Starting");
                    startForeground(NOTIFICATION_ID, notification);

                    handler.post(updateNotificationTask);
                    break;
                case "PAUSE_TIMER":
                    isPaused = true;
                    pausedAt = System.currentTimeMillis() - startTime;
                    handler.removeCallbacks(updateNotificationTask);
                    updateNotification(totalDuration, "Paused");
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Timer Notifications";
            String channelDescription = "Channel for timer updates";
            int importance = NotificationManager.IMPORTANCE_NONE;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);
            channel.setShowBadge(false);
            channel.setSound(null, null);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private Notification createNotification(long remainingTime, String statusText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer Running")
            .setContentText(statusText + ": " + remainingTime + " seconds remaining")
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(0xff000000)
            .setColorized(true)
            .setSound(null)
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

            long remainingTime = totalDuration - elapsedTime;  // Calculate remaining time
            if (remainingTime <= 0) {
                remainingTime = 0;  // Avoid negative time
                stopForeground(true);
                stopSelf();
            } else {
                updateNotification(remainingTime, "Running");
                handler.postDelayed(this, 1000);
            }
        }
    };

    private void updateNotification(long remainingTime, String statusText) {
        Notification notification = createNotification(remainingTime, statusText);
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
