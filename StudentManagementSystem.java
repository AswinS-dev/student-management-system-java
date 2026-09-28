import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagementSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Student> studentList = FileHandler.loadStudents();

    public static void main(String[] args) {

        int choice;

        System.out.println();
        System.out.println("======================================");
        System.out.println("       STUDENT MANAGEMENT SYSTEM");
        System.out.println("======================================");

        do {

            System.out.println();
            System.out.println("------------- MENU ----------------");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Show Total Students");
            System.out.println("7. Show Class Average");
            System.out.println("8. Show Topper");
            System.out.println("9. Exit");
            System.out.println("-----------------------------------");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    searchStudent();
                    break;

                case 6:
                    showTotalStudents();
                    break;

                case 7:
                    showAverage();
                    break;

                case 8:
                    showTopper();
                    break;

                case 9:
                    FileHandler.saveStudents(studentList);
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please enter 1-9.");
            }

        } while (choice != 9);

        sc.close();
    }

    // ==============================
    // ADD STUDENT
    // ==============================

    static void addStudent() {
        char choice = 'N';

        do {
            int id = readInt("\nEnter Student ID: ");

            if (studentExists(id)) {
                System.out.println("Student ID already exists. Please try again.");
                choice = 'Y';
                continue;
            }

            String name = readName("Enter Student Name: ");
            int marks = readMarks("Enter Student Marks: ");

            Student student = new Student(id, name, marks);
            studentList.add(student);

            System.out.println("Student added successfully.");
            FileHandler.saveStudents(studentList);

            // Safe Y/N prompt that won't crash on empty input
            System.out.print("Add another student? (Y/N): ");
            String input = sc.nextLine().trim();
            choice = (!input.isEmpty()) ? input.charAt(0) : 'N';

        } while (choice == 'Y' || choice == 'y');
    }

    // ==============================
    // VIEW STUDENTS
    // ==============================

    static void viewStudents() {

        if (studentList.isEmpty()) {

            System.out.println(
                    "\nNo student records available.");

            return;
        }

        System.out.println();
        System.out.println(
                "------------------------------------------------------");

        System.out.printf(
                "%-10s %-20s %-10s %-10s%n",
                "ID",
                "Name",
                "Marks",
                "Grade");

        System.out.println(
                "------------------------------------------------------");

        for (Student student : studentList) {

            System.out.printf(
                    "%-10d %-20s %-10d %-10s%n",
                    student.getId(),
                    student.getName(),
                    student.getMarks(),
                    student.getGrade());
        }

        System.out.println(
                "------------------------------------------------------");
    }

    // ==============================
    // UPDATE STUDENT
    // ==============================

    static void updateStudent() {

        int id = readInt("\nEnter Student ID to update: ");

        Student student = findStudentById(id);

        if (student == null) {

            System.out.println(
                    "Student not found.");

            return;
        }

        System.out.println("\nCurrent Details");

        System.out.println(
                "ID     : " + student.getId());

        System.out.println(
                "Name   : " + student.getName());

        System.out.println(
                "Marks  : " + student.getMarks());

        String newName = readName("Enter New Name: ");

        int newMarks = readMarks("Enter New Marks: ");

        student.setName(newName);
        student.setMarks(newMarks);

        System.out.println(
                "Student updated successfully.");

        FileHandler.saveStudents(studentList);
    }

    // ==============================
    // DELETE STUDENT
    // ==============================

    static void deleteStudent() {

        int id = readInt("\nEnter Student ID to delete: ");

        Student student = findStudentById(id);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Student Name: " + student.getName());

        System.out.print("Are you sure? (Y/N): ");
        String input = sc.nextLine().trim();
        char confirm = (!input.isEmpty()) ? input.charAt(0) : 'N';

        if (confirm == 'Y' || confirm == 'y') {
            studentList.remove(student);
            System.out.println("Student deleted successfully.");
            FileHandler.saveStudents(studentList);
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    // ==============================
    // SEARCH STUDENT
    // ==============================

    static void searchStudent() {

        if (studentList.isEmpty()) {

            System.out.println(
                    "No student records available.");

            return;
        }

        System.out.println();
        System.out.println("Search By:");
        System.out.println("1. ID");
        System.out.println("2. Name");

        int option = readInt("Enter choice: ");

        if (option == 1) {

            int id = readInt("Enter Student ID: ");

            Student student = findStudentById(id);

            if (student != null) {

                displayStudent(student);

            } else {

                System.out.println(
                        "Student not found.");
            }

        } else if (option == 2) {

            String name = readName("Enter Student Name: ");

            boolean found = false;

            for (Student student : studentList) {

                if (student.getName()
                        .equalsIgnoreCase(name)) {

                    displayStudent(student);

                    found = true;
                }
            }

            if (!found) {

                System.out.println(
                        "Student not found.");
            }

        } else {

            System.out.println(
                    "Invalid search option.");
        }
    }

    // ==============================
    // TOTAL STUDENTS
    // ==============================

    static void showTotalStudents() {

        System.out.println(
                "\nTotal Students: " +
                        studentList.size());
    }

    // ==============================
    // CLASS AVERAGE
    // ==============================

    static void showAverage() {

        if (studentList.isEmpty()) {

            System.out.println(
                    "No data available.");

            return;
        }

        int total = 0;

        for (Student student : studentList) {

            total += student.getMarks();
        }

        double average = (double) total /
                studentList.size();

        System.out.printf(
                "\nClass Average: %.2f%n",
                average);
    }

    // ==============================
    // TOPPER
    // ==============================

    static void showTopper() {

        if (studentList.isEmpty()) {

            System.out.println(
                    "No student records available.");

            return;
        }

        Student topper = studentList.get(0);

        for (Student student : studentList) {

            if (student.getMarks() > topper.getMarks()) {

                topper = student;
            }
        }

        System.out.println();
        System.out.println("========== TOPPER ==========");

        System.out.println(
                "ID     : " + topper.getId());

        System.out.println(
                "Name   : " + topper.getName());

        System.out.println(
                "Marks  : " + topper.getMarks());

        System.out.println(
                "Grade  : " + topper.getGrade());
    }

    // ==============================
    // FIND STUDENT BY ID
    // ==============================

    static Student findStudentById(int id) {

        for (Student student : studentList) {

            if (student.getId() == id) {

                return student;
            }
        }

        return null;
    }

    // ==============================
    // CHECK STUDENT EXISTS
    // ==============================

    static boolean studentExists(int id) {

        return findStudentById(id) != null;
    }

    // ==============================
    // DISPLAY STUDENT
    // ==============================

    static void displayStudent(Student student) {

        System.out.println();
        System.out.println("------------------------------");

        System.out.println(
                "ID     : " + student.getId());

        System.out.println(
                "Name   : " + student.getName());

        System.out.println(
                "Marks  : " + student.getMarks());

        System.out.println(
                "Grade  : " + student.getGrade());

        System.out.println("------------------------------");
    }

    // ==============================
    // INPUT VALIDATION
    // ==============================

    static int readInt(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(
                        sc.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number.");
            }
        }
    }

    // ==============================
    // MARKS VALIDATION
    // ==============================

    static int readMarks(String message) {

        while (true) {

            int marks = readInt(message);

            if (marks >= 0 && marks <= 100) {

                return marks;

            } else {

                System.out.println(
                        "Marks must be between 0 and 100.");
            }
        }
    }

    // ==============================
    // NAME VALIDATION
    // ==============================

    static String readName(String message) {

        while (true) {

            System.out.print(message);

            String name = sc.nextLine().trim();

            if (!name.isEmpty()) {

                return name;

            } else {

                System.out.println(
                    "Name cannot be empty.");
            }
        }
    }
}
    