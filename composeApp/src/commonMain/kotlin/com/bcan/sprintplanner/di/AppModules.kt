package com.bcan.sprintplanner.di

import com.bcan.sprintplanner.data.repositories.HomeRepository
import com.bcan.sprintplanner.data.repositories.HomeRepositoryImpl
import com.bcan.sprintplanner.presentation.home.HomeViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val provideFirebaseModule = module {
    single { Firebase.firestore }
}

val provideRepositoryBindModules = module {
    factoryOf(::HomeRepositoryImpl) { bind<HomeRepository>() }
}

val provideViewModelModule = module {
    factoryOf(::HomeViewModel)
}
