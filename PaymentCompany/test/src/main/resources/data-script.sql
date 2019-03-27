INSERT INTO company (companyId, companyAccount, companyName, companyUNP) VALUES (1, 'BY27BLBB34325630287478004008', 'Prestizh', 200342345);
INSERT INTO company (companyId, companyAccount, companyName, companyUNP) VALUES (2, 'BY27BLBB37899630217778006009', 'BrestVodokanal', 340098760);
INSERT INTO company (companyId, companyAccount, companyName, companyUNP) VALUES (3, 'BY27BLBB38800630217478006888', 'IdealMebel', 230095600);
INSERT INTO company (companyId, companyAccount, companyName, companyUNP) VALUES (4, 'BY27BLBB37899638899498006080', 'GefestBrestProm', 248960087);


INSERT INTO payment (paymentId, payerName, paymentDescription, paymentSum, companyId, paymentDate) VALUES (1, 'Ivanov', 'Za vodu', 230,1, '2019-03-10 12:12:30');
INSERT INTO payment (paymentId, payerName, paymentDescription, paymentSum, companyId, paymentDate) VALUES (2, 'Ivan', 'Stroitovary', 400, 2, '2019-03-10 12:30:30');
INSERT INTO payment (paymentId, payerName, paymentDescription, paymentSum, companyId, paymentDate) VALUES (3, 'Petov', 'Za svyaz', 1000, 3, '2019-03-10 12:40:50');
INSERT INTO payment (paymentId, payerName, paymentDescription, paymentSum, companyId, paymentDate) VALUES (4, 'Raduga', 'Za perevozku', 390, 4, '2019-03-11 12:55:30');
INSERT INTO payment (paymentId, payerName, paymentDescription, paymentSum, companyId, paymentDate) VALUES (5, 'Rert', 'Za gaz', 700, 4, '2019-03-11 12:57:15');