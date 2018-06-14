# kalki-dashboard

## Prerequisites
The Kalki Dashboard must be run using Docker for Mac.
It will not run on other Docker distributions.

## Configuration
Docker for Mac can be installed using `brew cask install docker`.
Also, see https://docs.docker.com/docker-for-mac/install/.

## Usage
First, create a new docker volume with `$ docker volume create --name=kalki`.
Then, start the database by running `$ ./run_postgres_container.sh` from the project root.

To build the docker image, run `$ docker build -t [NAME] .` in the directory containing
`Dockerfile` (it is in the project root by default). Note the period after `[NAME]`.

To run the image in a container, run `$ docker run --rm -i -p [PORT]:9000 [NAME]`.
The dashboard can then be viewed at `http://localhost:[PORT]`.

For example,
```
$ docker build -t kalki-dashboard .
$ docker run --rm -i -p 80:9000 kalki-dashboard
```
will make the dashboard available for viewing at `http://localhost`.

The server (and container) can be stopped by pressing `<C-D>`.
To stop the database, run `$ docker container stop kalki-postgres`.
All data is preserved and will be accessable next time `$ ./run-postgres-container.sh` is run.
