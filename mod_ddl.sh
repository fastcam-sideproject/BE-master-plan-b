#!/bin/bash

# Check if a file name is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <ddl_file_path>"
    exit 1
fi

# Input and output file paths
SRC_DIR="./src/main/resources/db"
ORIGINAL_DDL="$SRC_DIR/$1"
OUT_DIR="./src/main/resources/db/out"
UPDATED_DDL="$OUT_DIR/updated_${ORIGINAL_DDL##*/}"

# Check if the original DDL file exists
if [ ! -f "$ORIGINAL_DDL" ]; then
    echo "Error: Original DDL file '$ORIGINAL_DDL' not found."
    exit 1
fi

# Create a backup of the original file
cp "$ORIGINAL_DDL" "$UPDATED_DDL"

# Update the DDL to add AUTO_INCREMENT and PRIMARY KEY to id fields
sed -i -e '/CREATE TABLE/,/`id`/ { /`id`/ s/NOT NULL/NOT NULL AUTO_INCREMENT PRIMARY KEY/ }' "$UPDATED_DDL"

# Remove all lines starting from ALTER TABLE
sed -i '/^ALTER TABLE/,$d' "$UPDATED_DDL"

# Print success message
if [ $? -eq 0 ]; then
    echo "AUTO_INCREMENT and PRIMARY KEY added successfully. ALTER TABLE commands removed. Updated DDL saved to '$UPDATED_DDL'."
else
    echo "Error: Failed to update DDL file."
    exit 1
fi
