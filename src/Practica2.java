import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Random;

public class Practica2 {

    private static Random rand = new Random();
    private static int golpes = 0;
    private static int tam = 8;
    private static int nivel = 5;
    private static int filaCorchete = 1;
    private static int columnaCorchete = 1;
    private static int[][] tablero = new int[tam][tam];
    private static int[][] tableroCopia = new int[tam][tam];
    private static int[] golpesFilas = new int[100];
    private static int[] golpesColumnas = new int[100];

    public static final int VK_UP = 0x26;   // Tecla flecha arriba
    public static final int VK_DOWN = 0x28; // Tecla flecha abajo
    public static final int VK_LEFT = 0x25; // Tecla flecha izquierda
    public static final int VK_RIGHT = 0x27; // Tecla flecha derecha
    public static final int VK_ENTER = 0x0D; // Tecla Enter
    public static final int LETRA_N = 0x4E; // Tecla N;
    public static final int LETRA_R = 0x52; // Tecla R;
    public static final int LETRA_U = 0x55; // Tecla U;
    public static final int LETRA_S = 0x53; // Tecla S;
    public static final int LETRA_L = 0x4C; // Tecla L;
    public static final int NUMERO_1 = 0x31; // Tecla 1
    public static final int NUMERO_2 = 0x32; // Tecla 2
    public static final int NUMERO_3 = 0x33; // Tecla 3
    public static final int NUMERO_4 = 0x34; // Tecla 4
    public static final int NUMERO_5 = 0x35; // Tecla 5
    public static final int NUMERO_6 = 0x36; // Tecla 6
    public static final int NUMERO_7 = 0x37; // Tecla 7
    public static final int NUMERO_8 = 0x38; // Tecla 8
    public static final int NUMERO_9 = 0x39; // Tecla 9

    // Variables para mantener el estado de las teclas
    private static boolean upPressed = false;
    private static boolean downPressed = false;
    private static boolean leftPressed = false;
    private static boolean rightPressed = false;
    private static boolean enterPressed = false;
    private static boolean nPressed = false;
    private static boolean rPressed = false;
    private static boolean uPressed = false;
    private static boolean sPressed = false;
    private static boolean lPressed = false;
    private static boolean unoPressed = false;
    private static boolean dosPressed = false;
    private static boolean tresPressed = false;
    private static boolean cuatroPressed = false;
    private static boolean cincoPressed = false;
    private static boolean seisPressed = false;
    private static boolean sietePressed = false;
    private static boolean ochoPressed = false;
    private static boolean nuevePressed = false;

    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {
        prueba.Kernel32 INSTANCE = (prueba.Kernel32) Native.load("user32", User32.class);

        // Definir la función de Windows que lee un carácter de la consola
        boolean GetAsyncKeyState(int vKey);
    }

