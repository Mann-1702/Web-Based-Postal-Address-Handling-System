# Prerequisites

Make sure you have the following software installed on your development machine:
* Java 17 (LTS)
* Docker (version 27.5.1 or later)
* Docker Compose (version 2.12.2 or later)
* Apache Maven (version 3.9.9 or later)
* GNU Make (version 3.81 or later)
* 
To verify your installations, run:
```bash
java --version
docker --version
docker-compose --version
mvn --version
make --version

```

# How to Contribute
## 1. Develop and Test Locally

   Make your changes in the repository.
   From the project’s root folder, start the MySQL container and run the Spring Boot application:

make run

After you finish testing, stop and remove the local database container:

    make down

## 2. Deploy Multiple Containers

If you need to spin up all containers (MySQL, the Dockerized API, and an admin container for MySQL):

    Deploy the services:

make deploy

Once you’re done, clean up everything:

    make destroy

