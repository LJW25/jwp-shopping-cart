CREATE TABLE IF NOT EXISTS product
(
    id    BIGINT      NOT NULL AUTO_INCREMENT,
    name  VARCHAR(64) NOT NULL,
    price INT         NOT NULL,
    image TEXT,
    PRIMARY KEY (id)
);
