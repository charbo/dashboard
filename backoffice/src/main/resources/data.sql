MERGE INTO SOURCE (ID, NAME, DRIVER, USER, PASSWORD, URL)
  KEY(ID)
VALUES (1, 'sakila', 'com.mysql.jdbc.Driver', 'root', 'root', 'jdbc:mysql://localhost:3306/sakila');