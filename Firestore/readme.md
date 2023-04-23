# Queries en Firestore


```
data class City(
    var id: String = "",
    var country: String = "",
    var city: String = "",
    var population: Int = 0,
    var sectors: ArrayList<String> = arrayListOf() 
)
```

Use estos datos de entrada
```
        Firebase.firestore
            .collection("cities")
            .document("db8608bc")
            .set(
               City("db8608bc", "USA", "San Francisco", 860000, arrayListOf("Z", "A"))
            )

        Firebase.firestore
            .collection("cities")
            .document("5c5b8399")
            .set(
               City("5c5b8399","USA", "Los Angeles", 3900000, arrayListOf("B", "Q"))
            )

        Firebase.firestore
            .collection("cities")
            .document("26c1a9c7")
            .set(
               City("26c1a9c7","USA", "Washington, D.C", 680000, arrayListOf("C", "W"))
            )

        Firebase.firestore
            .collection("cities")
            .document("26c1a9c7")
            .set(
               City("26c1a9c7","Japan", "Tokio", 9000000, arrayListOf("W", "F"))
            )

        Firebase.firestore
            .collection("cities")
            .document("e9df2cb8")
            .set(
               City("e9df2cb8","Mexico", "Ciudad de México", 8800000, arrayListOf("Z", "R"))
            )
            
        Firebase.firestore
            .collection("cities")
            .document("306f85d2")
            .set(
               City("306f85d2","China", "Beijing", 21500000, arrayListOf("G", "A"))
            )
            
        Firebase.firestore
            .collection("cities")
            .document("43dbe338")
            .set(
               City("43dbe338","Colombia", "Bogotá", 7100000, arrayListOf("H", "E"))
            )


        Firebase.firestore
            .collection("cities")
            .document("e9211d10")
            .set(
               City("e9211d10","Colombia", "Cali", 2200000, arrayListOf("I", "Q"))
            )

        Firebase.firestore
            .collection("cities")
            .document("9c8d8090")
            .set(
               City("9c8d8090","Colombia", "Ibagué", 529000, arrayListOf("L", "M"))
            )

        Firebase.firestore
            .collection("cities")
            .document("9fef8768")
            .set(
               City("9fef8768","USA", "New York", 8300000, arrayListOf("N", "Z"))
            )

        Firebase.firestore
            .collection("cities")
            .document("69da425e")
            .set(
               City("69da425e","Argentina", "Buenos Aires", 15100000, arrayListOf("D", "L"))
            )


```

## Tenga en cuenta las siguientes reglas

1. Una query sólo puede aplicar un sólo filtro de no igualdad (!=, <, <=, >, >=) a un campo específico.

2. Si se usa un filtro de rango (!=, <, <=, >, >=) a un campo, el orden (orderby) sólo puede ser aplicado al mismo campo

3. No se pueden usar DOS o más ArrayContains

4. Puede hacer 2 o más equalTo (==) en una query

5. Puede hacer varios filtros de igualdad y uno sólo de rango.

6. Es posible utilizar más de un filtro de no igualdad en una misma consulta, siempre y cuando se utilicen en campos diferentes. Por ejemplo, se podría hacer una consulta que filtre por todos los documentos cuyo campo "edad" esté entre 18 y 30 años, y cuyo campo "ciudad" sea diferente a "Madrid".

7. No es posible ordenar por campos que se encuentren dentro de un array (como un array de etiquetas).

8. En lugar de usar múltiples operadores ArrayContains, es posible utilizar operadores de rango (como < y >) para filtrar elementos dentro de un array. Por ejemplo, se podría hacer una consulta que filtre los documentos que contengan al menos un elemento dentro de un array que esté entre 5 y 10.

9. Es posible utilizar varios filtros de igualdad en una misma consulta, siempre y cuando se utilicen en campos diferentes. Por ejemplo, se podría hacer una consulta que filtre por todos los documentos cuyo campo "ciudad" sea igual a "Madrid" y cuyo campo "edad" sea igual a 25 años.

10. En una misma consulta es posible utilizar múltiples filtros de rango (como < y >), siempre y cuando se utilicen en campos diferentes.



## Queries en clase

```
package edu.co.icesi.queriesfirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val outputTV:TextView = findViewById(R.id.outputTV)

        lifecycleScope.launch(Dispatchers.IO){

            //Pedir un objeto
            val result1 = Firebase.firestore
                .collection("cities")
                .document("26c1a9c7")
                .get().await()
            val tokio = result1.toObject(City::class.java)
            tokio?.let {
                withContext(Dispatchers.Main){outputTV.text = tokio.country}
            }

            //Pedir una coleccion
            val result2 = Firebase.firestore
                .collection("cities")
                .get().await()
            var cityNames = ""
            for(doc in result2.documents){
                val obj = doc.toObject(City::class.java)
                cityNames += obj?.city+"\n"
            }
            withContext(Dispatchers.Main){outputTV.text = cityNames}

            val result3 = Firebase.firestore
                .collection("cities")
                .whereEqualTo("country", "Colombia")
                .get().await()
            cityNames = ""
            for(doc in result3.documents){
                val obj = doc.toObject(City::class.java)
                cityNames += obj?.city+"\n"
            }
            withContext(Dispatchers.Main){outputTV.text = cityNames}

            val result4 = Firebase.firestore
                .collection("cities")
                .whereLessThan("population", 1000000)
                .get().await()
            cityNames = ""
            for(doc in result4.documents){
                val obj = doc.toObject(City::class.java)
                cityNames += obj?.city+"\n"
            }
            withContext(Dispatchers.Main){outputTV.text = cityNames}

            val result5 = Firebase.firestore
                .collection("cities")
                .orderBy("city")
                .limit(4)
                .get().await()
            cityNames = ""
            for(doc in result5.documents){
                val obj = doc.toObject(City::class.java)
                cityNames += obj?.city+"\n"
            }
            withContext(Dispatchers.Main){outputTV.text = cityNames}

            val result6 = Firebase.firestore
                .collection("cities")
                .whereArrayContains("sectors", "Z")
                .get().await()
            cityNames = ""
            for(doc in result6.documents){
                val obj = doc.toObject(City::class.java)
                cityNames += obj?.city+"\n"
            }
            withContext(Dispatchers.Main){outputTV.text = cityNames}

            val result7 = Firebase.firestore
                .collection("cities")
                .whereEqualTo("country", "Colombia")
                .orderBy("city")
                .get().await()

        }


    }
}

data class City(
    var id: String = "",
    var country: String = "",
    var city: String = "",
    var population: Int = 0,
    var sectors: ArrayList<String> = arrayListOf()
)
```
