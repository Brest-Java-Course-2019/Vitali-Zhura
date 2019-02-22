-- Schema HR
DROP TABLE IF EXISTS com;
DROP TABLE IF EXISTS pay;

-- company
CREATE TABLE com (
  com_id   SERIAL NOT NULL,
  com_account VARCHAR(45) NOT NULL,
  com_name VARCHAR(45) NOT NULL,
  PRIMARY KEY (com_id)
);

-- payment
CREATE TABLE pay (
  pay_id    SERIAL NOT NULL,
  pay_desc VARCHAR(45) NULL,
  pay_sum  INTEGER NOT NULL,
  com_id   INTEGER NOT NULL,
  PRIMARY KEY (pay_id),
  CONSTRAINT pay_to_com_fk
    FOREIGN KEY (com_id)
    REFERENCES com (com_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);
