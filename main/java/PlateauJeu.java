package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PlateauJeu {
    int[][] plateau;
    int size;
    public PlateauJeu(int size){
        this.plateau = new int[size][size];
        this.size = size;
    }

    public PlateauJeu(String file) throws IOException {
        File doc = new File(file);
        //On lit le fichier une premiere fois pour determiner le nombre de ligne et de colonne
        BufferedReader docReader = new BufferedReader(new FileReader(doc));
        String reader = "" ; //Variable qui lit les lignes
        int LINES = 0; //Nombre de ligne
        int COLUMNS = 0; //Nombre de colonne

        do { // Lecture de doc
            reader = docReader.readLine();
            if (reader!=null) {
                COLUMNS = reader.length();
                LINES++;
            }
        } while (reader!=null); // tant qu'il y a du contenu restant
        docReader.close();

        this.size = LINES;
        this.plateau = new int[LINES][COLUMNS]; // --> Création du tableau représentant la map.

        // On lit le fichier une deuxieme fois pour remplir l'attribut map
        docReader = new BufferedReader(new FileReader(doc));
        reader = "";

        int i=0;
        int x;

        do {//Lecture de doc
            reader = docReader.readLine();
            if (reader!=null) {
                for(int j=0; j<reader.length(); j++) {
                    x = Integer.parseInt("" + reader.charAt(j));//Methode pour convertir char -> String puis de String -> Integer
                    this.setColor(i,j,x);
                }
            }
            i++;
        } while (reader!=null);//tant qu'il y a du contenu restant
    }

    @Override
    public String toString() {
        String result = "";
        String c;
        for(int i=0;i<this.size;i++){
            for(int j=0;j<this.size;j++){
                c = ""+this.plateau[i][j];
                result=result.concat(c);
            }
            result=result.concat("\n");
        }
        return result;
    }

    public void print(){
        System.out.println( this.toString() );
    }

    public PlateauJeu copy() {
        int n = this.size;
        PlateauJeu copied = new PlateauJeu(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                copied.setColor(i,j,this.plateau[i][j]);
            }
        }
        return copied;
    }

    public boolean inside(int i,int j){
        return i>=0 && i<this.size && j>=0 && j<this.size;
    }

    public void setColor(int i,int j,int color){
        this.plateau[i][j]=color;
    }

    public boolean hasVoisinAtDirection(int i,int j,Dir dir,int distance){
        int k;
        int l;
        int color = this.plateau[i][j];

        switch(dir){
            case UP -> {k=-distance;l=0;}
            case DOWN -> {k=distance;l=0;}
            case LEFT -> {k=0;l=-distance;}
            case RIGHT -> {k=0;l=distance;}
            default -> {k=0;l=0;}
        }
        if( this.inside(i+k,j+l) ){
            return this.plateau[i+k][j+l] == color;
        }
        else return false;
    }

    public void twoInRowCompletion(int i, int j){
        int color = this.plateau[i][j];
        int otherColor = 3 - color;
        if ( this.hasVoisinAtDirection(i,j,Dir.LEFT,1) && this.inside(i,j+1) && this.plateau[i][j+1]==0){
            this.setColor(i,j+1,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.RIGHT,1) && this.inside(i,j-1) && this.plateau[i][j-1]==0){
            this.setColor(i,j-1,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.UP,1) && this.inside(i+1,j) && this.plateau[i+1][j]==0){
            this.setColor(i+1,j,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.DOWN,1) && this.inside(i-1,j) && this.plateau[i-1][j]==0){
            this.setColor(i-1,j,otherColor);
        }
    }

    public void emptyBetweenColorCompletion(int i,int j){
        int color = this.plateau[i][j];
        int otherColor = 3 - color;
        if ( this.hasVoisinAtDirection(i,j,Dir.LEFT,2) && this.inside(i,j-1) && this.plateau[i][j-1]==0){
            this.setColor(i,j-1,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.RIGHT,2) && this.inside(i,j+1) && this.plateau[i][j+1]==0){
            this.setColor(i,j+1,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.UP,2) && this.inside(i-1,j) && this.plateau[i-1][j]==0){
            this.setColor(i-1,j,otherColor);
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.DOWN,2) && this.inside(i+1,j) && this.plateau[i+1][j]==0){
            this.setColor(i+1,j,otherColor);
        }
    }

    public void lineColorComplete(int line){
        int color1=0;
        int color2=0;
        int n = this.size;
        for(int k=0;k<n;k++){
            switch( this.plateau[line][k] ) {
                case 1:
                    color1++;
                    break;
                case 2:
                    color2++;
                    break;
                default:
                    break;
            }
        }
        if( color1==n/2 ){
            for(int k=0;k<n;k++){
                if( this.plateau[line][k] == 0 ){
                    this.plateau[line][k] = 2;
                }
            }
        }
        if( color2==n/2 ){
            for(int k=0;k<n;k++){
                if( this.plateau[line][k] == 0 ){
                    this.plateau[line][k] = 1;
                }
            }
        }
    }

    public void columnColorComplete(int column){
        int color1=0;
        int color2=0;
        int n = this.size;
        for(int k=0;k<n;k++){
            switch( this.plateau[k][column] ) {
                case 1:
                    color1++;
                    break;
                case 2:
                    color2++;
                    break;
                default:
                    break;
            }
        }
        if( color1==n/2 ){
            for(int k=0;k<n;k++){
                if( this.plateau[k][column] == 0 ){
                    this.plateau[k][column] = 2;
                }
            }
        }
        if( color2==n/2 ){
            for(int k=0;k<n;k++){
                if( this.plateau[k][column] == 0 ){
                    this.plateau[k][column] = 1;
                }
            }
        }
    }

    /*
    Correction Part
     */
    public boolean linesIsEqual(int line1,int line2){
        for(int k=0;k<this.size;k++){
            if( this.plateau[line1][k]!=this.plateau[line2][k] ){
                return false;
            }
        }
        return true;
    }

    public boolean allLinesIsUnique(){
        for(int i=0;i<this.size;i++){
            for(int j=i+1;j<this.size;j++) {
                if ( this.linesIsEqual(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean columnIsEqual(int column1,int column2){
        for(int k=0;k<this.size;k++){
            if( this.plateau[k][column1]!=this.plateau[k][column2] ){
                return false;
            }
        }
        return true;
    }

    public boolean allColumnsIsUnique(){
        for(int i=0;i<this.size;i++){
            for(int j=i+1;j<this.size;j++) {
                if ( this.columnIsEqual(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean threeInRow(int i,int j){
        if ( this.hasVoisinAtDirection(i,j,Dir.LEFT,2) && this.hasVoisinAtDirection(i,j,Dir.LEFT,1)){
            return true;
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.RIGHT,2) && this.hasVoisinAtDirection(i,j,Dir.RIGHT,1)){
            return true;
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.UP,2) && this.hasVoisinAtDirection(i,j,Dir.UP,1)){
            return true;
        }
        if ( this.hasVoisinAtDirection(i,j,Dir.DOWN,2) && this.hasVoisinAtDirection(i,j,Dir.DOWN,1)){
            return true;
        }
        return false;
    }

    public boolean hasThreeInRow(){
        for(int i=0;i<this.size;i++){
            for(int j=i+1;j<this.size;j++) {
                if ( this.threeInRow(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

}
