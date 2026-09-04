#!/bin/sh
set -e
mkdir -p /etc/xdg/autostart
cat > /etc/xdg/autostart/quicknote.desktop << 'EOF'
[Desktop Entry]
Type=Application
Name=QuickNote
Exec=/usr/bin/quicknote
Terminal=false
X-GNOME-Autostart-enabled=true
X-GNOME-Autostart-Delay=5
EOF
chmod 644 /etc/xdg/autostart/quicknote.desktop
