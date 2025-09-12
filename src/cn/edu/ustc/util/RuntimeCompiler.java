package cn.edu.ustc.util;


import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 提供了一个方法，用于在运行时编译并加载Java源代码。
 * 使用Java编译器的API来编译源代码，并将编译后的类加载到内存中。
 */
public class RuntimeCompiler {
    private static final Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public static Class<?> compileAndLoad(String className, String sourceCode) throws Exception {
        // 始终清除缓存，确保每次都重新编译
        CLASS_CACHE.remove(className);
        // 添加一个系统参数，确保每次加载新类
        System.setProperty("java.system.class.loader", "custom.loader.NoCache" + System.currentTimeMillis());

//        // 输出调试信息
//        System.out.println("开始编译类: " + className + ", 代码长度: " + sourceCode.length());

        // 获取Java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException("没有找到Java编译器。请确保安装了JDK而不是JRE。");
        }

        // 设置编译选项
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);

        // 拿到当前应用的classpath
        String classpath = System.getProperty("java.class.path");
        Iterable<String> options = Arrays.asList("-classpath", classpath);

        try (MemoryFileManager fileManager = new MemoryFileManager(standardFileManager)) {
            // 创建编译任务
            JavaFileObject sourceFile = new MemoryJavaFileObject(className, sourceCode);
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null, fileManager, diagnostics, options, null, Arrays.asList(sourceFile)
            );

            boolean success = task.call();
            if (!success) {
                StringBuilder sb = new StringBuilder();
                for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                    sb.append(diagnostic).append("\n");
                }
                throw new RuntimeException("编译失败: " + sb);
            }

            System.out.println("编译成功，正在加载类...");

            // 加载编译后的类
            Class<?> loadedClass = fileManager.getClassLoader(null).loadClass(className);
            // 实例化一次确认可用
            Object instance = loadedClass.getDeclaredConstructor().newInstance();
            System.out.println("成功加载类: " + loadedClass.getName());

            // 重新放入缓存
            CLASS_CACHE.put(className, loadedClass);
            return loadedClass;
        }
    }

    // 确保源代码包含必要的导入语句
    private static String ensureImports(String sourceCode) {
        StringBuilder imports = new StringBuilder();

        // 检查是否已经包含必要的导入
        if (!sourceCode.contains("import cn.edu.ustc.service.Strategy")) {
            imports.append("import cn.edu.ustc.service.Strategy;\n");
        }
        if (!sourceCode.contains("import cn.edu.ustc.model.MaxSubArrayResult")) {
            imports.append("import cn.edu.ustc.model.MaxSubArrayResult;\n");
        }

        // 如果没有package声明，添加默认包
        if (!sourceCode.trim().startsWith("package ")) {
            return "package cn.edu.ustc.algorithm;\n\n" + imports.toString() + sourceCode;
        } else {
            // 在package声明后添加imports
            int packageEnd = sourceCode.indexOf(";") + 1;
            return sourceCode.substring(0, packageEnd) + "\n" + imports.toString()
                    + sourceCode.substring(packageEnd);
        }
    }

    // 内存文件管理器，用于在内存中编译Java源代码并加载类
    private static class MemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final MemoryClassLoader classLoader;
        private final Map<String, ByteArrayOutputStream> classBytes = new ConcurrentHashMap<>();

        protected MemoryFileManager(StandardJavaFileManager fileManager) {
            super(fileManager);
            this.classLoader = new MemoryClassLoader();
        }

        // 重写获取Java文件的方法，用于内存中编译
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className,
                                                   JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            if (kind == JavaFileObject.Kind.CLASS) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                classBytes.put(className, outputStream);
                return new MemoryOutputJavaFileObject(className, outputStream);
            } else {
                return super.getJavaFileForOutput(location, className, kind, sibling);
            }
        }

        // 重写获取类加载器的方法，使用自定义的内存类加载器
        @Override
        public ClassLoader getClassLoader(Location location) {
            return classLoader;
        }

        // 内存类加载器
        private class MemoryClassLoader extends ClassLoader {
            // 重写findClass方法，从内存中加载类
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                ByteArrayOutputStream bytes = classBytes.get(name);
                if (bytes == null) {
                    throw new ClassNotFoundException(name);
                }
                byte[] bytecode = bytes.toByteArray();
                return defineClass(name, bytecode, 0, bytecode.length);
            }
        }
    }

    // 内存Java源文件对象，用于在内存中存储Java源代码，继承自SimpleJavaFileObject
    private static class MemoryJavaFileObject extends SimpleJavaFileObject {
        private final String sourceCode;

        public MemoryJavaFileObject(String className, String sourceCode) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.sourceCode = sourceCode;
        }

        // 重写openReader方法，返回源代码的字符流
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }

    // 内存Java类文件对象
    private static class MemoryOutputJavaFileObject extends SimpleJavaFileObject {
        private final ByteArrayOutputStream outputStream;

        // 构造函数，创建内存输出流
        protected MemoryOutputJavaFileObject(String className, ByteArrayOutputStream outputStream) {
            super(URI.create("bytes:///" + className.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
            this.outputStream = outputStream;
        }

        // 重写openOutputStream方法，返回内存输出流
        @Override
        public OutputStream openOutputStream() {
            return outputStream;
        }
    }
}