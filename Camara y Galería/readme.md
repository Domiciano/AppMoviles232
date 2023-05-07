## Configuración de la cámara

Primero necesita configurar los permisos de la cámara en el manifest.xml

```
<application>
...
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
...
</application>
```

Estos permisos necesitan ser pedidos en tiempo de ejecución antes de usar la cámara

```
requestPermissions(arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
```

Como la cámara de fotos guarda archivos, necesita configurar esa parte también en el manifest.xml

```
<application>
...
  <provider
            android:authorities="com.example.icesiapp231"
            android:name="androidx.core.content.FileProvider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data android:name="android.support.FILE_PROVIDER_PATHS" android:resource="@xml/paths"/>
  </provider>
...
</application>
```
Ahí necesitará crear un archivo paths.xml en la carpeta xml. Se puede ayudar de Android Studio para esto

El contenido del archivo especifica qué rutas de la carpeta de la aplicación tendrán permiso de acceso por la cámara
```
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <external-path
        name="/storage/emulated/0"
        path="/Android/data/com.example.icesiapp231/files"/>
</paths>
```
