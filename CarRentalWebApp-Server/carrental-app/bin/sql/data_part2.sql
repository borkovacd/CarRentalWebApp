insert into vehicle (type_id, brand_id, body_type_id, model, description, name, number_of_doors, number_of_seats, engine_volume, gear_number, rental_price, drive_type, transmission_type, deleted, engine_power, image_id) values (1, 5, 5, 'Corolla', 'From standard Toyota Safety Sense™ P (TSS-P) to its available 7-in. touch-screen display and sporty interior, this Toyota car keeps your commute fun.', '2020 Corolla Hybrid', 3, 5, 3.26, 5, 15638.37, 1, 1, false, 4778, 1);
insert into vehicle (type_id, brand_id, body_type_id, model, description, name, number_of_doors, number_of_seats, engine_volume, gear_number, rental_price, drive_type, transmission_type, deleted, engine_power, image_id) values (1, 5, 5, 'Yaris', 'Keep your commute fresh with sport-contoured front seats, sophisticated chrome-tipped exhaust and standard Bluetooth® connectivity in this Toyota car. Plus, check out the 7-in. touch-screen display with available navigation.', '2020 Yaris Hatchback', 4, 4, 4.59, 2, 16649.54, 2, 1, false, 2191, 2);
insert into vehicle (id, deleted, description, drive_type, engine_power, engine_volume, gear_number, model, name, number_of_doors, number_of_seats, rental_price, transmission_type, body_type_id, brand_id, image_id, type_id) values (3, 0, 'Mazda CX-5 II 2.5 Turbo SKYACTIV-G (228 Hp) Automatic 2019', 1, 228, 2488, 6, 'CX-5',  'Mazda CX-5', 5, 5, 45.00, 1, 4, 1, 3, 2);

insert into vehicle_fuel_types (vehicle_id, fuel_type_id) values (1, 1);
insert into vehicle_fuel_types (vehicle_id, fuel_type_id) values (1, 6);
insert into vehicle_fuel_types (vehicle_id, fuel_type_id) values (2, 1);
insert into vehicle_fuel_types (vehicle_id, fuel_type_id) values (3, 1);

