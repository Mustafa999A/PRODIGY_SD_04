import java.util.Arrays;
import java.util.Scanner;

public class SudokuSolver {
    public static void printSudoku(int[][] sudoku) {
        for (int[] row : sudoku) {
            System.out.println(Arrays.toString(row).replaceAll("[\\[\\],]", ""));
        }
    }

    public static boolean isValidMove(int[][] sudoku, int row, int col, int num, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        // Check if the number is not in the current row or column
        if (rows[row][num - 1] || cols[col][num - 1]) {
            return false;
        }

        // Check if the number is not in the current 3x3 grid
        int boxIndex = (row / 3) * 3 + col / 3;
        if (boxes[boxIndex][num - 1]) {
            return false;
        }

        return true;
    }

    public static boolean solveSudoku(int[][] sudoku, boolean[][] rows, boolean[][] cols, boolean[][] boxes) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (sudoku[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidMove(sudoku, row, col, num, rows, cols, boxes)) {
                            sudoku[row][col] = num;
                            rows[row][num - 1] = true;
                            cols[col][num - 1] = true;
                            int boxIndex = (row / 3) * 3 + col / 3;
                            boxes[boxIndex][num - 1] = true;

                            if (solveSudoku(sudoku, rows, cols, boxes)) {
                                return true;
                            }

                            sudoku[row][col] = 0; // Backtrack
                            rows[row][num - 1] = false;
                            cols[col][num - 1] = false;
                            boxes[boxIndex][num - 1] = false;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[][] sudokuGrid = new int[9][9];
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] boxes = new boolean[9][10];

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the elements of the 9x9 matrix:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int num = scanner.nextInt();
                if (num < 0 || num > 9) {
                    System.out.println("Invalid input. Please enter a number between 0 and 9.");
                    return;
                }
                sudokuGrid[i][j] = num;
                if (num!= 0) {
                    rows[i][num - 1] = true;
                    cols[j][num - 1] = true;
                    int boxIndex = (i / 3) * 3 + j / 3;
                    boxes[boxIndex][num - 1] = true;
                }
            }
        }

        if (solveSudoku(sudokuGrid, rows, cols, boxes)) {
            System.out.println("Solved Sudoku:");
            printSudoku(sudokuGrid);
        } else {
            System.out.println("No solution exists for Sudoku.");
        }
    }
}
