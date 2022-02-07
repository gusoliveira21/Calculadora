package gusoliveira21.calculadora

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gusoliveira21.calculadora.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {
    lateinit var binding: FragmentCalculatorBinding
    lateinit var viewModel: CalculatorViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.expressaoDigitada.observe(viewLifecycleOwner, Observer { valor ->
            Log.e("ovserverValor", "recebido: $valor")
            binding.tvEntradaDados.text = viewModel.expressaoDigitada.value
        })
        return binding.root
    }
}

