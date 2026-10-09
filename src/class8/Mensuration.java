package class8;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mensuration extends Topic {

    // Lesson concepts
    private static final Concept TRAPEZIUM_RHOMBUS = new Concept("ME1", "Area of a Trapezium and Rhombus");
    private static final Concept CUBOID_SURFACE = new Concept("ME2", "Surface Area of Cube and Cuboid");
    private static final Concept CUBOID_VOLUME = new Concept("ME3", "Volume of Cube and Cuboid");
    private static final Concept CYLINDER = new Concept("ME4", "Surface Area and Volume of a Cylinder");
    private static final Concept VOLUME_CAPACITY = new Concept("ME5", "Volume and Capacity Units");

    // Prerequisite concepts
    private static final Concept AREA_BASICS = new Concept("ME6", "Area of Rectangle and Triangle");
    private static final Concept SQUARES_CUBES = new Concept("ME7", "Squares and Cubes of Numbers");
    private static final Concept UNIT_CONVERSION = new Concept("ME8", "Converting Units of Length");
    private static final Concept CIRCLE_BASICS = new Concept("ME9", "Circumference and Area of a Circle");

    public Mensuration() {
        super("Class 8", "Mensuration");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "What is the area of a rectangle with length 8 cm and breadth 5 cm?",
                        Arrays.asList("13 cm²", "40 cm²", "26 cm²", "80 cm²"),
                        1,
                        AREA_BASICS
                ),
                new Question(
                        "What is the area of a triangle with base 10 cm and height 6 cm?",
                        Arrays.asList("60 cm²", "16 cm²", "30 cm²", "15 cm²"),
                        2,
                        AREA_BASICS
                ),
                new Question(
                        "What is 4 x 4 x 4?",
                        Arrays.asList("12", "16", "48", "64"),
                        3,
                        SQUARES_CUBES
                ),
                new Question(
                        "What is 12 x 12?",
                        Arrays.asList("24", "122", "144", "124"),
                        2,
                        SQUARES_CUBES
                ),
                new Question(
                        "Convert 3 metres into centimetres.",
                        Arrays.asList("30 cm", "300 cm", "3000 cm", "0.03 cm"),
                        1,
                        UNIT_CONVERSION
                ),
                new Question(
                        "Convert 2 kilometres into metres.",
                        Arrays.asList("20 m", "200 m", "2000 m", "20000 m"),
                        2,
                        UNIT_CONVERSION
                ),
                new Question(
                        "Using π = 22/7, what is the circumference of a circle of radius 7 cm?",
                        Arrays.asList("22 cm", "44 cm", "154 cm", "49 cm"),
                        1,
                        CIRCLE_BASICS
                ),
                new Question(
                        "Using π = 22/7, what is the area of a circle of radius 7 cm?",
                        Arrays.asList("44 cm²", "49 cm²", "154 cm²", "308 cm²"),
                        2,
                        CIRCLE_BASICS
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
                        "Which formula gives the area of a trapezium?",
                        Arrays.asList("½ x (sum of parallel sides) x height", "base x height", "½ x base x height", "(a + b) x h"),
                        0,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "Find the area of a trapezium whose parallel sides are 8 cm and 12 cm and whose height is 5 cm.",
                        Arrays.asList("40 cm²", "50 cm²", "100 cm²", "60 cm²"),
                        1,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "Find the area of a rhombus whose diagonals are 10 cm and 8 cm.",
                        Arrays.asList("80 cm²", "40 cm²", "18 cm²", "36 cm²"),
                        1,
                        TRAPEZIUM_RHOMBUS
                ),
                new Question(
                        "What is the total surface area of a cube of side 5 cm?",
                        Arrays.asList("100 cm²", "125 cm²", "150 cm²", "30 cm²"),
                        2,
                        CUBOID_SURFACE
                ),
                new Question(
                        "Find the total surface area of a cuboid with l = 6 cm, b = 4 cm and h = 3 cm.",
                        Arrays.asList("72 cm²", "108 cm²", "54 cm²", "144 cm²"),
                        1,
                        CUBOID_SURFACE
                ),
                new Question(
                        "Which formula gives the lateral surface area of a cuboid?",
                        Arrays.asList("2(lb + bh + hl)", "2h(l + b)", "lbh", "6a²"),
                        1,
                        CUBOID_SURFACE
                ),
                new Question(
                        "What is the volume of a cube of side 4 cm?",
                        Arrays.asList("16 cm³", "48 cm³", "64 cm³", "96 cm³"),
                        2,
                        CUBOID_VOLUME
                ),
                new Question(
                        "Find the volume of a cuboid measuring 10 cm x 5 cm x 3 cm.",
                        Arrays.asList("150 cm³", "30 cm³", "300 cm³", "18 cm³"),
                        0,
                        CUBOID_VOLUME
                ),
                new Question(
                        "A cube has volume 125 cm³. What is the length of its side?",
                        Arrays.asList("25 cm", "5 cm", "15 cm", "12.5 cm"),
                        1,
                        CUBOID_VOLUME
                ),
                new Question(
                        "Find the curved surface area of a cylinder of radius 7 cm and height 10 cm. (π = 22/7)",
                        Arrays.asList("220 cm²", "440 cm²", "154 cm²", "1540 cm²"),
                        1,
                        CYLINDER
                ),
                new Question(
                        "Find the volume of a cylinder of radius 7 cm and height 10 cm. (π = 22/7)",
                        Arrays.asList("440 cm³", "154 cm³", "1540 cm³", "3080 cm³"),
                        2,
                        CYLINDER
                ),
                new Question(
                        "Which formula gives the total surface area of a closed cylinder?",
                        Arrays.asList("2πrh", "πr²h", "2πr(r + h)", "πr²"),
                        2,
                        CYLINDER
                ),
                new Question(
                        "How many cm³ are there in 1 litre?",
                        Arrays.asList("10 cm³", "100 cm³", "1000 cm³", "10000 cm³"),
                        2,
                        VOLUME_CAPACITY
                ),
                new Question(
                        "How many litres can a tank of volume 2 m³ hold?",
                        Arrays.asList("20 L", "200 L", "2000 L", "20000 L"),
                        2,
                        VOLUME_CAPACITY
                ),
                new Question(
                        "A tank measures 50 cm x 40 cm x 30 cm. How many litres of water can it hold?",
                        Arrays.asList("6 L", "60 L", "600 L", "6000 L"),
                        1,
                        VOLUME_CAPACITY
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
                        TRAPEZIUM_RHOMBUS,
                        "A trapezium has one pair of parallel sides. Its area is half the sum of the parallel sides "
                                + "multiplied by the distance between them. A rhombus is found from its two diagonals.",
                        "Trapezium: A = ½ x (a + b) x h     Rhombus: A = ½ x d1 x d2",
                        Arrays.asList(
                                "Trapezium with a = 8, b = 12, h = 5: A = ½ x 20 x 5 = 50 cm²",
                                "Rhombus with diagonals 10 and 8: A = ½ x 10 x 8 = 40 cm²"
                        )
                ),
                new LessonContent(
                        CUBOID_SURFACE,
                        "The total surface area (TSA) is the area of all the faces. The lateral surface area (LSA) "
                                + "counts only the four side faces, leaving out the top and bottom.",
                        "Cube: TSA = 6a²     Cuboid: TSA = 2(lb + bh + hl),  LSA = 2h(l + b)",
                        Arrays.asList(
                                "Cube of side 5 cm: TSA = 6 x 25 = 150 cm²",
                                "Cuboid 6 x 4 x 3: TSA = 2(24 + 12 + 18) = 108 cm²"
                        )
                ),
                new LessonContent(
                        CUBOID_VOLUME,
                        "Volume is the space a solid occupies, measured in cubic units. For a cuboid, multiply "
                                + "length, breadth and height. A cube is a cuboid whose three edges are equal.",
                        "Cuboid: V = l x b x h     Cube: V = a³",
                        Arrays.asList(
                                "Cube of side 4 cm: V = 4³ = 64 cm³",
                                "Cuboid 10 x 5 x 3: V = 150 cm³",
                                "A cube of volume 125 cm³ has side 5 cm, since 5³ = 125"
                        )
                ),
                new LessonContent(
                        CYLINDER,
                        "A cylinder has a curved surface and two circular ends. The curved surface opens out into a "
                                + "rectangle of width 2πr and height h. We use π = 22/7 in these problems.",
                        "CSA = 2πrh     TSA = 2πr(r + h)     V = πr²h",
                        Arrays.asList(
                                "r = 7, h = 10: CSA = 2 x 22/7 x 7 x 10 = 440 cm²",
                                "r = 7, h = 10: V = 22/7 x 7 x 7 x 10 = 1540 cm³"
                        )
                ),
                new LessonContent(
                        VOLUME_CAPACITY,
                        "Volume is the space a solid takes up, and capacity is how much liquid a container can hold. "
                                + "The two are linked by simple conversions.",
                        "1 cm³ = 1 mL     1000 cm³ = 1 L     1 m³ = 1000 L",
                        Arrays.asList(
                                "A tank of 2 m³ holds 2 x 1000 = 2000 L",
                                "A tank 50 x 40 x 30 cm has volume 60000 cm³ = 60 L"
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
}