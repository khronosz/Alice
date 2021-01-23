package cronos.alice.model.dto;

import java.time.LocalDate;

import cronos.alice.model.entity.Project;

public class ProjectDto {

	private Long id;
	private String projectName;
	private String sap;
	private String phase;
	private String status;
	private String manager;
	private String backupManager;
	private String owner;
	private String customer;
	private String bu;
	private String buHu;
	private String contactPerson;
	private Integer fte;
	private LocalDate start;
	private LocalDate end;
	private String orderType;
	private String projectType;
	private String description;
	private String comment;

	public ProjectDto() {
	}

	public ProjectDto(Project project) {
		this.id = project.getId();
		this.projectName = project.getProjectName();
		this.sap = project.getSap();
		this.phase = project.getPhase();
		this.status = project.getStatus();
		this.manager = project.getManager();
		this.backupManager = project.getBackupManager();
		this.owner = project.getOwner();
		this.customer = project.getCustomer();
		this.bu = project.getBu();
		this.buHu = project.getBuHu();
		this.contactPerson = project.getContactPerson();
		this.fte = project.getFte();
		this.start = project.getStart();
		this.end = project.getEnd();
		this.orderType = project.getOrderType();
		this.projectType = project.getProjectType();
		this.description = project.getDescription();
		this.comment = project.getComment();
	}

	public Long getId() {
		return id;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getSap() {
		return sap;
	}

	public String getPhase() {
		return phase;
	}

	public String getStatus() {
		return status;
	}

	public String getManager() {
		return manager;
	}

	public String getBackupManager() {
		return backupManager;
	}

	public String getOwner() {
		return owner;
	}

	public String getCustomer() {
		return customer;
	}

	public String getBu() {
		return bu;
	}

	public String getBuHu() {
		return buHu;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public Integer getFte() {
		return fte;
	}

	public LocalDate getStart() {
		return start;
	}

	public LocalDate getEnd() {
		return end;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getProjectType() {
		return projectType;
	}

	public String getDescription() {
		return description;
	}

	public String getComment() {
		return comment;
	}
}
