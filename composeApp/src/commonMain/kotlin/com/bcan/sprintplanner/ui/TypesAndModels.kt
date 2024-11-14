package com.bcan.sprintplanner.ui

sealed class PlatformTypes(val name: String) {
    data object Unknown : PlatformTypes(name = "Unknown")
    data object AND : PlatformTypes(name = "Android")
    data object IOS : PlatformTypes(name = "Ios")
    data object TEST : PlatformTypes(name = "Test")
}