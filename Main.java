package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/oris_db";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        Statement statement = connection.createStatement();
        Scanner scanner = new Scanner(System.in);

        // Вставка 6 учителей
        for (int i = 0; i < 6; i++) {
            System.out.println("Введите данные для учителя " + (i + 1) + ":");
            System.out.print("Имя: ");
            String firstName = scanner.nextLine();
            System.out.print("Фамилия: ");
            String lastName = scanner.nextLine();
            System.out.print("Возраст: ");
            String age = scanner.nextLine();

            String sqlInsertTeacher = "INSERT INTO teachers(name, surname, age) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertTeacher);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, age);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Было добавлено " + affectedRows + " строк");
        }

        // Вывод учителей старше 30 лет
        String sqlSelectTeachers = "SELECT * FROM teachers WHERE age::integer > 30";
        PreparedStatement selectStatement = connection.prepareStatement(sqlSelectTeachers);
        ResultSet result = selectStatement.executeQuery();

        System.out.println("Учителя старше 30 лет:");
        while (result.next()) {
            System.out.println(
                    result.getInt("id") + " " +
                            result.getString("name") + " " +
                            result.getString("surname") + " " +
                            result.getInt("age")
            );
        }

        // Закрытие ресурсов
        result.close();
        statement.close();
        connection.close();
        scanner.close();
    }
}
