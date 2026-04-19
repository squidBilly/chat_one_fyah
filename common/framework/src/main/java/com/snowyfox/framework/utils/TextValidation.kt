package com.snowyfox.framework.utils

import android.util.Patterns

sealed interface TextValidation {
    data class NameValidation(val name: String) : TextValidation
    data class PasswordValidation(val password: String) : TextValidation
    data class EmailValidation(val email: String) : TextValidation
    companion object {
        fun NameValidation.isValid(): Boolean {
            val names = this.name.split(" ").filter { it.isEmpty() }
            return names.size > 1
        }

        fun PasswordValidation.isValid(): Boolean {
            val hasUppercase = this.password.any { it.isUpperCase() }
            val hasLowercase = this.password.any { it.isLowerCase() }
            val hasDigit = this.password.any { it.isDigit() }
            val hasValidLength = password.length > 8
            return hasUppercase && hasLowercase && hasDigit && hasValidLength
        }

        fun EmailValidation.isValid(): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(this.email).matches()
        }
    }
}