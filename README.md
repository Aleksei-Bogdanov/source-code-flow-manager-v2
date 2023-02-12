# source-code-flow-manager-v2
RESTapi, webflux, r2dbc, postgresql

Creation db in docker, without using docker-compose.yml:

1. in terminal: docker pull postgres
2. in terminal: docker pull dpage/pgadmin4
3. in terminal: docker run --name postgres-app -e POSTGRES_PASSWORD=password -d -p 5432:5432 postgres
4. in terminal: docker run -p 5050:80 -e "PGADMIN_DEFAULT_EMAIL=pgadmin4@pgadmin.org" -e "PGADMIN_DEFAULT_PASSWORD=admin" -d dpage/pgadmin4
5. go to - 'http://localhost:5050' - to see pgadmin
6. click 'Add New Server' than set up a connection: 
on the tab General insert name 'postgres-app' 
on the tab Сonnection - Host: localhost, Port: 5432, Database: postgres, Username: postgres, Password: password - click 'Save'
7. create server named 'postgres-app' , create db named application_db