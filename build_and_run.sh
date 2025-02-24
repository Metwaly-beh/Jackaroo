#!/bin/bash
# Build and run script for Jackaroo game

set -e

SRC_DIR="JackarooM2Solution/src"
OUT_DIR="JackarooM2Solution/out"
RES_DIR="JackarooM2Solution"

echo "Cleaning previous build..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

echo "Compiling Java sources..."
# Find all Java files
find "$SRC_DIR" -name "*.java" > sources.txt

# Compile with JavaFX modules
javac --module-path /usr/share/openjfx/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.graphics \
      -d "$OUT_DIR" \
      -sourcepath "$SRC_DIR" \
      @sources.txt

echo "Copying resources..."
# Copy FXML and resource files
mkdir -p "$OUT_DIR/PlayingCards"
mkdir -p "$OUT_DIR/path"
mkdir -p "$OUT_DIR/marbles"
mkdir -p "$OUT_DIR/icons"

cp "$RES_DIR/Main.fxml" "$OUT_DIR/" 2>/dev/null || echo "Warning: Main.fxml not found in $RES_DIR"
cp "$RES_DIR/Cards.csv" "$OUT_DIR/" 2>/dev/null || echo "Warning: Cards.csv not found in $RES_DIR"
cp "$RES_DIR/PlayingCards/"*.png "$OUT_DIR/PlayingCards/" 2>/dev/null || echo "Warning: PlayingCards not found"
cp "$RES_DIR/path/"*.png "$OUT_DIR/path/" 2>/dev/null || echo "Warning: path images not found"
cp "$RES_DIR/marbles/"*.png "$OUT_DIR/marbles/" 2>/dev/null || echo "Warning: marbles images not found"
cp "$RES_DIR/icons/"*.png "$OUT_DIR/icons/" 2>/dev/null || echo "Warning: icons not found"

echo "Running game..."
java --module-path /usr/share/openjfx/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     -cp "$OUT_DIR" \
     controller.JackarooGUI

echo "Done."