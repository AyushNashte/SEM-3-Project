package class10;

import common.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import common.LessonContent;

public class Similarity extends Topic {

    // Concepts specific to this topic
    private static final Concept WHAT_IS_SIMILARITY = new Concept("SM1", "What is Similarity");
    private static final Concept AA_CRITERION = new Concept("SM2", "AA (Angle-Angle) Similarity Criterion");
    private static final Concept SSS_CRITERION = new Concept("SM3", "SSS Similarity Criterion");
    private static final Concept SAS_CRITERION = new Concept("SM4", "SAS Similarity Criterion");
    private static final Concept BASIC_PROPORTIONALITY_THEOREM = new Concept("SM5", "Basic Proportionality Theorem (Thales)");
    private static final Concept RATIO_OF_SIDES = new Concept("SM6", "Ratio of Corresponding Sides");
    private static final Concept RATIO_OF_AREAS = new Concept("SM7", "Ratio of Areas of Similar Triangles");
    private static final Concept BASIC_RATIOS = new Concept("SM8", "Basic Ratios");
    private static final Concept BASIC_PROPORTIONS = new Concept("SM9", "Basic Proportions");
    private static final Concept ANGLE_SUM_PROPERTY = new Concept("SM10", "Angle Sum Property of a Triangle");

    public Similarity() {
        super("Class 10", "Similarity");
    }

