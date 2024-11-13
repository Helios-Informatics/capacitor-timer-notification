import { WebPlugin } from '@capacitor/core';
import { TimerNotificationPlugin } from './definitions';

export class TimerNotificationWeb extends WebPlugin implements TimerNotificationPlugin {
  async startTimer(options: { duration: number }): Promise<void> {
    console.log(`Starting timer for ${options.duration} seconds`);
  }

  async updateNotification(options: { duration: number; statusText: string }): Promise<void> {
    console.log(`Updating notification: ${options.statusText} with ${options.duration} seconds remaining`);
  }

  async stopTimer(options: { playSound?: boolean } = { playSound: false }): Promise<void> {
    console.log(`Stopping timer and removing notification. Play sound: ${options.playSound}`);
  }
}

const TimerNotification = new TimerNotificationWeb();
export { TimerNotification };
