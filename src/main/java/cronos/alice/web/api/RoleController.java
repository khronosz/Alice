package cronos.alice.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cronos.alice.model.entity.Role;
import cronos.alice.service.RoleService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping
public class RoleController {

	private final RoleService roleService;

	@Autowired
	public RoleController(final RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping(value = "/roles")
	@ResponseBody
	public ResponseEntity<List<Role>> findAllRoles() {
		return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
	}

	@PostMapping(value = "/role", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Role> save(@RequestBody Role role) {
		return new ResponseEntity<>(roleService.save(role), HttpStatus.OK);
	}

}
