-- Indexes for primary keys have been explicitly created.

DROP TABLE Bid;
DROP TABLE Product;
DROP TABLE Category;
DROP TABLE User;

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

CREATE TABLE Category (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	CONSTRAINT CategoryPK PRIMARY KEY (id),
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
	winningBidId BIGINT,
	CONSTRAINT ProductPK PRIMARY KEY (id),
	CONSTRAINT ProductCategoryIdFK FOREIGN KEY(categoryId)
		REFERENCES Category (id),
	CONSTRAINT ProductUserIdFK FOREIGN KEY(userId)
		REFERENCES User (id)
) ENGINE = InnoDB;

CREATE TABLE Bid (
	id BIGINT NOT NULL AUTO_INCREMENT,
	quantity DECIMAL(11, 2) NOT NULL,
	productId BIGINT NOT NULL, 
	userId BIGINT NOT NULL,
	date DATETIME NOT NULL, 
	CONSTRAINT BidPK PRIMARY KEY (id),
	CONSTRAINT BidProductIdFK FOREIGN KEY(productId)
		REFERENCES Product (id),
	CONSTRAINT BidUserIdFK FOREIGN KEY(userId)
		REFERENCES User (id)
) ENGINE = InnoDB;




