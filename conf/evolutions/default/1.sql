# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table album (
  id                        bigint not null,
  patient_id                bigint,
  created                   timestamp,
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint pk_album primary key (id))
;

create table comment (
  id                        bigint not null,
  message                   varchar(255),
  album_id                  bigint,
  commented_by_id           bigint,
  created_by_id             bigint,
  modified_by_id            bigint,
  created                   timestamp,
  constraint pk_comment primary key (id))
;

create table image (
  id                        bigint not null,
  image_url                 varchar(255),
  album_id                  bigint,
  created                   timestamp,
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint pk_image primary key (id))
;

create table login (
  id                        bigint not null,
  image_url                 varchar(255),
  created                   timestamp,
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint pk_login primary key (id))
;

create table patient (
  id                        bigint not null,
  full_name                 varchar(255),
  email                     varchar(255),
  age                       integer,
  gender                    integer,
  created                   timestamp,
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint ck_patient_gender check (gender in (0,1,2)),
  constraint pk_patient primary key (id))
;

create table review (
  id                        bigint not null,
  reviewed                  boolean,
  album_id                  bigint,
  assigned_to_id            bigint,
  created                   timestamp,
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint pk_review primary key (id))
;

create table s3file (
  id                        varchar(40) not null,
  bucket                    varchar(255),
  name                      varchar(255),
  constraint pk_s3file primary key (id))
;

create table o_user (
  id                        bigint not null,
  user_name                 varchar(255),
  password                  varchar(255),
  display_name              varchar(255),
  user_type                 integer,
  location                  varchar(255),
  phone                     varchar(255),
  created_by_id             bigint,
  modified_by_id            bigint,
  constraint ck_o_user_user_type check (user_type in (0,1,2)),
  constraint pk_o_user primary key (id))
;

create sequence album_seq;

create sequence comment_seq;

create sequence image_seq;

create sequence login_seq;

create sequence patient_seq;

create sequence review_seq;

create sequence o_user_seq;

alter table album add constraint fk_album_patient_1 foreign key (patient_id) references patient (id);
create index ix_album_patient_1 on album (patient_id);
alter table album add constraint fk_album_createdBy_2 foreign key (created_by_id) references o_user (id);
create index ix_album_createdBy_2 on album (created_by_id);
alter table album add constraint fk_album_modifiedBy_3 foreign key (modified_by_id) references o_user (id);
create index ix_album_modifiedBy_3 on album (modified_by_id);
alter table comment add constraint fk_comment_album_4 foreign key (album_id) references album (id);
create index ix_comment_album_4 on comment (album_id);
alter table comment add constraint fk_comment_commentedBy_5 foreign key (commented_by_id) references o_user (id);
create index ix_comment_commentedBy_5 on comment (commented_by_id);
alter table comment add constraint fk_comment_createdBy_6 foreign key (created_by_id) references o_user (id);
create index ix_comment_createdBy_6 on comment (created_by_id);
alter table comment add constraint fk_comment_modifiedBy_7 foreign key (modified_by_id) references o_user (id);
create index ix_comment_modifiedBy_7 on comment (modified_by_id);
alter table image add constraint fk_image_album_8 foreign key (album_id) references album (id);
create index ix_image_album_8 on image (album_id);
alter table image add constraint fk_image_createdBy_9 foreign key (created_by_id) references o_user (id);
create index ix_image_createdBy_9 on image (created_by_id);
alter table image add constraint fk_image_modifiedBy_10 foreign key (modified_by_id) references o_user (id);
create index ix_image_modifiedBy_10 on image (modified_by_id);
alter table login add constraint fk_login_createdBy_11 foreign key (created_by_id) references o_user (id);
create index ix_login_createdBy_11 on login (created_by_id);
alter table login add constraint fk_login_modifiedBy_12 foreign key (modified_by_id) references o_user (id);
create index ix_login_modifiedBy_12 on login (modified_by_id);
alter table patient add constraint fk_patient_createdBy_13 foreign key (created_by_id) references o_user (id);
create index ix_patient_createdBy_13 on patient (created_by_id);
alter table patient add constraint fk_patient_modifiedBy_14 foreign key (modified_by_id) references o_user (id);
create index ix_patient_modifiedBy_14 on patient (modified_by_id);
alter table review add constraint fk_review_album_15 foreign key (album_id) references album (id);
create index ix_review_album_15 on review (album_id);
alter table review add constraint fk_review_assignedTo_16 foreign key (assigned_to_id) references o_user (id);
create index ix_review_assignedTo_16 on review (assigned_to_id);
alter table review add constraint fk_review_createdBy_17 foreign key (created_by_id) references o_user (id);
create index ix_review_createdBy_17 on review (created_by_id);
alter table review add constraint fk_review_modifiedBy_18 foreign key (modified_by_id) references o_user (id);
create index ix_review_modifiedBy_18 on review (modified_by_id);
alter table o_user add constraint fk_o_user_createdBy_19 foreign key (created_by_id) references o_user (id);
create index ix_o_user_createdBy_19 on o_user (created_by_id);
alter table o_user add constraint fk_o_user_modifiedBy_20 foreign key (modified_by_id) references o_user (id);
create index ix_o_user_modifiedBy_20 on o_user (modified_by_id);



# --- !Downs

drop table if exists album cascade;

drop table if exists comment cascade;

drop table if exists image cascade;

drop table if exists login cascade;

drop table if exists patient cascade;

drop table if exists review cascade;

drop table if exists s3file cascade;

drop table if exists o_user cascade;

drop sequence if exists album_seq;

drop sequence if exists comment_seq;

drop sequence if exists image_seq;

drop sequence if exists login_seq;

drop sequence if exists patient_seq;

drop sequence if exists review_seq;

drop sequence if exists o_user_seq;

