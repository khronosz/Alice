package cronos.alice.model.dto;

import java.util.Objects;

import cronos.alice.model.entity.Project;

public class ProjectDescriptionDto {

	private Long id;
	private String projectName;
	private String customer;
	private String description;
	private String technology;

	public ProjectDescriptionDto() {
	}

	public ProjectDescriptionDto(Project project) {
		this.id = project.getId();
		this.projectName = project.getProjectName();
		this.customer = project.getCustomer();
		this.description = project.getLongDescription();
		this.technology = project.getTechnology();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final ProjectDescriptionDto that = (ProjectDescriptionDto) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(projectName, that.projectName) &&
				Objects.equals(customer, that.customer) &&
				Objects.equals(description, that.description) &&
				Objects.equals(technology, that.technology);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, projectName, customer, description, technology);
	}

	public Long getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getCustomer() {
		return customer;
	}

	public String getDescription() {
		return description;
	}

	public String getTechnology() {
		return technology;
	}
}


