INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users(address, blocked, date_of_birth, deleted, email, first_name, last_name, password, phone_number, user_type, username) 
	VALUES ('777 Brockton Avenue, Abington MA 2351', false, '1996-05-05', false, 'devon.smith996@gmail.com', 'Devon', 'Smith', '$2a$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom', '202-555-0194', 'ADMIN', 'devonS');
	
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('1', 'Hatchback');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('2', 'Sedan');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('3', 'MPV');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('4', 'SUV');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('5', 'Crossover');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('6', 'Coupe');
INSERT INTO `db_carrental`.`vehicle_body_type` (`id`, `name`) VALUES ('7', 'Convertible');

INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('1', ' Mazda');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('2', 'Honda');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('3', 'Buick');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('4', 'Kia');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('5', 'Toyota');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('6', 'Fiat');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('7', 'Mitsubishi');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('8', 'Subaru');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('9', 'Hyundai');
INSERT INTO `db_carrental`.`vehicle_brand` (`id`, `name`) VALUES ('10', 'Volkswagen');

INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('1', 'Gasoline');
INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('2', 'Diesel');
INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('3', 'Liquified Petroleum');
INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('4', 'Compressed Natural Gas');
INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('5', 'Ethanol');
INSERT INTO `db_carrental`.`vehicle_fuel_type` (`id`, `name`) VALUES ('6', 'Bio-diesel');

INSERT INTO `db_carrental`.`vehicle_type` (`id`, `name`) VALUES ('1', 'Commercial vehicle');
INSERT INTO `db_carrental`.`vehicle_type` (`id`, `name`) VALUES ('2', 'Passenger vehicle');
