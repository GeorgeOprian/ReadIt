CREATE TABLE book
(
    isbn      bigint       NOT NULL ,
    name     varchar(255) NOT NULL DEFAULT '',
    PRIMARY KEY (isbn)
);

