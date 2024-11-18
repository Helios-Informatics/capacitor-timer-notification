import { WebPlugin } from "@capacitor/core";
import { TimerNotificationPlugin } from "./definitions";

export class TimerNotificationWeb
  extends WebPlugin
  implements TimerNotificationPlugin
{
  constructor() {
    super({
      name: "TimerNotification",
      platforms: ["web"],
    });
  }

  async startTimer(options: { duration: number }): Promise<void> {
    console.log(`Timer started for ${options.duration} seconds on Web`);
  }

  async stopTimer(): Promise<void> {
    console.log("Timer stopped on Web");
  }

  async getRemainingTime(): Promise<{ remainingTime: number }> {
    console.log("Returning remaining time on Web");
    return { remainingTime: 0 }; // Web doesn't support actual timers
  }
}
