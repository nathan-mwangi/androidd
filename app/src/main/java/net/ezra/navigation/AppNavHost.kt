package net.ezra.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.ezra.ui.SplashScreen
import net.ezra.ui.about.AboutScreen
import net.ezra.ui.auth.LoginScreen
import net.ezra.ui.auth.SignUpScreen
import net.ezra.ui.dashboard.DashboardScreen
//import net.ezra.ui.auth.SignupScreen
import net.ezra.ui.home.HomeScreen
import net.ezra.ui.location.bmscreen
import net.ezra.ui.location.hardrockscreen
import net.ezra.ui.location.maskedscreen
import net.ezra.ui.location.mhscreen
import net.ezra.ui.location.osscreen
import net.ezra.ui.location.rbscreen
import net.ezra.ui.location.rcscreen
import net.ezra.ui.location.roscreen
import net.ezra.ui.location.ssscreen
import net.ezra.ui.location.stscreen
import net.ezra.ui.products.AddProductScreen
import net.ezra.ui.products.ProductDetailScreen
import net.ezra.ui.products.ProductListScreen
import net.ezra.ui.students.AddStudents
import net.ezra.ui.students.Search
import net.ezra.ui.students.Students

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH


) {


    BackHandler {
        navController.popBackStack()

        }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {


        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }


        composable(ROUTE_ABOUT) {
            AboutScreen(navController)
        }


        composable(ROUTE_ADD_STUDENTS) {
            AddStudents(navController)
        }
        composable(ROUTE_ME){
            maskedscreen(navController)
        }

        composable(ROUTE_HR){
         hardrockscreen(navHostController = rememberNavController())
        }

        composable(ROUTE_RB){
            rbscreen(navController)
        }

        composable(ROUTE_ST){
            stscreen(navController)

        }

        composable(ROUTE_OS){
            osscreen(navController)

        }

        composable(ROUTE_BM){
            bmscreen(navHostController = rememberNavController())
        }

        composable(ROUTE_RC){
            rcscreen(navController)

        }

        composable(ROUTE_RO){
            roscreen(navController)

        }

        composable(ROUTE_MH){
            mhscreen(navController)

        }

        composable(ROUTE_SS){
            ssscreen(navController)
        }

        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUTE_VIEW_STUDENTS) {
           Students(navController = navController, viewModel = viewModel() )
        }

        composable(ROUTE_SEARCH) {
            Search(navController)
        }

        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }

        composable(ROUTE_REGISTER) {
           SignUpScreen(navController = navController) {

           }
        }

        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController){}
        }

        composable(ROUTE_ADD_PRODUCT) {
            AddProductScreen(navController = navController){}
        }

        composable(ROUTE_VIEW_PROD) {
            ProductListScreen(navController = navController, products = listOf() )
        }



        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetailScreen(navController, productId)
        }


        composable(ROUTE_SETTINGS) {
            LoginScreen(navController = navController){}


    }
}}