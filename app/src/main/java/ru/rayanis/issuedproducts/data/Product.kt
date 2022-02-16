package ru.rayanis.issuedproducts.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Product(
    var title: String = "" ,
    val destination: String = "" ,
    val date: String ,
    val quantity: String ,
    val productCost: String ,
    val description: String = "" ,
    val quantPersons: String ,
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