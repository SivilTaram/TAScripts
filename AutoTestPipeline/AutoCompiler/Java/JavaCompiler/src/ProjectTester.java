import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ProjectTester {
	private StorageLoader storageLoader;
	private ArrayList<String> storagesList;
	private ArrayList<String> storageNameList;
	private ArrayList<String> storagePathList;
	private ArrayList<Class<?>> mainClassList;
    private Scanner input;

	ProjectTester() {
		input = new Scanner(System.in);
		storagesList = new ArrayList<>();
		storageNameList = new ArrayList<>();
		storagePathList = new ArrayList<>();
		mainClassList = new ArrayList<>();
	}
	void InitTest() {
		FileHandler.CreateFileAndStream();
		GetStorages();
		GetMainClasses();
	}
	/*
	 * Load all the storages and get the list of correct storage.
	 */
	private void GetStorages() {
		String folderName = null;
		File folder;
		do {
			System.out.println("Please Input A Valid Root Project Dir(Contains a list of dirs, every dir is a project directory):");
			folderName = input.nextLine();
			folder = new File(folderName);
		} while (!folder.isDirectory());
		storageLoader = new StorageLoader(folderName);
		storagesList = storageLoader.GetStorages();
		return;
	}

	private Class<?> GetMainClass(String storagePath, String identify) {
		String srcPath = null, classPath = null;
		File src = null, classes = null;
		srcPath = storagePath + File.separator + "src";
		classPath = storagePath + File.separator + "bin";
		src = new File(srcPath);
		classes = new File(classPath);
		if (!classes.exists()) {
			classes.mkdir();
		}
		FilesLoader filesLoader = new FilesLoader(storagePath);
		Compiler.CompilerJavaFile(filesLoader.getFiles(src, "java"), classPath, identify);
		UserClassLoader userClassLoader = new UserClassLoader();
		Class<?> mainClass = userClassLoader.loadStorageClass(filesLoader.getFiles(classes, "class"));
		return mainClass;
	}

	/*
	 * Compile all the projects and get load the main class.
	 */
	void GetMainClasses() {
		for (int i = 0; i < storagesList.size(); i++) {
			String[] tempStr = storagesList.get(i).split("\\|");
			String storageName = tempStr[0];
			String storagePath = tempStr[1];
			try {
                Class<?> mainClass = GetMainClass(storagePath, storageName);
                if (mainClass != null) {
                    mainClassList.add(mainClass);
                    storageNameList.add(storageName);
                    storagePathList.add(storagePath);
                }
            }catch (Error e){
			    FileHandler.ErrorReport(storageName, e.getMessage());
            }
		}
		return;
	}
}
