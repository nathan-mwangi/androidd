package net.ezra.ui.products

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import net.ezra.navigation.ROUTE_ADD_PRODUCT
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_VIEW_PROD
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController, onProductAdded: () -> Unit) {
    var hotelName by remember { mutableStateOf("") }
    var hotelDescription by remember { mutableStateOf("") }
    var hotelPrice by remember { mutableStateOf("") }
    var productImageUri by remember { mutableStateOf<Uri?>(null) }

    // Track if fields are empty
    var hotelNameError by remember { mutableStateOf(false) }
    var hotelDescriptionError by remember { mutableStateOf(false) }
    var hotelPriceError by remember { mutableStateOf(false) }
    var hotelImageError by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            productImageUri = it
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add Hotel", fontSize = 30.sp, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(ROUTE_VIEW_PROD)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "backIcon",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff4B0082), // Exotic purple color
                    titleContentColor = Color.White,
                )
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xffF0E68C)) // Light golden background
            ) {
                item {
                    if (productImageUri != null) {
                        // Display selected image
                        Image(
                            painter = rememberImagePainter(productImageUri), // Using rememberImagePainter with Uri
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    } else {
                        // Display placeholder if no image selected
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Image Selected", modifier = Modifier.padding(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { launcher.launch("image/*") }) {
                        Text("Select Image",
                            color = Color.Gray

                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = hotelName,
                        onValueChange = { hotelName = it },
                        label = { Text("Hotel Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = hotelDescription,
                        onValueChange = { hotelDescription = it },
                        label = { Text("Hotel Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = hotelPrice,
                        onValueChange = { hotelPrice = it },
                        label = { Text("Hotel Price") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onDone = { /* Handle Done action */ }),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (hotelNameError) {
                        Text("Hotel Name is required", color = Color.Red)
                    }
                    if (hotelDescriptionError) {
                        Text("Hotel Description is required", color = Color.Red)
                    }
                    if (hotelPriceError) {
                        Text("Hotel Price is required", color = Color.Red)
                    }
                    if (hotelImageError) {
                        Text("Hotel Image is required", color = Color.Red)
                    }

                    // Button to add hotel
                    Button(
                        onClick = {
                            // Reset error flags
                            hotelNameError = hotelName.isBlank()
                            hotelDescriptionError = hotelDescription.isBlank()
                            hotelPriceError = hotelPrice.isBlank()
                            hotelImageError = productImageUri == null

                            // Add hotel if all fields are filled
                            if (!hotelNameError && !hotelDescriptionError && !hotelPriceError && !hotelImageError) {
                                addHotelToFirestore(
                                    navController,
                                    onProductAdded,
                                    hotelName,
                                    hotelDescription,
                                    hotelPrice.toDouble(),
                                    productImageUri
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add Hotel")
                    }
                }
            }
        }
    )
}

private fun addHotelToFirestore(navController: NavController, onProductAdded: () -> Unit, hotelName: String, hotelDescription: String, hotelPrice: Double, hotelImageUri: Uri?) {
    if (hotelName.isEmpty() || hotelDescription.isEmpty() || hotelPrice.isNaN() || hotelImageUri == null) {
        // Validate input fields
        return
    }

    val hotelId = UUID.randomUUID().toString()

    val firestore = Firebase.firestore
    val hotelData = hashMapOf(
        "name" to hotelName,
        "description" to hotelDescription,
        "price" to hotelPrice,
        "imageUrl" to ""
    )

    firestore.collection("hotels").document(hotelId)
        .set(hotelData)
        .addOnSuccessListener {
            uploadImageToStorage(hotelId, hotelImageUri) { imageUrl ->
                firestore.collection("hotels").document(hotelId)
                    .update("imageUrl", imageUrl)
                    .addOnSuccessListener {
                        // Display toast message
                        Toast.makeText(
                            navController.context,
                            "Hotel added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Navigate to another screen
                        navController.navigate(ROUTE_HOME)

                        // Invoke the onProductAdded callback
                        onProductAdded()
                    }
                    .addOnFailureListener { e ->
                        // Handle error updating hotel document
                    }
            }
        }
        .addOnFailureListener { e ->
            // Handle error adding hotel to Firestore
        }
}

private fun uploadImageToStorage(hotelId: String, imageUri: Uri?, onSuccess: (String) -> Unit) {
    if (imageUri == null) {
        onSuccess("")
        return
    }

    val storageRef = Firebase.storage.reference
    val imagesRef = storageRef.child("hotels/$hotelId.jpg")

    imagesRef.putFile(imageUri)
        .addOnSuccessListener { taskSnapshot ->
            imagesRef.downloadUrl
                .addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
                .addOnFailureListener {
                    // Handle failure to get download URL
                }
        }
        .addOnFailureListener {
            // Handle failure to upload image
        }
}
