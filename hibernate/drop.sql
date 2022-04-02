
    alter table bank_contact 
       drop 
       foreign key FKaxnpdk31fp707ousdyu5ilra6;

    alter table bank_contact2 
       drop 
       foreign key FKhkjbbk5fen965yadvjse4giau;

    alter table budget_transaction 
       drop 
       foreign key FKemsmc6k4c5ueumaoxac69nto3;

    alter table budget_transaction 
       drop 
       foreign key FKd29kggwou1k21wratamdv7kky;

    alter table credential 
       drop 
       foreign key FKlde6756f90cgp6ssa39p6jh3b;

    alter table joined_defect 
       drop 
       foreign key FK6hsmtrqrh9k4x4kv6moeftx64;

    alter table joined_feature 
       drop 
       foreign key FKcoog0e5sum5mp1gjggwjwlwpa;

    alter table market 
       drop 
       foreign key FKn6x9e782vgm79kagdj0226hwa;

    alter table transaction 
       drop 
       foreign key FK6g20fcr3bhr6bihgy24rq1r1b;

    alter table user_account 
       drop 
       foreign key FK41ueby8j4pbnlid8w7t59k0bb;

    alter table user_account 
       drop 
       foreign key FK1pxs5fj6ujqfs13gmfg3o5cwo;

    alter table user_address 
       drop 
       foreign key FKn5cqa3r4dp2k5l945c3w8iqn7;

    drop table if exists account;

    drop table if exists bank;

    drop table if exists bank_contact;

    drop table if exists bank_contact2;

    drop table if exists budget;

    drop table if exists budget_transaction;

    drop table if exists credential;

    drop table if exists currency;

    drop table if exists finances_user;

    drop table if exists hibernate_sequence;

    drop table if exists ifinaces_keys;

    drop table if exists joined_defect;

    drop table if exists joined_feature;

    drop table if exists joined_job;

    drop table if exists mapped_super_class_bug;

    drop table if exists mapped_super_class_enhancement;

    drop table if exists market;

    drop table if exists single_table_task;

    drop table if exists table_per_class_activity;

    drop table if exists table_per_class_item;

    drop table if exists table_per_class_test;

    drop table if exists transaction;

    drop table if exists user_account;

    drop table if exists user_address;
