insert into category (name) values ('Application & Services');
insert into category (name) values ('Benefits & Paper Work');
insert into category (name) values ('Hardware & Software');
insert into category (name) values ('People Management');
insert into category (name) values ('Security & Access');
insert into category (name) values ('Workplaces & Facilities');

insert into user (firstName, lastName, role, email, password) values
('User1Name', 'User1Surname', 'EMPLOYEE', 'user1', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');
insert into user (firstName, lastName, role, email, password) values
('User2Name', 'User2Surname', 'EMPLOYEE', 'user2', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');
insert into user (firstName, lastName, role, email, password) values
('Manager1Name', 'Manager1Surname', 'MANAGER', 'manager1', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');
insert into user (firstName, lastName, role, email, password) values
('Manager2Name', 'Manager2Surname', 'MANAGER', 'manager2', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');
insert into user (firstName, lastName, role, email, password) values
('Engineer1Name', 'Engineer1Surname', 'ENGINEER', 'engineer1', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');
insert into user (firstName, lastName, role, email, password) values
('Engineer2Name', 'Engineer2Surname', 'ENGINEER', 'engineer2', '$2a$10$4EdpRpnwx.Xl9UsKkjctVeRAUfqK3Jky4.bT95uGrtA1nYJPYdk.a');

insert into ticket (name, description, createdOn, desiredResolutionDate, state, urgency, owner, category) values
('ticket1', 'description1', '2012-10-10', '2013-10-15', 1, 1, 1, 1);
insert into ticket (name, description, createdOn, desiredResolutionDate, state, urgency, owner, category) values
('ticket2', 'description2', '2014-12-11', '2015-12-25', 0, 1, 1, 2);
insert into ticket (name, description, createdOn, desiredResolutionDate, state, urgency, owner, category) values
('ticket3', 'description3', '2015-12-31', '2017-01-05', 1, 2, 2, 2);
insert into ticket (name, description, createdOn, desiredResolutionDate, state, urgency, owner, category) values
('ticket4', 'description4', '2019-06-30', '2020-10-15', 2, 3, 3, 4);
insert into ticket (name, description, createdOn, desiredResolutionDate, state, urgency, owner, category) values
('ticket5', 'description5', '2021-06-30', '2020-10-15', 1, 3, 4, 6);

insert into comment(date, text, ticket, user) values
('2020-08-20', 'comment1', 1, 1);
insert into comment(date, text, ticket, user) values
('2021-08-22', 'comment2', 1, 1);
insert into comment(date, text, ticket, user) values
('2020-08-15', 'comment3', 1, 1);
insert into comment(date, text, ticket, user) values
('2021-08-22', 'comment4', 1, 1);
insert into comment(date, text, ticket, user) values
('2020-08-22', 'comment5', 1, 1);
insert into comment(date, text, ticket, user) values
('2021-08-22', 'comment6', 1, 1);
insert into comment(date, text, ticket, user) values
('2020-08-22', 'comment7', 1, 1);

insert into history(date,action,description,ticket,user) values
('2021-08-20', 'action1', 'descr1', 1, 1);
insert into history(date,action,description,ticket,user) values
('2020-08-22', 'action2', 'descr2', 1, 2);
insert into history(date,action,description,ticket,user) values
('2019-08-20', 'action3', 'descr3', 1, 1);
insert into history(date,action,description,ticket,user) values
('2018-08-20', 'action4', 'descr4', 1, 3);
insert into history(date,action,description,ticket,user) values
('2017-08-20', 'action5', 'descr5', 1, 4);
insert into history(date,action,description,ticket,user) values
('2016-08-20', 'action6', 'descr6', 1, 5);
insert into history(date,action,description,ticket,user) values
('2015-08-20', 'action7', 'descr7', 1, 6);

