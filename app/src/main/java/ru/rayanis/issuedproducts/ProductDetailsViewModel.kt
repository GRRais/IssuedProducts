package ru.rayanis.issuedproducts

import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.rayanis.issuedproducts.data.DataProvider
import ru.rayanis.issuedproducts.data.Product

class ProductDetailsViewModel: ViewModel() {
    private val _title = MutableLiveData("")
    private val _destination = MutableLiveData("")
    private val _date = MutableLiveData("")
    private val _quantity = MutableLiveData("")
    private val _productCost = MutableLiveData("")
    private val _description = MutableLiveData("")
    private val _quantPerson = MutableLiveData("")

    val title: LiveData<String> = _title
    val destination: LiveData<String> = _title
    val date: LiveData<String> = _title
    val quantity: LiveData<String> = _title
    val productCost: LiveData<String> = _title
    val description: LiveData<String> = _title
    val quantPerson: LiveData<String> = _title

    private var _products = MutableLiveData(Product())
    val products: LiveData<Product> = _products

    //состояние списка выпущенных продуктов
    private var _productsList = MutableLiveData(listOf<Product>())
    val productsList: LiveData<List<Product>> = _productsList

    val context: ComponentActivity
    //событие добавление продукта
    fun addProduct(product: Product) {
        DataProvider.saveProducts(context,  )
    }

    fun removeProduct(product: Product) {

    }

    fun onTitleChange(newTitle: String) { _title.value = newTitle }
    fun onDestinationChange(newDestination: String) { _destination.value = newDestination }
    fun onDateChange(newDate: String) { _date.value = newDate }
    fun onQuantityChange(newQuantity: String) { _quantity.value = newQuantity }
    fun onProductCostChange(newProductCost: String) { _productCost.value = newProductCost }
    fun onDescriptionChange(newDescription: String) { _description.value = newDescription }
    fun onQuantPersonChange(newQuantPerson: String) { _quantPerson.value = newQuantPerson }
}