package com.application.fasrecon.ui.viewmodelfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.application.fasrecon.data.repository.UserRepository
import com.application.fasrecon.di.InjectionUser
import com.application.fasrecon.ui.changepassword.ChangePasswordViewModel
import com.application.fasrecon.ui.home.HomeViewModel
import com.application.fasrecon.ui.profile.ProfileViewModel
import com.application.fasrecon.ui.profilesettings.ProfileSettingsViewModel

class ViewModelFactoryUser private constructor(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileSettingsViewModel::class.java)) {
            return ProfileSettingsViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(ChangePasswordViewModel::class.java)) {
            return ChangePasswordViewModel(userRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }


    companion object {
        @Volatile
        private var instance: ViewModelFactoryUser? = null

        fun getInstance(context: Context): ViewModelFactoryUser =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryUser(InjectionUser.createRepository(context))
            }.also { instance = it }
    }
}