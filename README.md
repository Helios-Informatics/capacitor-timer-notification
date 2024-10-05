# capacitor-timer-notification

A Capacitor plugin to create a timer notification that will show a notification with a countdown timer.


## Usage

Add the following to your `android/app/src/main/AndroidManifest.xml` file:

```xml
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />

<service android:name=".TimerService" android:enabled="true" android:exported="false" android:foregroundServiceType="mediaPlayback"/>
```




## Install

```bash
npm install capacitor-timer-notification
npx cap sync
```

## API

<docgen-index>

* [`startTimer(...)`](#starttimer)
* [`pauseTimer()`](#pausetimer)
* [`stopTimer()`](#stoptimer)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startTimer(...)

```typescript
startTimer(options: { duration: number; }) => Promise<void>
```

| Param         | Type                               |
| ------------- | ---------------------------------- |
| **`options`** | <code>{ duration: number; }</code> |

--------------------


### pauseTimer()

```typescript
pauseTimer() => Promise<void>
```

--------------------


### stopTimer()

```typescript
stopTimer() => Promise<void>
```

--------------------

</docgen-api>
