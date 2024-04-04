# [GraalVM](https://www.graalvm.org/) Native Executable Example for [b<span style="color:orange">l</span>d](https://rife2.com/bld)

Be sure to have the GraalVM `native-image` utility in your path, or set its location in the build file.

## Compile the application

```console
./bld compile
```
## Create a Java archive

```console
./bld jar
```

## Create the native application

```console
./bld native-exec
```

## Combine the commands

```console
./bld compile jar native-exec
```

## Launch the application

```console
./hello
```

## Native Image Options

The options documentation can be found at:

* https://www.graalvm.org/22.0/reference-manual/native-image/Options/

