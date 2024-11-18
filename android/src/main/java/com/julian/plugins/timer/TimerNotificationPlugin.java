package simple.workout.log;

import android.content.Intent;
import com.getcapacitor.PluginCall;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSObject;


@CapacitorPlugin(name = "TimerNotification")
public class TimerNotificationPlugin extends Plugin {
    @PluginMethod()

    public void startTimer(PluginCall call) {
        long duration = (long) call.getInt("duration", 0);
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("START_TIMER");
        serviceIntent.putExtra("TIMER_DURATION", duration);
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

    // Method to retrieve the remaining time

    @PluginMethod()
    public void getRemainingTime(PluginCall call) {
        TimerService timerService = TimerService.getInstance();
        if (timerService != null) {
            long remainingTime = timerService.getRemainingTime();
            JSObject ret = new JSObject();
            ret.put("remainingTime", remainingTime); // Add the long value to JSObject
            call.resolve(ret); // Return the JSObject
        } else {
            call.reject("TimerService not running");
        }
    }
}
