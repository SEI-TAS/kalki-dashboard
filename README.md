# kalki-dashboard

## Prerequisites
To compile this program, Java JDK 8 is required. This program uses Gradle as its build system, but since it uses an included Gradle wrapper, no external Gradle setup is required.

To build the Docker image of the program, Docker should be installed first.

Kalki-db should be installed on a local Maven repo for this program to compile. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

This program requires a Postgres database engine to run. This can be installed manually, or a Docker image can be used. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

The Kalki Dashboard is a web application developed using the Java play! framework.  This framework depends on sbt as a build tool and must be installed prior to building or running the dashboard.

### MAC
sbt for Mac can be installed using `$ brew install sbt@1`. Also, see https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html

Docker for Mac can be installed using `brew cask install docker`.
Also, see https://docs.docker.com/docker-for-mac/install/.

### Linux
sbt for Linux can installed by following the directions found at https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html.

Docker for Linux can be installed by running `sudo apt-get install docker`.  Also, see https://docs.docker.com/install/linux/linux-postinstall/ for instructions on setting up a user group so that docker can be run without prefacing it with the sudo command.

## Usage
### Local Development
To develop locally you only need to know how to run the application:
- Run the application:
```
cd dashboard/
sbt run
```
- The application can then be viewed at `http://localhost:9000`

The server can be stopped by pressing `<C-D>`. 
### Docker Deployment
To deploy and run a docker container navigate to the *dashboard/* directory:

- create the docker image by running `sbt docker:publishLocal`
- Deploy the docker container by running `./run_dashboard_container.sh`

The dashboard can then be viewed at `http://localhost:9000`

The container can be stopped by running `$ docker stop kalki-dashboard`
