create table tb_user (
                         id serial not null,
                         email varchar(255) not null,
                         name varchar(255) not null,
                         password varchar(255) not null,
                         phone varchar(255),
                         type smallint check (type between 0 and 1),
                         primary key (id)
)