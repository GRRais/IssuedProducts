package ru.rayanis.issuedproducts.data

import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DataProvider {

    private val issuedProductsRef = Firebase.firestore.collection("issuedProducts")

    fun saveProducts(activity: ComponentActivity, product: Product) =
        CoroutineScope(Dispatchers.IO).launch {
        try {
            issuedProductsRef.add(product)
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, "Успешно сохранен", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun retrievePerson(activity: ComponentActivity) =
        CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = issuedProductsRef
                .get()
                .await()
            val sb = StringBuilder()
            for (document in querySnapshot.documents) {
                val person = document.toObject<Person>()
                sb.append("$person\n")
            }
            withContext(Dispatchers.Main) {
                b.tvPersons.text = sb.toString()
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}