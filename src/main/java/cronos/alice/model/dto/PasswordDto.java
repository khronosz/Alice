package cronos.alice.model.dto;

import cronos.alice.model.entity.User;

public class PasswordDto {

	private Long userId;
	private String currentPassword;
	private String newPassword;
	private String confirmPassword;

	public PasswordDto() {
	}

	public PasswordDto(User user) {
		this.userId = user.getId();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(final String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
