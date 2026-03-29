.PHONY: build run test clean docker-build docker-up

build:
	./gradlew build -x test

run:
	./gradlew bootRun

test:
	./gradlew test

clean:
	./gradlew clean
	rm -rf data/ logs/

docker-build:
	docker build -t simple-cms-api .

docker-up:
	docker compose up --build
