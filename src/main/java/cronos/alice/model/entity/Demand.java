package cronos.alice.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "demands")
public class Demand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank!")
    @Size(max = 20, message = "Name cannot be longer than 20 characters!")
    @Column
    private String name;

    @NotBlank(message = "MIB cannot be blank!")
    @Size(max = 20, message = "MIB cannot be longer than 20 characters!")
    @Column
    private String mib;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "project_id")
    private Long projectId;

    @Max(value = 100, message = "Utilization cannot be more than 100!")
    @Column
    private int utilization;

    @Column(name = "project_start")
    private LocalDate projectStart;

    @Column(name = "project_end")
    private LocalDate projectEnd;

    public Demand() {
    }

    public Demand(@NotBlank @Size(max = 20) String name,
                  @NotBlank @Size(max = 20) String mib,
                  Long projectId) {
        this.name = name;
        this.mib = mib;
        this.projectId = projectId;
    }

    public Demand(@NotBlank @Size(max = 20) String name,
                  @NotBlank @Size(max = 20) String mib,
                  Long projectId,
                  Long userId,
                  int utilization,
                  LocalDate projectStart,
                  LocalDate projectEnd) {
        this(name, mib, projectId);
        this.userId = userId;
        this.utilization = utilization;
        this.projectStart = projectStart;
        this.projectEnd = projectEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMib() {
        return mib;
    }

    public void setMib(String mib) {
        this.mib = mib;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public int getUtilization() {
        return utilization;
    }

    public void setUtilization(int utilization) {
        this.utilization = utilization;
    }

    public LocalDate getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(LocalDate projectStart) {
        this.projectStart = projectStart;
    }

    public LocalDate getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(LocalDate projectEnd) {
        this.projectEnd = projectEnd;
    }
}
