create table credential (credential_id bigint not null, password varchar(255), username varchar(255), user_id bigint, primary key (credential_id)) engine=InnoDB;
create table finances_user (user_id bigint not null, birth_date datetime, email_address varchar(255), first_name varchar(255), last_name varchar(255), primary key (user_id)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
alter table credential add constraint FKlde6756f90cgp6ssa39p6jh3b foreign key (user_id) references finances_user (user_id);
