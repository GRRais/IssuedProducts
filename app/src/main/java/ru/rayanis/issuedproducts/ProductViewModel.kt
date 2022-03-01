package ru.rayanis.issuedproducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rayanis.issuedproducts.data.Product

class ProductViewModel: ViewModel() {

    //состояние списка выпущенных продуктов
    private var _products = MutableLiveData(listOf<Product>())
    val products: LiveData<List<Product>> = _products

    //событие добавление продукта
    fun addProduct(product: Product) {

    }

    fun removeProduct(product: Product) {

    }


}