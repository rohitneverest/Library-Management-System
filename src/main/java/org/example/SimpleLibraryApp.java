package org.example;

import java.util.*;

class Book {
    int id;
    String title;
    String author;
    boolean issued = false;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " by " + author + (issued ? " (Issued)" : " (Available)");
    }
}

class Student {
    int id;
    String name;
    List<Book> borrowed = new ArrayList<>();

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name + " (Borrowed: " + borrowed.size() + ")";
    }
}

public class SimpleLibraryApp {
    private static List<Book> books = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1) Add book");
            System.out.println("2) View all books");
            System.out.println("3) Add student");
            System.out.println("4) Issue book");
            System.out.println("5) Return book");
            System.out.println("6) View students");
            System.out.println("7) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": addBook(); break;
                case "2": viewBooks(); break;
                case "3": addStudent(); break;
                case "4": issueBook(); break;
                case "5": returnBook(); break;
                case "6": viewStudents(); break;
                case "7": running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void addBook() {
        System.out.print("Book title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        int id = books.size() + 1;
        books.add(new Book(id, title, author));
        System.out.println("Book added.");
    }

    private static void viewBooks() {
        if (books.isEmpty()) System.out.println("No books yet.");
        else books.forEach(System.out::println);
    }

    private static void addStudent() {
        System.out.print("Student name: ");
        String name = sc.nextLine();
        int id = students.size() + 1;
        students.add(new Student(id, name));
        System.out.println("Student added.");
    }

    private static void viewStudents() {
        if (students.isEmpty()) System.out.println("No students yet.");
        else students.forEach(s -> {
            System.out.println(s);
            if (!s.borrowed.isEmpty()) {
                System.out.println("  Borrowed: " + s.borrowed);
            }
        });
    }

    private static void issueBook() {
        viewBooks();
        System.out.print("Enter book ID to issue: ");
        int bid = Integer.parseInt(sc.nextLine());
        Book book = findBook(bid);
        if (book == null || book.issued) {
            System.out.println("Book not available.");
            return;
        }
        viewStudents();
        System.out.print("Enter student ID: ");
        int sid = Integer.parseInt(sc.nextLine());
        Student student = findStudent(sid);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        book.issued = true;
        student.borrowed.add(book);
        System.out.println("Book issued.");
    }

    private static void returnBook() {
        viewStudents();
        System.out.print("Enter student ID: ");
        int sid = Integer.parseInt(sc.nextLine());
        Student student = findStudent(sid);
        if (student == null || student.borrowed.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }
        for (int i = 0; i < student.borrowed.size(); i++) {
            System.out.println((i+1) + ". " + student.borrowed.get(i));
        }
        System.out.print("Choose book number to return: ");
        int num = Integer.parseInt(sc.nextLine());
        if (num <= 0 || num > student.borrowed.size()) {
            System.out.println("Invalid.");
            return;
        }
        Book book = student.borrowed.remove(num - 1);
        book.issued = false;
        System.out.println("Book returned.");
    }

    private static Book findBook(int id) {
        return books.stream().filter(b -> b.id == id).findFirst().orElse(null);
    }

    private static Student findStudent(int id) {
        return students.stream().filter(s -> s.id == id).findFirst().orElse(null);
    }
}
