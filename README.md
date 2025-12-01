# Connect4 Backend

A Java-based backend implementation for the game Connect4, built as a course project with a focus on software design principles.  
The project emphasizes clean layering, separation of concerns, data integrity, and maintainable architecture.

---

## Features

- Create and manage players  
- Start new games, resume ongoing games, and play moves  
- Validate moves (column full, board full, invalid game/player, etc.)  
- Detect win/draw states  
- Retrieve player statistics and game history  
- Query for games that share identical move sequences up to a given turn  
- Fully testable backend (JUnit)

> **Note:** This project does **not** include a UI. It is designed as a backend service with clear boundaries between layers so it can be adapted into a REST API later.

---

## Architecture

This project uses a structured three-layer design:

### **1. Data Access Layer**
- Stores and retrieves persistent entities (currently in-memory arrays)  
- Works with *DataObjects* (flattened player/game/board structures)  
- Implementation can be replaced by a database with no impact on upper layers

### **2. Model Layer**
- Contains the core domain logic  
- Converts between *DataObjects* and *DomainObjects*  
- Validates user actions and game state  
- Ensures state changes propagate consistently to the data layer

### **3. Controller Layer**
- Provides service-style methods that accept request objects and return response objects  
- Coordinates the model layer to carry out operations  
- Designed to be easily adapted into REST endpoints (e.g., via Spring Boot)

---

## Testing

Unit tests are written with **JUnit**, covering essential game functionality and edge case scenarios.
Tests are organized logically by feature.

---

## Running the Project

The project has no external dependencies beyond JDK and JUnit.

### **Compile**
```bash
javac -d out $(find src -name "*.java")
```
---

## Folder Structure

```
connect4-backend-java/
│
├── src/          # Source code (controllers, models, data access, domain objects)
├── test/         # JUnit test files
└── README.md
```

