#!/bin/bash

set -e

SH_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
PARENT_DIR="$(dirname "$SH_DIR")"
WORKING_DIR="$(basename "$PARENT_DIR")"

echo Releasing...

# Your Docker Hub repository and Dockerfile
REPOSITORY="alphaxbdx/chaussonm2-services"
DOCKERFILE="Docker/Dockerfile"
echo $PARENT_DIR

# Build for multiple platforms
PLATFORMS="linux/amd64"

# Check if Docker Buildx is available and create a builder instance if not exist
if ! docker buildx &>/dev/null; then
    echo "Docker Buildx not found. Creating a new builder instance..."
    docker buildx create --use
fi

# Run Docker build
docker buildx inspect --bootstrap
docker buildx build --push --platform $PLATFORMS -t $REPOSITORY:latest -f "$PARENT_DIR/$DOCKERFILE" "$PARENT_DIR"

# Log out from Docker
docker logout