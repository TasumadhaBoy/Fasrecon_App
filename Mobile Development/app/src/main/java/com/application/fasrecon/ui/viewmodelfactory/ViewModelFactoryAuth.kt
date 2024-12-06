package com.application.fasrecon.ui.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.fasrecon.data.repository.AuthRepository
import com.application.fasrecon.di.InjectionAuth
import com.application.fasrecon.ui.login.LoginViewModel
import com.application.fasrecon.ui.register.RegisterViewModel
import com.application.fasrecon.ui.splashscreen.SplashScreenViewModel

class ViewModelFactoryAuth private constructor(private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(authRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository) as T
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryAuth? = null

        fun getInstance(context: Context): ViewModelFactoryAuth =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryAuth(InjectionAuth.createRepository(context))
            }.also { instance = it }
    }
}