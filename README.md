# Jackaroo 🎮

A JavaFX implementation of the classic **Jackaroo** board game (also known as *Marble Chase* or *Tock*). This project was developed as a university course project (CS4, Semester 4) by **Team 250**.

---

## 🎯 What is Jackaroo?

Jackaroo is a strategic board game for 4 players where each player races to move their 4 marbles from their **Home** area, around the **Track**, and into their **Safe Zone**. Players use a hand of cards to move marbles, with special cards enabling unique actions like swapping, burning, or saving marbles.

**Objective**: Be the first player to get all 4 marbles into your Safe Zone.

---

## ✨ Features

- **Full Game Logic**: Complete implementation of Jackaroo rules including all card types
- **JavaFX GUI**: Polished graphical interface built with FXML/Scene Builder
- **AI Opponents**: Play against 3 CPU players with basic strategy
- **All Card Types**:
  - **Standard Cards** (A, 2–10, J, Q, K) — movement with special rules
  - **Wild Cards** — *Saver* (protect a marble) and *Burner* (send opponent home)
- **Visual Board**: Track, Safe Zones, Home bases, Fire Pit, and player hands
- **Interactive Controls**: Select cards/marbles, play turns, field marbles, deselect

---

## 📋 Game Rules Overview

| Card | Effect |
|------|--------|
| **Ace** | Move 1 or 11; can field a marble |
| **2–6** | Move marble forward N spaces |
| **7** | Split 7 spaces among multiple marbles |
| **8** | Move 8 forward OR move 1 backward |
| **9** | Split 9 spaces among multiple marbles |
| **10** | Move 10 forward OR move 1 backward |
| **Jack** | Swap with any opponent's marble on track |
| **Queen** | Move 12 spaces |
| **King** | Move 13 spaces; can field a marble |
| **Saver** (Wild) | Protect a marble from being sent home |
| **Burner** (Wild) | Send any opponent's marble on track home |

### Key Mechanics
- **Fielding**: Play Ace/King to move a marble from Home → Base (start of track)
- **Landing on Opponent**: Sends their marble back to Home
- **Safe Zone**: Marbles enter after completing a full lap; cannot be attacked
- **Fire Pit**: Discarded cards go here; reshuffled when deck runs low
- **Turn Cycle**: 4 cards per round, then new hands dealt

---

## 🏗 Project Structure

```
jackaroo/
├── build_and_run.sh          # Build & run script (Linux/macOS)
├── README.md                 # This file
├── Cards.csv                 # Card definitions
└── JackarooM2Solution/       # Main solution (Milestone 2)
    ├── src/
    │   ├── controller/       # JavaFX controllers
    │   │   ├── Controller.java      # Main game logic & UI binding
    │   │   └── JackarooGUI.java     # Application entry point
    │   ├── engine/           # Game engine
    │   │   ├── Game.java            # Core game state & rules
    │   │   ├── GameManager.java     # Interface for board/player callbacks
    │   │   └── board/               # Board, cells, safe zones
    │   ├── exception/        # Custom exceptions
    │   ├── model/
    │   │   ├── card/              # Card hierarchy (standard + wild)
    │   │   │   ├── standard/        # Ace, 2–10, Jack, Queen, King
    │   │   │   └── wild/            # Saver, Burner
    │   │   ├── player/            # Player, CPU, Marble
    │   │   └── Colour.java        # Player colours (Red, Blue, Green, Yellow)
    │   └── test/             # Public/private test suites
    ├── Main.fxml             # JavaFX UI layout (Scene Builder)
    ├── PlayingCards/         # Card front/back images
    ├── marbles/              # Coloured marble images
    ├── path/                 # Track/path tile images
    └── icons/                # UI icons
```

---

## 🛠 Requirements

| Tool | Version |
|------|---------|
| **JDK** | 17+ (tested on 21) |
| **JavaFX SDK** | 17+ (matching JDK) |
| **OS** | Linux / macOS / Windows (WSL) |

> **Note**: The build script assumes JavaFX is installed at `/usr/share/openjfx/lib`. Adjust the `--module-path` in `build_and_run.sh` if your installation differs.

### Install JavaFX (Ubuntu/Debian)
```bash
sudo apt update && sudo apt install openjfx
```

### Install JavaFX (Fedora)
```bash
sudo dnf install java-21-openjfx
```

### Install JavaFX (macOS with Homebrew)
```bash
brew install openjfx
```

---

## 🚀 Building & Running

