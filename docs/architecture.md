# EduSnap - System Architecture

## 1. Overview

EduSnap will consist of three main components:

1. Chrome Extension
2. Java Spring Boot Backend
3. MySQL Database

## 2. System Components

### 2.1 Chrome Extension

The Chrome Extension will provide the interface through which
students interact with EduSnap.

Responsibilities:
- Allow students to select or enter a concept.
- Send requests to the backend.
- Display explanations and examples.
- Display visual aids.
- Display quizzes.

### 2.2 Java Spring Boot Backend

The backend will act as the main processing layer of EduSnap.

Responsibilities:
- Receive requests from the Chrome Extension.
- Process requests.
- Retrieve relevant educational content.
- Send explanations, examples and quiz data to the extension.
- Communicate with the database.

### 2.3 MySQL Database

The database will store educational content.

Possible data:
- Classes
- Subjects
- Topics
- Explanations
- Examples
- Quiz questions
- Quiz options
- Answers

## 3. Basic Data Flow

The basic flow will be:

Student
→ Chrome Extension
→ Java Spring Boot Backend
→ MySQL Database
→ Java Spring Boot Backend
→ Chrome Extension
→ Student

## 4. Technologies

| Component | Technology |
|---|---|
| Extension | HTML, CSS, JavaScript |
| Backend | Java, Spring Boot |
| Database | MySQL |
| Development IDE | IntelliJ IDEA |
| Version Control | Git + GitHub |

## 5. Initial Architecture

The initial architecture is intentionally kept simple.
More detailed components such as controllers, services,
repositories and models will be defined during backend development.