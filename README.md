# EduSnap

EduSnap is a desktop learning app for **Maharashtra State Board (SSC) Mathematics, Class 7 to 10**.
It does not just show content. It checks what a student already knows, teaches what is missing,
tests understanding, and keeps retesting weak concepts until the student gets them.

This is a Semester 3 Java mini-project built by a team of 4. The Mathematics content is organised
**class-wise** (`class7/` to `class10/`) on top of one shared learning engine (`common/`).

## Objective

Help a student understand a topic properly before moving on:

1. Check the pre-requisites.
2. Teach the topic with explanations, formulae and worked examples.
3. Test the student on it.
4. Identify the exact weak concepts and retest only those.

## Target users

Students of Class 7, 8, 9 and 10 following the Maharashtra SSC Mathematics syllabus.

## Learning flow

Every topic in every class follows the same flow:

```
Select Class
    |
Select Topic
    |
Pre-requisite Test (5 random questions)
    |
100%?  --Yes--> Already Mastered --> Report
    |
    No
    |
Lesson (one concept per page, optional game)
    |
Lesson Test (5 random questions)
    |
>= 75%? --Yes--> Completed --> Report
    |
    No
    |
Weak-concept review --> Retest --> (repeats until >= 75%) --> Report
```

Every question is tagged with the concept it tests. A wrong answer therefore points to a specific
concept, and only those concepts are reviewed and retested, not the whole topic.

## Features

- Pre-requisite check that can skip a topic the student already knows (100% = Already Mastered).
- Concept-wise lessons: explanation, formula and worked examples.
- A mini-game for every topic, played from the lesson screen.
- Lesson test with a pass mark of 75% and a weak-concept list.
- Retest on weak concepts only, repeated until the student passes.
- Final report: scores, attempts, weak concepts and status.
- **Balbharati-style lesson screen for Class 7** (book cover, "Let's learn.", "Remember :", Examples,
  Activity, Practice Set). Other classes still use the original lesson screen.

## Technology

| Component | Technology |
|---|---|
| Language | Java (JDK 25 in development) |
| GUI | JavaFX, styled in Java code (no CSS files) |
| Data storage | In memory, per session (no database, no server) |
| IDE | IntelliJ IDEA |
| Version control | Git and GitHub |

EduSnap is a standalone desktop application. There is no backend, no database and no browser extension.

## Why one shared `common/` engine

The pre-requisite test, lesson flow, scoring, weak-concept detection and report are identical for
every class. Only the content (questions, explanations, formulae, games) differs. The flow logic is
written once in `src/common/`. A topic class such as `class10/ArithmeticProgression.java` extends the
abstract `Topic` and supplies only its Mathematics content.

## Project structure

```
SEM-3-Project/
|-- src/
|   |-- Main.java                    JavaFX entry point, starts AppLauncher
|   |-- common/                      Shared learning engine
|   |   |-- Concept.java             A trackable unit of knowledge
|   |   |-- Question.java            A question tagged to a Concept
|   |   |-- Test.java                A set of questions, scoring, shuffling
|   |   |-- TestResult.java          Score % and weak concepts
|   |   |-- TopicStatus.java         ALREADY_MASTERED / IN_PROGRESS / RETEST_REQUIRED / COMPLETED
|   |   |-- LessonContent.java       Explanation, formula and examples for one concept
|   |   |-- StudentReport.java       Scores, attempts, weak concepts, status
|   |   |-- Topic.java               Abstract flow controller every topic extends
|   |   `-- gui/                     JavaFX screens
|   |       |-- AppLauncher.java     Owns the window, moves between screens
|   |       |-- ClassCatalog.java    Registry of classes and their topics
|   |       |-- HomeScreen.java, ClassTopicsScreen.java
|   |       |-- PrerequisiteTestScreen.java, LessonTestScreen.java, RetestScreen.java
|   |       |-- QuestionTestScreen.java   Reusable question-paging screen
|   |       |-- LessonScreen.java    Original lesson screen (Classes 8, 9, 10)
|   |       |-- ReportScreen.java
|   |       |-- Theme.java           Purple theme used by most screens
|   |       |-- UiStyle.java         Older shared styles
|   |       |-- book/                Balbharati-style lesson screen (Class 7)
|   |       |   |-- BookTheme.java   Colours, fonts, sizes
|   |       |   |-- BookParts.java   Page building blocks
|   |       |   `-- BookLessonScreen.java  Cover page, concept pages, footer
|   |       `-- games/ArcadeGame.java    Base class for JavaFX canvas games
|   |-- class7/   Integers, Ratio and Proportion (+ games/)
|   |-- class8/   Factorisation, Linear Equations
|   |-- class9/   Real Numbers, Polynomials (+ games/)
|   `-- class10/  Arithmetic Progression, Similarity, ... (+ games/)
|-- docs/
|   |-- requirements.md
|   `-- architecture.md
`-- README.md
```

## Topics and games available in the app

The topic list on each class screen comes from `ClassCatalog.all()`.

| Class | Topic | Game |
|---|---|---|
| 7 | Integers | Integer Hopper |
| 7 | Ratio and Proportion | Smoothie Rush |
| 8 | Factorisation | Factor Breaker |
| 8 | Linear Equations | Equation Duel |
| 9 | Real Numbers | Upside Down Lights |
| 9 | Polynomials | Borderland Games |
| 10 | Arithmetic Progression | Hill Climb |
| 10 | Similarity | Similarity Defence |

Topic classes that are written but not yet listed in the catalog: Class 7 Algebraic Expressions
(Term Catcher game) and a Fractions and Decimals topic inside `Integers.java` (Fraction Fisher game);
Class 10 Quadratic Equations (Root Radar), Coordinate Geometry (Coordinate Radar), Trigonometry and Probability.
Adding a topic to the app means adding one line to `ClassCatalog`.

## Running the project

JavaFX is not bundled with the JDK, so the JavaFX SDK must be installed separately.

**In IntelliJ (how the team runs it):** add the JavaFX SDK `lib` folder as a library
(*File > Project Structure > Libraries*), add the VM options below to the run configuration, then run `Main`.

```
--module-path "<path-to-javafx-sdk>\lib" --add-modules javafx.controls,javafx.fxml
```

**From the command line (PowerShell):**

```powershell
javac -d out --module-path "<path-to-javafx-sdk>\lib" --add-modules javafx.controls,javafx.fxml (Get-ChildItem -Recurse src -Filter *.java).FullName
java --module-path "<path-to-javafx-sdk>\lib" --add-modules javafx.controls,javafx.fxml -cp out Main
```

**Class 7 cover image:** put `class7_cover.jpg` in `src/common/gui/book/` and rebuild so it is copied
next to the classes. If the file is missing, a plain drawn cover is shown instead.

## Balbharati-style lesson screen (Class 7)

Our teacher asked that the app feel familiar to students because their textbooks are Balbharati books.
For the first demo only the **lesson screen** of **Class 7** has this look. It uses the book colours
(sky blue and pale blue on white), a serif font, a chapter banner, a "Let's learn." tag, a "Remember :"
box for formulae, numbered examples, an "Activity" block that opens the game, a footer with page
number and arrows, and a "Practice Set" button on the last page that opens the existing lesson test.
No lesson content or questions were changed. The code lives in `common/gui/book/` and is switched on in
`AppLauncher.showLesson()` when the selected class is Class 7.

## Current status and known limitations

- Class 7 to 10 each have two topics in the app, each with a lesson, question bank and game.
- Class 8 games open as separate Swing windows and do not match the JavaFX look.
- The test screen shows one multiple-choice question at a time with radio buttons.
- Progress is not saved between sessions.
- The Balbharati look is applied to Class 7 lessons only, pending the teacher's review.

## Team

| Member   | Responsibility                                                       |
|----------|----------------------------------------------------------------------|
| Member 1 | Class 7 Mathematics content                                          |
| Member 2 | Class 8 Mathematics content                                          |
| Member 3 | Class 9 Mathematics content                                          |
| Member 4 | Class 10 Mathematics content| , shared `common/` engine and JavaFX GUI |