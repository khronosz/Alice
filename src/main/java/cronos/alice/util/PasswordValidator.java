package cronos.alice.util;

import java.util.regex.Pattern;

public class PasswordValidator {

	private static final Pattern regex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[_.@$!%*?&-/])[A-Za-z\\d_.@$!%*?&-/]{8,}$");

	public static boolean isValid(String password) {
		return regex.matcher(password).matches();
	}
}
