import { registerPlugin } from '@capacitor/core';
import { TimerNotificationPlugin } from './definitions';
import { TimerNotificationWeb } from './web';

const TimerNotification = registerPlugin<TimerNotificationPlugin>('TimerNotification', {
  web: () => new TimerNotificationWeb(),
});

export * from './definitions';
export { TimerNotification };
