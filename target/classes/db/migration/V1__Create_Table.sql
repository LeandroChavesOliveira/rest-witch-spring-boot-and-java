-- Copiando estrutura para tabela rest_witch_spring_boot_java.person
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(80) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `address` varchar(80) NOT NULL,
  `gender` varchar(80) NOT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `books` (
  `id` INT(10) AUTO_INCREMENT PRIMARY KEY,
  `author` longtext,
  `launch_date` datetime(6) NOT NULL,
  `price` decimal(65,2) NOT NULL,
  `title` longtext
) ENGINE=InnoDB DEFAULT CHARSET=latin1;







