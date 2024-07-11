package net.ezra.ui.products

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
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
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                              Color(0xFF3B5998) ,
                                Color(0xFF8B9DC3)

                            )

                        )

                    )

                    .padding(16.dp)
            ) {
                item {
                    if (productImageUri != null) {
                        Image(
                            painter = rememberImagePainter(productImageUri), // Using rememberImagePainter with Uri
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    } else {
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
                    Button(onClick =
                    { launcher.launch("image/*") }) {
                        Text(
                            "Select Image",
                            color = Color.Gray
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = hotelName,
                        onValueChange = { hotelName = it },
                        label = { Text("Hotel Name", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = hotelNameError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (hotelNameError) {
                        Text("Hotel Name is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = hotelDescription,
                        onValueChange = { hotelDescription = it },
                        label = { Text("Hotel Description", color = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = hotelDescriptionError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (hotelDescriptionError) {
                        Text("Hotel Description is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = hotelPrice,
                        onValueChange = { hotelPrice = it },
                        label = { Text("Hotel Price", color = Color.White) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions(onDone = { /* Handle Done action */ }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White.copy(alpha = 0.1f)),
                        isError = hotelPriceError,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                            textColor = Color.White
                        )
                    )
                    if (hotelPriceError) {
                        Text("Hotel Price is required", color = Color.Red)
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    if (hotelImageError) {
                        Text("Hotel Image is required", color = Color.Red)
                    }

                    Button(
                        onClick = {
                            hotelNameError = hotelName.isBlank()
                            hotelDescriptionError = hotelDescription.isBlank()
                            hotelPriceError = hotelPrice.isBlank()
                            hotelImageError = productImageUri == null

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
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B5998)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .padding(15.dp)
                    ) {
                        Text("Add Hotel", color = Color.White)
                    }
                }
            }
        }
    )
}

private fun addHotelToFirestore(navController: NavController, onProductAdded: () -> Unit, hotelName: String, hotelDescription: String, hotelPrice: Double, hotelImageUri: Uri?) {
    if (hotelName.isEmpty() || hotelDescription.isEmpty() || hotelPrice.isNaN() || hotelImageUri == null) {
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
                        Toast.makeText(
                            navController.context,
                            "Hotel added successfully!",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.navigate(ROUTE_HOME)

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
