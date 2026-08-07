package com.example;

import rife.bld.BuildCommand;
import rife.bld.Project;
import rife.bld.extension.ExecOperation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.Attributes;

import static rife.bld.dependencies.Repository.*;
import static rife.bld.dependencies.Scope.test;

/**
 * Gradle Native Example Build.
 *
 * <pre>{@code ./bld clean compile jar native-class }</pre>
 */
public class GraalNativeBuild extends Project {

    public GraalNativeBuild() {
        pkg = "com.example";
        name = "GraalNative";
        mainClass = "com.example.GraalNativeMain";
        version = version(0, 1, 0);

        javaRelease = 17;

        downloadSources = true;
        autoDownloadPurge = true;

        repositories = List.of(MAVEN_CENTRAL, RIFE2_RELEASES, RIFE2_SNAPSHOTS);

        var junit = version(6, 1, 3);
        scope(test)
                .include(dependency("org.junit.jupiter", "junit-jupiter", junit))
                .include(dependency("org.junit.platform", "junit-platform-console-standalone", junit));

        // Add the main class to the manifest
        jarOperation().manifestAttribute(Attributes.Name.MAIN_CLASS, mainClass());
    }

    @Override
    public void clean() throws Exception {
        Files.deleteIfExists(Path.of("hello")); // delete binary if exists
        super.clean();
    }

    public static void main(String[] args) {
        new GraalNativeBuild().start(args);
    }

    @BuildCommand(value = "native-class", summary = "Builds a native executable")
    public void nativeClass() throws Exception {
        new ExecOperation()
                .fromProject(this)
                .timeout(120)
                .workDir(buildMainDirectory())
                // The native image options documentation can be found at:
                // https://www.graalvm.org/22.0/reference-manual/native-image/Options/
                .command("native-image",
                        mainClass(),
                        new File(workDirectory(), "hello").getAbsolutePath())
                .execute();
    }

    @BuildCommand(value = "native-jar", summary = "Builds a native executable from the JAR")
    public void nativeJar() throws Exception {
        new ExecOperation()
                .fromProject(this)
                .timeout(120)
                // The native image options documentation can be found at:
                // https://www.graalvm.org/22.0/reference-manual/native-image/Options/
                .command("native-image",
                        "-jar",
                        new File(buildDistDirectory(), jarFileName()).toString(), "hello")
                .execute();
    }
}
