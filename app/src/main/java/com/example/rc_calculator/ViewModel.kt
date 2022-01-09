package com.example.rc_calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udojava.evalex.Expression
import java.math.BigDecimal
import java.util.*



class ViewModel : ViewModel() {

    private var isNumberPositive = true
    private val validOperators = Arrays.asList("+", "-", "/", "*")
    private val COMMA_STRING = "."
    private val PERC = "%"
    private val SCIENTIFIC_NOTATION_CHAR = "E"
    private val IMMENSITY = "Infinity"
    private val currentExpression = MutableLiveData<String>()
    private val resultInfo = MutableLiveData<String>()
    private val invalidMessage = MeasureLone<Boolean>()

    fun getInvalidMessage(): MeasureLone<Boolean> {
        return invalidMessage
    }

    fun getCurrentExpression(): MutableLiveData<String> {
        return currentExpression
    }

    fun getResultInfo(): MutableLiveData<String> {
        return resultInfo
    }

    fun onOperatorAdd(addedValue: String) {
        if (currentInvalid() && (validOperators.contains(addedValue) || COMMA_STRING == addedValue || PERC == addedValue)) {
            showInvalidMessage()
        } else {
            var isCommaAddedToExpression = false

            if ((isValueAnOperator(addedValue) || addedValue == PERC) && currentExpression.value!!.isNotEmpty()) {
                val lastCharacterOfExpression = currentExpression.value!!.get(currentExpression.value!!.length - 1)

                if (isValueAnOperator(lastCharacterOfExpression.toString())) {
                    clearLastChar()
                }
            } else if (addedValue == COMMA_STRING) {
                val expressionArray = currentExpression.value!!.toCharArray()
                for (c in expressionArray) {
                    if (c == COMMA_STRING.toCharArray()[0]) {
                        isCommaAddedToExpression = true
                    }
                    if (validOperators.contains(c.toString())) {
                        isCommaAddedToExpression = false
                    }
                }

                val lastCharacterOfExpression = currentExpression.value!!.get(currentExpression.value!!.length - 1)
                if (validOperators.contains(lastCharacterOfExpression.toString())) {
                    isCommaAddedToExpression = true
                }
            }

            if (!isCommaAddedToExpression) {
                if (currentExpression.value == null) {
                    currentExpression.postValue(addedValue)
                } else {
                    currentExpression.postValue(currentExpression.value + addedValue)
                }
            }
        }
    }

    fun onClear() {
        currentExpression.postValue("")
        resultInfo.postValue("")
    }

    fun onResult() {
        if (currentExpression.value == null || currentExpression.value!!.contains(IMMENSITY) || currentExpression.value!!.isEmpty()) {
            showInvalidMessage()
        } else {
            clearLastValueIfItIsAnOperator()

            currentExpression.value = currentExpression.value!!.replace(PERC.toRegex(), "/100")

            val expression = Expression(currentExpression.value!!)

            val bigDecimalResult = expression.eval()

            val doubleResult = bigDecimalResult.toDouble()

            val stringResult: String

            if (isValueInteger(doubleResult) && !isScientificNotation(java.lang.Double.toString(doubleResult))) {
                val roundedValue = Math.round(doubleResult).toInt()
                stringResult = roundedValue.toString()
            } else {
                stringResult = java.lang.Double.toString(doubleResult)
            }

            currentExpression.postValue(stringResult)
            resultInfo.postValue(stringResult)
        }
    }

    fun onChange() {
        if (currentInvalid()) {
            showInvalidMessage()
        } else {
            val newCurrentExpression = if (isNumberPositive)
                "-${currentExpression.value}"
            else
                currentExpression.value!!.substring(1, currentExpression.value!!.length)

            currentExpression.postValue(newCurrentExpression)
            isNumberPositive = !isNumberPositive
        }
    }

    private fun currentInvalid() =
        currentExpression.value == null || currentExpression.value!!.isEmpty()

    private fun isValueAnOperator(value: String): Boolean {
        return validOperators.contains(value.toCharArray()[0].toString())
    }

    private fun clearLastChar() {
        currentExpression.postValue(currentExpression.value!!.substring(0, currentExpression.value!!.length - 1))
    }

    private fun clearLastValueIfItIsAnOperator() {
        if (currentInvalid()) {
            showInvalidMessage()
        } else if (isValueAnOperator(getLastChar().toString())) {
            clearLastChar()
        }
    }

    private fun getLastChar(): Char {
        val currentExpressionLastValuePosition = currentExpression.value!!.length - 1
        return currentExpression.value!![currentExpressionLastValuePosition]
    }

    private fun isValueInteger(number: Double): Boolean {
        val roundedValue = Math.round(number).toInt()
        return number % roundedValue == 0.0
    }

    private fun isScientificNotation(numberString: String): Boolean {
        try {
            BigDecimal(numberString)
        } catch (e: NumberFormatException) {
            return false
        }

        return numberString.toUpperCase().contains(SCIENTIFIC_NOTATION_CHAR)
    }

    private fun showInvalidMessage() {
        invalidMessage.value = true
    }
}