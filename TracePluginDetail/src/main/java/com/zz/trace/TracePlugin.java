package com.zz.trace;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author zz
 * @date 2023/4/7
 * @describe
 **/
public class TracePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("GreetingStandaloneGradlePlugins(standalone) ---> apply");
        project.task("helloStandalone").doLast(
                task -> System.out.println("Hello from the com.nxg.plugins.GreetingStandaloneGradlePlugins(standalone)"));
    }
}
