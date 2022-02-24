package ru.rayanis.issuedproducts.data

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Product(
    var id: String? = null,
    var title: String? = null,
    val destination: String? = null,
    @ServerTimestamp
    var date: String? = null,
    val quantity: Int? = null,
    val productCost: Int? = null,
    val description: String? = null,
    val quantPersons: Int? = null,
    //val list: MutableList<UsedMaterial>
):Parcelable

@Parcelize
data class UsedMaterial(
    val nameMaterial: Material,
    val quantityMaterial: Float = 0f,
    val unit: Material,
    val priceMaterial: Material,
    val costMaterial: Int = 0
): Parcelable

@Parcelize
data class Material(
    val name: String = "",
    val unit: String = "",
    val price: Float = 0f
):Parcelable