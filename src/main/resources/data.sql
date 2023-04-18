-- inserting dummy data into table
INSERT INTO tickets (name, age, gender, pet, status, price) values 
('lime', 19, 'male', 'no',  'Student', 15.00)
,('amy', 35, 'female', 'yes', 'Not a Student', 20.50)
,('lary', 16, 'male', 'yes','Student', 15.00)
,('Anna', 40, 'female', 'no', 'Student', 30.00)
,('mary', 17, 'female', 'no','Not a Student',  20.50)
,('lime', 19, 'male', 'no', 'Student', 20.50)
,('amy', 35, 'female', 'yes','Not a Student',  15.00)
,('lary', 16, 'male', 'yes','Student',  30.00)
,('Anna', 40, 'female', 'no','Student',  20.50)
,('mary', 17, 'female', 'no', 'Not a Student', 15.00);

-- assigning dummy passwords to the data 
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('vender', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
 
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('lime', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('amy', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('lary', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Anna', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('mary', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

-- adding roles
insert into sec_role (roleName)
values ('ROLE_VENDER');
 
insert into sec_role (roleName)
values ('ROLE_GUEST');

-- assigning roles to dummy users
insert into user_role (userId, roleId)
values (1, 1);
 
insert into user_role (userId, roleId)
values (2, 2);

insert into user_role (userId, roleId)
values (3, 2);

insert into user_role (userId, roleId)
values (4, 2);

insert into user_role (userId, roleId)
values (5, 2);

insert into user_role (userId, roleId)
values (6, 2);

