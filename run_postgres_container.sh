#!/usr/local/bin/bash
docker run -p 5432:5432 --rm --name kalki-postgres -v kalki-database:/var/lib/postgresql/data \
-e POSTGRES_USER=kalki-user -e POSTGRES_PASSWORD=kalki-pass -e POSTGRES_DB=kalki-db -d postgres
