import java.io.File;
import java.util.ArrayList;

public class FilesLoader {
	String storagePath;
	ArrayList<File> filesList = new ArrayList<File>();

	public FilesLoader(String StoragePath) {
		storagePath = StoragePath;
	}

	public static String getRealStorage(String thisDir) {
		File file = new File(thisDir);
		File[] files = file.listFiles();
		String realStorage = null;
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].getName().equals("src")) {
				return files[i].getParent();
			}
		}
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				realStorage = getRealStorage(files[i].getAbsolutePath());
				if (realStorage != null) {
					return realStorage;
				}
			}
		}
		return null;
	}

	private boolean isType(String fileName, String fileType) {
		String[] sections = fileName.split("\\.");
		if (sections[sections.length - 1].equals(fileType)) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<File> getFiles(File file, String Type) {
		filesList = new ArrayList<File>();
		loadFiles(file, Type);
		return filesList;
	}

	public void loadFiles(File file, String Type) {
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				loadFiles(files[i], Type);
			} else {
				if (isType(files[i].getName(), Type)) {
					filesList.add(files[i]);
				}
			}
		}
		return;
	}
}

