SHELL := /bin/bash
OS := $(shell uname)

define start-services
	@docker compose -f compose.yaml up --force-recreate -d --remove-orphans sonar fluentbit db kafka kafdrop elasticsearch prometheus grafana telegraf influxdb keycloak
endef

define start-check
	@docker compose -f sonar-compose.yaml up --force-recreate -d --remove-orphans sonar-db sonar
endef

define start-app
	@docker compose -f compose.yaml up -d web worker
endef

define teardown
	@docker compose -f compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define check-teardown
	@docker compose -f sonar-compose.yaml rm -f -v -s
	@docker system prune -f --volumes
endef

define local-setup
	$(call start-services)
endef

define local-app
	$(call start-app)
endef

define setup
	@docker compose -f compose.yaml up -d --build --force-recreate --remove-orphans
endef

define k8s-apply
	kubectl create namespace argocd
    kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
    kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.0.0/deploy/static/provider/cloud/deploy.yaml
    kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.0.0/deploy/static/provider/baremetal/deploy.yaml
    kubectl apply -f k8s/mysql.yaml
    kubectl apply -f k8s/kafka.yaml
    kubectl apply -f k8s/kafdrop.yaml
    kubectl create -f https://download.elastic.co/downloads/eck/2.9.0/crds.yaml
    kubectl apply -f https://download.elastic.co/downloads/eck/2.9.0/operator.yaml
    kubectl apply -f k8s/es.yaml
    kubectl create namespace prometheus
    helm repo add bitnami https://charts.bitnami.com/bitnami
    helm install prometheus bitnami/kube-prometheus -n prometheus
    kubectl apply -f k8s/app.yaml
    kubectl apply -f k8s/grafana.yaml
endef

define k8s-delete-app
    @kubectl delete -f ./k8s/app.yaml
endef

define del-local-app
    @docker stop grocery-booking-worker
    @docker stop grocery-booking-web
    @docker rm grocery-booking-worker
    @docker rm grocery-booking-web
    @docker image rm grocery-booking
    @docker image rm shubham01/grocery-booking
endef


.PHONY: help install setup teardown

help:
	@echo "######### Targets ##########"
	@echo "help: Display the help menu"
	@echo "install: Display the help menu"
	@echo "local-setup: Setup test resources locally"
	@echo "local-app: Setup test resources locally"
	@echo "format: Formats the java codebase"
	@echo "setup: Setup test resources"
	@echo "teardown: Destroy test resources"
	@echo "start-services: Start dependent services for testing"
	@echo "tests: Run tests in local"
	@echo "run-test: Run specific test"
	@echo "############################"

start-check:
	$(call start-check)

check:
	./gradlew sonar

check-teardown:
	$(call check-teardown)

local-setup: teardown
	$(call local-setup)

setup: teardown build
	$(call setup, "Setting up...")

start-services:
	$(call start-services)

teardown:
	$(call teardown, "Tearing down...")

run-test:
	@docker-compose -f compose.yaml up --build --force-recreate -d service

migrations:

checkformat:
	sudo ./gradlew checkformat

format:
	sudo ./gradlew format

install: setup

clean:
	clear
	./gradlew clean

build-local: clean
	./gradlew build -x test

build-local-test: clean
	./gradlew build

rm-images: clean
	docker image rm shubham01/grocery-booking
	docker image rm grocery-booking

docker-build:
	docker build -t shubham01/grocery-booking:latest .

build: clean build-local docker-build

rebuild: rm-images build

run-local: build-local
	./gradlew bootRun

run: build
	docker run -p 8080:8080 shubham01/grocery-booking:latest --network="host"

k8s-apply:
	$(call k8s-apply)

k8s-delete-app:
	$(call k8s-delete-app)

del-local-app:
	$(call del-local-app)

local-app: format build
	$(call local-app)

local-app-re: del-local-app local-app

coverage:
	./gradlew jacocoTestCoverageVerification

tests: local-setup
	sudo ./gradlew test
	make teardown

pipeline-build: local-setup
	./gradlew build

# condb:
#     mysql -h 127.0.0.1 -P 3306 -u test grocery-booking -p
