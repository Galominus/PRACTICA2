import java.util.Scanner;
import java.util.Random;
public class Practica2 {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        String opcion;
        int[][] tablero = new int[8][8];

        do {
            System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S )  ");
            System.out.println();
            mostrarTableroConCorchete(tablero);

            System.out.println("\nNivel de juego ( L ) :" + "\t\tNuevo nivel:");
            System.out.println("\nGolpes:");
            System.out.println("Intrucciones:");
            System.out.println("\t Mueva el cursor a un botón del tablero (con las flechas).");
            System.out.println("\t Pulse \"return\"");
            System.out.println("\t para decrementar el valor de ese botón en 1,");
            System.out.println("\t y también los valores de sus 4 vecinos.");
            System.out.println("Objetivo:");
            System.out.println("\t Dejar todos los botones en '0'.");

            opcion = sc.nextLine();
            opcion = opcion.toUpperCase();

            switch (opcion) {
                case "\\u001B[A" -> {  // Flecha hacia arriba
                    System.out.println("Flecha hacia arriba detectada.");
                }
                case "\\u001B[B" -> {  // Flecha hacia abajo
                    System.out.println("Flecha hacia abajo detectada.");
                }
                case "\\u001B[D" -> { // Flecha hacia izquierda
                    System.out.println("Flecha hacia izquierda detectada.");
                }
                case "\\u001B[C" -> { // Flecha hacia derecha
                    System.out.println("Flecha hacia derecha detectada.");
                }
                case "N" -> { //Tablero nuevo

                }
                case "R" -> { //Recomenzar el tablero existente

                }
                case "U" -> { // Deshacer

                }
                case "L" -> { // Seleccionar nivel de dificultad

                }
                case "S" -> { // Salir del programa
                    System.out.println("Gracias por jugar\nFIN DEL PROGRAMA");

                }
                default -> { // Opción no válida

                }

            }


        } while (!opcion.equals("S"));
    }

    public static void mostrarTableroConCorchete(int[][] tablero) {
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {
                tablero[i][j] = 0;
                if (i == 1 && j == 1) {
                    System.out.print("[" + tablero[i][j] + "]" + "  ");
                }else{
                    System.out.print(" " +tablero[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }
}


