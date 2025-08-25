package br.edu.ifsp.scl.ads.prdm.sc3014789.calculadora

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3014789.calculadora.databinding.ActivityMainBinding
import java.util.Objects.isNull
import java.util.Objects.nonNull

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var numeroAtual = ""
    private var primeiroNumero: Double? = null
    private var operacao: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(amb) {
            setContentView(amb.root)

            initBotoesNumeros()
            initBotoesOperacoes()
            clearBt.setOnClickListener { clear() }

            igualBt.setOnClickListener {
                val resultado = getResultadoOperacao()
                if (nonNull(resultado)) {
                    val resultadoFormatted = getFormattedNumber(resultado)
                    visorTv.text = resultadoFormatted
                    numeroAtual = resultadoFormatted
                    operacao = null
                } else clear()
            }

        }

    }

    private fun getResultadoOperacao(): Double? {
        val segundoNumero: Double = numeroAtual.toDouble()
        return when(operacao) {
            "/" -> {
                if (segundoNumero != 0.0)
                    return primeiroNumero?.div(segundoNumero)
                else {
                    Toast.makeText(this@MainActivity, "Divisão por 0 é inválida!", Toast.LENGTH_SHORT).show()
                    return null
                }
            }
            "X" -> primeiroNumero?.times(segundoNumero)
            "-" -> primeiroNumero?.minus(segundoNumero)
            "+" -> primeiroNumero?.plus(segundoNumero)
            else -> {
                Toast.makeText(this@MainActivity, "Algo de errado aconteceu!", Toast.LENGTH_SHORT).show()
                return null
            }
        }
    }

    private fun ActivityMainBinding.initBotoesNumeros() {
        val botoesNumeros = listOf(
            zeroBt, umBt, doisBt, tresBt, quatroBt, cincoBt, seisBt, seteBt, oitoBt, noveBt
        )

        botoesNumeros.forEach { botao ->
            botao.setOnClickListener {
                numeroAtual += botao.text
                if (isNull(operacao)) {
                    visorTv.text = numeroAtual
                } else {
                    val operacaoCompleta = getFormattedNumber(primeiroNumero) + operacao + numeroAtual
                    visorTv.text = operacaoCompleta
                }
            }
        }
    }

    private fun ActivityMainBinding.initBotoesOperacoes() {
        val botoesOperacoes = listOf(
            divisaoBt, multiplicacaoBt, subtracaoBt, adicaoBt
        )

        botoesOperacoes.forEach { botao ->
            botao.setOnClickListener {
                if (isNull(operacao)) {
                    primeiroNumero = numeroAtual.toDouble()
                    operacao = botao.text.toString()
                    val operacaoParcial = numeroAtual + operacao
                    visorTv.text = operacaoParcial
                    numeroAtual = ""
                } else {
                    primeiroNumero = getResultadoOperacao()
                    operacao = botao.text.toString()
                    val operacaoParcial = getFormattedNumber(primeiroNumero) + operacao
                    visorTv.text = operacaoParcial
                    numeroAtual = ""
                }
            }
        }
    }

    private fun ActivityMainBinding.clear() {
        visorTv.text = ""
        numeroAtual = ""
        primeiroNumero = null
        operacao = null
    }

    fun isInteger(num: Double?): Boolean {
        return num == num?.toInt()?.toDouble()
    }

    fun getFormattedNumber(num: Double?): String {
        if (isInteger(num))
            return num?.toInt().toString()
        else
            return num.toString()
    }

}