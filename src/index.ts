import { registerPlugin } from '@capacitor/core';
import { TimerNotificationPlugin } from './definitions';

const TimerNotification = registerPlugin<TimerNotificationPlugin>('TimerNotification');

export * from './definitions';
export { TimerNotification };
