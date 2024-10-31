package com.bcan.sprintplanner.di

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.firestore }
}