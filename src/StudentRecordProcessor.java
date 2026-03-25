import java.io.*;
import java.util.*;


public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();

    // _____реализуйте класс Student ниже в этом же файле______

    private double averageScore;
    private Student highestStudent;


    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile(){
        // TODO: реализуйте чтение файла здесь
        try(BufferedReader br = new BufferedReader(new FileReader("input/students.txt"))){
            String line;
            while((line=br.readLine())!=null){
                String[] data=line.split(",");

                if(data.length!=2){
                    throw new InvalidScoreException("Invalid line format");
                }

                double score = Double.parseDouble(data[1]);

                if(score<0 || score>100){
                    throw new InvalidScoreException("Invalid score format");
                }
                students.add(new Student(data[0],score));
            }

            System.out.println("Students added");

        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }catch(InvalidScoreException ise){
            System.out.println(ise.getMessage());
        }catch(IOException ioe){
            System.out.println("Input or Output exception");
        }catch(Exception e){
            System.out.println("Error with file reading");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        // TODO: обработка данных и сортировка здесь
        try {
            if(students.isEmpty()){
                throw new Exception("Student are empty");
            }
            double total = 0;
            Student maxStudent = students.getFirst();

            for (Student s : students) {
                total += s.score;
                if (s.score > maxStudent.score) {
                    maxStudent = s;
                }
            }

            averageScore=total/ students.size();
            highestStudent=maxStudent;

            System.out.println("Average Score : " + averageScore);
            System.out.println("Highest Student : " + highestStudent);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        // TODO: запись результата в файл здесь
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("output/report.txt"))){

            bw.write("/// Student Report ///\n");
            bw.write("Average Score : " + averageScore + "\n");
            bw.write("Highest Student : " + highestStudent + "\n\n");

            bw.write("Sorted Students :\n");

            for(Student s : students){
                bw.write(s.toString());
                bw.newLine();
            }

            System.out.println("Wrote to file");

        }catch(FileNotFoundException fnfe){
            System.out.println("file not found");
        }catch (IOException ioe){
            System.out.println("Input or Output Exception");
        }catch (Exception e){
            System.out.println("Some error");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

class InvalidScoreException extends Exception {
    public InvalidScoreException (String msg){
        super(msg);
    }
}

class Student implements Comparable<Student>{
    String name;
    double score;

    Student(String name , double score){
        this.name= name;
        this.score=score;
    }

    @Override
    public String toString() {
        return this.name
                +" | Score : "+this.score;
    }

    @Override
    public int compareTo(Student other) {
        int result = Double.compare(other.score, this.score);
        if(result==0){
            return this.name.compareTo(other.name);
        }
        return result;
    }
}