import java.io.File;
import java.util.ArrayList;

public class StorageLoader {
	String folderName = null;
	ArrayList<String> storagesList = new ArrayList<String>();

	StorageLoader(String FolderName) {
		folderName = FolderName;
	}

	void StoragesCheck() {
		File folder = new File(folderName);
		File[] storages = folder.listFiles();
		for (int i = 0; i < storages.length; i++) {
			String storage = FilesLoader.getRealStorage(storages[i].getAbsolutePath());
			if (storage != null) {
				storage = storages[i].getName() + "|" + storage;
				storagesList.add(storage);
			} else {
				FileHandler.ErrorReport(storages[i].getName(), ExceptionHandler.GetErrorMessage(0));
			}
		}
	}

	ArrayList<String> GetStorages() {
		if (storagesList.size() == 0) {
			StoragesCheck();
		}
		return storagesList;
	}

}
