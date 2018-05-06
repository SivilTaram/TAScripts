import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileHandler {
	static File checkFile, errorFile;
	static FileOutputStream errorStream, checkStream;
	static Charset charset = Charset.forName("UTF-8");


	static void OutToFile(String info) {
		try {
			checkStream.write(info.getBytes(charset));
			checkStream.write(System.lineSeparator().getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	static void CreateFileAndStream() {
		checkFile = new File("CheckFile.txt");
		if (!checkFile.exists()) {
			try {
				checkFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		errorFile = new File("ErrorReport.txt");
		if (!errorFile.exists()) {
			try {
				errorFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			errorStream = new FileOutputStream(errorFile.getAbsolutePath(), true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			checkStream = new FileOutputStream(checkFile.getAbsolutePath(), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return;
	}

	static void ErrorReport(String storage, String errorMessage) {
		String errorInfo = storage + "\n" + errorMessage + "\n---------------\n";
		try {
			errorStream.write(errorInfo.getBytes(charset));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
