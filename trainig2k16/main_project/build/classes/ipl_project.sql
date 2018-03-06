create table players(playerid int primary key identity(1,1),playername varchar(30) not null,playerstatus varchar(30))
create table sponsers(sponserid int primary key identity(101,1),sponsername varchar(30) not null)
create table teams(teamid int primary key identity(1001,1),teamname varchar(90),sponserid int references sponsers)
create table teamplayers(teamid int references teams,playerid int references players)
create table venue(venueid int primary key identity(1,1),venuename varchar(30),state varchar(30),loc varchar(90))
create table stadium(stadiumid int primary key identity(1,1),stadiumname varchar(90))
----
--create table match(matchid int,teamid int references teams,venueid int references venue,matchstatus varchar(90),stadiumid int references stadium)
--create table matchstatus(matchid int references match,teamid references teams,points int)--
--create table stadiumblock(blockid int primary key
---create table blockseats(venueblockid          ,seatno      ,blockseatno  )
--create table bookedseats(matchid int references match,ticketno    ,blockseatno    references blockseats)




alter proc sp_sponser(@sponsername varchar(30),@sponserid int out)
as
begin
set @sponserid = (select ISNULL(max(sponserid),0) from sponsers)+1
insert sponsers values(@sponsername)
end
select * from sponsers
