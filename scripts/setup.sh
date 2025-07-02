
#!/bin/bash
# This Script is used to install or uninstall quicknote in the system
# pass --install to install the quicknote [default]
# pass --install-from-path /path/to/quicknote to install the quicknote from a custom path
# pass --uninstall to uninstall the quicknote
# pass --help to get the help information
# pass --purge to uninstall the quicknote and remove the configuration files

# Define the List of Constants to pull the AppImage and Icon
ICON_URL="https://github.com/srilakshmikanthanp/quicknote/blob/main/assets/meta/icon.png?raw=true"
APP_URL="https://github.com/srilakshmikanthanp/quicknote/releases/download/v2.0.3/quicknote-2.0.3.AppImage"

# APP_HOME is where App is installed
APP_HOME="$HOME/.quicknote";
APP_NAME="quicknote.AppImage";

# Define the List of Variables
ICON_LOC="$APP_HOME/quicknote.png"
SH_LOC="$APP_HOME/quicknote.sh"
APP_LOC="$APP_HOME/$APP_NAME"
LOG_FILE="$APP_HOME/log.txt"

# Conf for .desktop file & auto start
APPDESKTOP_FILE="$HOME/.local/share/applications/quicknote.desktop"
AUTOSTART_DIR="$HOME/.config/autostart"
AUTOSTART_ENTRY="$AUTOSTART_DIR/quicknote.desktop"

# Check if the script is running as root user
if [ "$EUID" -eq 0 ]; then
  echo "This script should not be run as root"
  exit 1
fi

# Check Applocation if running
if pgrep -f "$APP_LOC" > /dev/null; then
  echo "Stop the Application before Running this script"
  exit 1
fi

install() {
  # Uninstall existing installation if any
  if [ -d "$APP_HOME" ]; then
    echo "Existing installation found. Uninstalling..."
    uninstall
  fi

  # make the directory in HOME
  mkdir -p $APP_HOME

  # copy the first argument to APP_HOME if provided or download the AppImage
  if [ -n "$1" ]; then
    echo "Installing from provided path: $1"
    cp "$1" "$APP_LOC"
  else
    echo "Downloading AppImage from $APP_URL"
    curl -L -o "$APP_LOC" "$APP_URL"
  fi

  # Make the AppImage executable
  chmod +x "$APP_LOC"

  # Echo Information of Image
  echo "Downloading $ICON_LOC to $APP_HOME"

  # Download the Image
  curl -L -o "$ICON_LOC" "$ICON_URL"

  # Create the .desktop file
  mkdir -p "$(dirname "$APPDESKTOP_FILE")"

  # Fill the content for desktop file
  echo "[Desktop Entry]
  Name=quicknote
  Comment=quicknote Application
  Exec=$SH_LOC
  Icon=$ICON_LOC
  Terminal=false
  Type=Application
  Categories=Utility" > "$APPDESKTOP_FILE"

  # Make the .desktop file executable
  chmod +x "$APPDESKTOP_FILE"

  # Fill the Shell script File
  echo "$APP_LOC > "$LOG_FILE" 2>&1 &" > "$SH_LOC";

  # Make the Shell script executable
  chmod +x "$SH_LOC"

  # make auto start dir
  mkdir -p $AUTOSTART_DIR

  # create the auto start entry
  ln -s "$APPDESKTOP_FILE" "$AUTOSTART_ENTRY"

  # start the Application
  "$SH_LOC"
}

uninstall() {
  # Remove the AppImage file
  if [ -f "$APP_LOC" ]; then
    echo "Removing AppImage..."
    rm "$APP_LOC"
  fi

  # Remove the Script file
  if [ -f "$SH_LOC" ]; then
    echo "Removing quicknote..."
    rm "$SH_LOC"
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
  if [ -f "$APPDESKTOP_FILE" ]; then
    echo "Removing .desktop file..."
    rm "$APPDESKTOP_FILE"
  fi

  # remove the auto start file
  if [ -L "$AUTOSTART_ENTRY" ]; then
    echo "Removing auto start file..."
    rm "$AUTOSTART_ENTRY"
  fi
}

purge() {
  uninstall && rm -rf "$APP_HOME"
}

help() {
  echo "Usage: setup.sh [OPTION]"
  echo "Install or Uninstall the quicknote Application"
  echo ""
  echo "Options:"
  echo "  $UNINSTALL uninstall the quicknote"
  echo "  $PURGE     uninstall the quicknote completely"
  echo "  $HELP      Show this help information"
  exit 0
}

main() {
  # parse the command line arguments using getopts
  OPTIONS=$(getopt --options "" --long install,install-from:,uninstall,purge,help -- "$@")

  if [ $? -ne 0 ]; then
    echo "Error parsing passed options aborting..."
    help
    exit 1
  fi

  eval set -- "$OPTIONS"

  while :; do
    case "${1}" in
    --install-from)
      install "$2"
      echo "Quicknote installed from custom path: $2"
      shift 2
      ;;
    --install)
      install
      echo "Quicknote installed successfully."
      shift 1
      ;;
    --uninstall)
      uninstall
      echo "Quicknote uninstalled successfully."
      shift 1
      ;;
    --purge)
      purge
      echo "Quicknote purged successfully."
      shift 1
      ;;
    --help)
      help
      shift 1
      ;;
    --)
      shift
      break
      ;;
    *)
      help
      exit 1
      ;;
    esac
  done
}

# if no arguments are passed, default to install
if [ $# -eq 0 ]; then
  main --install
else
  main "$@"
fi
