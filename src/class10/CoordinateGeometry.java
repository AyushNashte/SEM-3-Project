package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class CoordinateGeometry extends Topic {

    // Concepts specific to this topic
    private static final Concept CARTESIAN_PLANE = new Concept("CG1", "The Cartesian Plane");
    private static final Concept DISTANCE_FORMULA = new Concept("CG2", "Distance Formula");
    private static final Concept MIDPOINT_FORMULA = new Concept("CG3", "Midpoint Formula");
    private static final Concept SECTION_FORMULA = new Concept("CG4", "Section Formula");
    private static final Concept SLOPE_OF_LINE = new Concept("CG5", "Slope of a Line");
    private static final Concept PYTHAGORAS_BASICS = new Concept("CG6", "Pythagoras Theorem Basics");
    private static final Concept SQUARE_ROOTS = new Concept("CG7", "Square Roots");
    private static final Concept PLOTTING_POINTS = new Concept("CG8", "Plotting Points");
    private static final Concept BASIC_ALGEBRA = new Concept("CG9", "Basic Algebra");

    public CoordinateGeometry() {
        super("Class 10", "Coordinate Geometry");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "According to Pythagoras' theorem, in a right triangle, the square of the hypotenuse equals:",
                        Arrays.asList("The sum of the other two sides", "The sum of the squares of the other two sides", "The product of the other two sides", "Twice the longest side"),
                        1,
                        PYTHAGORAS_BASICS
                ),
                new Question(
                        "A right triangle has legs 3 and 4. What is its hypotenuse?",
                        Arrays.asList("5", "6", "7", "12"),
                        0,
                        PYTHAGORAS_BASICS
                ),
                new Question(
                        "What is the square root of 25?",
                        Arrays.asList("4", "5", "6", "10"),
                        1,
                        SQUARE_ROOTS
                ),
                new Question(
                        "Simplify: √(9 + 16)",
                        Arrays.asList("4", "5", "25", "7"),
                        1,
                        SQUARE_ROOTS
                ),
                new Question(
                        "For the point (2, 3), what is the x-coordinate?",
                        Arrays.asList("2", "3", "5", "0"),
                        0,
                        PLOTTING_POINTS
                ),
                new Question(
                        "Which quadrant does the point (-2, 3) lie in?",
                        Arrays.asList("Quadrant I", "Quadrant II", "Quadrant III", "Quadrant IV"),
                        1,
                        PLOTTING_POINTS
                ),
                new Question(
                        "Solve for x: x^2 = 36 (take the positive value)",
                        Arrays.asList("4", "6", "9", "18"),
                        1,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "Simplify: √36 + √16",
                        Arrays.asList("8", "10", "12", "52"),
                        1,
                        BASIC_ALGEBRA
                )
        );
        return new Test(questions);
    }

    @Override
    protected Test getLessonTest() {
        return new Test(getLessonQuestionBank());
    }

    // Full bank kept separate so the retest can filter it by concept
    private List<Question> getLessonQuestionBank() {
        return Arrays.asList(
                new Question(
                        "The point where the x-axis and y-axis intersect is called the:",
                        Arrays.asList("Vertex", "Origin", "Midpoint", "Axis point"),
                        1,
                        CARTESIAN_PLANE
                ),
                new Question(
                        "In which quadrant does the point (-3, -5) lie?",
                        Arrays.asList("Quadrant I", "Quadrant II", "Quadrant III", "Quadrant IV"),
                        2,
                        CARTESIAN_PLANE
                ),
                new Question(
                        "What are the coordinates of the origin?",
                        Arrays.asList("(1, 1)", "(0, 0)", "(1, 0)", "(0, 1)"),
                        1,
                        CARTESIAN_PLANE
                ),
                new Question(
                        "What is the distance formula between points (x1, y1) and (x2, y2)?",
                        Arrays.asList("√((x2-x1) + (y2-y1))", "√((x2-x1)² + (y2-y1)²)", "(x2-x1)² + (y2-y1)²", "(x2-x1) x (y2-y1)"),
                        1,
                        DISTANCE_FORMULA
                ),
                new Question(
                        "What is the distance between (0, 0) and (6, 8)?",
                        Arrays.asList("8", "10", "12", "14"),
                        1,
                        DISTANCE_FORMULA
                ),
                new Question(
                        "What is the distance between (2, 3) and (5, 7)?",
                        Arrays.asList("4", "5", "6", "7"),
                        1,
                        DISTANCE_FORMULA
                ),
                new Question(
                        "What is the midpoint formula for points (x1, y1) and (x2, y2)?",
                        Arrays.asList("(x1+x2, y1+y2)", "((x1+x2)/2, (y1+y2)/2)", "((x2-x1)/2, (y2-y1)/2)", "(x1 x x2, y1 x y2)"),
                        1,
                        MIDPOINT_FORMULA
                ),
                new Question(
                        "What is the midpoint of (0, 0) and (8, 6)?",
                        Arrays.asList("(4, 3)", "(8, 6)", "(4, 6)", "(2, 3)"),
                        0,
                        MIDPOINT_FORMULA
                ),
                new Question(
                        "What is the midpoint of (2, 3) and (4, 7)?",
                        Arrays.asList("(3, 4)", "(3, 5)", "(6, 10)", "(2, 4)"),
                        1,
                        MIDPOINT_FORMULA
                ),
                new Question(
                        "The section formula is used to find:",
                        Arrays.asList("The distance between two points", "The coordinates of a point dividing a line segment in a given ratio", "The slope of a line", "The area of a triangle"),
                        1,
                        SECTION_FORMULA
                ),
                new Question(
                        "A point divides the segment joining (1, 2) and (7, 8) in the ratio 1:2. What are its coordinates?",
                        Arrays.asList("(3, 4)", "(4, 5)", "(5, 6)", "(2, 3)"),
                        0,
                        SECTION_FORMULA
                ),
                new Question(
                        "If a point divides a segment in the ratio 1:1, the section formula becomes the:",
                        Arrays.asList("Distance formula", "Midpoint formula", "Slope formula", "Area formula"),
                        1,
                        SECTION_FORMULA
                ),
                new Question(
                        "What is the formula for the slope of a line through (x1, y1) and (x2, y2)?",
                        Arrays.asList("(y2-y1) / (x2-x1)", "(x2-x1) / (y2-y1)", "(y2+y1) / (x2+x1)", "(x2-x1) x (y2-y1)"),
                        0,
                        SLOPE_OF_LINE
                ),
                new Question(
                        "What is the slope of the line through (2, 3) and (4, 7)?",
                        Arrays.asList("1", "2", "3", "4"),
                        1,
                        SLOPE_OF_LINE
                ),
                new Question(
                        "If the slope of a line is 0, the line is:",
                        Arrays.asList("Vertical", "Horizontal", "Diagonal", "Undefined"),
                        1,
                        SLOPE_OF_LINE
                )
        );
    }

    @Override
    protected Test getRetest(List<Concept> weakConcepts) {
        List<Question> filtered = getLessonQuestionBank().stream()
                .filter(q -> weakConcepts.contains(q.getConcept()))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            return new Test(getLessonQuestionBank());
        }
        return new Test(filtered);
    }

    @Override
    protected void teachLesson() {
        getLessonContentBank().forEach(LessonContent::display);
    }

    @Override
    protected List<LessonContent> getLessonContentBank() {
        return Arrays.asList(
                new LessonContent(
                        CARTESIAN_PLANE,
                        "The Cartesian plane is formed by two perpendicular number lines: the horizontal x-axis "
                                + "and the vertical y-axis. They meet at a point called the origin, and together divide the "
                                + "plane into four regions called quadrants.",
                        null,
                        Arrays.asList(
                                "The point (3, -2) lies in Quadrant IV, since x is positive and y is negative",
                                "The origin has coordinates (0, 0)"
                        )
                ),
                new LessonContent(
                        DISTANCE_FORMULA,
                        "The distance formula finds the straight-line distance between any two points on the "
                                + "plane. It comes directly from the Pythagoras theorem, treating the horizontal and vertical "
                                + "gaps between the points as the two legs of a right triangle.",
                        "Distance = √((x2 - x1)² + (y2 - y1)²)",
                        Arrays.asList(
                                "Distance between (0,0) and (6,8): √(6² + 8²) = √(36+64) = √100 = 10",
                                "Distance between (2,3) and (5,7): √(3² + 4²) = √(9+16) = √25 = 5"
                        )
                ),
                new LessonContent(
                        MIDPOINT_FORMULA,
                        "The midpoint formula finds the exact center point of a line segment joining two points "
                                + "— simply the average of the x-coordinates and the average of the y-coordinates.",
                        "Midpoint = ((x1 + x2)/2, (y1 + y2)/2)",
                        Arrays.asList(
                                "Midpoint of (2,4) and (6,8): ((2+6)/2, (4+8)/2) = (4, 6)"
                        )
                ),
                new LessonContent(
                        SECTION_FORMULA,
                        "The section formula finds the coordinates of a point that divides a line segment "
                                + "internally in a given ratio m:n. When m:n = 1:1, it reduces exactly to the midpoint formula.",
                        "Point = ((m·x2 + n·x1)/(m+n), (m·y2 + n·y1)/(m+n))",
                        Arrays.asList(
                                "Point dividing (1,2) and (7,8) in ratio 1:2: x=(1×7+2×1)/3=3, y=(1×8+2×2)/3=4 -> (3,4)"
                        )
                ),
                new LessonContent(
                        SLOPE_OF_LINE,
                        "The slope of a line measures how steep it is — how much y changes for a given change "
                                + "in x, between any two points on the line.",
                        "Slope (m) = (y2 - y1) / (x2 - x1)",
                        Arrays.asList(
                                "Slope through (1,2) and (3,6): (6-2)/(3-1) = 4/2 = 2",
                                "Slope through (0,0) and (4,4): (4-0)/(4-0) = 1"
                        )
                )
        );
    }

    @Override
    protected void reviewConcepts(List<Concept> weakConcepts) {
        System.out.println("Reviewing weak concepts:");
        for (Concept c : weakConcepts) {
            System.out.println(" - " + c.getName());
        }
    }

    @Override
    public java.util.Optional<String> getVisualComponentId(Concept concept) {
        if (concept == DISTANCE_FORMULA || concept == MIDPOINT_FORMULA) {
            return java.util.Optional.of("coordinate-geometry-explorer");
        }
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<String> getGameId() {
        return java.util.Optional.of("coordinate-radar");
    }
}