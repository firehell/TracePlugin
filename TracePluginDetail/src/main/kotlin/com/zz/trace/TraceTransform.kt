package com.zz.trace

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils


class TraceTransform : Transform() {
    override fun getName(): String {
        return "traceTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        transformInvocation.inputs.forEach { transformInput ->
            transformInput.directoryInputs.forEach {
                traceDirectoryFiles(it, transformInvocation.outputProvider)
            }
            transformInput.jarInputs.forEach {
                traceJarFiles(it, transformInvocation.outputProvider)
            }
        }
    }

    private fun traceDirectoryFiles(
        directoryInput: DirectoryInput, outputProvider: TransformOutputProvider
    ) {
//        directoryInput.file.walkTopDown()
//            .filter { it.isFile }
//            .forEach { file ->
//                FileInputStream(file).use { fis ->
//
//                }
//            }
        val dest = outputProvider.getContentLocation(
            directoryInput.name,
            directoryInput.contentTypes,
            directoryInput.scopes,
            Format.DIRECTORY
        )
        println("Dir: ${directoryInput.file}, Dest: ${dest}")
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    private fun traceJarFiles(
        jarInput: JarInput, outputProvider: TransformOutputProvider
    ) {
        val dest = outputProvider.getContentLocation(
            jarInput.name,
            jarInput.contentTypes,
            jarInput.scopes,
            Format.JAR
        )
        println("Dir: ${jarInput.file}, Dest: ${dest}")
        FileUtils.copyFile(jarInput.file, dest)
    }
}