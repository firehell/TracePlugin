package com.zz.trace;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author zz
 * @date 2023/4/11
 * @describe
 **/
public class ClassTraceVisitor extends ClassVisitor implements Opcodes {
    private String className;

    public ClassTraceVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodTraceVisitor visitor = new MethodTraceVisitor(
                Opcodes.ASM7,
                super.visitMethod(access, name, descriptor, signature, exceptions),
                access, name, descriptor
        );
        visitor.setClassName(className);
        return visitor;
    }
}
