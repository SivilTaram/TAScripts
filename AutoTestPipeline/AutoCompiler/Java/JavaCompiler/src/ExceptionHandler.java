public class ExceptionHandler {
	static String[] errorMessages = { "No src folder is found.", "No java file is found.", "No main class is found.",
			"UTF-8 encoding format is expected.", "Compiling failed.", "Running out of time" };

	static String GetErrorMessage(int errorCode) {
		String errorMessage = null;
		errorMessage = errorMessages[errorCode];
		return errorMessage;
	}
}
