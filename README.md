![](https://i.postimg.cc/hjyQ091y/temp-Imagewrz-UWr.jpg)

# Arad Push Notification system (APN)
You can use this sdk in Kotlin applications such as **Android**.

> ## Before start (IMPORTANT)
> You need The **firebase configuration object before initialize APN sdk.

> ### — Firebase Configuration
> 1. **Create a Firebase project:** Open the [Firebase console](https://console.firebase.google.com) and click on **“Add project”**. Follow the instructions to create your project.
> 2. **Get your Firebase configuration:** After your project is created, click on the gear icon next to **“Project Overview”** and select **“Project settings”**. Here, you’ll find your Firebase SDK snippet under the **“General”** tab. It will look something like this:

>```kotlin
> data class FirebaseConfig(
>   var ApiKey: String,
>   var ApplicationId: String,
>   var ProjectId: String,
>)
>```

## Installation
>Always get the latest version with the following code

## How to use

### Import
[![](https://jitpack.io/v/araditc/Arad.Push.Android.SDK.svg)](https://jitpack.io/#araditc/Arad.Push.Android.SDK)

# Step 1:
>Add it in your root build.gradle at the end of repositories:

``` groovy
dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```
# Step 2:
>Add the dependency:

```groovy
implementation 'com.github.araditc:Arad.Push.Android.SDK:TAG'
```

# Usage

>```kotlin
> class Application : Application() {
>     override fun onCreate() {
>         super.onCreate()
>         //================================= Init APN SDK ===========================================
>         Arad.init(this , FirebaseConfig("{API_KEY}", "{APPLICATION_ID}" , "PROJECT_ID"))
>     }
> }
>```

>```kotlin
> var devicename = DeviceUtils.getDeviceName()
> var packageName = DeviceUtils.getPackageName(this)
> var deviceVersion = DeviceUtils.getVersion(this)
> //================================ FCM Implementation ======================================
> Thread {
>     val token = Arad.getToken()
>     Log.i("Fcm_Token", token.toString())
> }.start()
> 
> Arad.SetIToken(object : IToken {
>     override fun newToken(token: String) {
>         Log.i("APN", "This Method Return New Token If Generate From FCM")
>     }
> })
> //================================ APN Implementation ======================================
> // This Method Call For Message
> Arad.getMessage(this@MainActivity)
> Arad.setIMessage(object : IMessage {
>     override fun MessageReceive(payload: String) {
>         Log.i("APN", "Message Receive If Message Exist For This User In Server")
>     }
> })
> Arad.setIWakeUp(object : IWakeUp {
>     override fun WakeUp(wakeUp: Boolean) {
>         Log.i("APN", "Wake Up App From FCM For Get Message From APN")
>         Arad.getMessage(this@MainActivity)
>      }
>  })
>```

## Methods

| Method Arad Class                                | Info                                                                       |
|--------------------------------------------------|----------------------------------------------------------------------------|
| `init(context: Context , config:FirebaseConfig)` | initialize sdk width `context` (Context) and `firebaseConfig` (object)     |
| `getToken()`                                     | return firebase token `string` (return `null` before before init complete) |
| `checkConfig()`                                  | check if configs **defined** and **valid** `boolean`                       |
| `setConfig()`                                    | **set** connection data `void`                                             |
| `getMessage()`                                   | return push message with `context` (Context)                               |

| Method Device Util Class                         | Info                                                                       |
|--------------------------------------------------|----------------------------------------------------------------------------|
| `getPackageName(context)`                        | returns Package Name `string` with (Context)                               |
| `getDeviceName()`                                | returns Device Name `string`                                               |
| `getVersion(context)`                            | returns Version `string` with (Context)                                    |

## Events

| Event              | Info                                        |
|--------------------|---------------------------------------------|
| **MessageReceive** | trigger when a message received from server |
| **NewToken**       | trigger when a new token from fcm           |
| **WakeUp**         | trigger when a new message from fcm         |