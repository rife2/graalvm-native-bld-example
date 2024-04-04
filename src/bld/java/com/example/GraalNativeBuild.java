package com.example;

import rife.bld.BuildCommand;
import rife.bld.Project;
import rife.bld.extension.ExecOperation;

import java.nio.file.Paths;
import java.util.List;
import java.util.jar.Attributes;

import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;
import static rife.bld.dependencies.Repository.RIFE2_RELEASES;
import static rife.bld.dependencies.Scope.test;

public class GraalNativeBuild extends Project {
    public GraalNativeBuild() {
        pkg = "com.example";
        name = "GraalNative";
        mainClass = "com.example.GraalNativeMain";
        version = version(0, 1, 0);

        javaRelease = 17;

        downloadSources = true;
        autoDownloadPurge = true;

        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES);
        scope(test)
                .include(dependency("org.junit.jupiter", "junit-jupiter", version(5, 10, 2)))
                .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(1, 10, 2)));

        jarOperation().manifestAttribute(Attributes.Name.MAIN_CLASS, mainClass());
    }

    public static void main(String[] args) {
        new GraalNativeBuild().start(args);
    }

    @BuildCommand(value = "native-exec", summary = "Build a native executable")
    public void nativeExec() throws Exception {
        new ExecOperation()
                .fromProject(this)
                .timeout(120)
                // The native image options documentation can be found at:
                // https://www.graalvm.org/22.0/reference-manual/native-image/Options/
                .command("native-image", // use its absolute path if not found
                        "-jar",
                        Paths.get(buildDistDirectory().getAbsolutePath(), jarFileName()).toString(),
                        "hello")
                .execute();

    }
}