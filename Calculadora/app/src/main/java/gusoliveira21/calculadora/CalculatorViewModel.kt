package gusoliveira21.calculadora

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.objecthunter.exp4j.ExpressionBuilder

class CalculatorViewModel : ViewModel() {

    private val _listaDeSimbolos = listOf("+", "-", "*", "/", ".")

    private val _expressaoDigitada = MutableLiveData("")
    val expressaoDigitada: LiveData<String>
        get() = _expressaoDigitada

    private val clean = ""


    private fun onDigitaExpressao(valor: String) {
        _expressaoDigitada.value = _expressaoDigitada.value + valor
    }

    fun onAddSimbolMath(valor: Int) {
        if (valor in 0..9) onDigitaExpressao("$valor")
        else if (onConditionalTest(expressaoDigitada)) {
            when (valor) {
                11 -> onDigitaExpressao("+")
                12 -> onDigitaExpressao("-")
                13 -> onDigitaExpressao("*")
                14 -> onDigitaExpressao("/")
                15 -> if (onCheckIfAHasDot(expressaoDigitada)) onDigitaExpressao(
                    ".")
            }
        }
    }

    private fun onConditionalTest(expressaoDigitada: LiveData<String>): Boolean =
        ((onHasElementList(expressaoDigitada) && !onCheckIfLastElementOfListIsSymbol(
            expressaoDigitada)))

    private fun onHasElementList(campoDigitado: LiveData<String>): Boolean =
        ((campoDigitado.value!!.length).toString() != "0")

    //TODO:Se for true, chame a função onDelete ultimo elemento!
    private fun onCheckIfLastElementOfListIsSymbol(campoDigitado: LiveData<String>): Boolean {
        return try {
            onCheckIfIsASymbol(campoDigitado.value!![campoDigitado.value!!.length - 1].toString())
        } catch (e: Exception) {
            false
        }
    }

    //TODO: Se já houver um simbolo, chamar função para apagar o mesmo e digitar o novo simbolo no lugar
    private fun onCheckIfIsASymbol(elementoParaVerificarSeESimbolo: String): Boolean =
        ((_listaDeSimbolos.indexOf(elementoParaVerificarSeESimbolo)) != -1)

    private fun onCheckIfAHasDot(campoDigitado: LiveData<String>): Boolean {
        val qtdElementos = campoDigitado.value!!.length
        return when {
            contSimbol(qtdElementos,campoDigitado) == 0 -> true
            contDot(qtdElementos,campoDigitado) == 0 -> true
            findSimbol(qtdElementos,campoDigitado) -> true
            else -> false
        }
    }

    private fun findSimbol(qtdElementos:Int, campoDigitado: LiveData<String>):Boolean{
        for (i in qtdElementos - 1 downTo 0 step 1){
            if (campoDigitado.value!![i].toString() == _listaDeSimbolos[0] ||
                campoDigitado.value!![i].toString() == _listaDeSimbolos[1] ||
                campoDigitado.value!![i].toString() == _listaDeSimbolos[2] ||
                campoDigitado.value!![i].toString() == _listaDeSimbolos[3])
                    return true
            else if(campoDigitado.value!![i].toString() == _listaDeSimbolos[4])
                return false
        }
        return false
    }

    private fun contDot(qtdElementos:Int, campoDigitado: LiveData<String>):Int{
        var contDot = 0
        for (i in qtdElementos - 1 downTo 0 step 1)
                if (campoDigitado.value!![i].toString() == _listaDeSimbolos[4])
                    contDot+=1
        return contDot
    }

    private fun contSimbol(qtdElementos:Int, campoDigitado: LiveData<String>):Int{
        var simbol = 0
        for (i in qtdElementos - 1 downTo 0 step 1)
            _listaDeSimbolos.forEachIndexed { index, value ->
                if (campoDigitado.value!![i].toString() == value)
                    simbol+=1
            }
        return simbol
    }


    fun onDeleteAll() {
        _expressaoDigitada.value = clean
    }

    fun onDeleteTheLastElement() {
        if (onHasElementList(_expressaoDigitada))
            _expressaoDigitada.value = (_expressaoDigitada.value!!.subSequence(0,
                _expressaoDigitada.value!!.length - 1)).toString()
    }

    fun result() {
        _expressaoDigitada.value = retiraVirgula(onCalculaResult(expressaoDigitada))
    }

    private fun retiraVirgula(onCalculaResult: String): String {
        var valor = ""
        onDeleteAll()
        onCalculaResult.forEachIndexed { index, value ->
            if ((onCalculaResult[index].toString()) == ",") valor = "$valor."
            else valor += value
        }
        return valor
    }

    private fun onCalculaResult(expressaoDigitada: LiveData<String>): String {
        var value = ""
        value = if (onCheckIfLastElementOfListIsSymbol(expressaoDigitada))
            expressaoDigitada
                .value!!
                .subSequence(0, expressaoDigitada.value!!.length - 1)
                .toString()
        else
            expressaoDigitada.value!!
        val eval = ExpressionBuilder(value).build()
        val res = eval.evaluate()
        val longRes = res.toLong()
        return if (res == longRes.toDouble())
            longRes.toString()
        else
            String.format("%.2f", res)

    }


}



