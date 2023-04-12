package com.zz.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DemoPlugin:Plugin<Project> {
    override fun apply(p0: Project) {
        println("开始 ")
        p0.plugins.forEach {
            println(it.toString())
        }
//        p0.pluginManager.withPlugin("com.android.build.gradle.internal.plugins.AppPlugin") {
//            val extension = p0.extensions.getByType(AppExtension::class.java)
//            extension.applicationVariants.forEach {
//                println("name: ${it.name}")
//            }
//        }
        println("结束")
    }
}