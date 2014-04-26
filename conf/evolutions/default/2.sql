# --- !Ups

alter table if exists o_user add column status int default 0;

alter table if exists o_user add constraint ck_o_user_status check (status in (0,1,2));

# --- !Downs

alter table if exists o_user drop column status;

alter table if exists o_user drop constraint ck_o_user_status;