package com.zz.trace

import org.gradle.api.Plugin
import org.gradle.api.Project

class ZLogPlugin : Plugin<Project> {
    override fun apply(p0: Project) {
        println("开始 log plugin")
    }
}