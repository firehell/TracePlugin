package com.zz.trace;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * @author zz
 * @date 2023/4/11
 * @describe
 **/
public class MethodTraceVisitor extends AdviceAdapter {
    private String className;

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param api           the ASM API version implemented by this visitor. Must be one of {@link
     *                      Opcodes#ASM4}, {@link Opcodes#ASM5}, {@link Opcodes#ASM6} or {@link Opcodes#ASM7}.
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    protected MethodTraceVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        mv.visitLdcInsn(className+ "/" + getName());
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC, "android/os/Trace", "beginSection",
                "(Ljava/lang/String;)V", false
        );
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitMethodInsn(
                Opcodes.INVOKESTATIC, "android/os/Trace", "endSection",
                "()V", false
        );
    }
}
