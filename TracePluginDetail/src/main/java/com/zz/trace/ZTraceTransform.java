package com.zz.trace;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * @author zz
 * @date 2023/4/11
 * @describe
 **/
public class ZTraceTransform extends Transform {

    @Override
    public String getName() {
        return "traceTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        for (TransformInput input : transformInvocation.getInputs()) {
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                File dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.getName(),
                        directoryInput.getContentTypes(), directoryInput.getScopes(),
                        Format.DIRECTORY);
                traceDirectoryFilesAsm(directoryInput.getFile(), dest);
            }
            for (JarInput jarInput : input.getJarInputs()) {
                traceJarFiles(jarInput, transformInvocation.getOutputProvider());
            }
        }
    }

    private void traceDirectoryFilesAsm(File input, File dest) throws IOException {
        if (dest.exists()) {
            FileUtils.forceDelete(dest);
        }
        FileUtils.forceMkdir(dest);
        String srcDirPath = input.getAbsolutePath();
        String destDirPath = dest.getAbsolutePath();
        for (File file : input.listFiles()) {
            String destFilePath = file.getAbsolutePath().replace(srcDirPath, destDirPath);
            File destFile = new File(destFilePath);
            if (file.isDirectory()) {
                traceDirectoryFilesAsm(file, destFile);
            } else if (file.isFile()) {
                FileUtils.touch(destFile);
                injectDetail(file.getAbsolutePath(), destFile.getAbsolutePath());
            }
        }
    }

    private void injectDetail(String inputPath, String outputPath) {
        System.out.println("插入代码开始");
        try {
            FileInputStream is = new FileInputStream(inputPath);
            ClassReader cr = new ClassReader(is);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassTraceVisitor adapter = new ClassTraceVisitor(Opcodes.ASM7, cw);
            cr.accept(adapter, ClassReader.EXPAND_FRAMES);
            FileOutputStream fos = new FileOutputStream(outputPath);
            fos.write(cw.toByteArray());
            fos.close();
            System.out.println("插入代码结束");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void traceDirectoryFiles(DirectoryInput directoryInput, TransformOutputProvider outputProvider) throws IOException {
//        File dest = outputProvider.getContentLocation((directoryInput.getName()), directoryInput.getContentTypes(),
//                directoryInput.getScopes(), Format.DIRECTORY);
//        System.out.println("Dir:" + directoryInput.getFile());
//        try {
//            FileUtils.copyDirectory(directoryInput.getFile(), dest);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void traceJarFiles(JarInput jarInput, TransformOutputProvider outputProvider) {
        File dest = outputProvider.getContentLocation(
                jarInput.getFile().getAbsolutePath(),
                jarInput.getContentTypes(),
                jarInput.getScopes(),
                Format.JAR);
        //将修改过的字节码copy到dest，就可以实现编译期间干预字节码的目的了
        try {
            FileUtils.copyFile(jarInput.getFile(), dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
