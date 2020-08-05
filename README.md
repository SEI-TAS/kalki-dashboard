# kalki-dashboard
The Kalki Dashboard is a web application developed using the Java play! framework. It allows configuring and monitoring of the Kalki platform.

Kalki is an IoT platform for allowing untrusted IoT devices to connect to a network in a secure way, protecting both the IoT device and the network from malicious attackers.

Kalki comprises a total of 8 GitHub projects:
- kalki-node-setup (Kalki Main Repository, composes all non-UI components)
- kalki-controller (Kalki Main Controller)
- kalki-umbox-controller (Kalki Umbox Controller)
- kalki-device-controller (Kalki Device Controller)
- kalki-dashboard (Kalki Dashboard)
- kalki-db (Kalki Database Library)
- kalki-iot-interface (Kalki IoT Interface)
- kalki-umboxes (Kalki Umboxes, sample umboxes and umboxes components)

## Prerequisites

### Local Setup 
To compile and run this application locally, the following are required:

- Java JDK 8
- Scala 
- The sbt build system
  - sbt for macOS can be installed using `$ brew install sbt@1`. Also, see https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Mac.html
  - sbt for Linux can installed by following the directions found at https://www.scala-sbt.org/1.0/docs/Installing-sbt-on-Linux.html.
- Kalki-db should be installed on a local Maven repo for this program to compile. You can find more details here: https://github.com/SEI-TTG/kalki-db/tree/dev

### Docker Setup
Alternatively, this application can be built in a Docker container, and produce a Docker image as an output. In this case:

 - Docker is required to compile and run this program.
   - Docker for macOS can be installed using `brew cask install docker`. Also, see https://docs.docker.com/docker-for-mac/install/.
   - Docker for Linux can be installed by running `sudo apt-get install docker`.  Also, see https://docs.docker.com/install/linux/linux-postinstall/ for instructions on setting up a user group so that docker can be run without prefacing it with the sudo command.
 
 - The Kalki-db build env image should be created before compiling this program. You can find more details here: https://github.com/SEI-TTG/kalki-db


## Usage
The Kalki-db Postgres container has to be running for this program to work. You can find more details here: https://github.com/SEI-TTG/kalki-db

Once this app is running, the application can be viewed at `http://localhost:9000`

To build and run the application, follow either procedure below.

### Local Development
To develop locally:
- Run the application:
```
cd dashboard/
sbt run
```

The server can be stopped by pressing `<Ctrl-C>`. 

### Docker Deployment
To create the docker image for this program, execute the following command:

`bash build_container.sh`

Alternatively, a specific configuration present in the `deployments` folder can be passed with:

`bash build_container.sh <deployment_folder_name>`

NOTE: The "dev" configuration can be used along with the docker-compose-dev.yml configuration to run a fully contained Dashboard plus database. This can be especially useful on MacOS, where host networking doesn't work properly.

To execute the program inside docker, execute the following command:

`bash run_compose.sh`

Alternatively, to run the container development environment (ignoring any already running containers with Kalki DB), which will also start a local container with the Kalki DB, execute:

`bash run_compose.sh docker-compose-dev.yml`

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
