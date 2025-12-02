package ipca.example.calculator

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ipca.example.calculator.ui.theme.Blue
import ipca.example.calculator.ui.theme.CalculatorTheme
import ipca.example.calculator.ui.theme.Orange
import ipca.example.calculator.ui.theme.Purple80

@Composable
fun CalculatorButton(
    text: String = "0",
    isOperator: Boolean = false,
    onClick : (String) -> Unit = {}
) {
    Button(onClick = { onClick(text) },
        modifier = Modifier
            .size(90.dp)
            .aspectRatio(1f)
            .padding(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isOperator) Orange else Blue ,
            contentColor = Color.White,Orange,
        )
    ) {
        Text(
            text,
            fontSize = TextUnit(if (text == "AC") 18f else 45f, TextUnitType.Sp)



            )



    }
}


@Preview(showBackground = true)
@Composable
fun CalculatorButtonPreview(){
    CalculatorTheme {
        CalculatorButton(
            isOperator = false,
        )
    }
}
