package cronos.alice.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.dto.ProjectDescriptionDto;
import cronos.alice.model.entity.Project;
import cronos.alice.service.HomeService;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class HomeController {

  private final HomeService homeService;

  @Autowired
  public HomeController(final HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping(value = "/")
  public ResponseEntity<List<ProjectDescriptionDto>> findAllProjectDescriptionDto() {
    return new ResponseEntity<>(homeService.findAllProjectDescriptionDto(), HttpStatus.OK);
  }

  @PutMapping(value = "/")
  public ResponseEntity<ProjectDescriptionDto> update(@RequestBody ProjectDescriptionDto dto) {
    Project project = homeService.convertToEntity(dto);
    Project projectCreated = homeService.save(project);
    return new ResponseEntity<>(homeService.convertToDto(projectCreated), HttpStatus.OK);
  }
}
