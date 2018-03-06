create table filenamedata1(id int primary key identity(1,1),name varchar(90),pathname varchar(90))
select * from filenamedata1
create table cmd(commands varchar(90))
delete from filenamedata1 where id=11
select * from cmd
delete from cmd where commands='hahhahha'