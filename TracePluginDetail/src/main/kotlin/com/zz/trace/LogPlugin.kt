package com.zz.trace

import com.android.build.gradle.AppExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project


class LogPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("ceshi new ")
        if (p0.plugins.hasPlugin("com.android.application")) {
            throw GradleException("Matrix Plugin, Android Application plugin required.")
        }
        val extension = p0.extensions.getByType(AppExtension::class.java)

    }
}