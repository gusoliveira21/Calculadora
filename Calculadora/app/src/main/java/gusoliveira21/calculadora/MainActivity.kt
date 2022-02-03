package gusoliveira21.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import gusoliveira21.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //binding.lifecycleOwner = viewLifecycleOwner
        listenerNumbers()
        listenerSimbolosMatematicos()
        listenerDot()
        listenerDelete()
        listenerExibeResposta()
    }

    private fun ToastMessage() {
        /*Toast.makeText(
            this@MainActivity, "Informe a expressão corretamente!", Toast.LENGTH_SHORT
        ).show()*/
    }

    private fun listenerDot() {
        binding.btVirgula.setOnClickListener {
            if (viewModel.onConditionalTest(binding))
                ToastMessage()
            else if (viewModel.onVerificaSeJaFoiDigitadoUmPontoAnteriormente(binding.tvEntradaDados.text))
                ToastMessage()
            else
                binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "."
        }
    }

    private fun listenerSimbolosMatematicos() {
        binding.apply {
            btSoma.setOnClickListener {
                if (viewModel.onConditionalTest(binding))
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "+"
            }

            btDivisao.setOnClickListener {
                if (viewModel.onConditionalTest(binding))
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "/"
            }
            btSubtracao.setOnClickListener {
                if (viewModel.onConditionalTest(binding))
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "-"
            }
            btMultiplicacao.setOnClickListener {
                if (viewModel.onConditionalTest(binding))
                    ToastMessage()
                else
                    binding.tvEntradaDados.text = "${binding.tvEntradaDados.text}" + "*"
            }
        }
    }

    private fun listenerExibeResposta() {
        binding.btCalcular.setOnClickListener {
            if (binding.tvEntradaDados.text.isEmpty())
                Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
            else {
                binding.tvExibeResposta.setText(viewModel.onResult(binding.tvEntradaDados.text))
            }
        }
    }

    private fun listenerDelete() {
        binding.apply {
            btDelAll.setOnClickListener {
                binding.tvEntradaDados.text = viewModel.clean
                binding.tvExibeResposta.text = viewModel.clean
            }

            btDelLast.setOnClickListener {
                if (binding.tvEntradaDados.text.isEmpty())
                    Toast.makeText(this@MainActivity, "Campo Vazio", Toast.LENGTH_SHORT).show()
                else
                    binding.tvEntradaDados.setText(
                        viewModel.onDeleteTheLastElement(
                            binding.tvEntradaDados.text))
            }
        }
    }

    private fun listenerNumbers() {
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
        }
    }



}



