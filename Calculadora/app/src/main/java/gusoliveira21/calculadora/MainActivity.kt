package gusoliveira21.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import gusoliveira21.calculadora.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var valor: Double = 0.0
    //val stringParaSomar:String = "${binding.tvEntradaDados.text}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            tvEntradaDados.setText("")

            bt0.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "0"
            }
            bt1.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "1"
            }
            bt2.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "2"
            }
            bt3.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "3"
            }
            bt4.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "4"
            }
            bt5.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "5"
            }
            bt6.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "6"
            }
            bt7.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "7"
            }
            bt8.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "8"
            }
            bt9.setOnClickListener {
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "9"
            }


            btSoma.setOnClickListener {
                if (CheckIfIsEmpty(binding.tvEntradaDados.text)
                    || CheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)
                )
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "+"

            }
            btDivisao.setOnClickListener {
                if (CheckIfIsEmpty(binding.tvEntradaDados.text)
                    || CheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)
                )
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "/"
            }
            btSubtracao.setOnClickListener {
                if (CheckIfIsEmpty(binding.tvEntradaDados.text)
                    || CheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)
                )
                    ToastMessage()
                else

                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "-"

            }
            btMultiplicacao.setOnClickListener {
                if (CheckIfIsEmpty(binding.tvEntradaDados.text)
                    || CheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)
                )
                    ToastMessage()
                else

                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "*"
            }
            btVirgula.setOnClickListener {
                if (CheckIfIsEmpty(binding.tvEntradaDados.text)
                    || CheckIfLastElementOfListIsSymbol(binding.tvEntradaDados.text)
                )
                    ToastMessage()
                else if (VerificaSeJaFoiDigitadoUmPontoAnteriormente(binding.tvEntradaDados.text)) {
                    ToastMessage()
                } else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "."

            }

            btDelAll.setOnClickListener {
                binding.tvEntradaDados.setText("")
            }
            btDelLast.setOnClickListener {
                if (binding.tvEntradaDados.text.isEmpty())
                    Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
                else
                    binding.tvEntradaDados.setText(DeleteTheLastElement(binding.tvEntradaDados.text))
            }

            btCalcular.setOnClickListener {
                if (binding.tvEntradaDados.text.isEmpty())
                    Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
                else {
                    binding.tvEntradaDados.setText(Result(binding.tvEntradaDados.text))
                }
            }
        }
    }

    private fun ToastMessage() {
        /*Toast.makeText(
            this@MainActivity, "Informe a expressão corretamente!", Toast.LENGTH_SHORT
        ).show()*/
    }

    private fun VerificaSeJaFoiDigitadoUmPontoAnteriormente(ViewCampoDigitado: CharSequence?): Boolean {
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

    private fun CheckIfIsEmpty(ViewCampoDigitado: CharSequence): Boolean {
        if ((ViewCampoDigitado.length - 1).toString() == "-1")
            return true
        return false
    }

    private fun CheckIfLastElementOfListIsSymbol(ViewCampoDigitado: CharSequence): Boolean {
        return CheckIfIsASymbol(ViewCampoDigitado[ViewCampoDigitado.length - 1].toString())
    }

    private fun CheckIfIsASymbol(posicaoParaVerificarSeESimbolo: String): Boolean {
        when (posicaoParaVerificarSeESimbolo) {
            "+" -> return true
            "-" -> return true
            "/" -> return true
            "*" -> return true
            "." -> return true
        }
        return false
    }

    private fun DeleteTheLastElement(ViewCampoDigitado: CharSequence): CharSequence {
        return ViewCampoDigitado.subSequence(0, ViewCampoDigitado.length - 1)
    }

    private fun Result(ViewCampoDigitado: CharSequence): CharSequence {
        var text: String
        if (CheckIfLastElementOfListIsSymbol(ViewCampoDigitado))
            text = ViewCampoDigitado.subSequence(0, ViewCampoDigitado.length - 1).toString()
        else
            text = ViewCampoDigitado.toString()

        val eval = ExpressionBuilder(text).build()
        val res = eval.evaluate()
        text = res.toString()

        return String.format("%.2f", text.toDouble())
    }


}



