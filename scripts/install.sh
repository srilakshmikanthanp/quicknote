#!/bin/bash

# Define the List of Constants to install quicknote
ICON_URL="https://github.com/srilakshmikanthanp/quicknote/blob/main/assets/meta/icon.png?raw=true"
API_URL="https://api.github.com/repos/srilakshmikanthanp/quicknote/releases/latest"

APP_HOME="$HOME/.quicknote";

ICON_LOC="$APP_HOME/quicknote.png"
APP_LOC="$APP_HOME/quicknote.AppImage"
LOG_FILE="$APP_HOME/log.txt"

DESKTOP_FILE="$HOME/.local/share/applications/quicknote.desktop"

# Exit immediately if any command fails
set -e

# make the directory in HOME
mkdir -p $APP_HOME

# Ge the latest release json
RELEASE=$(curl -s $API_URL)

# Extract the AppImage download URL
APP_URL=$(echo $RELEASE | grep -oP '"browser_download_url": "\K(.*AppImage)(?=")')

# Check if the AppImage URL was found
if [ -z "$APP_URL" ]; then
  echo "AppImage not found in the latest release."
  exit 1
fi

# Echo Information of AppImage
echo "Downloading $APP_URL to $APP_HOME"

# Download the AppImage
curl -L -o "$APP_LOC" "$APP_URL"

# Make the AppImage executable
chmod +x "$APP_LOC"

# Echo Information of Image
echo "Downloading $ICON_LOC to $APP_HOME"

# Download the Image
curl -L -o "$ICON_LOC" "$ICON_URL"

# Create the .desktop file
mkdir -p "$(dirname "$DESKTOP_FILE")"

# Fill the content for desktop file
echo "[Desktop Entry]
Name=QuickNote
Comment=QuickNote Application
Exec=$APP_LOC
Icon=$ICON_LOC
Terminal=false
Type=Application
Categories=Utility;" > "$DESKTOP_FILE"

# Make the .desktop file executable
chmod +x "$DESKTOP_FILE"

# start the Application
"$APP_LOC" > "$LOG_FILE" 2>&1 &

# Log the status of Installation
echo "Application Installed to $APP_HOME"
