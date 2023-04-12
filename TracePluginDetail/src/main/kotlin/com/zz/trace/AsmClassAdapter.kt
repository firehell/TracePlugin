package com.zz.trace

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes


class AsmClassAdapter(cv: ClassVisitor?) : ClassVisitor(Opcodes.ASM7, cv), Opcodes {
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("visit Method 开始")
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return AsmMethodVisitor(mv, access, name, descriptor)
    }

    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
        println("visit annotation -> $descriptor")
        val av = super.visitAnnotation(descriptor, visible)
        return AsmAnnotationVisitor(av)
    }
}