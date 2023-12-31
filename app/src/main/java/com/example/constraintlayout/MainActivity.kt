package com.example.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.constraintlayout.ui.theme.ConstraintLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

/**
 * Criando e utilizando constraint layouts para layouts mais responsivos.
 */

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen() {
    Box(Modifier.fillMaxSize()) {
        val constraint = myConstraintSet(8.dp)
        ConstraintLayout(
            constraint,
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(Color.Cyan)
        ){
            // Criando referencias para usar constraint layout nos componentes
            val (button1, button2, button3, button4) = createRefs()

            // iniciando constraintAs passando a referencia do button1 para o MyButton
            /*MyButton(text = "Button1", Modifier.constrainAs(button1){
                /* mudando posicao do button em relacao ao constraint layout
                top.linkTo(parent.top, 60.dp)
                start.linkTo(parent.start, 30.dp)*/

                /**
                 * Definindo o linkTo(parent.start, parent.end), utilizamos restricoes opostas
                 * aos constraint e dessa forma conseguiriamos centrlizar o botao horizontalmente,
                 * pois, ele nao ficaria nem no start ou no end sobrando o centro para se posicionar.
                 */

                /*se o objetivo for alinhar elementos no centro podemos usar o centerVerticallyTo(parent)
                ou centerHorizontallyTo(parent), centralizando o elemento em relacao ao pai
                centerVerticallyTo(parent)
                centerHorizontallyTo(parent)*/

                // Alinhando itens em relacao a outros itens
                centerHorizontallyTo(parent)
                top.linkTo(parent.top)
                // difinindo que o bottom do button1 esta linkado ao top do button2
                bottom.linkTo(button2.top)
            })*/

            MyButton(text = "Button1", Modifier.layoutId("button1"))
            MyButton(text = "Button2", Modifier.layoutId("button2"))
            MyButton(text = "Button3", Modifier.layoutId("button3"))
            MyButton(text = "Button4", Modifier.layoutId("button4"))
        }
    }
}

@Composable
fun MyButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Text(text)
    }
}

// Criando funcao myConstraintSet que sera usada para reduzir codigos uma vez que podemos
// definir constraints fora de funcoes composable e depois aplicar dentro de elementos
private fun myConstraintSet(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button4 = createRefFor("button4")
        val button1 = createRefFor("button1")
        val button2 = createRefFor("button2")
        val button3 = createRefFor("button3")

        constrain(button4) {
            linkTo(parent.top, parent.bottom)
            start.linkTo(parent.start, margin = margin)

            width = Dimension.value(100.dp)
            height = Dimension.value(50.dp)
        }

        constrain(button1) {
            /* mudando posicao do button em relacao ao constraint layout
                top.linkTo(parent.top, 60.dp)
                start.linkTo(parent.start, 30.dp)*/

            /**
             * Definindo o linkTo(parent.start, parent.end), utilizamos restricoes opostas
             * aos constraint e dessa forma conseguiriamos centrlizar o botao horizontalmente,
             * pois, ele nao ficaria nem no start ou no end sobrando o centro para se posicionar.
             */

            /*se o objetivo for alinhar elementos no centro podemos usar o centerVerticallyTo(parent)
            ou centerHorizontallyTo(parent), centralizando o elemento em relacao ao pai
            centerVerticallyTo(parent)
            centerHorizontallyTo(parent)*/

            // Alinhando itens em relacao a outros itens
            centerHorizontallyTo(parent)
            top.linkTo(parent.top)
            // difinindo que o bottom do button1 esta linkado ao top do button2
            bottom.linkTo(button2.top)
        }

        constrain(button2) {
            // centralizando elemento em relacao ao pai
            centerHorizontallyTo(parent)
            // definindo que o top do button2 esta linkado ao bottom do button1
            top.linkTo(button1.bottom)
            // definindo o bottom do button2 ao bottom do pai
            bottom.linkTo(parent.bottom)
        }

        constrain(button3) {
            top.linkTo(parent.top, 20.dp)
            // criando bias, OBS: Bias vao de 0.0 ate 1.0, onde 0.0 estara
            // mais proximo ao start e 1.0 ao end
            linkTo(parent.start, parent.end, endMargin = 30.dp, bias = 1.0f)
            /**
             * Se quisermos criar um espacamento final padrao para os nossos
             * componentes podemos usar algumas opcoes como o endMargin usado acima
             * e o startMargin que limitam onde nossos componentes podem chegar,
             * por exemplo o button3 com bias = 1.0f colaria no lado direito dos dispositivos
             * se definirmos uma endMargin de 30.dp do lado direito o componente mesmo com o bias
             * nao conseguirar colar no fim da tela, com isso podemos modificar layout internos
             * e conseguimos manter um layout responsivo apos trocar a tela da vertical para a horizontal
             */
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConstraintLayoutTheme {
        MainScreen()
    }
}