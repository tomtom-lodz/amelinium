--SQLITE
PRAGMA foreign_keys = ON;
CREATE TABLE project(id integer primary key asc,dtCreated datetime,dtLastModified datetime, name text);
CREATE TABLE backlog(id integer primary key asc, revision integer, dt datetime, content text, revertedFromRevision integer);
CREATE TABLE chart(id integer primary key asc, revision integer, dt datetime, content text, revertedFromRevision integer);
CREATE TABLE snapshot(id integer primary key asc, revision integer, dt datetime, sync text, projectId integer not null, backlogId integer not null, chartId integer not null,
foreign key(projectId) references project(id) on delete cascade on update cascade,
foreign key(backlogId) references backlog(id) on delete cascade on update cascade,
foreign key(chartId) references chart(id) on delete cascade on update cascade);

insert into project(name) values('2013-02-13 11:08:46+01:00','2013-02-13 11:08:46+01:00','Example');
insert into backlog(dt,revision,content) values('2013-02-01 15:23:27',1,'this is backlog');
insert into chart(dt,revision,content) values('2013-02-01 15:23:27',1,'this is chart');
insert into snapshot(dt,revision,projectId,backlogId,chartId) values ('2013-02-01 15:23:27',1,1,1,1);

--POSTGRESQL
drop table project cascade;
drop table backlog cascade;
drop table chart cascade;
drop table snapshot cascade;

CREATE TABLE project(id serial primary key, dtCreated timestamp with time zone, dtLastModified timestamp with time zone, name text);
CREATE TABLE backlog(id serial primary key, revision integer, dt timestamp with time zone, content text, revertedFromRevision integer);
CREATE TABLE chart(id serial primary key, revision integer, dt timestamp with time zone, content text, revertedFromRevision integer);
CREATE TABLE snapshot(id serial primary key, revision integer, dt timestamp with time zone, sync text, projectId integer not null, backlogId integer not null, chartId integer not null,
foreign key(projectId) references project(id) on delete cascade on update cascade,
foreign key(backlogId) references backlog(id) on delete cascade on update cascade,
foreign key(chartId) references chart(id) on delete cascade on update cascade);

insert into project(dtCreated,dtLastModified,name) values('2013-02-13 11:08:46+01:00','2013-02-13 11:08:46+01:00','Example');
insert into backlog(dt,revision,content) values('2013-02-13 11:08:46+01:00',1,'this is backlog');
insert into chart(dt,revision,content) values('2013-02-13 11:08:46+01:00',1,'this is chart');
insert into snapshot(dt,revision,sync,projectId,backlogId,chartId) values ('2013-02-13 11:08:46+01:00',1,'synchronized',1,1,1);