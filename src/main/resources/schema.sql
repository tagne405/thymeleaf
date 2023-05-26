-- CREATE table if not EXISTS users(
--     username VARCHAR_ignorecase(50) not null PRIMARY KEY,
--     password varchar_ignorecase(500) not nul enabled boolean not null,

--     );

-- create table authorities if not EXISTS(
--     username varchar_ignorecase (50) not null,
--     authority varchar_ignorecase(50) not null,
--     contraint fk authorities_users foreign key(username),
--     references users{username;}

-- )

-- create unique index ix_auth_username on authorities(username,authority)
create table if not EXISTS users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table if not EXISTS authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);
