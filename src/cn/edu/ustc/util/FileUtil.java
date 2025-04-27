package cn.edu.ustc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

/**
 * \author SiriusPaul
 * \version V1.0
 * \CreateDate 2025/3/21
 * \Description
 */
public class FileUtil {
    /**
     * Read an input file with a format like:
     * 17: -32, -10, 33, -23, 32, -12, 41, -12, 1, 3, 5, -98, 70, -21, 10, -9, 61
     * First number stands for the amount of the numbers, followed by comma separated numbers
     * <p>
     * \param filePath
     * \return the int array
     */
    public static int[] readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("File did not exist. Create new file: " + filePath);
                    System.out.println("Please add data to the file and try again.");
                    return new int[0];
                } else {
                    System.out.println("File did not exist. Failed to create new file: " + filePath);
                    return new int[0];
                }
            } catch (IOException e) {
                System.err.println("Failed to create the file: " + filePath);
                return new int[0];
            }
        }

        try {
            return processFile(filePath);
        } catch (IOException e) {
            System.err.println("Failed to read the file: " + e.getMessage());
            return new int[0];
        }
    }

    private static int[] processFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                return new int[0];
            }

            // Split the input
            String[] parts = line.split(":", 2);
            if (parts.length != 2) {
                System.err.println("Wrong format: Missing \":\" in line: " + line);
                return new int[0];
            }

            // Parse the count of numbers
            try {
                int count = Integer.parseInt(parts[0].trim());
                // Split and parse the numbers
                String[] numberStrings = parts[1].split(",");
                int[] nums = new int[numberStrings.length];
                for (int i = 0; i < numberStrings.length; i++) {
                    nums[i] = Integer.parseInt(numberStrings[i].trim());
                }
                return nums;
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + e.getMessage());
                return new int[0];
            }
        }
    }
}