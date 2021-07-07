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
    //val stringParaSomar:String = "${binding.textViewResultado.text}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.apply {
            textViewResultado.setText("")

            bt0.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "0"
            }
            bt1.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "1"
            }
            bt2.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "2"
            }
            bt3.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "3"
            }
            bt4.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "4"
            }
            bt5.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "5"
            }
            bt6.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "6"
            }
            bt7.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "7"
            }
            bt8.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "8"
            }
            bt9.setOnClickListener {
                binding.textViewResultado.text = "${binding.textViewResultado.text}" + "9"
            }


            btSoma.setOnClickListener {
                if (CheckIfIsEmpty(binding.textViewResultado.text)
                    || CheckIfLastElementOfListIsSymbol(binding.textViewResultado.text)
                )
                    ToastMessage()
                else
                    binding.textViewResultado.text = "${binding.textViewResultado.text}" + "+"

            }
            btDivisao.setOnClickListener {
                if (CheckIfIsEmpty(binding.textViewResultado.text)
                    || CheckIfLastElementOfListIsSymbol(binding.textViewResultado.text)
                )
                    ToastMessage()
                else
                    binding.textViewResultado.text = "${binding.textViewResultado.text}" + "/"
            }
            btSubtracao.setOnClickListener {
                if (CheckIfIsEmpty(binding.textViewResultado.text)
                    || CheckIfLastElementOfListIsSymbol(binding.textViewResultado.text)
                )
                    ToastMessage()
                else

                    binding.textViewResultado.text = "${binding.textViewResultado.text}" + "-"

            }
            btMultiplicacao.setOnClickListener {
                if (CheckIfIsEmpty(binding.textViewResultado.text)
                    || CheckIfLastElementOfListIsSymbol(binding.textViewResultado.text)
                )
                    ToastMessage()
                else

                    binding.textViewResultado.text = "${binding.textViewResultado.text}" + "*"
            }
            btVirgula.setOnClickListener {
                if (CheckIfIsEmpty(binding.textViewResultado.text)
                    || CheckIfLastElementOfListIsSymbol(binding.textViewResultado.text)
                )
                    ToastMessage()
                else if (VerificaSeJaFoiDigitadoUmPontoAnteriormente(binding.textViewResultado.text)) {
                    ToastMessage()
                } else
                    binding.textViewResultado.text = "${binding.textViewResultado.text}" + "."

            }

            btDelAll.setOnClickListener {
                binding.textViewResultado.setText("")
            }
            btDelLast.setOnClickListener {
                if (binding.textViewResultado.text.isEmpty())
                    Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
                else
                    binding.textViewResultado.setText(DeleteTheLastElement(binding.textViewResultado.text))
            }

            btCalcular.setOnClickListener {
                if (binding.textViewResultado.text.isEmpty())
                    Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
                else {
                    binding.textViewResultado.setText(Result(binding.textViewResultado.text))
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