    @Override
    protected Test getPrerequisiteTest() {
        List<Question> questions = Arrays.asList(
                new Question(
                        "Simplify the ratio 8:12",
                        Arrays.asList("2:3", "4:6", "3:4", "1:2"),
                        0,
                        BASIC_RATIOS
                ),
                new Question(
                        "What is 15:20 in its simplest form?",
                        Arrays.asList("3:4", "4:5", "2:3", "5:6"),
                        0,
                        BASIC_RATIOS
                ),
                new Question(
                        "Which ratio is equivalent to 6:9?",
                        Arrays.asList("2:3", "3:4", "4:5", "5:6"),
                        0,
                        BASIC_RATIOS
                ),
                new Question(
                        "If a:b = 3:4 and b = 8, what is a?",
                        Arrays.asList("5", "6", "7", "8"),
                        1,
                        BASIC_PROPORTIONS
                ),
                new Question(
                        "Solve for x: x/4 = 3/2",
                        Arrays.asList("4", "5", "6", "8"),
                        2,
                        BASIC_PROPORTIONS
                ),
                new Question(
                        "If two ratios a/b and c/d are equal, they are said to be:",
                        Arrays.asList("Equal", "Proportional", "Congruent", "Similar"),
                        1,
                        BASIC_PROPORTIONS
                ),
                new Question(
                        "What is the sum of the angles in a triangle?",
                        Arrays.asList("90°", "180°", "270°", "360°"),
                        1,
                        ANGLE_SUM_PROPERTY
                ),
                new Question(
                        "In a triangle, two angles are 50° and 60°. What is the third angle?",
                        Arrays.asList("60°", "65°", "70°", "75°"),
                        2,
                        ANGLE_SUM_PROPERTY
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
                        "Two figures are similar if they have:",
                        Arrays.asList("The same size and shape", "The same shape but not necessarily the same size", "The same size but not necessarily the same shape", "No relationship at all"),
                        1,
                        WHAT_IS_SIMILARITY
                ),
                new Question(
                        "Which of these pairs of figures are always similar, regardless of size?",
                        Arrays.asList("Two squares", "Two rectangles", "Two triangles", "Two parallelograms"),
                        0,
                        WHAT_IS_SIMILARITY
                ),
                new Question(
                        "The symbol '~' is used to denote:",
                        Arrays.asList("Congruence", "Similarity", "Equality", "Perpendicularity"),
                        1,
                        WHAT_IS_SIMILARITY
                ),
                new Question(
                        "According to the AA similarity criterion, two triangles are similar if:",
                        Arrays.asList("All three sides are proportional", "Two pairs of corresponding angles are equal", "One pair of sides and one angle are equal", "The triangles have the same perimeter"),
                        1,
                        AA_CRITERION
                ),
                new Question(
                        "In triangles ABC and DEF, angle A = angle D and angle B = angle E. What can we conclude?",
                        Arrays.asList("The triangles are congruent", "The triangles are similar (by AA)", "The triangles are not related", "Angle C must differ from angle F"),
                        1,
                        AA_CRITERION
                ),
                new Question(
                        "Why does the AA criterion only require 2 equal angles, not 3?",
                        Arrays.asList("Because the third angle must also be equal, since a triangle's angles sum to 180°", "Because the third angle doesn't matter", "Because triangles never have 3 equal angles", "Because AA only works for right triangles"),
                        0,
                        AA_CRITERION
                ),
                new Question(
                        "According to the SSS similarity criterion, two triangles are similar if:",
                        Arrays.asList("All three pairs of corresponding sides are in the same ratio", "All three angles are equal", "Two sides are equal", "The triangles have equal area"),
                        0,
                        SSS_CRITERION
                ),
                new Question(
                        "Triangle ABC has sides 3, 4, 5. Triangle DEF has sides 6, 8, 10. Are they similar by SSS?",
                        Arrays.asList("Yes, since each pair of sides is in the ratio 1:2", "No, since the sides are different lengths", "Cannot be determined", "Only if the angles are also given"),
                        0,
                        SSS_CRITERION
                ),
                new Question(
                        "Which criterion confirms similarity using only the three sides, with no angle information?",
                        Arrays.asList("AA", "SAS", "SSS", "ASA"),
                        2,
                        SSS_CRITERION
                ),
                new Question(
                        "According to the SAS similarity criterion, two triangles are similar if:",
                        Arrays.asList("Two pairs of sides are proportional and the included angle between them is equal", "All three sides are equal", "Any one angle is equal", "The triangles have the same height"),
                        0,
                        SAS_CRITERION
                ),
                new Question(
                        "In triangles PQR and XYZ, PQ/XY = PR/XZ and angle P = angle X. By which criterion are they similar?",
                        Arrays.asList("AA", "SSS", "SAS", "None of these"),
                        2,
                        SAS_CRITERION
                ),
                new Question(
                        "Why must the equal angle in SAS similarity be the INCLUDED angle, between the two known sides?",
                        Arrays.asList("It doesn't need to be included", "If it isn't included, the triangles may not actually be similar", "Included angles are always 90°", "It only matters for congruence, not similarity"),
                        1,
                        SAS_CRITERION
                ),
                new Question(
                        "The Basic Proportionality Theorem states that a line parallel to one side of a triangle, intersecting the other two sides:",
                        Arrays.asList("Divides the other two sides in the same ratio", "Divides the triangle into two equal areas", "Creates two congruent triangles", "Has no effect on the triangle"),
                        0,
                        BASIC_PROPORTIONALITY_THEOREM
                ),
                new Question(
                        "In triangle ABC, DE is parallel to BC, with D on AB and E on AC. What can we conclude?",
                        Arrays.asList("AD/DB = AE/EC", "AD = AE", "DB = EC", "AD x DB = AE x EC"),
                        0,
                        BASIC_PROPORTIONALITY_THEOREM
                ),
                new Question(
                        "The Basic Proportionality Theorem is also known as:",
                        Arrays.asList("Pythagoras Theorem", "Thales Theorem", "Euclid's Theorem", "Remainder Theorem"),
                        1,
                        BASIC_PROPORTIONALITY_THEOREM
                ),
                new Question(
                        "Two similar triangles have a scale factor of 3. If a side of the smaller triangle is 4 cm, what is the corresponding side of the larger one?",
                        Arrays.asList("7 cm", "8 cm", "12 cm", "16 cm"),
                        2,
                        RATIO_OF_SIDES
                ),
                new Question(
                        "Two similar triangles have corresponding sides in ratio 2:5. If a side in the first is 10 cm, what is the corresponding side in the second?",
                        Arrays.asList("20 cm", "25 cm", "4 cm", "50 cm"),
                        1,
                        RATIO_OF_SIDES
                ),
                new Question(
                        "In similar triangles, corresponding sides are always:",
                        Arrays.asList("Equal", "In the same ratio (proportional)", "Perpendicular", "Parallel"),
                        1,
                        RATIO_OF_SIDES
                ),
                new Question(
                        "If two similar triangles have corresponding sides in ratio 2:3, what is the ratio of their areas?",
                        Arrays.asList("2:3", "4:9", "6:9", "8:27"),
                        1,
                        RATIO_OF_AREAS
                ),
                new Question(
                        "Two similar triangles have a side ratio of 1:4. What is the ratio of their areas?",
                        Arrays.asList("1:4", "1:8", "1:16", "1:2"),
                        2,
                        RATIO_OF_AREAS
                ),
                new Question(
                        "The ratio of areas of two similar triangles is 9:25. What is the ratio of their corresponding sides?",
                        Arrays.asList("3:5", "9:25", "81:625", "3:25"),
                        0,
                        RATIO_OF_AREAS
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
                        WHAT_IS_SIMILARITY,
                        "Two figures are similar if they have exactly the same shape, even if they are "
                                + "different sizes. Their corresponding angles are equal, and their corresponding sides "
                                + "are in the same ratio.",
                        null,
                        Arrays.asList(
                                "Any two squares are similar, no matter their size, since all angles are 90° and sides are proportional",
                                "A small triangle and a larger triangle can be similar if they have the same shape"
                        )
                ),
                new LessonContent(
                        AA_CRITERION,
                        "The AA (Angle-Angle) criterion says two triangles are similar if two pairs of "
                                + "corresponding angles are equal. The third pair is automatically equal too, since "
                                + "angles in a triangle always sum to 180°.",
                        "If ∠A = ∠D and ∠B = ∠E, then △ABC ~ △DEF",
                        Arrays.asList(
                                "If two triangles both have a 50° angle and a 70° angle, they are similar by AA"
                        )
                ),
                new LessonContent(
                        SSS_CRITERION,
                        "The SSS (Side-Side-Side) criterion says two triangles are similar if all three pairs "
                                + "of corresponding sides are in the same ratio.",
                        "If AB/DE = BC/EF = CA/FD, then △ABC ~ △DEF",
                        Arrays.asList(
                                "Triangle with sides 3, 4, 5 and triangle with sides 6, 8, 10 are similar, since every side doubles"
                        )
                ),
                new LessonContent(
                        SAS_CRITERION,
                        "The SAS (Side-Angle-Side) criterion says two triangles are similar if two pairs of "
                                + "sides are in the same ratio, AND the angle between those two sides (the included angle) is equal.",
                        "If PQ/XY = PR/XZ and ∠P = ∠X, then △PQR ~ △XYZ",
                        Arrays.asList(
                                "If two sides are in ratio 2:1 and the angle between them is equal in both triangles, they are similar by SAS"
                        )
                ),
                new LessonContent(
                        BASIC_PROPORTIONALITY_THEOREM,
                        "The Basic Proportionality Theorem (also called Thales' Theorem) states that if a "
                                + "line is drawn parallel to one side of a triangle, cutting the other two sides, it divides "
                                + "those two sides in the same ratio.",
                        "If DE ∥ BC, then AD/DB = AE/EC",
                        Arrays.asList(
                                "If DE is parallel to BC and AD = 4, DB = 2, then AE/EC must also equal 4/2 = 2"
                        )
                ),
                new LessonContent(
                        RATIO_OF_SIDES,
                        "In similar triangles, every pair of corresponding sides shares the same ratio, "
                                + "called the scale factor.",
                        "Scale factor = (side of one triangle) / (corresponding side of the other)",
                        Arrays.asList(
                                "If the scale factor is 3, a 4 cm side becomes a 12 cm side in the larger similar triangle"
                        )
                ),
                new LessonContent(
                        RATIO_OF_AREAS,
                        "The ratio of the areas of two similar triangles equals the square of the ratio of "
                                + "their corresponding sides — not the same ratio as the sides themselves.",
                        "Ratio of Areas = (Ratio of Sides)²",
                        Arrays.asList(
                                "If the side ratio is 2:3, the area ratio is 2² : 3² = 4:9",
                                "If the area ratio is 9:25, the side ratio is √9 : √25 = 3:5"
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
        if (concept == RATIO_OF_SIDES || concept == RATIO_OF_AREAS) {
            return java.util.Optional.of("similarity-scale-explorer");
        }
        return java.util.Optional.empty();
    }
}