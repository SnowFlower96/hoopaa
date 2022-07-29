FROM mysql:5.7.35
COPY HooPaa.sql /docker-entrypoint-initdb.d/init.sql
EXPOSE 3306
CMD ["mysqld"]