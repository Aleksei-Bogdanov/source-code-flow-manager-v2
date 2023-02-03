REST api with webflux r2dbc postgresql

Creation db with docker:

1. docker pull postgres
2. docker pull dpage/pgadmin4
3. docker run --name postgres-app -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres
4. docker run -p 5050:80  -e "PGADMIN_DEFAULT_EMAIL=pgadmin4@pgadmin.org" -e "PGADMIN_DEFAULT_PASSWORD=admin"  -d dpage/pgadmin4
5. переходим на http://localhost:5050 в браузере, откроектся pgadmin 
6. жмём Add New Server и далее настраиваем соединение:
вкладка General - вводим имя postgres-app
вкладка Сonnection - Host: localhost, Port: 5432, Database: postgres, Username: postgres, Password: password
жмём Save
7. создастся сервер с именем postgres-app , в него можно добавить базу с названием application_db
