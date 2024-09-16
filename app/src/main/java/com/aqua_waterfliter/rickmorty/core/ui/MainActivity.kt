package com.aqua_waterfliter.rickmorty.core.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.aqua_waterfliter.rickmorty.core.ui.navigation.NavGraph
import com.aqua_waterfliter.rickmorty.core.ui.theme.RickMortyTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickMortyTheme(
                darkTheme = true
            ) {

                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val isLoggedIn = remember { mutableStateOf(Firebase.auth.currentUser != null) }
                val context = LocalContext.current

                // Register auth state listener
                DisposableEffect(Unit) {
                    val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                        isLoggedIn.value = auth.currentUser != null
                    }
                    Firebase.auth.addAuthStateListener(authStateListener)
                    onDispose {
                        Firebase.auth.removeAuthStateListener(authStateListener)
                    }
                }
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = false,
                    drawerContent = {
                        if (isLoggedIn.value) {
                            AppDrawer(
                                onLogoutClick = {
                                    Firebase.auth.signOut()
                                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                                    navController.navigate("login")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                }
                            )
                        }
                    },
                    scrimColor = MaterialTheme.colorScheme.background,
                    content = {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(
                                            "Rick&Morty",
                                            style = MaterialTheme.typography.headlineMedium
                                        )
                                    },
                                    navigationIcon = {
                                        if (isLoggedIn.value) {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    drawerState.open() // Open the drawer
                                                }
                                            }) {
                                                Icon(
                                                    Icons.Default.Menu,
                                                    contentDescription = "Menu"
                                                )
                                            }
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            },

                            ) { padding ->
                            NavGraph(padding, navController, isLoggedIn.value)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppDrawer(
    onLogoutClick: () -> Unit,
) {
    var isLoggingOut by remember { mutableStateOf(false) } // State to handle loading indicator
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),

        ) {
        TextButton(
            onClick = {
                isLoggingOut = true
                onLogoutClick()
                isLoggingOut = false
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        ) { Text("Logout", style = MaterialTheme.typography.headlineMedium) }
    }
}
