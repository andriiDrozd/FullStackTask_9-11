DELETE FROM Vessel;

ALTER sequence vessel_id_seq restart with 1;

INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V AAL Paris', 2010);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Merkur', 2009);
INSERT INTO Vessel(vessel_name, year_of_build) VALUES ('M/V Warnow Moon', 2011);

