DROP TABLE IF EXISTS `user_roles`;
DROP TABLE IF EXISTS `demands`;
DROP TABLE IF EXISTS `projects`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `roles`;

CREATE TABLE `users` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
	`job` VARCHAR(50) NOT NULL COLLATE 'utf8_hungarian_ci',
	`department` VARCHAR(50) NOT NULL COLLATE 'utf8_hungarian_ci',
	`email` VARCHAR(50) NOT NULL COLLATE 'utf8_hungarian_ci',
	`passwd` VARCHAR(120) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
	`city` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
	`user_level` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
	`note` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
	`direct_manager` INT(11) NULL DEFAULT NULL,
	`last_validation` DATE NULL DEFAULT NULL,
	`last_login` DATE NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UNIQUE_USER_EMAIL` (`email`),
	UNIQUE INDEX `UNIQUE_USER_USERNAME` (`username`)
)
COLLATE='utf8_hungarian_ci'
ENGINE=InnoDB
;

CREATE TABLE `roles` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `UNIQUE_ROLE_NAME` (`name`)
)
COLLATE='utf8_hungarian_ci'
ENGINE=InnoDB
;

CREATE TABLE `user_roles` (
	`user_id` INT(11) NOT NULL,
	`role_id` INT(11) NOT NULL,
	PRIMARY KEY (`user_id`, `role_id`),
	UNIQUE INDEX `UNIQUE_USER_ROLE` (`user_id`, `role_id`),
	INDEX `FK_ROLE_ID_KEY` (`role_id`),
	CONSTRAINT `FK_ROLE_ID_KEY` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
	CONSTRAINT `FK_USER_ID_KEY` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)
COLLATE='utf8_hungarian_ci'
ENGINE=InnoDB
;

CREATE TABLE `projects` (
     `id` INT(11) NOT NULL AUTO_INCREMENT,
     `project_name` VARCHAR(50) NOT NULL COLLATE 'utf8_hungarian_ci',
     `sap` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
     `phase` VARCHAR(10) NOT NULL COLLATE 'utf8_hungarian_ci',
     `status` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
     `manager` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `backup_manager` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `owner` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `customer` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `bu` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `bu_hu` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `contact_person` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `fte` SMALLINT(6) NULL DEFAULT NULL,
     `start` DATE NULL DEFAULT NULL,
     `end` DATE NULL DEFAULT NULL,
     `order_type` VARCHAR(30) NOT NULL COLLATE 'utf8_hungarian_ci',
     `project_type` VARCHAR(40) NOT NULL COLLATE 'utf8_hungarian_ci',
     `description` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `comment` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `long_desc` VARCHAR(5000) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     `technology` VARCHAR(500) NULL DEFAULT NULL COLLATE 'utf8_hungarian_ci',
     PRIMARY KEY (`id`),
     UNIQUE INDEX `UNIQUE_PROJECTS_SAP` (`sap`)
)
    COLLATE='utf8_hungarian_ci'
    ENGINE=InnoDB
;

CREATE TABLE `demands` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
    `mib` VARCHAR(20) NOT NULL COLLATE 'utf8_hungarian_ci',
    `user_id` INT(11) NULL,
    `project_id` INT(11) NOT NULL,
    `utilization` INT(11) NULL,
    `project_start` DATE NULL DEFAULT NULL,
    `project_end` DATE NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `UNIQUE_DEMANDS_NAME` (`name`),
    UNIQUE INDEX `UNIQUE_DEMANDS_MIB` (`mib`),
    CONSTRAINT `UNIQUE_DEMANDS_USER_PROJECT` UNIQUE (`user_id`, `project_id`),
    CONSTRAINT `FK_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE SET NULL,
    CONSTRAINT `FK_PROJECT_ID` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`) ON DELETE CASCADE
)
    COLLATE='utf8_hungarian_ci'
    ENGINE=InnoDB
;