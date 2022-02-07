package gusoliveira21.calculadora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {
    //TODO: Depois de uma expressão cujo o resultado é um valor co virgula, aoefetuar outra conta o app buga
    // Realizar uma modificação para substituir virgula por ponto sempre que aparecer.
    private val _listaDeSimbolos = listOf("+", "-", "*", "/", ".")
    private val _listaDeValores = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")

    private val _expressaoDigitada = MutableLiveData("")
    val expressaoDigitada: LiveData<String>
        get() = _expressaoDigitada

    val clean = ""


    private fun onDigitaExpressao(valor: String) {
        _expressaoDigitada.value = _expressaoDigitada.value + valor
    }

    fun digitaSimbolMath(valor: Int) {
        if (valor in 0..9) onDigitaExpressao("$valor")
        else if (onConditionalTest(expressaoDigitada)) {
            when (valor) {
                11 -> onDigitaExpressao("+")
                12 -> onDigitaExpressao("-")
                13 -> onDigitaExpressao("*")
                14 -> onDigitaExpressao("/")
                15 -> if (onVerificaSeJaFoiDigitadoUmPontoAnteriormente(expressaoDigitada) == false) onDigitaExpressao(".")
            }
        }
    }

    fun onConditionalTest(expressaoDigitada: LiveData<String>): Boolean =
        ((onHasElementList(expressaoDigitada) && !onCheckIfLastElementOfListIsSymbol(expressaoDigitada)))

    private fun onHasElementList(campoDigitado: LiveData<String>): Boolean =
        ((campoDigitado.value!!.length).toString() != "0") //(=0)vazio/(!=0)com elementos




    //TODO:Se for true, chame a função onDelete ultimo elemento!
    private fun onCheckIfLastElementOfListIsSymbol(campoDigitado: LiveData<String>): Boolean {
        try {
            return onCheckIfIsASymbol(campoDigitado.value!![campoDigitado.value!!.length - 1].toString())
        } catch (e: Exception) {
            return false
        }
    }


    //TODO: Se já houver um simbolo, chamar função para apagar o mesmo e digitar o novo simbolo no lugar
    private fun onCheckIfIsASymbol(elementoParaVerificarSeESimbolo: String): Boolean =
        ((_listaDeSimbolos.indexOf(elementoParaVerificarSeESimbolo)) != -1)
    //true = Sim,  possivel escrever o simbolo na tela
    //false = Não, não é possivel escrever o simbolo na tela, já tem um simbolo no local



    //TODO: Permite que digitemos no formato "00.00", não podemos digitar "0.0.0.0.0"
    fun onVerificaSeJaFoiDigitadoUmPontoAnteriormente(campoDigitado: LiveData<String>): Boolean {
        for (i in campoDigitado.value!!.length - 2 downTo 0 step 1) {
            if (campoDigitado.value!![i].toString().equals(_listaDeSimbolos[4])) return true

            _listaDeSimbolos.forEachIndexed { index, value ->
                if (campoDigitado.value!![i].toString().equals("$value")) return false
            }

            _listaDeValores.forEachIndexed { index, value ->
                if (campoDigitado.value!![i].toString().equals("$value"))
                    _listaDeSimbolos.forEachIndexed { index, value ->
                        if (campoDigitado.value!![i - 1].toString().equals("$value")) return false
                    }
            }
        }
        return false
    }

    fun onDeleteAll() {
        _expressaoDigitada.value = clean
    }

    fun onDeleteTheLastElement() {
        _expressaoDigitada.value = (_expressaoDigitada.value!!.subSequence(0,
            _expressaoDigitada.value!!.length - 1)).toString()
    }

    fun result() {
        _expressaoDigitada.value = onCalculaResult(expressaoDigitada)
    }

    private fun onCalculaResult(expressaoDigitada: LiveData<String>): String {
        var value = ""
        if (onCheckIfLastElementOfListIsSymbol(expressaoDigitada))
            value = expressaoDigitada
                .value!!
                .subSequence(0, expressaoDigitada.value!!.length - 1)
                .toString()
        else
            value = expressaoDigitada.value!!
        val eval = ExpressionBuilder(value).build()
        val res = eval.evaluate()
        val longRes = res.toLong()
        if (res == longRes.toDouble())
            return longRes.toString()
        else
            return String.format("%.2f", res)
    }


}



