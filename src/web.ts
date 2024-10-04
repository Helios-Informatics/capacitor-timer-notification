import { WebPlugin } from '@capacitor/core';

import type { TimerNotificationPlugin } from './definitions';

export class TimerNotificationWeb extends WebPlugin implements TimerNotificationPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
