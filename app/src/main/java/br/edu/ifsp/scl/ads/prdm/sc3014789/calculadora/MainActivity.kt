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
    private var primeiroNumero: Int? = null
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
                    visorTv.text = resultado.toString()
                    numeroAtual = resultado.toString()
                    operacao = null
                } else clear()
            }

        }

    }

    private fun getResultadoOperacao(): Int? {
        val segundoNumero: Int = numeroAtual.toInt()
        return when(operacao) {
            "/" -> {
                if (segundoNumero != 0)
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
                    val operacaoCompleta = primeiroNumero.toString() + operacao + numeroAtual
                    visorTv.text = operacaoCompleta
                }
            }
        }
    }

    // TODO - tratar casos onde já tem uma operação selecionada
    private fun ActivityMainBinding.initBotoesOperacoes() {
        val botoesOperacoes = listOf(
            divisaoBt, multiplicacaoBt, subtracaoBt, adicaoBt
        )

        botoesOperacoes.forEach { botao ->
            botao.setOnClickListener {
                if (isNull(operacao)) {
                    primeiroNumero = numeroAtual.toInt()
                    operacao = botao.text.toString()
                    numeroAtual = ""
                    val operacaoParcial = primeiroNumero.toString() + operacao
                    visorTv.text = operacaoParcial
                } else {
                    primeiroNumero = getResultadoOperacao()
                    operacao = botao.text.toString()
                    numeroAtual = ""
                    val operacaoParcial = primeiroNumero.toString() + operacao
                    visorTv.text = operacaoParcial
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
}