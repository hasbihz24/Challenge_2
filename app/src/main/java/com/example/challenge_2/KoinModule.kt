package com.example.challenge_2

import android.app.Application
import com.example.challenge_2.ViewModel.ApiViewModel
import com.example.challenge_2.api.APIClient
import com.example.challenge_2.repos.MainRepository
import org.koin.android.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val Application.dataModule
        get() = module {
            //DATABASE

            //API
            single { APIClient.instance }
            //REPOSITORY
            factory { MainRepository(get()) }
        }

    val Application.uiModule
        get() = module {
            viewModel { ApiViewModel(get())}
        }
}
