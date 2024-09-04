#!/bin/bash

# Define the List of Constants to install quicknote
ICON_URL="https://github.com/srilakshmikanthanp/quicknote/blob/main/assets/meta/icon.png?raw=true"
API_URL="https://api.github.com/repos/srilakshmikanthanp/quicknote/releases/latest"

APP_HOME="$HOME/.quicknote";

ICON_LOC="$APP_HOME/quicknote.png"
APP_LOC="$APP_HOME/quicknote.AppImage"
LOG_FILE="$APP_HOME/log.txt"

DESKTOP_FILE="$HOME/.local/share/applications/quicknote.desktop"
CONFIG_DIR="$HOME/.config/autostart"
AUTO_START="$CONFIG_DIR/quicknote.desktop"

# Check Applocation if running
if pgrep -f "$APP_LOC" > /dev/null; then
  echo "Stop the Application before Running this script"
  exit 1
fi

# Remove the AppImage file and log file
if [ -f "$APP_LOC" ]; then
  echo "Removing AppImage..."
  rm "$APP_LOC"
fi

# Remove the Log file
if [ -f "$LOG_FILE" ]; then
  echo "Removing log file..."
  rm "$LOG_FILE"
fi

# Remove the Image
if [ -f "$ICON_LOC" ]; then
  echo "Removing icon..."
  rm "$ICON_LOC"
fi

# Remove the .desktop file if it exists
if [ -f "$DESKTOP_FILE" ]; then
  echo "Removing .desktop file..."
  rm "$DESKTOP_FILE"
fi

# make the directory in HOME
mkdir -p $APP_HOME

# Ge the latest release json
RELEASE=$(curl -s $API_URL)

# Extract the AppImage download URL
APP_URL=$(echo $RELEASE | grep -oP '"browser_download_url": "\K(.*AppImage)(?=")')

# Check if the AppImage URL was found
if [ -z "$APP_URL" ]; then
  echo "AppImage not found in the latest release."
  echo $RELEASE
  exit 1
fi

# Exit immediately if any command fails
set -e

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

# make auto start dir
mkdir -p $CONFIG_DIR

ln -s "$DESKTOP_FILE" "$AUTO_START"

# start the Application
"$APP_LOC" > "$LOG_FILE" 2>&1 &

# Log the status of Installation
echo "Application Installed to $APP_HOME"
