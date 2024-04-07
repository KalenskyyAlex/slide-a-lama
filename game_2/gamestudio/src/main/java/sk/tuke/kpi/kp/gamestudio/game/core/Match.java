package sk.tuke.kpi.kp.gamestudio.game.core;

public enum Match {

    LINE_3_V(0, 3),
    LINE_3_H(1, 3),
    LINE_4_V(0, 4),
    LINE_4_H(1, 4),
    LINE_5_V(0, 5),
    LINE_5_H(1, 5);

    private final int orientation;
    private final int length;

    private final int lengthMulriplier;

    Match(int orientation, int length){
        this.length = length;
        this.orientation = orientation;

        switch (length){
            case 3 -> lengthMulriplier = 1;
            case 4 -> lengthMulriplier = 2;
            case 5 -> lengthMulriplier = 3;
            default -> lengthMulriplier = 0;
        }
    }

    public MatchResult checkForMatch(Tile[][] field){
        if (this.orientation == 0){
            for(int i = 0; i < field.length; i++){
                for(int j = 0; j < field.length - this.length + 1; j++){
                    boolean equals = true;
                    for(int k = 1; k < this.length; k++){
                        if(field[i][j + k] != field[i][j + k - 1]) {
                            equals = false;
                            break;
                        }
                    }

                    if(field[i][j] == Tile.EMPTY) continue;
                    if(equals) return new MatchResult(j, i, orientation, length, field[i][j].getScoreMultiplier(), lengthMulriplier);
                }
            }
        }
        else {
            for(int j = 0; j < field.length; j++){
                for(int i = 0; i < field.length - this.length + 1; i++){
                    boolean equals = true;
                    for(int k = 1; k < this.length; k++){
                        if(field[i + k][j] != field[i + k - 1][j]){
                            equals = false;
                            break;
                        }
                    }

                    if(field[i][j] == Tile.EMPTY) continue;
                    if(equals) return new MatchResult(j, i, orientation, length, field[i][j].getScoreMultiplier(), lengthMulriplier);
                }
            }
        }

        return null;
    }
}
