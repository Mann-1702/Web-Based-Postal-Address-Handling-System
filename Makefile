# Default target
run: up wait dbclient-start
	mvn clean
	mvn -X spring-boot:run -Dspring-boot.run.profiles=local


# Step 1: Spin up MySQL container in background
up:
	@echo "Starting MySQL Docker container..."
	docker run -d  --rm -p 3306:3306 --name mysqllocal --platform linux/amd64 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=addressdb mysql:9.2.0

# Step 2: Wait until MySQL is ready
wait:
	@echo "Waiting for MySQL to be ready..."
	@for i in 1 2 3 4 5 6 7 8 9 10; do \
		docker exec mysqllocal mysqladmin ping -hlocalhost -proot >/dev/null 2>&1 && { \
			echo "MySQL is up!"; \
			exit 0; \
		}; \
		echo "Still waiting for mysqllocal to become ready ...($$i)"; \
		sleep 5; \
	done; \
	echo "MySQL did not become ready in time."; \
	exit 1

# Optional: Stop the container
down: dbclient-down
	@echo "Stopping MySQL Docker container..."
	docker stop mysqllocal


dbclient-start:
	@echo "starting Adminer Container to manage and inspect data"
	docker run -d --rm --name mysqllocal-adminer --platform linux/amd64 -p 8081:8080  -e ADMINER_DEFAULT_SERVER=host.docker.internal adminer

dbclient-down:
	@echo "Stopping Adminer container..."
	docker stop mysqllocal-adminer


deploy:
	@echo "Building Spring Boot Application..."
	mvn clean package -DskipTests
	@echo "Building Docker Image..."
	docker build -t address-manager .
	@echo "Starting All Services with Docker Compose..."
	docker-compose up --build -d
	@echo "Deployment Complete!"
	@echo "Application running at: http://localhost:9091/swagger-ui/index.html"
	@echo "Prometheus UI: http://localhost:9090"
	@echo "To manipulate data interface available at: http://localhost:8081"

destroy:
	@echo "Stopping and Removing Docker Compose Services..."
	docker-compose down --volumes --remove-orphans
	@echo "Removing Address Manager Docker Image..."
	docker rmi address-manager || echo "Image not found, skipping removal."
	@echo "Cleanup Complete!"