create table User (
    id      INT8        auto_increment  not null,
    name    VARCHAR(255),
    salt    VARCHAR(255),
    hash    VARCHAR(255)
);

create table Game (
    id          INT8        auto_increment  not null,
    timestamp   TIMESTAMP,
    handId      INT8
);

create table PokerHand (
    id          INT8        auto_increment  not null,
    hand        VARCHAR(255),
    playerName  VARCHAR(255),
    gameId      INT8
);