-- Schema HR
DROP TABLE IF EXISTS company;

-- company
CREATE TABLE company (
  companyId  INT NOT NULL AUTO_INCREMENT,
  companyAccount VARCHAR(255) NOT NULL UNIQUE,
  companyName VARCHAR(255) NOT NULL,
  companyUNP INT NOT NULL
);

-- payment
DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
  paymentId    INT NOT NULL AUTO_INCREMENT,
  payerName VARCHAR(255) NOT NULL,
  paymentDescription VARCHAR(255),
  paymentSum  INT NOT NULL,
  companyId   INT NOT NULL,
  paymentDate TIMESTAMP(0) NOT NULL,
  PRIMARY KEY (paymentId),
  FOREIGN KEY (companyId)
  REFERENCES company (companyId)
);
