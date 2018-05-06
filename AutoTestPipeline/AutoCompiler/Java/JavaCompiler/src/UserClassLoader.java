import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UserClassLoader extends ClassLoader {
	Class<?> thisClass = null;

	public UserClassLoader() {
		super();
	}

	public Class<?> loadStorageClass(ArrayList<File> files) {
		File file;
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			try {
				if (file.getName().equals("Main.class")) {
					thisClass = loadLocalClass(file);
				} else {
					loadLocalClass(file);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return thisClass;
	}

	public Class<?> loadLocalClass(File file) throws ClassNotFoundException {
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] classByte = new byte[(int) file.length()];
			inputStream.read(classByte);
			return defineClass(null, classByte, 0, classByte.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
		System.out.println("class");
		return null;
	}
}
