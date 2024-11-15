package com.bcan.sprintplanner

import android.app.Application
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.google.firebase.FirebasePlatform
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

fun main() = application {

    initFirebase()

    Window(
        state = rememberWindowState(
            width = 1440.dp,
            height = 960.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        onCloseRequest = ::exitApplication,
        title = "SprintPlanner",
    ) {
        App()
    }
}

fun initFirebase() {
    FirebasePlatform.initializeFirebasePlatform(object : FirebasePlatform() {

        val storage = mutableMapOf<String, String>()
        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) = println(msg)

        override fun retrieve(key: String) = storage[key]

        override fun store(key: String, value: String) = storage.set(key, value)

    })

    val options = FirebaseOptions(
        projectId = "sprintplanner-f0e8f",
        applicationId = "1:318569107972:web:88fd484b5dd3246e701b51",
        apiKey = "AIzaSyAcy8zDq95TuP-FSSp9JPR1RMsBjJm66hE"
    )

    Firebase.initialize(Application(), options)
}