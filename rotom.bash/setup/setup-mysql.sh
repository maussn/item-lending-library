#!/usr/bin/env bash

MYSQL_ROOT_USERNAME="root"
MYSQL_ROOT_PASSWORD="root"


mysql -u "$MYSQL_ROOT_USERNAME" "-p$MYSQL_ROOT_PASSWORD" < setup-mysql.sql