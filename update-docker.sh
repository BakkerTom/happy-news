#!/bin/bash

# Stop docker containers before updating docker-compose.yml to make sure we stop all old containers
# This will cause a little bit of downtime, but this is acceptable for now
docker-compose down

# Pull latest code changes
git reset --hard
git pull

# Rebuild applications
./gradlew clean build -PskipAndroid

# Rebuild docker containers
docker-compose build

# Start docker containers
docker-compose up -d
