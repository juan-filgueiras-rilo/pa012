-- Indexes for primary keys have been explicitly created.

DROP TABLE User;
DROP TABLE Bid;
DROP TABLE Category;
DROP TABLE Product;

CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    password VARCHAR(60) NOT NULL, 
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL, 
    email VARCHAR(60) NOT NULL,
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName)
) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

CREATE TABLE Bid (
	id BIGINT NOT NULL AUTO_INCREMENT,
	quantity DECIMAL(11, 2) NOT NULL,
	productId BIGINT NOT NULL, 
	userId BIGINT NOT NULL,
	date DATETIME NOT NULL, 
	CONSTRAINT BidPK PRIMARY KEY (id),
	CONSTRAINT BidProductIdFK FOREING KEY (productId)
		REFERENCES Product (id),
	CONSTRAINT BidUserIdFK FOREING KEY (userId)
		REFERENCES User (id)
) ENGINE = InnoDB;

CREATE TABLE Category (
	categoryId BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	CONSTRAINT CategoryPK PRIMARY KEY (categoryId)
	CONSTRAINT CategoryNameUniqueKey UNIQUE (name)
) ENGINE = InnoDB;

CREATE INDEX CategoryIndexByName ON Category (name);

CREATE TABLE Product (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	descriptionProduct VARCHAR(2000) NOT NULL,
	duration BIGINT NOT NULL,
	creationTime DATETIME NOT NULL,
	currentPrice DECIMAL(11, 2) NOT NULL,
	initialPrice DECIMAL(11, 2) NOT NULL,
	shipmentInfo VARCHAR(2000) NOT NULL,
	categoryId BIGINT NOT NULL, 
	userId BIGINT NOT NULL,
	CONSTRAINT ProductPK PRIMARY KEY (id),
	CONSTRAINT ProductCategoryIdFK FOREING KEY (categoryId)
		REFERENCES Category (categoryId),
	CONSTRAINT ProductUserIdFK FOREING KEY (userId)
		REFERENCES User (id)
) ENGINE = InnoDB;
