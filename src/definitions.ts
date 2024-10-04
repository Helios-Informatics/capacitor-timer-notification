export interface TimerNotificationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
