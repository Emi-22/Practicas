package com.example.practica2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.error
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica2.ui.theme.Practica2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Practica2Theme {
                CalculatorScreen()
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun CalculatorScreen() {
    var displayValue by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("0") }
    var currentInput by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var previousInput by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    var currentOperator by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<String?>(null) }

    fun onNumberClick(number: String) {
        if (displayValue == "0" && number != ".") {
            displayValue = number
        } else if (displayValue.length < 15) { // Limitar la longitud de la entrada
            displayValue += number
        }
        currentInput += number
    }

    fun formatResult(result: Double): String {
        // Evitar ".0" para números enteros y manejar decimales
        return if (result % 1 == 0.0) {
            result.toLong().toString()
        } else {
            String.format("%.2f", result) // Formatear a 2 decimales si es necesario
        }
    }

    fun onEqualsClick() {
        if (currentInput.isNotEmpty() && previousInput.isNotEmpty() && currentOperator != null) {
            val num1 = previousInput.toDoubleOrNull() ?: 0.0
            val num2 = currentInput.toDoubleOrNull() ?: 0.0
            val result = when (currentOperator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                else -> 0.0
            }
            displayValue = formatResult(result)
            previousInput = displayValue // Guardar resultado para operaciones consecutivas
            currentInput = ""
            currentOperator = null
        }
    }

    fun onOperatorClick(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (previousInput.isNotEmpty() && currentOperator != null) {
                // Si ya hay un operador y un input previo, calcula primero
                onEqualsClick() // Calcula el resultado intermedio
                // El resultado actual se convierte en el previousInput para la nueva operación
                previousInput = displayValue
                currentInput = ""
                currentOperator = operator
            } else {
                previousInput = currentInput
                currentInput = ""
                currentOperator = operator
                displayValue = previousInput // Muestra el número anterior mientras se espera el siguiente
            }
        } else if (previousInput.isNotEmpty()) {
            // Permite cambiar el operador si no se ha ingresado un nuevo número
            currentOperator = operator
        }
    }



    fun onClearClick() {
        displayValue = "0"
        currentInput = ""
        previousInput = ""
        currentOperator = null
    }




    androidx.compose.material3.Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {
            // Display
            androidx.compose.material3.Text(
                text = displayValue,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ocupa el espacio disponible arriba
                    .padding(vertical = 32.dp),
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End // Alinea el texto a la derecha
            )

            // Buttons
            CalculatorButtonsLayout(
                onNumberClick = ::onNumberClick,
                onOperatorClick = ::onOperatorClick,
                onEqualsClick = ::onEqualsClick,
                onClearClick = ::onClearClick
            )
        }
    }
}

@androidx.compose.runtime.Composable
fun CalculatorButtonsLayout(
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit
) {
    androidx.compose.foundation.layout.Column(
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        // Fila 1: 7, 8, 9, +
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(text = "7", onClick = { onNumberClick("7") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "8", onClick = { onNumberClick("8") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "9", onClick = { onNumberClick("9") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "+", onClick = { onOperatorClick("+") }, modifier = Modifier.weight(1f), color = androidx.compose.material3.MaterialTheme.colorScheme.secondary)
        }
        // Fila 2: 4, 5, 6, -
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(text = "4", onClick = { onNumberClick("4") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "5", onClick = { onNumberClick("5") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "6", onClick = { onNumberClick("6") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "-", onClick = { onOperatorClick("-") }, modifier = Modifier.weight(1f), color = androidx.compose.material3.MaterialTheme.colorScheme.secondary)
        }
        // Fila 3: 1, 2, 3, =
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(text = "1", onClick = { onNumberClick("1") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "2", onClick = { onNumberClick("2") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "3", onClick = { onNumberClick("3") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = "=", onClick = onEqualsClick, modifier = Modifier.weight(1f), color = androidx.compose.material3.MaterialTheme.colorScheme.primary)
        }
        // Fila 4: C, 0, .
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(text = "C", onClick = onClearClick, modifier = Modifier.weight(1f), color = androidx.compose.material3.MaterialTheme.colorScheme.error)
            CalculatorButton(text = "0", onClick = { onNumberClick("0") }, modifier = Modifier.weight(1f))
            CalculatorButton(text = ".", onClick = { onNumberClick(".") }, modifier = Modifier.weight(1f))
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f)) // Espacio vacío para alinear con las otras filas
        }
    }
}

@androidx.compose.runtime.Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant,
    contentColor: androidx.compose.ui.graphics.Color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1.3f) // Hace los botones un poco más anchos que altos, puedes ajustar esto
            .padding(4.dp),
        shape = androidx.compose.material3.MaterialTheme.shapes.medium,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = contentColor
        )
    ) {
        androidx.compose.material3.Text(text = text, fontSize = 20.sp)
    }
}


@Preview(showBackground = true, showSystemUi = true)
@androidx.compose.runtime.Composable
fun CalculatorScreenPreview() {
    Practica2Theme {
        CalculatorScreen()
    }
}
