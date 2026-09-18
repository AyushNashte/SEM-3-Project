package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class Trigonometry extends Topic {

    // Concepts specific to this topic
    private static final Concept TRIG_RATIOS_BASICS = new Concept("TR1", "Basic Trigonometric Ratios (sin, cos, tan)");
    private static final Concept STANDARD_ANGLES = new Concept("TR2", "Trigonometric Ratios of Standard Angles");
    private static final Concept TRIG_IDENTITIES = new Concept("TR3", "Trigonometric Identities");
    private static final Concept RECIPROCAL_RATIOS = new Concept("TR4", "Reciprocal Ratios (cosec, sec, cot)");
    private static final Concept HEIGHTS_AND_DISTANCES = new Concept("TR5", "Heights and Distances");
    private static final Concept RIGHT_TRIANGLE_BASICS = new Concept("TR6", "Right Triangle Basics");
    private static final Concept PYTHAGORAS_BASICS = new Concept("TR7", "Pythagoras Theorem Basics");
    private static final Concept RATIOS_BASICS = new Concept("TR8", "Basic Ratios");
    private static final Concept BASIC_ALGEBRA = new Concept("TR9", "Basic Algebra");

    public Trigonometry() {
        super("Class 10", "Trigonometry");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "In a right triangle, the side opposite the right angle is called the:",
                        Arrays.asList("Leg", "Hypotenuse", "Base", "Adjacent side"),
                        1,
                        RIGHT_TRIANGLE_BASICS
                ),
                new Question(
                        "Which two sides of a right triangle meet to form the right angle?",
                        Arrays.asList("The two legs", "The hypotenuse and a leg", "Any two sides", "None of these"),
                        0,
                        RIGHT_TRIANGLE_BASICS
                ),
                new Question(
                        "A right triangle has legs 6 and 8. What is the hypotenuse?",
                        Arrays.asList("9", "10", "12", "14"),
                        1,
                        PYTHAGORAS_BASICS
                ),
                new Question(
                        "A right triangle has legs 5 and 12. What is the hypotenuse?",
                        Arrays.asList("11", "12", "13", "14"),
                        2,
                        PYTHAGORAS_BASICS
                ),
                new Question(
                        "Simplify the ratio 6:8",
                        Arrays.asList("2:3", "3:4", "4:5", "1:2"),
                        1,
                        RATIOS_BASICS
                ),
                new Question(
                        "Express 3/4 as a decimal",
                        Arrays.asList("0.25", "0.5", "0.75", "0.8"),
                        2,
                        RATIOS_BASICS
                ),
                new Question(
                        "Solve for x: x/5 = 0.6",
                        Arrays.asList("2", "3", "4", "5"),
                        1,
                        BASIC_ALGEBRA
                ),
                new Question(
                        "What is the square root of 169?",
                        Arrays.asList("11", "12", "13", "14"),
                        2,
                        PYTHAGORAS_BASICS
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
                        "In a right triangle, sin θ is defined as:",
                        Arrays.asList("Opposite / Hypotenuse", "Adjacent / Hypotenuse", "Opposite / Adjacent", "Hypotenuse / Opposite"),
                        0,
                        TRIG_RATIOS_BASICS
                ),
                new Question(
                        "In a right triangle, cos θ is defined as:",
                        Arrays.asList("Opposite / Hypotenuse", "Adjacent / Hypotenuse", "Opposite / Adjacent", "Hypotenuse / Adjacent"),
                        1,
                        TRIG_RATIOS_BASICS
                ),
                new Question(
                        "In a right triangle with opposite = 3, adjacent = 4, hypotenuse = 5, what is tan θ?",
                        Arrays.asList("3/5", "4/5", "3/4", "4/3"),
                        2,
                        TRIG_RATIOS_BASICS
                ),
                new Question(
                        "What is sin 30°?",
                        Arrays.asList("0", "1/2", "1/√2", "1"),
                        1,
                        STANDARD_ANGLES
                ),
                new Question(
                        "What is cos 60°?",
                        Arrays.asList("0", "1/2", "√3/2", "1"),
                        1,
                        STANDARD_ANGLES
                ),
                new Question(
                        "What is tan 45°?",
                        Arrays.asList("0", "1/√3", "1", "√3"),
                        2,
                        STANDARD_ANGLES
                ),
                new Question(
                        "Which identity is always true for any angle θ?",
                        Arrays.asList("sin θ + cos θ = 1", "sin²θ + cos²θ = 1", "sin θ × cos θ = 1", "sin²θ - cos²θ = 1"),
                        1,
                        TRIG_IDENTITIES
                ),
                new Question(
                        "If sin θ = 3/5, what is cos θ (using sin²θ + cos²θ = 1, positive value)?",
                        Arrays.asList("3/5", "4/5", "5/4", "5/3"),
                        1,
                        TRIG_IDENTITIES
                ),
                new Question(
                        "Which identity relates tan θ and sec θ?",
                        Arrays.asList("1 + tan²θ = sec²θ", "1 - tan²θ = sec²θ", "tan²θ = sec²θ", "sec²θ + tan²θ = 0"),
                        0,
                        TRIG_IDENTITIES
                ),
                new Question(
                        "What is cosec θ in terms of sin θ?",
                        Arrays.asList("1/sin θ", "1/cos θ", "1/tan θ", "sin θ"),
                        0,
                        RECIPROCAL_RATIOS
                ),
                new Question(
                        "If sin θ = 1/2, what is cosec θ?",
                        Arrays.asList("1/2", "1", "2", "4"),
                        2,
                        RECIPROCAL_RATIOS
                ),
                new Question(
                        "What is cot θ in terms of tan θ?",
                        Arrays.asList("1/tan θ", "tan θ", "1/sin θ", "1/cos θ"),
                        0,
                        RECIPROCAL_RATIOS
                ),
                new Question(
                        "The angle of elevation is measured from:",
                        Arrays.asList("The horizontal line upward to an object", "The vertical line downward", "The ground to the base", "Any two points on a line"),
                        0,
                        HEIGHTS_AND_DISTANCES
                ),
                new Question(
                        "A pole is 10 m away from an observer, and the angle of elevation to its top is 45°. What is the height of the pole? (tan 45° = 1)",
                        Arrays.asList("5 m", "10 m", "15 m", "20 m"),
                        1,
                        HEIGHTS_AND_DISTANCES
                ),
                new Question(
                        "What is the relationship between the angle of elevation and the angle of depression between the same two points?",
                        Arrays.asList("They are equal (alternate angles)", "They always add up to 90°", "They are unrelated", "The depression angle is always larger"),
                        0,
                        HEIGHTS_AND_DISTANCES
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
                        TRIG_RATIOS_BASICS,
                        "Trigonometric ratios relate the angles of a right triangle to the ratios of its sides. "
                                + "The three basic ratios are sine, cosine, and tangent, each comparing two of the triangle's "
                                + "three sides relative to a chosen angle θ.",
                        "sin θ = Opposite/Hypotenuse, cos θ = Adjacent/Hypotenuse, tan θ = Opposite/Adjacent",
                        Arrays.asList(
                                "In a right triangle with opposite=3, adjacent=4, hypotenuse=5: sinθ=3/5, cosθ=4/5, tanθ=3/4"
                        )
                ),
                new LessonContent(
                        STANDARD_ANGLES,
                        "Certain angles (0°, 30°, 45°, 60°, 90°) have fixed, well-known trigonometric ratio "
                                + "values that are used constantly in problems, so they're worth memorizing directly.",
                        "sin: 0, 1/2, 1/√2, √3/2, 1  |  cos: 1, √3/2, 1/√2, 1/2, 0  (for 0°,30°,45°,60°,90°)",
                        Arrays.asList(
                                "sin 30° = 1/2, cos 60° = 1/2, tan 45° = 1"
                        )
                ),
                new LessonContent(
                        TRIG_IDENTITIES,
                        "Trigonometric identities are equations that hold true for every value of θ. The most "
                                + "fundamental one connects sine and cosine directly, and comes from applying Pythagoras' "
                                + "theorem to a right triangle.",
                        "sin²θ + cos²θ = 1   (also: 1 + tan²θ = sec²θ,   1 + cot²θ = cosec²θ)",
                        Arrays.asList(
                                "If sinθ = 3/5, then cos²θ = 1 - 9/25 = 16/25, so cosθ = 4/5"
                        )
                ),
                new LessonContent(
                        RECIPROCAL_RATIOS,
                        "Three more ratios are simply the reciprocals of sine, cosine, and tangent: cosecant, "
                                + "secant, and cotangent.",
                        "cosec θ = 1/sin θ,   sec θ = 1/cos θ,   cot θ = 1/tan θ",
                        Arrays.asList(
                                "If sinθ = 1/2, then cosecθ = 1/(1/2) = 2"
                        )
                ),
                new LessonContent(
                        HEIGHTS_AND_DISTANCES,
                        "Trigonometry is used to find heights and distances that are hard to measure directly — "
                                + "like the height of a tower — using the angle of elevation (looking up) or angle of "
                                + "depression (looking down) along with a known distance.",
                        "Height = Distance × tan(angle of elevation)",
                        Arrays.asList(
                                "If a pole is 10 m away and its angle of elevation is 45°, height = 10 × tan45° = 10 × 1 = 10 m"
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
        if (concept == TRIG_RATIOS_BASICS || concept == STANDARD_ANGLES) {
            return java.util.Optional.of("trig-ratio-explorer");
        }
        return java.util.Optional.empty();
    }
}