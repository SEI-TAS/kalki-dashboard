# kalki-dashboard
The Kalki Dashboard is a web application developed using the Java play! framework.  

## Prerequisites

The Kalki-db Postgres container has to be running for this program to work. You can find more details here: https://github.com/SEI-TTG/kalki-db

### Local Setup 
To compile this application locally, Java JDK 8 and Scala is required. This application also uses sbt as its build system, so sbt will need to be installed in order to build and run the dashboard.

Kalki-db should be installed on a local Maven repo for this program to compile. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

sbt for macOS can be installed using `$ brew install sbt@1`. Also, see https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html

sbt for Linux can installed by following the directions found at https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html.

### Docker Setup
Alternatively, this application can be built in a Docker container, and produce a Docker image as an output. In this case:

 - Docker is required to compile and run this program.
 - The Kalki-db build env image should be created before compiling this program. You can find more details here: https://github.com/SEI-TTG/kalki-db

Docker for macOS can be installed using `brew cask install docker`.
Also, see https://docs.docker.com/docker-for-mac/install/.

Docker for Linux can be installed by running `sudo apt-get install docker`.  Also, see https://docs.docker.com/install/linux/linux-postinstall/ for instructions on setting up a user group so that docker can be run without prefacing it with the sudo command.

## Usage
Once it is running, the application can be viewed at `http://localhost:9000`

### Local Development
To develop locally:
1) Make sure the Postgres DB container is running.
2) Run the application:
```
cd dashboard/
sbt run
```

The server can be stopped by pressing `<Ctrl-C>`. 

### Docker Deployment
To create the docker image for this program, execute the following command:

`bash build_container.sh`

Alternatively, a specific configuration present in the `deployments` folder can be passed with:

`bash build_container.sh <name_folder_in_deployments>`

To execute the program inside docker, execute the following command:

`bash run_compose.sh`

You can stop viewing the logs with `<Ctrl+C>`, and the app will continue running on the background.

To see the logs:

`bash compose_logs.sh`

To stop it:

`bash stop_compose.sh`

## Debugging Tips
* Always check dev tools console for javascript errors first since these are the most common (probably obvious)
* `cmd-shift-c` lets you click on an element on the page and inspect it, which is easier than navigating through the html to find the element
* If you ever see "undefined" in one of the tables, it is most likely a synchronization issue.  In most cases, names need to be stored into maps before the table is loaded.  Check that the functions that store data into maps are being awaited before the table is populated.
* If a table does not appear to be sorting based on the formatted time correctly, then go into the javascript for that table and look at the datatables definition at the top.  Under columnDefs remove the part that specifies time-uni.  If that is not there, then look into datatables sorting with datatime-moment
