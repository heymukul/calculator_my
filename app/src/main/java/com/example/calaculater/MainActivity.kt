package com.example.calaculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calaculater.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun OnDigitClick(view: View) {
        if (stateError){
            binding.dataTv.text = (view as Button).text
            stateError = false
        }else{
            binding.dataTv.append((view as Button).text)

        }
        lastNumeric = true
        OnEqual()
    }

    fun OnEqualClick(view: View) {
        OnEqual()
        binding.dataTv.text = binding.ResultTv.text.toString().drop(1)

    }

    fun OnAllclearClick(view: View) {
        binding.dataTv.text = ""
        binding.ResultTv.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
        binding.ResultTv.visibility = View.GONE
    }

    fun OnOperaterClick(view: View) {
        if(!stateError && lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastNumeric = false
            lastDot = false
            OnEqual()
            }
    }

    fun OnClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumeric = false
    }

    fun OnBackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()
            if (lastChar.isDigit()){
                OnEqual()
            }
        }
        catch (e: Exception){
            binding.ResultTv.text = ""
            binding.ResultTv.visibility = View.GONE
            Log.e("last char error",e.toString() )
        }
    }

    fun OnEqual(){
        if (lastNumeric && !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()

                binding.ResultTv.visibility = View.VISIBLE
                binding.ResultTv.text = "=$result"

            }
            catch ( ex: ArithmeticException){
                Log.e("evaluate errors",ex.toString())
                binding.ResultTv.text = "errors"
                stateError = true
                lastNumeric = false

            }
        }
    }
}
