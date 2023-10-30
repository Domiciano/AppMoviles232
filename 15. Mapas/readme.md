Use el siguiente código para usar la ubicación continuamente

Declare los permisos en el manifest
```
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```

Pida los permisos en tiempo de ejecución
```
requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ), 1
        )
```


Dentro del fragmento usted puede usar el GPS 
```
val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000,
            1f){ location ->
            val latitude = location.latitude
            val longitude = location.longitude
            googleMap.addMarker(MarkerOptions().position(LatLng(latitude,longitude)).title("Marker"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude,longitude)))
        }
```
Si quiere calcular la distancia entre 2 puntos, puede usar

```
val distance = Location.distanceBetween(pointA.latitude, pointA.longitude, pointB.latitude, pointB.longitude, Metrics.KILOMETERS)
```

