# Aplicación a crear
Crearemos una aplicación que permite a los usuarios registrarse y hacer login para acceder a varias funcionalidades, incluyendo un perfil personalizado, chat y una comunidad de publicación de posts. A continuación está la lista de características que irán aumentando conforme avancemos

## Características

### Registro y Login
La aplicación permite a los usuarios crear una cuenta e iniciar sesión en la aplicación. Al iniciar sesión, los usuarios son redirigidos a la pestaña de perfil, donde pueden ver sus propios posts y editar su información de perfil.

### Perfil de Usuario y Mis Posts
La pestaña de perfil de usuario muestra la información del usuario y sus posts publicados. Los usuarios pueden editar su información de perfil, incluyendo su nombre, foto de perfil y otros detalles.

### Chat
La funcionalidad de chat permite a los usuarios enviar y recibir mensajes entre sí. Los usuarios pueden ver una lista de todos los usuarios registrados y seleccionar uno para iniciar una conversación. Las conversaciones se almacenan en tiempo real en la base de datos y se actualizan automáticamente en la pantalla del chat.

### Comunidad
La pestaña de Comunidad muestra un RecyclerView que contiene todos los posts publicados por los usuarios de la aplicación. Los usuarios pueden ver los posts y agregar comentarios o darles "me gusta". También pueden publicar sus propios posts para que los vea la comunidad.

## Dependencias necesarias
```
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.5.1'
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1'
implementation 'androidx.fragment:fragment-ktx:1.5.2'
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4"

```
