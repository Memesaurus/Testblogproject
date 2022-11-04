create TABLE `postagramdb`.`users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

create TABLE `postagramdb`.`posts` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `body` VARCHAR(45) NULL,
  `userid` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `USER_ID_FK_idx` (`userid` ASC) VISIBLE,
  CONSTRAINT `USER_ID_FK`
    FOREIGN KEY (`userid`)
    REFERENCES `postagramdb`.`users` (`id`)
    ON delete NO ACTION
    ON update NO ACTION);

