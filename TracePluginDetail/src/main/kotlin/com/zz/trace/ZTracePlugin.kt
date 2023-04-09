package com.zz.trace

import com.android.build.gradle.AppExtension
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class ZTracePlugin : Plugin<Project> {
    companion object{
        const val TAG = "Trace plugin"
    }
    override fun apply(p0: Project) {
//        if (p0.plugins.hasPlugin("com.android.application")) {
//            throw GradleException("Matrix Plugin, Android Application plugin required.")
//        }
        p0.extensions.getByType(AppExtension::class.java).registerTransform(TraceTransform())
    }
}