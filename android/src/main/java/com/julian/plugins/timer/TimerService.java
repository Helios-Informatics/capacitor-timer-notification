package simple.workout.log;

import android.app.Service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import android.R;

public class TimerService extends Service {
    private final int NOTIFICATION_ID = 1;
    private final String CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;

    private static final String TAG = "TimerService"; // For logging

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        Log.d(TAG, "Service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            Log.d(TAG, "onStartCommand called with action: " + intent.getAction());

            switch (intent.getAction()) {
                case "START_TIMER":
                    long duration = intent.getLongExtra("TIMER_DURATION", 0);
                    Log.d(TAG, "Starting timer with duration: " + duration);
                    Notification notification = createNotification(duration, "Starting");
                    startForeground(NOTIFICATION_ID, notification);
                    break;

                case "UPDATE_NOTIFICATION":
                    long remainingTime = intent.getLongExtra("TIMER_DURATION", 0);
                    String statusText = intent.getStringExtra("STATUS_TEXT");
                    updateNotification(remainingTime, statusText);
                    break;

                case "STOP_TIMER":
                    Log.d(TAG, "Stopping timer and removing notification");
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
        return new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Timer Running")
            .setContentText(statusText + ": " + convertSecondsToMS(remainingTime))
            .setSmallIcon(R.drawable.ic_lock_idle_alarm)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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
