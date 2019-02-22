-- Schema HR
DROP TABLE IF EXISTS company;

-- company
CREATE TABLE company (
  companyId  INT NOT NULL AUTO_INCREMENT,
  companyAccount VARCHAR(255) NOT NULL,
  companyName VARCHAR(255) NOT NULL,
  PRIMARY KEY (companyId)
);

-- payment
DROP TABLE IF EXISTS payment;

CREATE TABLE payment (
  paymentId    INT NOT NULL AUTO_INCREMENT,
  paymentDescription VARCHAR(255) NOT NULL,
  paymentSum  INT NOT NULL,
  companyId   INT NOT NULL,
  PRIMARY KEY (paymentId),
  FOREIGN KEY (companyId)
  REFERENCES company (companyId)
);
