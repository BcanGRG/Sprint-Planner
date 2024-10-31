package com.bcan.sprintplanner

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform