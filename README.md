# EduSnap 📚

EduSnap is an educational learning application built for Maharashtra State
Board SSC students (Class 7–10) to understand Mathematics concepts through
guided, adaptive lessons — with pre-requisite checks, weak-concept tracking,
and retests, rather than static content.

This is a Java mini-project (Semester 3), built by a team of 4, divided
**class-wise**: each member owns the Mathematics content for one class
(7, 8, 9, or 10), while sharing a single common learning engine.

## 🎯 Project Objective

Help students understand a topic properly before moving on — not just
show content, but check what a student already knows, teach what's
missing, test understanding, and keep retesting weak spots until the
student actually gets it.

## 👨‍🎓 Target Users

Maharashtra SSC Board students — Class 7, 8, 9, and 10 — studying
Mathematics.

## 🔁 Learning Flow

Every topic, for every class, follows the same flow:

```
Select Class
    ↓
Select Topic
    ↓
Pre-requisite Test
    ↓
Score 100%? ──Yes──► Already Mastered ──► Report
    │
    No
    ↓
Main Teaching Program
 (explanation, formulae, worked examples)
    ↓
Lesson Test
    ↓
Score ≥ 75%? ──Yes──► Completed ──► Report
    │
    No
    ↓
Weak Concept Review ──► Retest ──► (loops until ≥ 75%) ──► Report
```

Every question is tagged with the specific concept it tests, so a wrong
answer identifies exactly which concept the student is weak in — the
student then only reviews and retests on those concepts, not the whole
topic again.

## 🏗️ Current Architecture

EduSnap is a **standalone Java desktop application** — no backend server,
no database, no browser extension. Everything runs locally.

| Component | Technology |
|---|---|
| Language | Java |
| GUI | JavaFX |
| Data storage | In-memory (per session) |
| Build | `javac` / manual compilation |
| IDE | IntelliJ IDEA |
| Version Control | Git & GitHub |



### Why one shared `common/` engine

The pre-requisite test, lesson flow, scoring, weak-concept detection, and
report generation are **identical for every class** — only the educational
content (questions, explanations, formulae) differs. So the entire flow
logic lives once in `src/common/`, and each class folder (`class7/`,
`class8/`, `class9/`, `class10/`) contains *only* that class's Mathematics
content, built on top of the shared engine. No class re-implements the
flow.

## 📁 Project Structure

```
SEM-3-Project/
├── src/
│   ├── Main.java                     # JavaFX entry point — starts the app only
│   ├── common/                       # Shared, reusable learning engine
│   │   ├── Concept.java              # A trackable unit of knowledge
│   │   ├── Question.java             # A question, tagged to a Concept
│   │   ├── Test.java                 # Runs a set of questions, scores them
│   │   ├── TestResult.java           # Score % + weak concepts found
│   │   ├── TopicStatus.java          # Already Mastered / In Progress / Retest Required / Completed
│   │   ├── LessonContent.java        # Explanation + formula + worked examples for one concept
│   │   ├── StudentReport.java        # Final report: scores, attempts, weak concepts, status
│   │   ├── Topic.java                # Abstract flow controller every topic extends
│   │   ├── ConsoleRunner.java        # Console-based test runner (used during early development)
│   │   └── gui/                      # JavaFX screens
│   │       ├── AppLauncher.java      # Owns the window, moves between screens
│   │       ├── QuestionTestScreen.java   # Reusable question-paging screen (prereq/lesson/retest)
│   │       ├── PrerequisiteTestScreen.java
│   │       ├── LessonScreen.java
│   │       ├── LessonTestScreen.java
│   │       ├── RetestScreen.java
│   │       ├── ReportScreen.java
│   │       └── UiStyle.java          # Shared style constants (no CSS)
│   ├── class7/                       # Class 7 Mathematics content (in progress)
│   ├── class8/                       # Class 8 Mathematics content (in progress)
│   ├── class9/                       # Class 9 Mathematics content (in progress)
│   └── class10/
│       └── ArithmeticProgression.java   # Class 10 content: Arithmetic Progression
├── docs/
│   ├── requirements.md
│   └── architecture.md
└── README.md
```



## 🚀 Running the Project

EduSnap uses JavaFX, which is not bundled with the JDK — you'll need
the JavaFX SDK installed separately.

1. Download the JavaFX SDK from [gluonhq.com/products/javafx](https://gluonhq.com/products/javafx),
   matching your JDK version (17 or 21 LTS recommended).
2. Compile with the JavaFX module path:
   ```powershell
   javac -d out --module-path "<path-to-javafx-sdk>\lib" --add-modules javafx.controls,javafx.fxml src/Main.java src/common/*.java src/common/gui/*.java src/class10/*.java
   ```
3. Run:
   ```powershell
   java --module-path "<path-to-javafx-sdk>\lib" --add-modules javafx.controls,javafx.fxml -cp out Main
   ```

Only teammates working on GUI screens need the JavaFX SDK installed —
teammates building Class 7/8/9 content can develop and test their
question banks independently before GUI wiring.

## 🧮 Class 10 Topics (planned)

1. Arithmetic Progression — ✅ built
2. Quadratic Equations
3. Similarity
4. Coordinate Geometry
5. Trigonometry
6. Probability

## 👥 Team

| Member | Responsibility |
|---|---|
| Member 1 | Class 7 Mathematics content |
| Member 2 | Class 8 Mathematics content |
| Member 3 | Class 9 Mathematics content |
| Member 4 (me) | Class 10 Mathematics content + shared `common/` engine + JavaFX GUI |