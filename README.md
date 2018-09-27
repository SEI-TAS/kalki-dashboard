# kalki-dashboard

## IMPORTANT
This project is temporarily not setup to work with docker. This will be fixed soon. The run, install `sbt` and then do
the following:
```
$ ./run_postgres_container.sh
$ cd dashboard
$ sbt run
```
The project can then be viewed at `http://localhost:9000`.

## Prerequisites
The Kalki Dashboard must be run using Docker for Mac.
It will not run on other Docker distributions.

## Configuration
Docker for Mac can be installed using `brew cask install docker`.
Also, see https://docs.docker.com/docker-for-mac/install/.

sbt for Mac can be installed using `$ brew install sbt@1`. Also, see https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html

## Usage
Set up a docker network by running `$ docker network create -d bridge kalki_nw`. This initializes a container network to 
allow this application to connect to the database. *This only needs to be done once*

Start the database by running `$ ./run_postgres_container.sh` from the project root.
This will create a docker volume named `kalki-database` so the data persists when the database is terminated.

#### Local Development
To develop locally:
- Set `db_host="localhost"` in the config file at `dashboard/conf/application.conf`
- Run the application:
```
cd dashboard/
sbt run
```
- The application can then be viewed at `http://localhost:9000`
### Docker Deployment
To deploy and run a docker container:
- Set `db_host="kalki-db"` in the config file at `dashboard/conf/application.conf`
- Build the docker image:
```
$ cd dashboard/
$ sbt docker:publishLocal
```
- Run a container:
```
$ docker run --p [PORT]:9000 --net=kalki_nw --rm -d --name=[NAME] dashboard:1.0-SNAPSHOT
```

The dashboard can then be viewed at `http://localhost:[PORT]`.

For example,
```
$ cd dashboard/
$ sbt docker:publishLocal
$ docker run -p 80:9000 --net=kalki_nw --rm -d --name=kalki-dashboard dashboard:1.0-SNAPSHOT
```
will make the dashboard available for viewing at `http://localhost`.

The server can be stopped by pressing `<C-D>`. \
The container can be stopped by running `$ docker container stop [name]` \
To stop the database, run `$ docker container stop kalki-postgres`.
All data is preserved and will be accessable next time `$ ./run-postgres-container.sh` is run.
