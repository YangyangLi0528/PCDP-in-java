package edu.coursera.parallel;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * A simple wrapper class for various analytics methods.
 */
public final class StudentAnalytics {
    /**
     * Sequentially computes the average age of all actively enrolled students
     * using loops.
     *
     * @param studentArray Student data for the class.
     * @return Average age of enrolled students
     */
    public double averageAgeOfEnrolledStudentsImperative(
            final Student[] studentArray) {
        List<Student> activeStudents = new ArrayList<Student>();

        for (Student s : studentArray) {
            if (s.checkIsCurrent()) {
                activeStudents.add(s);
            }
        }

        double ageSum = 0.0;
        for (Student s : activeStudents) {
            ageSum += s.getAge();
        }

        return ageSum / (double) activeStudents.size();
    }

    /**
     * compute the average age of all actively enrolled students using
     * parallel streams. This should mirror the functionality of
     * averageAgeOfEnrolledStudentsImperative. This method should not use any
     * loops.
     *
     * @param studentArray Student data for the class.
     * @return Average age of enrolled students
     */
    public double averageAgeOfEnrolledStudentsParallelStream(
            final Student[] studentArray) {

        double ageSum = Arrays.stream(studentArray).parallel().filter(Student::checkIsCurrent)
                .mapToDouble(Student::getAge).sum();
        double size = Arrays.stream(studentArray).parallel().filter(Student::checkIsCurrent)
                .count();

//        List<Student> studentss = new ArrayList<>(Arrays.asList(studentArray));
        Stream students = Arrays.stream(studentArray);
//        double ageSum = studentss.parallelStream()
//                .filter(Student::checkIsCurrent)
//                .mapToDouble(Student::getAge).sum();
//
//        double size = studentss.parallelStream()
//                .filter(Student::checkIsCurrent).count();
        return ageSum/size;
    }

    /**
     * Sequentially computes the most common first name out of all students that
     * are no longer active in the class using loops.
     *
     * @param studentArray Student data for the class.
     * @return Most common first name of inactive students
     */
    public String mostCommonFirstNameOfInactiveStudentsImperative(
            final Student[] studentArray) {
        List<Student> inactiveStudents = new ArrayList<Student>();

        for (Student s : studentArray) {
            if (!s.checkIsCurrent()) {
                inactiveStudents.add(s);
            }
        }

        Map<String, Integer> nameCounts = new HashMap<String, Integer>();

        for (Student s : inactiveStudents) {
            if (nameCounts.containsKey(s.getFirstName())) {
                nameCounts.put(s.getFirstName(),
                        new Integer(nameCounts.get(s.getFirstName()) + 1));
            } else {
                nameCounts.put(s.getFirstName(), 1);
            }
        }

        String mostCommon = null;
        int mostCommonCount = -1;
        for (Map.Entry<String, Integer> entry : nameCounts.entrySet()) {
            if (mostCommon == null || entry.getValue() > mostCommonCount) {
                mostCommon = entry.getKey();
                mostCommonCount = entry.getValue();
            }
        }

        return mostCommon;
    }

    /**
     * TODO compute the most common first name out of all students that are no
     * longer active in the class using parallel streams. This should mirror the
     * functionality of mostCommonFirstNameOfInactiveStudentsImperative. This
     * method should not use any loops.
     *
     * @param studentArray Student data for the class.
     * @return Most common first name of inactive students
     */
    public String mostCommonFirstNameOfInactiveStudentsParallelStream(
            final Student[] studentArray) {
//        Map<String,Long> maps = Arrays.stream(studentArray).parallel()
//                .filter((stu)-> !stu.checkIsCurrent())
//                .collect(groupingBy(Student::getFirstName,counting()));

//        String result = maps.entrySet().stream()
////                .sorted((p1,p2) -> Long.compare(p1.getValue(),p2.getValue())).limit(0)
//                .max((p1,p2)-> (int) (p1.getValue()-p2.getValue()))
//                .map(map -> map.getKey())
//                .get();
////                .collect(joining());


                String resultss = Arrays.stream(studentArray).parallel()
                .filter(student -> !student.checkIsCurrent())
                .collect(groupingBy(Student::getFirstName,counting()))
                .entrySet()
                .stream()
//                .max((entry1,entry2)->(int)(entry1.getValue() - entry2.getValue()))
                .max((p1,p2) -> Long.compare(p1.getValue(),p2.getValue()))
                .get()
                .getKey();
                return resultss;
    }

    /**
     * Sequentially computes the number of students who have failed the course
     * who are also older than 20 years old. A failing grade is anything below a
     * 65. A student has only failed the course if they have a failing grade and
     * they are not currently active.
     *
     * @param studentArray Student data for the class.
     * @return Number of failed grades from students older than 20 years old.
     */
    public int countNumberOfFailedStudentsOlderThan20Imperative(
            final Student[] studentArray) {
        int count = 0;
        for (Student s : studentArray) {
            if (!s.checkIsCurrent() && s.getAge() > 20 && s.getGrade() < 65) {
                count++;
            }
        }
        return count;
    }

    /**
     * compute the number of students who have failed the course who are
     * also older than 20 years old. A failing grade is anything below a 65. A
     * student has only failed the course if they have a failing grade and they
     * are not currently active. This should mirror the functionality of
     * countNumberOfFailedStudentsOlderThan20Imperative. This method should not
     * use any loops.
     *
     * @param studentArray Student data for the class.
     * @return Number of failed grades from students older than 20 years old.
     */
    public int countNumberOfFailedStudentsOlderThan20ParallelStream(
            final Student[] studentArray) {

        return Arrays.stream(studentArray).parallel().filter(
                (stu)-> !stu.checkIsCurrent()
                        && stu.getAge()>20
                        && stu.getGrade()<65)
                        .mapToInt((stu)-> 1)
                        .sum();

    }
}
