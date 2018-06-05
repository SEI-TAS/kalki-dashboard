# kalki-dashboard

## Prerequisites
The Kalki Dashboard is run from within a Docker container. Therefore, you will need to have
Docker installed.

## Configuration
You can download Docker here: https://www.docker.com/get-docker.
I do not recommend installing Docker using a package manager.
For Mac, Docker can be installed using `brew cask install docker`.

## Usage
To build the docker image, run `$ docker build -t [NAME] .` in the directory containing
`Dockerfile` (it is in the root of the project by default). Note the period after `[NAME]`.

To run the image in a container, run `$ docker run -i -p [PORT]:9000 [NAME]`.
The dashboard can then be viewed at `http://localhost:[PORT]`.

For example, `$ docker build -t kalki-dashboard . && docker run -i -p 80:9000 kalki-dashboard`
will make the dashboard available for viewing at `http://localhost`.

The server (and container) can be stopped by pressing `<C-D>`.
