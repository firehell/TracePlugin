package com.zz.trace

import org.gradle.api.Plugin
import org.gradle.api.Project


class KtTracePlugin :Plugin<Project>{
    override fun apply(p0: Project) {
        println("ceshi hha")
        p0.task("hello gradle").doLast {
            println("last")
        }
    }
}