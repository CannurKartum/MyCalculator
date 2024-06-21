package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastNumeric:Boolean = false
    var lastDot:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onClear(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view:View){
        if (lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEqual(view:View){
        if (lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    // This is the way how we can see the putted value in the calculator,
                    // for example if we have "99-1" in the [0] we will get the "99" and in the [1] we will get the "1"
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    removeZero((one.toDouble() - two.toDouble()).toString())
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    removeZero((one.toDouble() + two.toDouble()).toString())
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                }else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    tvInput?.text = removeZero((one.toDouble() / two.toDouble()).toString())
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                }else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    tvInput?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                }
            }catch(e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZero(result:String):String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
            return value
    }


}