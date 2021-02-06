package cronos.alice.web.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.ProjectDto;
import cronos.alice.model.entity.Project;
import cronos.alice.service.ProjectService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/projects")
    public ResponseEntity<List<ProjectDto>> findAll() {
        return new ResponseEntity<>(projectService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/projects/search")
    public ResponseEntity<List<ProjectDto>> findAllDtoBySearchText(@RequestParam String searchText) {
        return new ResponseEntity<>(projectService.findAllDtoBySearchText(searchText), HttpStatus.OK);
    }

    @GetMapping(value = "/project/{id}")
    public ResponseEntity<ProjectDto> findById(@PathVariable("id") Long id) {
        Project project = projectService.findById(id);
        if (project != null) {
            return new ResponseEntity<>(projectService.convertToDto(project), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/projects/ids")
    public ResponseEntity<List<Long>> findAllIds() {
        return new ResponseEntity<>(projectService.findAllIds(), HttpStatus.OK);
    }

    @GetMapping(value = "/projects/export")
    public void export(HttpServletResponse response) {
        projectService.export(response);
    }

    @PostMapping(value = "/project", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> save(@RequestBody ProjectDto dto) {
        Project project = new Project();
        projectService.updateProjectFields(dto, project);
        Project projectCreated = projectService.save(project);
        return new ResponseEntity<>(projectService.convertToDto(projectCreated), HttpStatus.OK);
    }

    @PutMapping(value = "/project/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDto> update(@RequestBody ProjectDto dto, @PathVariable Long id) {
        Project project = projectService.findById(id);
        projectService.updateProjectFields(dto, project);
        Project projectCreated = projectService.save(project);
        return new ResponseEntity<>(projectService.convertToDto(projectCreated), HttpStatus.OK);
    }

    @DeleteMapping(value = "/project/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        try {
            projectService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
}
