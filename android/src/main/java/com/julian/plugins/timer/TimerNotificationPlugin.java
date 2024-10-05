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
        getContext().startService(serviceIntent);
        call.success();
    }

    @PluginMethod()
    public void pauseTimer(PluginCall call) {
        Intent serviceIntent = new Intent(getContext(), TimerService.class);
        serviceIntent.setAction("PAUSE_TIMER");
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