### Option 1: Using the Build Script (Recommended)
```bash
chmod +x build_and_run.sh
./build_and_run.sh
```

### Option 2: Manual Build & Run
```bash
# 1. Compile
mkdir -p JackarooM2Solution/out
find JackarooM2Solution/src -name "*.java" > sources.txt
javac --module-path /usr/share/openjfx/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.graphics \
      -d JackarooM2Solution/out \
      -sourcepath JackarooM2Solution/src \
      @sources.txt

# 2. Copy resources
mkdir -p JackarooM2Solution/out/{PlayingCards,path,marbles,icons}
cp JackarooM2Solution/Main.fxml JackarooM2Solution/out/
cp JackarooM2Solution/Cards.csv JackarooM2Solution/out/
cp JackarooM2Solution/PlayingCards/*.png JackarooM2Solution/out/PlayingCards/
cp JackarooM2Solution/path/*.png JackarooM2Solution/out/path/
cp JackarooM2Solution/marbles/*.png JackarooM2Solution/out/marbles/
cp JackarooM2Solution/icons/*.png JackarooM2Solution/out/icons/

# 3. Run
java --module-path /usr/share/openjfx/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     -cp JackarooM2Solution/out \
     controller.JackarooGUI
```

### Option 3: From an IDE (IntelliJ / Eclipse / VS Code)
1. Open `JackarooM2Solution` as a project
2. Configure JavaFX module path in run configuration:
   - VM options: `--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics`
3. Run `controller.JackarooGUI`

---

## 🎮 How to Play

1. **Launch the game** — Enter your name when prompted
2. **Your turn** — Cards appear at the bottom of the screen
3. **Select a card** — Click a card from your hand
4. **Select a marble** — Click one of your marbles (on track, in Home, or in Safe Zone)
5. **Play** — Click **"Play Card"** to execute the move
6. **Field a marble** — With Ace/King, click **"Field Marble"** to bring a marble from Home → Base
7. **Deselect** — Click **"Deselect All"** to clear selection
8. **Win** — First to get all 4 marbles into Safe Zone wins!

### UI Layout
```
┌─────────────────────────────────────────────────────────────┐
│  CPU2 (top)                    Track (center)              │
│  [Hand]                        [Safe Zones]                │
├─────────────────────────────────────────────────────────────┤
│  CPU1 (left)                                             CPU3 (right) │
│  [Hand]                                                  [Hand]      │
├─────────────────────────────────────────────────────────────┤
│                    YOUR HAND (bottom)                        │
│  [Card1] [Card2] [Card3] [Card4]   [Field] [Play] [Deselect]│
│                    [Fire Pit]                                │
└─────────────────────────────────────────────────────────────┘
```

---

## 🧪 Testing

The project includes test suites in `JackarooM2Solution/src/test/`:
- `Milestone1PublicTests.java` / `Milestone1PrivateTests.java`
- `Milestone2PublicTests.java`

Run with:
```bash
cd JackarooM2Solution
javac -cp "out:src" --module-path /usr/share/openjfx/lib \
      --add-modules javafx.controls,javafx.fxml,javafx.graphics \
      -d out src/test/*.java
java -cp "out:src" --module-path /usr/share/openjfx/lib \
     --add-modules javafx.controls,javafx.fxml,javafx.graphics \
     org.junit.runner.JUnitCore test.Milestone2PublicTests
```

---

## 🏛 Architecture Highlights

- **MVC Pattern**: FXML (View) ↔ `Controller` (Controller) ↔ `Game`/`Board`/`Player` (Model)
- **Polymorphic Cards**: `Card` base class with `Standard` and `Wild` subclasses; each rank overrides `applyEffect()`
- **GameManager Interface**: Decouples `Game` from `Board`/`Player` for callbacks (send home, field, discard)
- **Exception-Driven Validation**: Custom exceptions (`InvalidCardException`, `CannotFieldException`, etc.) for clear error handling
- **CPU Strategy**: Basic AI in `CPU.play()` — prioritizes fielding, safe zone entry, and knocking opponents

---

## 👥 Team 250

| Member | Role |
|--------|------|
| **Adham** | Game Engine, Card Logic, Board Model |
| **...** | JavaFX UI, Controller, Assets |

*This was our first ever project — built with lots of learning, debugging, and coffee ☕*

---

## 📄 License

University course project — educational use only.

---

## 🙏 Acknowledgments

- JavaFX / OpenJFX team
- Scene Builder for UI design
- Original Jackaroo/Tock game designers