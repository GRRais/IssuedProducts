package ru.rayanis.issuedproducts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.rayanis.issuedproducts.data.DataProvider
import ru.rayanis.issuedproducts.data.Product
import ru.rayanis.issuedproducts.ui.theme.IssuedProductsTheme

class MainActivity : ComponentActivity() {

    private val productDetailsViewModel by viewModels<ProductDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IssuedProductsTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "mainScreen"
                ) {
                    composable("mainScreen") {
                        ProductsActivityScreen(this@MainActivity, navController, productDetailsViewModel)
                    }
                    composable(
                        route = "details",
                    ) {
                       // val
                        DetailsScreen(this@MainActivity,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsActivityScreen(
    activity: ComponentActivity,
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel = viewModel()
) {
    //val products: List<Product> by productDetailsViewModel.productsList.observeAsState(listOf())

    DataProvider.retrieveProducts(activity)
    val productsList = DataProvider.productList
    ProductsScreen(
        activity = activity,
        navController = navController,
        products = productsList,
        onAddProduct = { productDetailsViewModel.saveProduct(activity, it) },
        onRemoveProduct = { productDetailsViewModel.removeProduct(it) }
    )
}

@Composable
fun ProductsScreen(
    activity: ComponentActivity,
    navController: NavController,
    products: List<Product>,
    onAddProduct: (Product) -> Unit,
    onRemoveProduct: (Product) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(end = 8.dp , bottom = 8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
    ) {

        ProductsHomeContent(navigateToDetails = navController, )
        FloatingActionButton(onClick = {
            navController.navigate("details")
        },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )
    }
}

@Composable
fun ProductsHomeContent(navigateToDetails: (Product) -> Unit) {
    val products = remember{ DataProvider.productList}
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = products,
            itemContent = {
                ProductListItem(product = it, navigateToDetails)
            })
    }
}

@Composable
fun DetailsScreen(
    activity: ComponentActivity,
    navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel = viewModel()
) {
    val title by productDetailsViewModel.title.observeAsState("")
    val destination by productDetailsViewModel.destination.observeAsState("")
    val date by productDetailsViewModel.date.observeAsState("")
    val quantity by productDetailsViewModel.quantity.observeAsState("")
    val productCost by productDetailsViewModel.productCost.observeAsState("")
    val description by productDetailsViewModel.description.observeAsState("")
    val quantPerson by productDetailsViewModel.quantPerson.observeAsState("")
    val product by productDetailsViewModel.product.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                 val product = Product(
                    title = title,
                    destination = destination,
                    date = date,
                    quantity = quantity.toInt(),
                    productCost = productCost.toInt(),
                    description = description,
                    quantPersons = quantPerson.toInt()
                )

                productDetailsViewModel.saveProduct(activity, product)

                navController.navigate("mainScreen")
            }) {
                Text("Добавить")
            }
            Button(onClick = {
                //TODO
            }) {
                Text("Рассчитать")
            }
        }
        Text("Карточка изделия ", textAlign = TextAlign.Center)

        ProductsDetailTF(
            value = title,
            label = stringResource(id = R.string.title),
            onValueChange = {productDetailsViewModel.onTitleChange(it)})
        ProductsDetailTF(
            value = destination,
            label = stringResource(id = R.string.destination),
            onValueChange = {productDetailsViewModel.onDestinationChange(it)})
        ProductsDetailTF(
            value = date,
            label = stringResource(id = R.string.date),
            onValueChange = {productDetailsViewModel.onDateChange(it)})
        ProductsDetailTF(
            value = quantity,
            label = stringResource(id = R.string.quantity),
            onValueChange = {productDetailsViewModel.onQuantityChange(it)})
        ProductsDetailTF(
            value = productCost,
            label = stringResource(id = R.string.productCost),
            onValueChange = {productDetailsViewModel.onProductCostChange(it)})
        ProductsDetailTF(
            value = description,
            label = stringResource(id = R.string.description),
            onValueChange = {productDetailsViewModel.onDescriptionChange(it)})
        ProductsDetailTF(
            value = quantPerson,
            label = stringResource(id = R.string.quantPersons),
            onValueChange = {productDetailsViewModel.onQuantPersonChange(it)}
        )
    }
}

//поле ввода значения
@Composable
fun ProductsDetailTF(value: String, label: String, onValueChange: (String) -> Unit) {
        OutlinedTextField(
            value = value,
            label = { Text(text = label)},
            onValueChange = onValueChange)
}
