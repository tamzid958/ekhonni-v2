#!/bin/bash

# Colors for better visibility
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

ENV_FILE=".env"

# Function to export variables
export_variables() {
    while IFS='=' read -r key value || [ -n "$key" ]; do
        # Skip comments and empty lines
        if [[ ! $key =~ ^#.*$ ]] && [ -n "$key" ]; then
            # Remove leading/trailing whitespace
            key=$(echo "$key" | xargs)
            value=$(echo "$value" | xargs)

            # Export the variable
            if [ -n "$key" ] && [ -n "$value" ]; then
                export "$key"="$value"
                echo "Exported: $key"
            fi
        fi
    done < "$ENV_FILE"
}

# Check if .env file exists
if [ -f "$ENV_FILE" ]; then
    # Set environment variables
    set -a
    export_variables
    set +a

    echo -e "${GREEN}Environment variables loaded successfully!${NC}"

    # Verify some key variables
    echo -e "\nVerifying key variables:"
    echo "APPLICATION_NAME=$APPLICATION_NAME"
    echo "DB_URL=$DB_URL"
    echo "MAIL_HOST=$MAIL_HOST"
else
    echo -e "${RED}.env file not found!${NC}"
    exit 1
fi