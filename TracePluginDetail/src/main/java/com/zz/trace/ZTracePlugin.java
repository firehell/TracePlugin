package com.zz.trace;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author zz
 * @date 2023/4/11
 * @describe
 **/
public class ZTracePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("===========  trace plugin start  ============");
        AppExtension appExtension = project.getExtensions().findByType(AppExtension.class);
        assert appExtension != null;
        appExtension.registerTransform(new ZTraceTransform());
    }
}
