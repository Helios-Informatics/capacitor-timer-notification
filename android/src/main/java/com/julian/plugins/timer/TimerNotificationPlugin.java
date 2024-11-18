import androidx.lifecycle.Observer;

@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {
    private Observer<Long> remainingTimeObserver;

    @Override
    public void load() {
        remainingTimeObserver = new Observer<Long>() {
            @Override
            public void onChanged(Long remainingTime) {
                if (remainingTime != null) {
                    JSObject ret = new JSObject();
                    ret.put("remainingTime", remainingTime);
                    notifyListeners("remainingTimeUpdate", ret); // Notify Vue app
                }
            }
        };

        TimerService.getRemainingTimeLiveData().observeForever(remainingTimeObserver);
    }

    @PluginMethod()
    public void startTimer(PluginCall call) {
        long duration = (long) call.getInt("duration", 0);
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");
        serviceIntent.putExtra("TIMER_DURATION", duration);
        getContext().startService(serviceIntent);
        call.resolve();
    }

    @PluginMethod()
    public void stopTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("STOP_TIMER");
        getContext().startService(serviceIntent);
        call.resolve();
    }

    @PluginMethod()
    public void pauseTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("PAUSE_TIMER");
        getContext().startService(serviceIntent);
        call.resolve();
    }

    @PluginMethod()
    public void resumeTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("RESUME_TIMER");
        getContext().startService(serviceIntent);
        call.resolve();
    }
}
