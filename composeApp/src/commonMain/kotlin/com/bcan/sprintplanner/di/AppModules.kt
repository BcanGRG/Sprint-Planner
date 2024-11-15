package com.bcan.sprintplanner.di

import com.bcan.sprintplanner.data.repositories.HomeRepository
import com.bcan.sprintplanner.data.repositories.HomeRepositoryImpl
import com.bcan.sprintplanner.data.repositories.SprintRepository
import com.bcan.sprintplanner.data.repositories.SprintRepositoryImpl
import com.bcan.sprintplanner.presentation.home.HomeViewModel
import com.bcan.sprintplanner.presentation.sprint.SprintViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val provideFirebaseModule = module {
    single {
        val firestore = Firebase.firestore
        firestore.setLoggingEnabled(true)
        firestore
    }
}

val provideRepositoryBindModules = module {
    factoryOf(::HomeRepositoryImpl) { bind<HomeRepository>() }
    factoryOf(::SprintRepositoryImpl) { bind<SprintRepository>() }
}

val provideViewModelModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::SprintViewModel)
}
