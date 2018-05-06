import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.tools.*;

public class Compiler {
	private static JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();

    public static String GetCode(String fileName){
        String code = "UTF-8";
        try {
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));
            int p = (bin.read() << 8) + bin.read();
            bin.close();
            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
            }
        } catch (IOException ignored) {
        }
        return code;
    }


    public static boolean CompilerJavaFile(ArrayList<File> inputFiles, String classFileOutputPath, String identify) {
		ArrayList<String> options = new ArrayList<String>();
		options.add("-encoding");
		String code = "utf8";
        for (File f: inputFiles){
            if(f.getName().equals("Main.java")){
                code = GetCode(f.getAbsolutePath());
            }
        }
		options.add(code);
		options.add("-d");
		options.add(classFileOutputPath);

		// DiagnosticListener用于获取Diagnostic信息，Diagnostic信息包括：错误，警告和说明性信息
        DiagnosticCollector diagnosticCollector = new DiagnosticCollector();

		StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(null, null, Charset.forName(code));
		Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(inputFiles);
        JavaCompiler.CompilationTask task = javaCompiler.getTask(null, fileManager, diagnosticCollector, options, null, compilationUnits);
	    Boolean result = task.call();
	    if(!result){
            List list = diagnosticCollector.getDiagnostics();
            StringBuilder errorBuilder = new StringBuilder();
            for (Object object : list) {
                Diagnostic d = (Diagnostic) object;
                errorBuilder.append(d.getMessage(Locale.ENGLISH)).append(System.lineSeparator());
            }
            FileHandler.ErrorReport(identify, errorBuilder.toString());
            FileHandler.OutToFile(identify+" Compile Fail.");
        }
        else{
            FileHandler.OutToFile(identify+" Compile Success.");
        }
	    return result;
	}
}


