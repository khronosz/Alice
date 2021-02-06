package cronos.alice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cronos.alice.exception.UniqueRoleNameException;
import cronos.alice.model.entity.Role;
import cronos.alice.repository.RoleRepository;

@Service
public class RoleService {

	private static final Logger log = LogManager.getLogger(RoleService.class);

	private final RoleRepository roleRepository;

	private final List<Role> roles;

	@Autowired
	public RoleService(final RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
		this.roles = roleRepository.findAll();
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Transactional
	public Role save(Role role) {
		roles.forEach(r -> {
			if (r.getName().name().equalsIgnoreCase(role.getName().name())) throw new UniqueRoleNameException("Role already exists!");
		});
		log.info("Save role: " + role.getName());
		return roleRepository.save(role);
	}

}
