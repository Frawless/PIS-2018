# PIS-2018

REST API for school project.

# Dependencies
* Maven
* Postgres
* Java

# Integration
Project is designed for IntelliJ IDE. For proper work create PostgreeSQL db, duplicate '''application.local.properties''' into sae folder and rename it into '''application.properties''', fillup databse information and run the server.

# Initialization
directory `bakery` contains backup for easy initialization. `users.csv` contains list of users, their passwords and roles, `bakery.pgsql` contains dump. For initialization use command `psql -U USERNAME DBNAME < dbexport.pgsql `.  `USERNAME` is name of the user with granted authorities, `DBNAME` is already created **empty** database. Sometimes it is needed to set `-h 127.0.0.1`.