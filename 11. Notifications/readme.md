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
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.icesiapp231.R

object NotificationUtil {

    private val CHANNEL_ID = "messages";
    private val CHANNEL_NAME = "Messages";
    private var id = 0;

    fun showNotification(context: Context, title:String, message:String){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
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
Si necesita que al pulsar la notificación se abra la actividad necesitará un pending intent
```
        val notifyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
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


# Configurar servicio de mensajería V1
Este es el servivio de mensajería actualizado y está pensado para estar desde un backend. La razón es que ahora no hay clave de servicio estática, sino que es ahora un Token (dinámico).

Para poder usarlo, revise este proyecto
[Generador de token OAuth Google](https://github.com/Domiciano/GoogleOAuthTokenGen) <br>
Este repositorio muestra cómo se puede generar un short-time token para poder enviar solicitudes a FCM

El endpoint cambia a
```
https://fcm.googleapis.com/v1/projects/facelogprueba/messages:send
```
Los headers son
```
Content-Type: application/json; UTF-8
Authorization: Bearer <FCM_KEY>
```
Donde <FCM_KEY> corresponde al token obtenido del Google Authenticator.

El payload del mensaje es
```
{
  "message": {
    "topic": "noti",
    "data": {
      "alfa": "Nuevo cambio"
    }
  }
}
```
Verá que le hace falta una clave JSON
Para obtenerla ingrese a la configuración de su proyecto.
<ol>
    <li>Vaya a Cloud Messaging</li>
    <li>Si no esta habilitada la V1, actívela mendiante el link</li>
    <li>Vaya a Administrar cuentas de servicio</li>
    <li>Cree una cuenta de servicio usando el botón CREAR CUENTA DE SERVICIO con un nombre cualquiera</li>
    <li>Quedará creado, el campo email se le llama PRINCIPAL</li>
    <li>Ingrese a la cuenta de servicio y vaya a la pestaña CLAVES y cree una CLAVE JSON nueva</li>
    <li>Descargue el JSON, ese será su clave.json para el programa</li>
    <li>Vaya al panel IAM, y vaya a Otorgar acceso</li>
    <li>Use como principal el correo creado en pasos anteriores y en rol escoga Firebase Admin y guarde</li>
    <li>Ya tendrá todo lo necesario para publicar mensajes PUSH</li>
</ol>
## Referencias
[1. Arquitectura de FCM](https://firebase.google.com/docs/cloud-messaging/fcm-architecture?hl=es-) <br>
[2. Migración de Legacy a V1](https://firebase.google.com/docs/cloud-messaging/migrate-v1)
