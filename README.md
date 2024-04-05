# [GraalVM](https://www.graalvm.org/) Native Executable Example for [b<span style="color:orange">l</span>d](https://rife2.com/bld)

Be sure to have the GraalVM `native-image` utility in your path, or set its location in the
[build file](https://github.com/rife2/graalvm-native-bld-example/tree/main/src/bld/java/com/example).

## Compile the application

```console
./bld compile
```
## Create the Java archive

```console
./bld jar
```

## Create the native application from the Java archive

```console
./bld native-jar
```

## Create the native application from the main class

```console
./bld compile native-class
```

## Launch the application

```console
./hello
```

## Native Image Options

The options documentation can be found at:

* https://www.graalvm.org/22.0/reference-manual/native-image/Options/
