#!/bin/bash

# Update packages and install necessary dependencies
echo "Updating packages and installing dependencies..."
sudo apt-get update -y
sudo apt-get install -y curl git

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "Docker is not installed. Installing Docker..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sh get-docker.sh
else
    echo "Docker is already installed."
fi

# Add the current user to the Docker group (to avoid using sudo with Docker)
USER_NAME=$(whoami)
echo "Adding the user $USER_NAME to the Docker group..."
sudo usermod -aG docker $USER_NAME

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "Docker Compose is not installed. Installing Docker Compose..."
    # Docker Compose Plugin installation
    sudo curl -L "https://github.com/docker/compose/releases/download/v2.18.0/docker-compose-linux-x86_64" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose
else
    echo "Docker Compose is already installed."
fi

# Define the directory where the project will be cloned
REPO_DIR="/home/debian/M2Chausson/Services"

# Check if the directory exists and remove it if necessary
if [ -d "$REPO_DIR" ]; then
  echo "Directory $REPO_DIR exists. Removing the directory..."
  sudo rm -rf "$REPO_DIR"
fi

# Clone the project from GitHub into the directory
echo "Cloning the project from GitHub into $REPO_DIR..."
git clone git@github.com:alphaxBDX/Services.git "$REPO_DIR"

# Go to the cloned directory
cd "$REPO_DIR"

# Stop the Docker services if they are running
#echo "Stopping Docker services..."
#sudo docker-compose -f Docker/docker-compose.yml down

# Start the services with Docker Compose
echo "Starting services with Docker Compose..."
sudo docker-compose -f Docker/docker-compose.yml up -d

echo "Deployment completed successfully in the directory $REPO_DIR!"