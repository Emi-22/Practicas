package com.example.segundodia.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.segundodia.R
import java.text.NumberFormat
import java.util.Locale

private data class TaxBracket(
    val lower: Double,
    val upper: Double?,
    val fixedFee: Double,
    val rate: Double
)

// Tabla mensual SAT 2024
private val monthlyTaxBrackets = listOf(
    TaxBracket(0.0, 746.04, 0.0, 0.0192),
    TaxBracket(746.05, 6332.05, 14.32, 0.064),
    TaxBracket(6332.06, 11128.01, 371.83, 0.1088),
    TaxBracket(11128.02, 12935.82, 893.63, 0.16),
    TaxBracket(12935.83, 15487.71, 1182.88, 0.1792),
    TaxBracket(15487.72, 31236.49, 1640.18, 0.2136),
    TaxBracket(31236.5, 49233.0, 5004.12, 0.2352),
    TaxBracket(49233.01, 93993.9, 9230.62, 0.30),
    TaxBracket(93993.91, 125325.2, 22665.17, 0.32),
    TaxBracket(125325.21, 375975.61, 32691.18, 0.34),
    TaxBracket(375975.62, null, 117912.32, 0.35)
)

private fun calculateIsrMonthly(income: Double): Double {
    if (income <= 0) return 0.0
    val bracket = monthlyTaxBrackets.first {
        income >= it.lower && (it.upper == null || income <= it.upper)
    }
    val excedente = income - bracket.lower
    return bracket.fixedFee + excedente * bracket.rate
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ISRCalculatorScreen() {
    var salario by rememberSaveable { mutableStateOf("") }
    var isr by rememberSaveable { mutableStateOf<Double?>(null) }
    var neto by rememberSaveable { mutableStateOf<Double?>(null) }
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    val currencyFormatter = remember {
        NumberFormat.getCurrencyInstance(Locale("es", "MX"))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Calculadora ISR") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sat),
                contentDescription = "Logo del SAT",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Ingresa tu salario mensual para estimar ISR y neto.",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = salario,
                onValueChange = { salario = it },
                label = { Text("Salario mensual") },
                placeholder = { Text("Ej. 25000") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val ingreso = salario.replace(",", "").toDoubleOrNull()
                    if (ingreso == null || ingreso < 0) {
                        error = "Ingresa un número válido mayor o igual a 0."
                        isr = null
                        neto = null
                    } else {
                        val isrCalculado = calculateIsrMonthly(ingreso)
                        isr = isrCalculado
                        neto = (ingreso - isrCalculado).coerceAtLeast(0.0)
                        error = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            ResultRow(
                label = "ISR estimado",
                value = isr,
                formatter = currencyFormatter
            )
            ResultRow(
                label = "Ingreso neto",
                value = neto,
                formatter = currencyFormatter
            )
        }
    }
}

@Composable
private fun ResultRow(
    label: String,
    value: Double?,
    formatter: NumberFormat
) {
    val texto = value?.let { formatter.format(it) } ?: "--"
    Column {
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text(text = texto, fontSize = 20.sp)
    }
}

