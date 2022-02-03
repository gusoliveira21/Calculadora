package gusoliveira21.calculadora

import androidx.lifecycle.ViewModel
import gusoliveira21.calculadora.databinding.FragmentCalculatorBinding
import net.objecthunter.exp4j.ExpressionBuilder


class CalculatorViewModel: ViewModel() {
    private val simbol = listOf("+","-","/","*",".")
    val clean = ""

    fun onVerificaSeJaFoiDigitadoUmPontoAnteriormente(ViewCampoDigitado: CharSequence?): Boolean {
        val listaDeValores = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
        val listaDeSimbolos = listOf("+", "-", "*", "/")

        for (i in ViewCampoDigitado!!.length - 2 downTo 0 step 1) {
            if (ViewCampoDigitado[i].toString().equals(".")) return true

            listaDeSimbolos.forEachIndexed { index, value ->
                if (ViewCampoDigitado[i].toString().equals("$value")) return false
            }

            listaDeValores.forEachIndexed { index, value ->
                if (ViewCampoDigitado[i].toString().equals("$value"))

                    listaDeSimbolos.forEachIndexed { index, value ->
                        if (ViewCampoDigitado[i - 1].toString().equals("$value")) return false
                    }
            }
        }
        return false
    }

    fun onCheckIfIsEmpty(ViewCampoDigitado: CharSequence): Boolean {
        if ((ViewCampoDigitado.length - 1).toString() == "-1")
            return true
        return false
    }

    fun onDeleteTheLastElement(ViewCampoDigitado: CharSequence): CharSequence {
        return ViewCampoDigitado.subSequence(0, ViewCampoDigitado.length - 1)
    }
    //TODO:Se for true, chame a função onDelete ultumo elemento!
    fun onCheckIfLastElementOfListIsSymbol(ViewCampoDigitado: CharSequence): Boolean {
        return onCheckIfIsASymbol(ViewCampoDigitado[ViewCampoDigitado.length - 1].toString())
    }
    private fun onCheckIfIsASymbol(posicaoParaVerificarSeESimbolo: String): Boolean {
        if(simbol.lastIndexOf(posicaoParaVerificarSeESimbolo)>=0)
            return true
        return false
    }

    fun onResult(ViewCampoDigitado: CharSequence): CharSequence {
        val text: String
        if (onCheckIfLastElementOfListIsSymbol(ViewCampoDigitado))
            text = ViewCampoDigitado.subSequence(0, ViewCampoDigitado.length - 1).toString()
        else
            text = ViewCampoDigitado.toString()

        val eval = ExpressionBuilder(text).build()
        val res = eval.evaluate()

        val longRes = res.toLong()

        if(res == longRes.toDouble())
            return longRes.toString()
        else
            return String.format("%.2f", res)

        return ""
    }

    fun onConditionalTest(binding: FragmentCalculatorBinding):Boolean {
        if ((onCheckIfIsEmpty(binding.tvEntradaDados.text) || onCheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)))
            return true
        return false
    }
}