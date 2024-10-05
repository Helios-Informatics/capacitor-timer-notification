import { registerPlugin } from '@capacitor/core';
import type { TimerNotificationPlugin } from './definitions';

const TimerNotification = registerPlugin<TimerNotificationPlugin>('TimerNotification', {
  web: () => import('./web').then((m) => new m.TimerNotificationWeb()),
});

export * from './definitions';
export { TimerNotification };
