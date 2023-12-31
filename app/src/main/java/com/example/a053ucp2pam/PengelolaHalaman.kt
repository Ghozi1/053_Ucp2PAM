package com.example.a053ucp2pam

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a053ucp2pam.data.Dospem.dosen1

enum class PengelolaHalaman {
    Home,
    Formulir,
    Summary
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormulirApp(
    viewModel: FormViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold { inner ->
        val uiState by viewModel.stateUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PengelolaHalaman.Home.name,
            modifier = Modifier.padding(inner)
        ) {
            composable(route = PengelolaHalaman.Home.name) {
                HalamanSatu(
                    onNextButtonClicked = {
                        navController.navigate(PengelolaHalaman.Formulir.name)
                    }
                )
            }
            composable(route = PengelolaHalaman.Formulir.name) {
                val context = LocalContext.current
                HalamanDua(
                    dosenpembimbing1 = dosen1.map { id -> context.resources.getString(id) },
                    dosenpembimbing2 = dosen1.map { id -> context.resources.getString(id) },
                    onSelectionChanged1 = {viewModel.setDosenPem1(it)},
                    onSelectionChanged2 = {viewModel.setDosenPem2(it)},
                    onSubmitClicked = {viewModel.setData(it)
                        navController.navigate(PengelolaHalaman.Summary.name)
                    },
                )
            }
            composable(route = PengelolaHalaman.Summary.name) {
                HalamanTiga(FormUIState = uiState, onCancelButtonClicked = { navController.popBackStack(PengelolaHalaman.Formulir.name,inclusive = false) })
            }
        }
    }
}