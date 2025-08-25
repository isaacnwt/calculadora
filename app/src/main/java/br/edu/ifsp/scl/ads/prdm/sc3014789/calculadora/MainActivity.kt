package br.edu.ifsp.scl.ads.prdm.sc3014789.calculadora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.ads.prdm.sc3014789.calculadora.databinding.ActivityMainBinding

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
            initClearButton()

            igualBt.setOnClickListener {
                val resultado = getResultadoOperacao()
                visorTv.text = resultado.toString()
                numeroAtual = resultado.toString()
                operacao = null
            }

        }

    }

    private fun ActivityMainBinding.getResultadoOperacao(): Int? {
        val segundoNumero: Int = numeroAtual.toInt()
        return when(operacao) {
            "/" -> primeiroNumero?.div(segundoNumero)
            "X" -> primeiroNumero?.times(segundoNumero)
            "-" -> primeiroNumero?.minus(segundoNumero)
            "+" -> primeiroNumero?.plus(segundoNumero)
            else -> {null}
        }
    }

    private fun ActivityMainBinding.initBotoesNumeros() {
        val botoesNumeros = listOf(
            zeroBt, umBt, doisBt, tresBt, quatroBt, cincoBt, seisBt, seteBt, oitoBt, noveBt
        )

        botoesNumeros.forEach { botao ->
            botao.setOnClickListener {
                if (operacao == null) {
                    numeroAtual += botao.text
                    visorTv.text = numeroAtual
                } else {
                    numeroAtual += botao.text
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
                primeiroNumero = numeroAtual.toInt()
                numeroAtual = ""
                operacao = botao.text.toString()

                val operacaoParcial = primeiroNumero.toString() + operacao
                visorTv.text = operacaoParcial
            }
        }
    }

    private fun ActivityMainBinding.initClearButton() {
        clearBt.setOnClickListener {
            visorTv.text = ""
            numeroAtual = ""
            primeiroNumero = null
            operacao = null
        }
    }
}