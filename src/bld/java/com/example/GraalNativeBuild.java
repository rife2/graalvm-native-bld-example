package com.example;

import rife.bld.BuildCommand;
import rife.bld.Project;
import rife.bld.extension.ExecOperation;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.Attributes;

import static rife.bld.dependencies.Repository.MAVEN_CENTRAL;
import static rife.bld.dependencies.Repository.RIFE2_RELEASES;
import static rife.bld.dependencies.Scope.test;

/**
 * Gradle Native Example Build.
 *
 * <pre>{@code ./bld compile jar native-class }</pre>
 */
public class GraalNativeBuild extends Project {
    // Command(s) used to invoke the `native-image` tool
    final static List<String> native_image;

    static {
        if (ExecOperation.isWindows()) {
            native_image = List.of("cmd", "/c", "native-image"); // Windows
        } else {
            native_image = List.of("native-image"); // MacOS & Linux
        }
    }

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
                .include(dependency("org.junit.jupiter", "junit-jupiter", version(6, 0, 1)))
                .include(dependency("org.junit.platform", "junit-platform-console-standalone", version(6, 0, 1)));

        // Add the main class to the manifest
        jarOperation().manifestAttribute(Attributes.Name.MAIN_CLASS, mainClass());
    }

    public static void main(String[] args) {
        new GraalNativeBuild().start(args);
    }

    @Override
    public void clean() throws Exception {
        Files.deleteIfExists(Path.of("hello")); // delete binary if exists
        super.clean();
    }

    @BuildCommand(value = "native-class", summary = "Builds a native executable")
    public void nativeClass() throws Exception {
        new ExecOperation()
                .fromProject(this)
                .timeout(120)
                .workDir(buildMainDirectory())
                // The native image options documentation can be found at:
                // https://www.graalvm.org/22.0/reference-manual/native-image/Options/
                .command(native_image)
                .command(mainClass(),
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
                .command(native_image)
                .command("-jar",
                        new File(buildDistDirectory(), jarFileName()).toString(),
                        "hello")
                .execute();
    }
}