CREATE TABLE person (
    id bigserial not null,
    name text not null,
    age int,
    constraint pk_person primary key (id)
);
