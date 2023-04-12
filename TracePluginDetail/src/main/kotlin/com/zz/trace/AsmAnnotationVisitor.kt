package com.zz.trace

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Opcodes

/**
 * Create by yiyonghao on 2020-08-20
 * Email: yiyonghao@bytedance.com
 */
class AsmAnnotationVisitor(annotationVisitor: AnnotationVisitor?) :
    AnnotationVisitor(Opcodes.ASM7, annotationVisitor), Opcodes {
    override fun visitAnnotation(name: String, descriptor: String): AnnotationVisitor {
        println("$name $descriptor")
        return av
    }
}