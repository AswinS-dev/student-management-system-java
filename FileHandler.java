    
import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String FILE_NAME = "students.txt";

    // Save students to file
    public static void saveStudents(ArrayList<Student> studentList) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Student student : studentList) {

                writer.write(
                        student.getId() + "|" +
                                student.getName() + "|" +
                                student.getMarks());

                writer.newLine();
            }

            System.out.println("Student data saved successfully.");

        } catch (IOException e) {

            System.out.println("Error while saving student data.");
            System.out.println("Reason: " + e.getMessage());
        }
    }

    // Load students from file
    public static ArrayList<Student> loadStudents() {

        ArrayList<Student> studentList = new ArrayList<>();

        File file = new File(FILE_NAME);

        // If file doesn't exist
        if (!file.exists()) {
            return studentList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\\|");

                if (data.length == 3) {

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    int marks = Integer.parseInt(data[2]);

                    Student student = new Student(id, name, marks);

                    studentList.add(student);
                }
            }

        } catch (IOException e) {

            System.out.println("Error while reading student data.");

        } catch (NumberFormatException e) {

            System.out.println("Invalid data found inside file.");
        }

        return studentList;
    }
}
