package cronos.alice.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.Tuple;
import cronos.alice.exception.ExportFailedException;
import cronos.alice.exception.InvalidPasswordFormatException;
import cronos.alice.exception.PasswordDoesNotMatchException;
import cronos.alice.model.UserDetailsImpl;
import cronos.alice.model.dto.NonUtilizedUserDto;
import cronos.alice.model.dto.NonValidatedUserDto;
import cronos.alice.model.dto.PasswordDto;
import cronos.alice.model.dto.TeamDto;
import cronos.alice.model.dto.UserDto;
import cronos.alice.model.dto.UtilPlanDto;
import cronos.alice.model.entity.User;
import cronos.alice.repository.UserRepository;
import cronos.alice.util.PasswordValidator;
import cronos.alice.util.TeamExporter;
import cronos.alice.util.UtilPlanExporter;

@Service
public class UserService {

	private static final Logger log = LogManager.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User getLoggedInUser() {
		String username = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		log.info("Get logged in user: " + username);
		return userRepository.findByUsername(username).orElse(null);
	}

	public User findById(Long id) {
		log.info("Find user by ID: " + id);
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with the ID: " + id));
	}

	public List<String> findAllUsernames() {
		log.info("Find all usernames");
		return userRepository.findAllUsernames();
	}

	public List<String> findAllDirectManagers() {
		log.info("Find all direct managers");
		return userRepository.findAllDirectManagerNames();
	}

	public List<UtilPlanDto> findAllUtilPlanDto() {
		List<UtilPlanDto> dtoList = userRepository.findAllUtilPlanDto().stream().map(UtilPlanDto::new).collect(Collectors.toList());
		List<Tuple> projectNames = userRepository.initProjectNamesForDto();
		dtoList.forEach(dto -> dto.setProjectNames(concat(projectNames, dto.getId())));
		return dtoList;
	}

	public List<TeamDto> findAllTeamDto(Long directManagerId) {
		List<TeamDto> dtoList = userRepository.findAllTeamDto(directManagerId).stream().map(TeamDto::new).collect(Collectors.toList());
		List<Tuple> projectNames = userRepository.initProjectNamesForDto();
		dtoList.forEach(dto -> dto.setProjectNames(concat(projectNames, dto.getId())));
		return dtoList;
	}

	private String concat(final List<Tuple> projectNames, final Long id) {
		return projectNames.stream().filter(projectName -> Objects.equals(id, projectName.get(0, Long.class)))
				.map(projectName -> projectName.get(1, String.class))
				.collect(Collectors.joining(", "));
	}

	public void exportTeam(HttpServletResponse response) {
		try {
			ByteArrayInputStream stream = new TeamExporter(findAllTeamDto(getLoggedInUser().getId())).export(response);
			IOUtils.copy(stream, response.getOutputStream());
			log.info("Export all users to excel file by direct manager: " + getLoggedInUser().getUsername());
		} catch (Exception e) {
			log.error("Team export failed " + e);
			throw new ExportFailedException("Team export failed!");
		}
	}

	public void exportUtilPlan(HttpServletResponse response) {
		try {
			ByteArrayInputStream stream = new UtilPlanExporter(findAllUtilPlanDto()).export(response);
			IOUtils.copy(stream, response.getOutputStream());
			log.info("Export all underutilized users to excel file");
		} catch (Exception e) {
			log.error("Utilization plan export failed " + e);
			throw new ExportFailedException("Utilization plan export failed!");
		}
	}

	public List<NonUtilizedUserDto> getNonUtilizedUsers(Long userId) {
		List<NonUtilizedUserDto> dtoList = userRepository.findAllNonUtilizedUserDto(userId).stream().map(NonUtilizedUserDto::new).collect(Collectors.toList());
		List<Tuple> projectNames = userRepository.initProjectNamesForDto();
		dtoList.forEach(dto -> dto.setProjectNames(concat(projectNames, dto.getId())));
		return dtoList;
	}

	public List<NonValidatedUserDto> getNonValidatedUsers(Long userId) {
		List<NonValidatedUserDto> dtoList = userRepository.findAllNonValidatedUserDto(userId).stream().map(NonValidatedUserDto::new).collect(Collectors.toList());
		List<Tuple> projectNames = userRepository.initProjectNamesForDto();
		dtoList.forEach(dto -> dto.setProjectNames(concat(projectNames, dto.getId())));
		return dtoList;
	}

	public TeamDto findTeamDtoById(Long id) {
		log.info("Find user by ID: " + id);
		TeamDto dto = new TeamDto(userRepository.findTeamDtoById(id));
		List<Tuple> projectNames = userRepository.initProjectNamesForDto();
		dto.setProjectNames(concat(projectNames, dto.getId()));
		return dto;
	}

	@Transactional
	public User save(User user) {
		log.info("Save user: " + user.getUsername());
		return userRepository.save(user);
	}

	public User convertToEntity(UserDto dto) {
		log.debug("Convert dto to entity: " + dto.getUsername());
		User user = dto.getId() != null ? findById(dto.getId()) : new User();
		updateUserFields(dto, user);
		return user;
	}

	private void updateUserFields(final UserDto dto, final User user) {
		User directManager = userRepository.findByUsername(dto.getDirectManagerName()).orElseThrow(() -> new RuntimeException("User not found with the name: " + dto.getDirectManagerName()));
		user.setUsername(dto.getUsername());
		user.setJob(dto.getJob());
		user.setDepartment(dto.getDepartment());
		user.setEmail(dto.getEmail());
		user.setCity(dto.getCity());
		user.setLevel(dto.getLevel());
		user.setDirectManagerId(directManager.getId());
		user.setNotes(dto.getNotes());
		user.setLastValidation(LocalDate.now());
		user.setLastLogin(dto.getLastLogin());
	}

	public UserDto convertToDto(User user) {
		log.debug("Convert entity to dto: " + user.getUsername());
		UserDto dto = new UserDto(user);
		dto.setDirectManagerName(findById(user.getDirectManagerId()).getUsername());
		return dto;
	}

	@Transactional
	public TeamDto updateLastValidation(TeamDto dto) {
		User user = findById(dto.getId());
		user.setLastValidation(LocalDate.now());
		userRepository.save(user);
		return findTeamDtoById(user.getId());
	}

	@Transactional
	public User changePassword(PasswordDto passwordDto) {
		User user = findById(passwordDto.getUserId());
		if (user.getPassword() != null) {
			if (!passwordEncoder.matches(passwordDto.getCurrentPassword(), user.getPassword())) {
				throw new PasswordDoesNotMatchException("Password does not match!");
			}
			if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
				throw new PasswordDoesNotMatchException("Password does not match!");
			}
			if (!PasswordValidator.isValid(passwordDto.getNewPassword())) {
				throw new InvalidPasswordFormatException("Password does not meet complexity requirements!");
			}
			user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
		}
		log.info("Change password for user: " + user.getUsername());
		return userRepository.save(user);
	}

	@Transactional
	public void deleteById(Long userId) {
		log.warn("Delete user by ID: " + userId);
		userRepository.deleteById(userId);
	}
}
