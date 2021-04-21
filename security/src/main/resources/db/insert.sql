SET FOREIGN_KEY_CHECKS  = 0;

truncate table Users;
truncate table ConfirmationToken;


INSERT into Users(`id`, `name`, `email`, `password`)
VALUES(1, 'Kings Immanuel', 'kings@yahoo.com', 'fisherking167'),
      (2, 'Sunday Ebe', 'sunday@gmail.com', 'hardworkikpaudo1577');

INSERT into Users(`id`, `confirmationToken`, `createdAt`)


SET FOREIGN_KEY_CHECKS  = 1;
