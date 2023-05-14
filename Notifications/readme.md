# Configurar servicio de mensajería 
Esta guía corresponde al uso de FCM Legacy, una versión anterior a la actual que permite enviar notificaciones de forma simple desde una app móvil. La siguiente versión está más diseñada para ser desplegada desde Backend

## Dependencias

Para esta sección necesita las dependencias de mensajería
```
implementation 'com.google.firebase:firebase-messaging-ktx:23.0.4'
```

## Cree una clase de servicio
```
class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        ...
    }
}
```

## Usar datos del RemoteMessage
Para obtener JSON del mensaje recibido
```
val obj = JSONObject(message.data as Map<*, *>)
val json = obj.toString()
```

## Registre el servicio en el manifest
```
<application>
    ...
    <service
        android:name=".services.FCMService"
        android:exported="false">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT"/>
        </intent-filter>
    </service>
    ...
</application>
```

## Permisos para notificaciones
Para android 13 o superior, se requiere usar este permiso
```
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
<uses-permission android:name="android.permission.INTERNET" />
```

## No olvide perdir el permiso en tiempo de ejecución
```
requestPermissions(
            arrayOf(
                android.Manifest.permission.POST_NOTIFICATIONS
            ), 1
)
```

## Ahora puede suscribirse
```
Firebase.messaging.subscribeToTopic("noti").addOnSuccessListener {
    Log.e(">>>","Suscrito")
}
```

## Crear notificaciones UI
Generar notificaciones visualmente
```
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import edu.co.icesi.claseauth.ProfileActivity
import edu.co.icesi.claseauth.R

object NotificationUtil {

    private val CHANNEL_ID = "messages";
    private val CHANNEL_NAME = "Messages";
    private var id = 0;

    fun showNotification(context: Context, title:String, message:String){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentText(message)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
        val notification = builder.build()
        notificationManager.notify(id, notification)
        id++
    }

}
```

## Hacer el POST Request desde Android

```
    fun POSTtoFCM(json: String): String {
        val url = URL("https://fcm.googleapis.com/fcm/send")
        val client = url.openConnection() as HttpsURLConnection
        client.requestMethod = "POST"
        client.setRequestProperty("Content-Type", "application/json")
        client.setRequestProperty("Authorization", "key=$FCM_KEY")
        client.doOutput = true
        client.outputStream.bufferedWriter().use {
            it.write(json)
            it.flush()
        }
        return client.inputStream.bufferedReader().readText()
    }
```

## Payload del mensaje a FCM
```
{
  "to": "/topics/alfa",
  "data": {
    "name": "Alfa"
  }
}
```


## Recursos
[Generador de token OAuth Google](https://github.com/Domiciano/GoogleOAuthTokenGen) <br>
Este repositorio muestra cómo se puede generar un short-time token para poder enviar solicitudes a FCM


## Referencias
[1. Arquitectura de FCM](https://firebase.google.com/docs/cloud-messaging/fcm-architecture?hl=es-) <br>
[2. Migración de Legacy a V1](https://firebase.google.com/docs/cloud-messaging/migrate-v1)
