import java.util.Scanner;
import java.util.Random;
public class Practica2 {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        String opcion;
        int tam = 8;
        int nivel = 5;
        int[][] tablero = new int[tam][tam];

        do {
            System.out.println("Nuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S )  ");
            System.out.println();
            generarTablero(nivel,tam,tablero);
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
            //opcion = opcion.toUpperCase();

            switch (opcion) {
                case "/u001B[A" -> {  // Flecha hacia arriba
                    System.out.println("Flecha hacia arriba detectada.");
                }
                case "\u001B[B" -> {  // Flecha hacia abajo
                    System.out.println("Flecha hacia abajo detectada.");
                }
                case "\u001B[D" -> { // Flecha hacia izquierda
                    System.out.println("Flecha hacia izquierda detectada.");
                }
                case "\u001B[C" -> { // Flecha hacia derecha
                    System.out.println("Flecha hacia derecha detectada.");
                }
                case "N" -> { //Tablero nuevo
                    generarTablero(nivel,tam,tablero);
                }
                case "R" -> { //Recomenzar el tablero existente

                }
                case "U" -> { // Deshacer

                }
                case "L" -> { // Seleccionar nivel de dificultad

                    do {
                        System.out.println("Nuevo nivel: (1-9)");
                        nivel = sc.nextInt();
                        if (nivel < 1 || nivel > 9) {
                            System.out.println("Nivel incorrecto");
                        }
                    }while (nivel < 1 || nivel > 9);

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

                if (i == 1 && j == 1) {
                    System.out.print("[" + tablero[i][j] + "]" + "  ");
                }else{
                    System.out.print(" " +tablero[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

    public static void generarTablero(int nivel, int tam, int[][] tablero) {
        Random rand = new Random();


        // Inicializamos el tablero con ceros
        for (int i = 0; i < tam - 1; i++) {
            for (int j = 0; j < tam - 1; j++) {
                tablero[i][j] = 0;
            }
        }

        // Generamos el tablero aplicando "nivel" golpes aleatorios
        for (int i = 0; i < nivel * 3; i++) {
            int fila = rand.nextInt(1,tam -1);
            int columna = rand.nextInt(1, tam -1);
            aumentar(fila, columna, tablero); // Casilla seleccionada
            if (posicionValida(fila - 1, columna)) aumentar(fila - 1, columna, tablero); // Arriba
            if (posicionValida(fila + 1, columna)) aumentar(fila + 1, columna, tablero); // Abajo
            if (posicionValida(fila, columna - 1)) aumentar(fila, columna - 1, tablero); // Izquierda
            if (posicionValida(fila, columna + 1)) aumentar(fila, columna + 1, tablero);

        }
    }
    public static void golpear(int fila, int columna, int[][] tablero) {
        decrementar(fila, columna, tablero); // Casilla seleccionada
        if (posicionValida(fila - 1, columna)) decrementar(fila - 1, columna, tablero); // Arriba
        if (posicionValida(fila + 1, columna)) decrementar(fila + 1, columna, tablero); // Abajo
        if (posicionValida(fila, columna - 1)) decrementar(fila, columna - 1, tablero); // Izquierda
        if (posicionValida(fila, columna + 1)) decrementar(fila, columna + 1, tablero); // Derecha
    }
    public static boolean posicionValida(int fila, int columna) {
        return fila > 0 && fila < 8 && columna > 0 && columna < 8;
    }
    public static void decrementar(int fila, int columna, int[][] tablero) {
        tablero[fila][columna] = (tablero[fila][columna] - 1 + 4) % 4;
    }
    public static void aumentar(int fila, int columna, int[][] tablero) {
        tablero[fila][columna] = (tablero[fila][columna] + 1 + 4) % 4;
    }
    }


