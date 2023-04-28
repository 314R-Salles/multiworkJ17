CREATE TABLE Room
(
    id           INT PRIMARY KEY NOT NULL,
    name         VARCHAR(30),
    pwd          VARCHAR(5),
    creationDate datetime,
    lastUpdated  datetime
);

CREATE TABLE Player
(
    id           VARCHAR(36) PRIMARY KEY NOT NULL,
    name         VARCHAR(30),
    creationDate datetime,
    roomId       INT
);

CREATE TABLE History
(
    id     INT PRIMARY KEY NOT NULL,
    roomId INT,
    tour   text
);
