.class public CLikeProgram
.super java/lang/Object

.field private static i F
.field private static j F

.method public <init>()V

	aload_0
	invokenonvirtual	java/lang/Object/<init>()V
	return

.limit locals 1
.limit stack 1
.end method

.method public static main([Ljava/lang/String;)V

    ldc 4.0
      putstatic CLikeProgram/i F
    ldc 2.0
      putstatic CLikeProgram/j F
    getstatic CLikeProgram/i F
    getstatic CLikeProgram/j F
    fadd
      putstatic CLikeProgram/i F
       getstatic    java/lang/System/out Ljava/io/PrintStream;
       getstatic     CLikeProgram/i F
      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;
       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
    getstatic CLikeProgram/i F
    getstatic CLikeProgram/j F
      fmul
      putstatic CLikeProgram/i F
       getstatic    java/lang/System/out Ljava/io/PrintStream;
       getstatic     CLikeProgram/i F
      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;
       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
    getstatic CLikeProgram/i F
    getstatic CLikeProgram/j F
    fsub
      putstatic CLikeProgram/i F
       getstatic    java/lang/System/out Ljava/io/PrintStream;
       getstatic     CLikeProgram/i F
      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;
       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V
    getstatic CLikeProgram/i F
    getstatic CLikeProgram/j F
    fdiv
      putstatic CLikeProgram/i F
       getstatic    java/lang/System/out Ljava/io/PrintStream;
       getstatic     CLikeProgram/i F
      invokestatic  java/lang/String.valueOf(F)Ljava/lang/String;
       invokevirtual java/io/PrintStream.println(Ljava/lang/String;)V

    return

.limit stack 16
.end method
