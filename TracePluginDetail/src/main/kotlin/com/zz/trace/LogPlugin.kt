package com.zz.trace

import org.gradle.api.Plugin
import org.gradle.api.Project


class LogPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("ceshi new ")
//        p0.pluginManager.withPlugin("com.android.application") {
//            val androidComponentsExtension = p0.extensions.getByType(Android)
//        }
    }
}