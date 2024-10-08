package simple.workout.log;

import android.content.Intent;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {

    @PluginMethod()
    public void startTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");
        long duration = (long) call.getInt("duration", 0);
        serviceIntent.putExtra("TIMER_DURATION", duration);
        getContext().startService(serviceIntent);
        call.success();
    }

    @PluginMethod()
    public void updateNotification(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("UPDATE_NOTIFICATION");
        long remainingTime = (long) call.getInt("duration", 0);
        String statusText = call.getString("statusText", "Running");
        serviceIntent.putExtra("TIMER_DURATION", remainingTime);
        serviceIntent.putExtra("STATUS_TEXT", statusText);
        getContext().startService(serviceIntent);
        call.success();
    }

    @PluginMethod()
    public void stopTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("STOP_TIMER");
        getContext().startService(serviceIntent);
        call.success();
    }
}
