# kalki-dashboard

## Prerequisites
The Kalki Dashboard is a web application developed using the Java play! framework.  To compile this application, Java JDK 8 is required. This application also uses sbt as its build system, so sbt will need to be installed in order to build and run the dashboard.

To build the Docker image of the program, Docker should be installed first.

Kalki-db should be installed on a local Maven repo for this program to compile. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

This program requires a Postgres database engine to run. This can be installed manually, or a Docker image can be used. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

### macOS
sbt for macOS can be installed using `$ brew install sbt@1`. Also, see https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html

Docker for macOS can be installed using `brew cask install docker`.
Also, see https://docs.docker.com/docker-for-mac/install/.

### Linux
sbt for Linux can installed by following the directions found at https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html.

Docker for Linux can be installed by running `sudo apt-get install docker`.  Also, see https://docs.docker.com/install/linux/linux-postinstall/ for instructions on setting up a user group so that docker can be run without prefacing it with the sudo command.

## Usage
### Local Development
To develop locally:
1) Make sure the Postgres DB container is running. (this can be ensured by starting other components first).
2) Run the application:
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

## Debugging Tips
* Always check dev tools console for javascript errors first since these are the most common (probably obvious)
* `cmd-shift-c` lets you click on an element on the page and inspect it, which is easier than navigating through the html to find the element
* If you ever see "undefined" in one of the tables, it is most likely a synchronization issue.  In most cases, names need to be stored into maps before the table is loaded.  Check that the functions that store data into maps are being awaited before the table is populated.
* If a table does not appear to be sorting based on the formatted time correctly, then go into the javascript for that table and look at the datatables definition at the top.  Under columnDefs remove the part that specifies time-uni.  If that is not there, then look into datatables sorting with datatime-moment


