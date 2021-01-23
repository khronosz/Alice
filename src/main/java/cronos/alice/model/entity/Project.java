package cronos.alice.model.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank!")
    @Size(max = 50, message = "Name cannot be longer than 50 characters!")
    @Column(name = "project_name")
    private String projectName;

    @NotBlank(message = "SAP cannot be blank!")
    @Size(max = 20, message = "SAP cannot be longer than 20 characters!")
    @Column
    private String sap;

    @NotBlank(message = "Phase cannot be blank!")
    @Size(max = 10, message = "Phase cannot be longer than 10 characters!")
    @Column
    private String phase;

    @NotBlank(message = "Status cannot be blank!")
    @Size(max = 20, message = "Status cannot be longer than 20 characters!")
    @Column
    private String status;

    @Size(max = 50, message = "Manager cannot be longer than 50 characters!")
    @Column
    private String manager;

    @Size(max = 50, message = "Backup manager cannot be longer than 50 characters!")
    @Column(name = "backup_manager")
    private String backupManager;

    @Size(max = 50, message = "Owner cannot be longer than 50 characters!")
    @Column
    private String owner;

    @Size(max = 50, message = "Customer cannot be longer than 50 characters!")
    @Column
    private String customer;

    @Size(max = 50, message = "BU cannot be longer than 50 characters!")
    @Column
    private String bu;

    @Size(max = 50, message = "BU HU cannot be longer than 50 characters!")
    @Column(name = "bu_hu")
    private String buHu;

    @Size(max = 50, message = "Contact person cannot be longer than 50 characters!")
    @Column(name = "contact_person")
    private String contactPerson;

    @Column
    private int fte;

    @Column
    private LocalDate start;

    @Column
    private LocalDate end;

    @NotBlank(message = "Order type cannot be blank!")
    @Size(max = 30, message = "Order type cannot be longer than 30 characters!")
    @Column(name = "order_type")
    private String orderType;

    @NotBlank(message = "Project type cannot be blank!")
    @Size(max = 40, message = "Project type cannot be longer than 40 characters!")
    @Column(name = "project_type")
    private String projectType;

    @Size(max = 255, message = "Description cannot be longer than 255 characters!")
    @Column
    private String description;

    @Size(max = 255, message = "Comment cannot be longer than 255 characters!")
    @Column
    private String comment;

    @Size(max = 5000, message = "Description cannot be longer than 5000 characters!")
    @Column(name= "long_desc")
    private String longDescription;

    @Size(max = 500, message = "Technology cannot be longer than 500 characters!")
    @Column
    private String technology;

    public Project() {
    }

    public Project(@NotBlank @Size(max = 50) String projectName,
                   @NotBlank @Size(max = 20) String sap,
                   @NotBlank @Size(max = 10) String phase,
                   @NotBlank @Size(max = 20) String status,
                   @NotBlank @Size(max = 30) String orderType,
                   @NotBlank @Size(max = 40) String projectType) {
        this.projectName = projectName;
        this.sap = sap;
        this.phase = phase;
        this.status = status;
        this.orderType = orderType;
        this.projectType = projectType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getBackupManager() {
        return backupManager;
    }

    public void setBackupManager(String backupManager) {
        this.backupManager = backupManager;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public String getBuHu() {
        return buHu;
    }

    public void setBuHu(String buHu) {
        this.buHu = buHu;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getFte() {
        return fte;
    }

    public void setFte(int fte) {
        this.fte = fte;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(final String longDescription) {
        this.longDescription = longDescription;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(final String technology) {
        this.technology = technology;
    }
}
