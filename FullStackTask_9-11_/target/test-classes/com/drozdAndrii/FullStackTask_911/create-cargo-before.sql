DELETE FROM Cargo;

ALTER sequence cargo_cargo_id_seq restart with 1;

INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (1,'BMW x5',123456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (1,'Tesla Model 3',143446);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (2,'Maybach S 560',222456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (2,'Porsche Cayman S 987',223426);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (3,'Mercedes-Benz GLC',324456);
INSERT INTO Cargo(vessel_id, cargo_name, send_number) VALUES (3,'Lamborghini Urus',329426);
