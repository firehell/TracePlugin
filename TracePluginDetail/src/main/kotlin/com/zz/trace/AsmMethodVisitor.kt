package com.zz.trace

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

/**
 * Create by yiyonghao on 2020-08-08
 * Email: yiyonghao@bytedance.com
 */
class AsmMethodVisitor(
    methodVisitor: MethodVisitor?,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(
    ASM7, methodVisitor, access, name, descriptor
), Opcodes {
    private var isMatch = false
    override fun visitCode() {
        super.visitCode()
        if (isMatch) {
            //方法执行前插入
            mv.visitLdcInsn("tag")
            mv.visitLdcInsn("onCreate start")
            mv.visitMethodInsn(
                INVOKESTATIC,
                "android/util/Log",
                "d",
                "(Ljava/lang/String;Ljava/lang/String;)I",
                false
            )
            mv.visitInsn(POP)
        }
    }

    override fun visitInsn(opcode: Int) {
        if (isMatch) {
            //方法执行后插入
            // 如果不是return会出现两个end
            if (opcode == RETURN) {
                mv.visitLdcInsn("tag")
                mv.visitLdcInsn("onCreate end")
                mv.visitMethodInsn(
                    INVOKESTATIC,
                    "android/util/Log",
                    "d",
                    "(Ljava/lang/String;Ljava/lang/String;)I",
                    false
                )
                mv.visitInsn(POP)
            }
        }
        super.visitInsn(opcode)
    }

    override fun visitMethodInsn(
        opcodeAndSource: Int,
        owner: String,
        name: String,
        descriptor: String,
        isInterface: Boolean
    ) {
        if (isMatch) {
            mv.visitLdcInsn("tag")
            mv.visitLdcInsn("onCreate 1")
            mv.visitMethodInsn(
                INVOKESTATIC,
                "android/util/Log",
                "d",
                "(Ljava/lang/String;Ljava/lang/String;)I",
                false
            )
            mv.visitInsn(POP)
        }
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
        if (isMatch) {
            mv.visitLdcInsn("tag")
            mv.visitLdcInsn("onCreate 2")
            mv.visitMethodInsn(
                INVOKESTATIC,
                "android/util/Log",
                "d",
                "(Ljava/lang/String;Ljava/lang/String;)I",
                false
            )
            mv.visitInsn(POP)
        }
    }

    override fun visitAnnotation(descriptor: String, visible: Boolean): AnnotationVisitor {
        if (ANNOTATION_TRACK_METHOD == descriptor) isMatch = true
        return super.visitAnnotation(descriptor, visible)
    }

    companion object {
        private const val ANNOTATION_TRACK_METHOD = "Lcom/example/asm/Cat;"
    }
}