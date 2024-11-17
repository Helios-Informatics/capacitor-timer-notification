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
<<<<<<< Updated upstream
import android.R;
import android.app.PendingIntent;
=======
>>>>>>> Stashed changes

public class TimerService extends Service {
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;
<<<<<<< Updated upstream
=======
    private long remainingTime;
    private Handler timerHandler;
    private Runnable timerRunnable;

    private static TimerService instance;
>>>>>>> Stashed changes

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
<<<<<<< Updated upstream
=======
        instance = this;
>>>>>>> Stashed changes
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case "START_TIMER":
<<<<<<< Updated upstream
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
=======
                    remainingTime = intent.getLongExtra("TIMER_DURATION", 0);
                    startTimer();
                    break;

                case "STOP_TIMER":
                    stopTimer();
>>>>>>> Stashed changes
                    break;
            }
        }
        return START_STICKY;
    }

    private void startTimer() {
        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    updateNotification();
                    remainingTime--;
                    timerHandler.postDelayed(this, 1000);  // Update every second
                } else {
                    stopTimer();
                }
            }
        };
        timerHandler.post(timerRunnable);
        Notification notification = createNotification(remainingTime, "Running");
        startForeground(NOTIFICATION_ID, notification);
    }

    private void stopTimer() {
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
        stopForeground(true);
        stopSelf();
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
<<<<<<< Updated upstream
            .setContentTitle("Timer Running")
            .setContentText(statusText + ": " + convertSecondsToMS(remainingTime))
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(new NotificationCompat.Action(R.drawable.ic_media_play, "Pause", pausePendingIntent))
            .addAction(new NotificationCompat.Action(R.drawable.ic_media_pause, "Stop", stopPendingIntent))
            .setDefaults(0)
            .build();
=======
                .setContentTitle("Timer Running")
                .setContentText(statusText + ": " + convertSecondsToMS(remainingTime))
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(0)
                .build();
>>>>>>> Stashed changes
    }

    private void updateNotification() {
        Notification notification = createNotification(remainingTime, "Running");
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private String convertSecondsToMS(long seconds) {
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }

    // Method to retrieve the remaining time from the service
    public static TimerService getInstance() {
        return instance;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