    public static void main(String[] args) {
        String opcion = "A";
        generarTablero(nivel);

        do {

            // Limpiar la pantalla antes de cada ciclo del juego
            borrar(); // Llamamos a borrar() para limpiar la pantalla antes de mostrar la interfaz

            // Mostramos el menú y el tablero (la primera vez se genera un tablero de lvl 5).
            System.out.println("\nNuevo ( N ) - Recomenzar ( R ) - Deshacer ( U ) - Salir ( S )  ");
            System.out.println();

            mostrarTableroConCorchete(filaCorchete, columnaCorchete);

            System.out.println("\nNivel de juego ( L ) :" + nivel);
            System.out.println("\nGolpes: " + golpes);
            System.out.println("Intrucciones:");
            System.out.println("\t Mueva el cursor a un botón del tablero (con las flechas).");
            System.out.println("\t Pulse \"return\"");
            System.out.println("\t para decrementar el valor de ese botón en 1,");
            System.out.println("\t y también los valores de sus 4 vecinos.");
            System.out.println("Objetivo:");
            System.out.println("\t Dejar todos los botones en '0'.");

            // Bucle para detectar las pulsaciones de teclas.
            while (true) {
                // Verifica si la tecla de "flecha arriba" es presionada y aún no ha sido registrada.
                if ((User32.INSTANCE.GetAsyncKeyState(VK_UP) & 0x8000) != 0) {
                    if (!upPressed) {
                        filaCorchete = moverArriba(filaCorchete);
                        upPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    upPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }

                // Verifica si la tecla de "flecha abajo" es presionada y aún no ha sido registrada.
                if ((User32.INSTANCE.GetAsyncKeyState(VK_DOWN) & 0x8000) != 0) {
                    if (!downPressed) {
                        filaCorchete = moverAbajo(filaCorchete);
                        downPressed = true;
                        break;
                    }
                } else {
                    downPressed = false;
                }

                // Verifica si la tecla de "flecha izquierda" es presionada y aún no ha sido registrada.
                if ((User32.INSTANCE.GetAsyncKeyState(VK_LEFT) & 0x8000) != 0) {
                    if (!leftPressed) {
                        columnaCorchete = moverIzq(columnaCorchete);
                        leftPressed = true;
                        break;
                    }
                } else {
                    leftPressed = false;
                }

                // Verifica si la tecla de "flecha derecha" es presionada y aún no ha sido registrada.
                if ((User32.INSTANCE.GetAsyncKeyState(VK_RIGHT) & 0x8000) != 0) {
                    if (!rightPressed) {
                        columnaCorchete = moverDcha(columnaCorchete);
                        rightPressed = true;
                        break;
                    }
                } else {
                    rightPressed = false;
                }

                // Verifica si la tecla "Enter" es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(VK_ENTER) & 0x8000) != 0) {
                    if (!enterPressed) {
                        golpear(filaCorchete,columnaCorchete,golpes);
                        enterPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    enterPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }

                // Verifica si la tecla "N" es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(LETRA_N) & 0x8000) != 0) {
                    if (!nPressed) {
                        golpes = 0;
                        generarTablero(nivel);
                        nPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    nPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }

                // Verifica si la tecla "R" es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(LETRA_R) & 0x8000) != 0) {
                    if (!rPressed) {
                        golpes = 0;
                        copiarTableroConCorchete();
                        rPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    rPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }
                // Verifica si la tecla "U" es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(LETRA_U) & 0x8000) != 0) {
                    if (!uPressed) {
                        if (golpes > 0) {
                            golpes--;
                            deshacerGolpe(); // Deshacer el golpe
                        } else {
                            System.out.println("No hay golpes para deshacer.");
                        }
                        uPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    uPressed = false;// Restablecemos el estado cuando la tecla se ha soltado.
                }

                // Verifica si la tecla "L" es presionada y aún no ha sido registrada.
                if ((User32.INSTANCE.GetAsyncKeyState(LETRA_L) & 0x8000) != 0) {


                    //Seleccionamos el nivel de dificultad del tablero.
                    if (!lPressed) {
                        String nivelNuevo = String.valueOf(nivel);

                        System.out.print("Nivel de juego ( L ) : " + nivel + "\t\tNuevo nivel (1-9): ");

                        // Dificultades del 1 al 9.
                        while (true) {

                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_1) & 0x8000) != 0) {
                                        if (!unoPressed) {
                                            nivelNuevo = "1";
                                            unoPressed = true; // Marcamos que ya se ha presionad
                                            break;
                                        }
                                    } else {
                                        unoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_2) & 0x8000) != 0) {
                                        if (!dosPressed) {
                                            nivelNuevo = "2";
                                            dosPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        dosPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_3) & 0x8000) != 0) {
                                        if (!tresPressed) {
                                            nivelNuevo = "3";
                                            tresPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        tresPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_4) & 0x8000) != 0) {
                                        if (!cuatroPressed) {
                                            nivelNuevo = "4";
                                            cuatroPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        cuatroPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_5) & 0x8000) != 0) {
                                        if (!cincoPressed) {
                                            nivelNuevo = "5";
                                            cincoPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        cincoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_6) & 0x8000) != 0) {
                                        if (!seisPressed) {
                                            nivelNuevo = "6";
                                            seisPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        seisPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_7) & 0x8000) != 0) {
                                        if (!sietePressed) {
                                            nivelNuevo = "7";
                                            sietePressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        sietePressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_8) & 0x8000) != 0) {
                                        if (!ochoPressed) {
                                            nivelNuevo = "8";
                                            ochoPressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        ochoPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                    if ((User32.INSTANCE.GetAsyncKeyState(NUMERO_9) & 0x8000) != 0) {
                                        if (!nuevePressed) {
                                            nivelNuevo = "9";
                                            nuevePressed = true; // Marcamos que ya se ha presionado
                                            break;
                                        }
                                    } else {
                                        nuevePressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                                    }
                                }
                                   nivel = Integer.parseInt(nivelNuevo);
                                
                        //Generamos un nuevo tablero con el nivel de dificultad elegido.
                        generarTablero(nivel);
                    }
                        lPressed = true; // Marcamos que ya se ha presionado
                        break;

                } else {
                    lPressed = false;// Restablecemos el estado cuando la tecla se ha soltado
                }

                // Verifica si la tecla S es presionada y aún no ha sido registrada
                if ((User32.INSTANCE.GetAsyncKeyState(LETRA_S) & 0x8000) != 0) {

                    //Si presionamos la tecla "S" saldremos del programa.
                    if (!sPressed) {
                        System.out.println("Gracias por jugar\nFIN DEL PROGRAMA");
                        opcion = "S";
                        sPressed = true; // Marcamos que ya se ha presionado
                        break;
                    }
                } else {
                    sPressed = false;// Restablecemos el estado cuando la tecla se ha soltado.
                }

            }

            // Verificamos si el tablero está a cero después de cada golpe.
            if (tableroAcabado()) {
                System.out.println("¡Felicidades! Has ganado el juego.");
                int golpesNivel = obtenerGolpesEsperados();
                // Dependiendo del número de golpes usado y de la dificultad, mostramos distintos mensajes.
                if (golpes == golpesNivel) {
                    System.out.println("Perfecto. Hecho en " + golpes + " golpes.");
                } else if (golpes < golpesNivel) {
                    System.out.println("Extraordinariamente bien: Hecho en " + golpes + " golpes.");
                } else {
                    System.out.println("Hecho en " + golpes + " golpes.");
                }

                System.out.println("Nivel alcanzado: " + nivel);
                System.out.println("Presiona Enter para continuar con un nuevo tablero...");
                // Esperar a que el jugador presione Enter.
                // Verifica si la tecla Enter es presionada y aún no ha sido registrada.
                while (true) {
                    //Si presionamos la tecla Enter, generamos un nuevo tablero del mismo nivel y reiniciamos los golpes.
                    if ((User32.INSTANCE.GetAsyncKeyState(VK_ENTER) & 0x8000) != 0) {
                        if (!enterPressed) {
                            generarTablero(nivel);  // Generar nuevo tablero del mismo nivel.
                            golpes = 0;  // Restablecer los golpes.
                            enterPressed = true; // Marcamos que ya se ha presionado.
                            break;
                        }
                    } else {
                        enterPressed = false;// Restablecemos el estado cuando la tecla se ha soltado.
                    }
                }

            }

        } while (!opcion.equals("S")); //Bucle hasta que se presiona la tecla S.
    }

    // Función para generar un tablero dependiendo del nivel.
    public static void generarTablero(int nivel) {

        // Inicializamos el tablero con ceros
        for (int i = 0; i < tam - 1; i++) {
            for (int j = 0; j < tam - 1; j++) {
                tablero[i][j] = 0;
            }
        }

        // Generamos el tablero dependiendo del nivel de dificultad, con golpes aleatorios
        for (int i = 0; i < nivel * 3; i++) {
            int fila = rand.nextInt(1,tam -1);
            int columna = rand.nextInt(1, tam -1);
            aumentar(fila, columna); // Casilla seleccionada
            aumentar(fila - 1, columna); // Arriba
            aumentar(fila + 1, columna); // Abajo
            aumentar(fila, columna - 1); // Izquierda
            aumentar(fila, columna + 1);// Derecha

        }

        //Generamos una copia del tablero generado

        for (int i = 0; i < tam - 1; i++) {
            for (int j = 0; j < tam - 1; j++) {
                tableroCopia[i][j] = tablero[i][j];
            }
        }


    }

    // Función para mostrar la casilla seleccionada visualmente con corchetes.
    public static void mostrarTableroConCorchete(int fila, int columna) {

        //Mostramos dos corchetes en una determinada casilla para saber dónde vamos a golpear.
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {

                if (i == fila && j == columna) {
                    System.out.print("[" + tablero[i][j] + "]" + "  ");
                }else{
                    System.out.print(" " +tablero[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }

    // Función para copiar el Tablero original con el corchete, al tablero copia.
    public static void copiarTableroConCorchete() {
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {
                tablero[i][j] = tableroCopia[i][j];

            }
        }
    }
    // Función para restar 1 al valor de la casilla selecciona y sus vecinas
    public static void golpear(int fila, int columna,int golpe) {

        // Añadimos la fila y la columna a los Arrays con el nº de golpe correspondiente para luego poder acceder a ellos si queremos deshacer el
        // golpe.
        golpesFilas[golpe] = fila;
        golpesColumnas[golpe] = columna;
        golpes++; // Aumentamos en 1 la cantidad de golpes que llevamos.

        // Restamos los valores de la casilla seleccionada y sus vecinas.
        decrementar(fila, columna); // Casilla seleccionada
        decrementar(fila - 1, columna); // Arriba
        decrementar(fila + 1, columna); // Abajo
        decrementar(fila, columna - 1); // Izquierda
        decrementar(fila, columna + 1); // Derecha
























    }

    // Función para restar en 1 el valor de una casilla.
    public static void decrementar(int fila, int columna) {
        tablero[fila][columna] = tablero[fila][columna] - 1;

        // Si el valor de la casilla es cero, pasa a ser 3.
        if (tablero[fila][columna] < 0) {
            tablero[fila][columna] = 3;
        }
    }

    // Función para aumentar en 1 el valor de una casilla.
    public static void aumentar(int fila, int columna) {
        tablero[fila][columna] = tablero[fila][columna] + 1;

        // Si el valor de la casilla es 3, pasa a ser 0.
        if (tablero[fila][columna] > 3) {
            tablero[fila][columna] = 0;
        }

    }

    // Función para subir la casilla seleccionada.
    public static int moverArriba(int fila){
        fila--;

        // Si baja más allá de la fila 1, vuelve a la última fila
        if (fila < 1) {
            fila = 6;
        }
        return fila;
    }

    // Función para bajar la casilla seleccionada.
    public static int moverAbajo(int fila){
        fila++;
        if (fila > 6) {  // Si baja más allá de la fila 1, vuelve a la última fila
            fila = 1;
        }
        return fila;
    }

    // Función para desplazar a la izquierda la casilla seleccionada.
    public static int moverIzq(int columna){
        columna--;

        // Si baja más allá de la fila 1, vuelve a la última fila.
        if (columna < 1) {
            columna = 6;
        }
        return columna;
    }

    // Función para desplazar a la derecha la casilla seleccionada.
    public static int moverDcha(int columna){
        columna++;

        // Si baja más allá de la fila 1, vuelve a la última fila.
        if (columna > 6) {
            columna = 1;
        }
        return columna;
    }

    // Función para deshacer el golpe realizado.
    public static void deshacerGolpe() {

        // Recuperamos la última posición registrada
        int fila = golpesFilas[golpes];
        int columna = golpesColumnas[golpes];

        // Restauramos la celda y las vecinas sumando 1
        aumentar(fila, columna);
        aumentar(fila - 1, columna);
        aumentar(fila + 1, columna);
        aumentar(fila, columna - 1);
        aumentar(fila, columna + 1);

    }

    // Función para comprobar si hemos terminado el juego.
    public static boolean tableroAcabado() {
        boolean ganador = true;

        // Recorremos con dos bucles For el tablero.
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 7; j++) {
                if (tablero[i][j] != 0) {
                    ganador = false; // Si encontramos un valor distinto de 0, no está resuelto.
                }
            }
        }

        return ganador;
    }

    // Función para golpear(incrementar) X veces según el nivel elegido.
    public static int obtenerGolpesEsperados() {
        int valor = 15; //Valor por defecto con nivel 5

        // Según el nivel elegido, daremos los golpes incrementados.
        switch (nivel) {
            case 1 -> {
                valor = 3;
            }
            case 2 -> {
                valor = 6;
            }
            case 3 -> {
                valor = 9;
            }
            case 4 -> {
                valor = 12;
            }
            case 5 -> {
                valor = 15;
            }
            case 6 -> {
                valor = 18;
            }
            case 7 -> {
                valor = 21;
            }
            case 8 -> {
                valor = 24;
            }
            case 9 -> {
                valor = 27;
            }

        }
        return valor;
    }

    // Función para borrar la pantalla con cada interacción.
    public static void borrar(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}



