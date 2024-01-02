package main.java;

import java.io.IOException;

public class Main {

    //static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws IOException {
        System.out.println("Hello world");
        PlateauJeu jeu = new PlateauJeu("main/java/level.txt");
        //jeu.print();
        jeu.print();
        //System.out.println( ""+jeu.hasVoisinAtDirection(3,2,Dir.LEFT) );
        Solver.resolveLoop(jeu);
        jeu.print();
        System.out.println(""+Solver.isCorrect(jeu));

    }
}
