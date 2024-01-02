package main.java;

public class Solver {

    public static void globalTwoInRowCompletion(PlateauJeu jeu){
        int n = jeu.size;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if( jeu.plateau[i][j]!=0 ){
                    jeu.twoInRowCompletion(i,j);
                }
            }
        }
    }

    public static void globalEmptyBetweenColorCompletion(PlateauJeu jeu){
        int n = jeu.size;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if( jeu.plateau[i][j]!=0 ){
                    jeu.emptyBetweenColorCompletion(i,j);
                }
            }
        }
    }

    public static void globalCompleteColor(PlateauJeu jeu){
        PlateauJeu oldPlateau;
        do{
            oldPlateau = jeu.copy();
            for(int k=0;k<jeu.size;k++){
                jeu.columnColorComplete(k);
                jeu.lineColorComplete(k);
            }
        }while( !jeu.toString().equals(oldPlateau.toString()) );
    }

    public static PlateauJeu resolveLoop(PlateauJeu jeu){
        int n = jeu.size;
        PlateauJeu copyPlateau;

        do{
            copyPlateau = jeu.copy();
            globalTwoInRowCompletion(jeu);
            globalEmptyBetweenColorCompletion(jeu);
            globalCompleteColor(jeu);
        }while( !jeu.toString().equals(copyPlateau.toString()) );

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if( jeu.plateau[i][j] == 0 ){
                    copyPlateau = jeu.copy();
                    copyPlateau.setColor(i,j,1);
                    Solver.resolveLoop(copyPlateau);
                    if( Solver.isCorrect(copyPlateau) ){
                        jeu.setColor(i,j,1);
                        Solver.resolveLoop(jeu);
                    }else{
                        jeu.setColor(i,j,2);
                        Solver.resolveLoop(jeu);
                    }
                    return jeu;
                }
            }
        }
        return jeu;
    }

    public static boolean isCorrect(PlateauJeu jeu){
        if( !jeu.allLinesIsUnique() || !jeu.allColumnsIsUnique() || jeu.hasThreeInRow()){
            return false;
        }
        return true;
    }
}
