package net.ezra.ui.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import net.ezra.R
import net.ezra.navigation.ROUTE_ADD_PRODUCT
import net.ezra.navigation.ROUTE_HOME
import net.ezra.navigation.ROUTE_LOGIN
import net.ezra.navigation.ROUTE_REGISTER

@Composable
fun SignUpScreen(navController: NavController, onSignUpSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val backgroundImage: Painter = painterResource(id = R.drawable.b4)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF3B5998),
                        Color(0xFF8B9DC3)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.canvas),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Sign Up", style = MaterialTheme.typography.h4.copy(color = Color.White, fontSize = 30.sp))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.1f)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.1f)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White.copy(alpha = 0.1f)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    textColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Color.White
                )
            } else {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF3B5998)),
                    onClick = {
                        if (email.isBlank()) {
                            error = "Email is required"
                        } else if (password.isBlank()) {
                            error = "Password is required"
                        } else if (confirmPassword.isBlank()) {
                            error = "Password Confirmation required"
                        } else if (password != confirmPassword) {
                            error = "Passwords do not match"
                        } else {
                            isLoading = true
                            signUp(email, password, {
                                isLoading = false
                                Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                                onSignUpSuccess()
                            }) { errorMessage ->
                                isLoading = false
                                error = errorMessage
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Text("Sign Up", color = Color.White,

                        modifier = Modifier
                            .clickable { navController.navigate(ROUTE_ADD_PRODUCT){
                                popUpTo(ROUTE_REGISTER){inclusive = true}
                            }


                            }



                    )
                }

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ROUTE_LOGIN) {
                                popUpTo(ROUTE_REGISTER) { inclusive = true }
                            }
                        }
                        .padding(top = 8.dp),
                    text = "Already have an account? Log in",
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private fun signUp(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val signInMethods = task.result?.signInMethods ?: emptyList()
                if (signInMethods.isNotEmpty()) {
                    onError("Email is already registered")
                } else {
                    // Email is not registered, proceed with sign-up
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { signUpTask ->
                            if (signUpTask.isSuccessful) {
                                onSuccess()
                            } else {
                                onError(signUpTask.exception?.message ?: "Sign-up failed")
                            }
                        }
                }
            } else {
                onError(task.exception?.message ?: "Failed to check email availability")
            }
        }
}
