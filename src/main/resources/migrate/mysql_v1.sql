CREATE TABLE `postagramdb`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

CREATE TABLE `postagramdb`.`posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `body` VARCHAR(45) NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `USER_ID_FK_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `USER_ID_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `postagramdb`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
