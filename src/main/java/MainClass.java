import processing.core.PApplet;

public class MainClass extends PApplet {

    private int leftMargin = 20;
    private int upperMargin = 20;
    private int boxlength = 80;
    private int selectedDame = -1;
    private Dame[] damenarray = new Dame[24];
    private boolean start = true;
    private boolean whitesturn = true;
    private Boolean openedLink = false;

    public static void main(String[] args) {
        PApplet.main("MainClass", args);
    }

    public void settings() {
        size(800, 800);

    }

    public void setup() {
        frameRate(60);

    }

    public void draw() {

        if (start) {
            reset();
            start = false;
        }

        int changecolor = 0;
        boolean inversed = false;
        background(200);


        //region Brett
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                changecolor++;

                if (boardColor(inversed, changecolor)) {
                    fill(193, 148, 93);

                } else {
                    fill(255, 211, 158);
                }

                stroke(193, 148, 93);
                rect(leftMargin + j * boxlength, upperMargin + i * boxlength, boxlength, boxlength);
            }

            inversed = !inversed;

        }
        //endregion

        int hoveredIndex = isMouseOverDame();

        //region Hover-Effekt
        if (hoveredIndex != -1) {
            if (mousePressed) {
                if (selectedDame == hoveredIndex) {
                    selectedDame = -1;
                } else {
                    selectedDame = hoveredIndex;
                }
                delay(100);

            } else {
                fill(255, 231, 204);
            }
            rect(damenarray[hoveredIndex].X - boxlength/2f, damenarray[hoveredIndex].Y - boxlength / 2f, boxlength, boxlength);
        }

        fill(255);
        if (selectedDame != -1 && whitesturn && selectedDame < 12) {
            rect(damenarray[selectedDame].X - boxlength / 2f, damenarray[selectedDame].Y - boxlength / 2f, boxlength, boxlength);
        } else if (selectedDame != -1 && !whitesturn && selectedDame >= 12) {
            rect(damenarray[selectedDame].X - boxlength / 2f, damenarray[selectedDame].Y - boxlength / 2f, boxlength, boxlength);
        }
        //endregion

        if (selectedDame != -1 && !damenarray[selectedDame].doppelDame) {
            drawSuggestion(selectedDame);
        }else if(selectedDame != -1 && damenarray[selectedDame].doppelDame) {
            drawDoubleSuggestion(selectedDame);
        }


        //region Damen zeichnen
        stroke(0);
        for (int i = 12; i < 24; i++) {
            if (damenarray[i].X != -1 && damenarray[i].Y != -1) {
                fill(50);
                ellipse(damenarray[i].X, damenarray[i].Y, boxlength / 5f * 4, boxlength / 5f * 4);
                fill(55);
                ellipse(damenarray[i].X, damenarray[i].Y, boxlength / 5f * 3, boxlength / 5f * 3);

                if (damenarray[i].Y == upperMargin + boxlength / 2) {
                    damenarray[i].doppelDame = true;
                }
                if (damenarray[i].doppelDame) {
                    fill(50);
                    ellipse(damenarray[i].X, damenarray[i].Y - boxlength / 8f, boxlength / 5f * 4, boxlength / 5f * 4);
                    fill(55);
                    ellipse(damenarray[i].X, damenarray[i].Y - boxlength / 8f, boxlength / 5f * 3, boxlength / 5f * 3);
                }
            }
        }

        for (int i = 0; i < 12; i++) {
            if (damenarray[i].X != -1 && damenarray[i].Y != -1) {
                stroke(0);
                fill(245);
                ellipse(damenarray[i].X, damenarray[i].Y, boxlength / 5f * 4, boxlength / 5f * 4);
                stroke(225);
                fill(255);
                ellipse(damenarray[i].X, damenarray[i].Y, boxlength / 5f * 3, boxlength / 5f * 3);

                if (damenarray[i].Y == upperMargin + 7.5 * boxlength) {

                    damenarray[i].doppelDame = true;

                }
                if (damenarray[i].doppelDame) {
                    stroke(0);
                    fill(245);
                    ellipse(damenarray[i].X, damenarray[i].Y - boxlength / 8f, boxlength / 5f * 4, boxlength / 5f * 4);
                    stroke(225);
                    fill(255);
                    ellipse(damenarray[i].X, damenarray[i].Y - boxlength / 8f, boxlength / 5f * 3, boxlength / 5f * 3);
                }
            }
        }
        stroke(0);
        //endregion

        //region untere Box und Restet Button
        fill(255, 211, 158);
        rect(leftMargin, upperMargin * 2 + 8 * boxlength, 8 * boxlength, 25);
        textSize(boxlength / 5f);
        fill(0);
        if (whitesturn) {
            text("Weiß ist an der Reihe", leftMargin + 5, upperMargin * 2 + 8 * boxlength + boxlength / 5);
        } else {
            text("Schwarz ist an der Reihe", leftMargin + 5, upperMargin * 2 + 8 * boxlength + boxlength / 5);
        }

        if (mouseoverBox(1) && mousePressed) {
            fill(193, 148, 93);
            reset();
        } else if (mouseoverBox(1)) {
            fill(255, 231, 204);
        } else {
            fill(255, 211, 158);
        }
        rect(leftMargin, upperMargin * 2 + 8 * boxlength + 30, 4 * boxlength, 25);
        fill(0);

        textSize(boxlength / 5f);
        text("neues Spiel starten", leftMargin + boxlength, upperMargin * 2 + 8 * boxlength + 50);
        fill(255);

        if (mouseoverBox(2) && mousePressed) {
            fill(193, 148, 93);
            if(!openedLink) {
                link("https://www.brettspielnetz.de/spielregeln/dame.php");
                openedLink = true;
            }
        } else if (mouseoverBox(2)) {
            fill(255, 231, 204);
        } else {
            fill(255, 211, 158);
        }
        rect(leftMargin + 4* boxlength, upperMargin * 2 + 8 * boxlength + 30, 4 * boxlength, 25);
        fill(0);

        textSize(boxlength / 5f);
        text("Spielanleitung", leftMargin + 5.3f*boxlength, upperMargin * 2 + 8 * boxlength + 50);
        fill(255);

        //endregion

    }

    private void reset() {

        int j = 0;
        for (int i = 0; i < 4; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength / 2);
            j = j + 2;
        }
        j = 1;
        for (int i = 4; i < 8; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength + boxlength / 2);
            j = j + 2;
        }
        j = 0;
        for (int i = 8; i < 12; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength / 2 + 2 * boxlength);
            j = j + 2;
        }
        j = 1;
        for (int i = 12; i < 16; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength / 2 + 5 * boxlength);
            j = j + 2;
        }
        j = 0;
        for (int i = 16; i < 20; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength / 2 + 6 * boxlength);
            j = j + 2;
        }
        j = 1;
        for (int i = 20; i < 24; i++) {
            damenarray[i] = new Dame(leftMargin + j * boxlength + boxlength / 2, upperMargin + boxlength / 2 + 7 * boxlength);
            j = j + 2;
        }

        whitesturn = true;
        selectedDame = -1;

        for (Dame i:damenarray){
            i.doppelDame = false;
        }

    }

    private Boolean boardColor(Boolean inversed, int changecolor) {

        if (!inversed) {
            return changecolor % 2 == 0;
        } else {
            return changecolor % 2 != 0;
        }
    }

    private Boolean mouseoverBox(int boxCase) {
        if(boxCase == 1) {
            return mouseX >= leftMargin &&
                    mouseX <= leftMargin + 4 * boxlength &&
                    mouseY >= upperMargin * 2 + 8 * boxlength + 30 &&
                    mouseY <= upperMargin * 2 + 8 * boxlength + 55;
        }else{
            return mouseX >= leftMargin + 4 * boxlength &&
                    mouseX <= leftMargin + 8 * boxlength &&
                    mouseY >= upperMargin * 2 + 8 * boxlength + 30 &&
                    mouseY <= upperMargin * 2 + 8 * boxlength + 50;
        }
    }

    private int isMouseOverDame() {

        for (int i = 0; i < damenarray.length; i++) {
            if (mouseX > damenarray[i].X - boxlength / 2 &&
                    mouseX < damenarray[i].X + boxlength / 2 &&
                    mouseY > damenarray[i].Y - boxlength / 2 &&
                    mouseY < damenarray[i].Y + boxlength / 2
            ) {
                return i;
            }
        }

        return -1;
    }

    private void drawSuggestion(int selectedDame) {

        fill(242, 218, 191);

        boolean isOccupied = false;
        int occupant = -1;
        if (selectedDame < 12 && whitesturn) {

            //region weiß: links unten
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 1);
                        return;
                    }

                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);

            }

            if (isOccupied) {
                if (occupant >= 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region weiß: rechts unten
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5f &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 2);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant >= 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

        } else if (selectedDame >= 12 && !whitesturn) {

            //region schwarz: rechts oben
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 3);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region schwarz: links oben
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5 &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5 &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 4);
                        return;
                    }
                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
            }
            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion
        }


    }

    private void drawDoubleSuggestion(int selectedDame) {

        fill(242, 218, 191);

        boolean isOccupied = false;
        int occupant = -1;

        if(selectedDame < 12 && whitesturn) {

            //region links unten
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2 && damenarray[selectedDame].Y != upperMargin + 7.5 * boxlength) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 1);
                        return;
                    }

                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);

            }

            if (isOccupied) {
                if (occupant >= 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region rechts unten
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5f &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 2);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant >= 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region rechts oben
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 3);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region links oben
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5 &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5 &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 4);
                        return;
                    }
                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
            }
            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

        }else if(selectedDame > 11 && !whitesturn) {

            //region links unten
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2 && damenarray[selectedDame].Y != upperMargin + 7.5 * boxlength) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 1);
                        return;
                    }

                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);

            }

            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region rechts unten
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y + boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5f &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5f &&
                        mouseY > damenarray[selectedDame].Y + boxlength / 2f &&
                        mouseY < damenarray[selectedDame].Y + boxlength * 1.5f) {

                    if (mousePressed) {
                        moveDame(selectedDame, 2);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y + boxlength / 2f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant >= 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region rechts oben
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X - boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != leftMargin + boxlength / 2) {

                if (mouseX > damenarray[selectedDame].X - boxlength * 1.5f &&
                        mouseX < damenarray[selectedDame].X - boxlength * 0.5f &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 3);
                        return;
                    }

                    fill(255, 231, 204);
                }
                rect(damenarray[selectedDame].X - boxlength * 1.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
                fill(242, 218, 191);
            }

            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion

            //region links oben
            isOccupied = false;
            occupant = -1;
            for (int i = 0; i < damenarray.length; i++) {
                if (damenarray[i].X == damenarray[selectedDame].X + boxlength && damenarray[i].Y == damenarray[selectedDame].Y - boxlength) {
                    isOccupied = true;
                    occupant = i;
                }
            }
            if (!isOccupied && damenarray[selectedDame].X != 7.5 * boxlength + leftMargin) {

                if (mouseX > damenarray[selectedDame].X + boxlength * 0.5 &&
                        mouseX < damenarray[selectedDame].X + boxlength * 1.5 &&
                        mouseY > damenarray[selectedDame].Y - boxlength * 1.5f &&
                        mouseY < damenarray[selectedDame].Y - boxlength * 0.5f) {
                    if (mousePressed) {
                        moveDame(selectedDame, 4);
                        return;
                    }
                    fill(255, 231, 204);
                }

                rect(damenarray[selectedDame].X + boxlength * 0.5f, damenarray[selectedDame].Y - boxlength * 1.5f, boxlength, boxlength);
            }
            if (isOccupied) {
                if (occupant < 12) {
                    beatSuggestion(selectedDame, occupant);
                }
            }
            //endregion
        }
        }

    private void beatSuggestion(int selectedDame, int occupant){

        //region occupant links unten
        if(damenarray[selectedDame].Y < damenarray[occupant].Y && damenarray[selectedDame].X > damenarray[occupant].X){
            if(damenarray[selectedDame].X != leftMargin + 1.5 * boxlength && damenarray[selectedDame].Y != upperMargin + 6.5* boxlength){

                boolean isOccupied = false;

                for (Dame i : damenarray) {
                    if(i.X == damenarray[occupant].X - boxlength && i.Y == damenarray[occupant].Y + boxlength){
                        isOccupied = true;
                    }
                }

                //region hover-Effekt

                if(     mouseX <= damenarray[occupant].X - 0.5f * boxlength &&
                        mouseX >= damenarray[occupant].X - 1.5f * boxlength &&
                        mouseY <= damenarray[occupant].Y + 1.5f * boxlength &&
                        mouseY >= damenarray[occupant].Y + 0.5f * boxlength){
                    fill(255, 231, 204);

                    if(mousePressed){
                        beatDame(occupant, selectedDame, 1);
                    }
                }
                //endregion

                if(!isOccupied){
                    rect(damenarray[occupant].X - 1.5f * boxlength, damenarray[occupant].Y + 0.5f * boxlength, boxlength, boxlength);
                }
                fill(242, 218, 191);
            }
        }//endregion

        //region occupant rechts unten
        if(damenarray[selectedDame].Y < damenarray[occupant].Y && damenarray[selectedDame].X < damenarray[occupant].X){
            if(damenarray[selectedDame].X != leftMargin + 6.5 * boxlength && damenarray[selectedDame].Y != upperMargin + 6.5* boxlength){

                boolean isOccupied = false;

                for (Dame i : damenarray) {
                    if(i.X == damenarray[occupant].X + boxlength && i.Y == damenarray[occupant].Y + boxlength){
                        isOccupied = true;
                    }
                }

                //region hover-Effekt

                if(     mouseX <= damenarray[occupant].X + 1.5f * boxlength &&
                        mouseX >= damenarray[occupant].X + 0.5f * boxlength &&
                        mouseY <= damenarray[occupant].Y + 1.5f * boxlength &&
                        mouseY >= damenarray[occupant].Y + 0.5f * boxlength){
                    fill(255, 231, 204);

                    if(mousePressed){
                        beatDame(occupant, selectedDame, 2);                    }
                }
                //endregion

                if(!isOccupied){
                    rect(damenarray[occupant].X + 0.5f * boxlength, damenarray[occupant].Y + 0.5f * boxlength, boxlength, boxlength);
                }
                fill(242, 218, 191);
            }
        } //endregion

        //region occupant links oben
        if(damenarray[selectedDame].Y > damenarray[occupant].Y && damenarray[selectedDame].X > damenarray[occupant].X){
            if(damenarray[selectedDame].X != leftMargin + 1.5 * boxlength && damenarray[selectedDame].Y != upperMargin + 1.5 * boxlength){

                boolean isOccupied = false;

                for (Dame i : damenarray) {
                    if(i.X == damenarray[occupant].X - boxlength && i.Y == damenarray[occupant].Y - boxlength){
                        isOccupied = true;
                    }
                }

                //region hover-Effekt

                if(     mouseX <= damenarray[occupant].X - 0.5f * boxlength &&
                        mouseX >= damenarray[occupant].X - 1.5f * boxlength &&
                        mouseY <= damenarray[occupant].Y - 0.5f * boxlength &&
                        mouseY >= damenarray[occupant].Y - 1.5f * boxlength){
                    fill(255, 231, 204);

                    if(mousePressed){
                        beatDame(occupant, selectedDame, 3);
                    }
                }
                //endregion

                if(!isOccupied){
                    rect(damenarray[occupant].X - 1.5f * boxlength, damenarray[occupant].Y - 1.5f * boxlength, boxlength, boxlength);
                }
                fill(242, 218, 191);
            }
        }//endregion

        //region occupant rechts oben
        if(damenarray[selectedDame].Y > damenarray[occupant].Y && damenarray[selectedDame].X < damenarray[occupant].X){

            if(damenarray[selectedDame].X != leftMargin + 6.5 * boxlength && damenarray[selectedDame].Y != upperMargin + 1.5* boxlength){

                boolean isOccupied = false;

                for (Dame i : damenarray) {
                    if(i.X == damenarray[occupant].X + boxlength && i.Y == damenarray[occupant].Y - boxlength){
                        isOccupied = true;
                    }
                }

                //region Hover-Effekt

                if(     mouseX <= damenarray[occupant].X + 1.5f * boxlength &&
                        mouseX >= damenarray[occupant].X + 0.5f * boxlength &&
                        mouseY <= damenarray[occupant].Y - 0.5f * boxlength &&
                        mouseY >= damenarray[occupant].Y - 1.5f * boxlength){
                    fill(255, 231, 204);

                    if(mousePressed){
                        beatDame(occupant, selectedDame, 4);
                    }
                }
                //endregion

                if(!isOccupied){
                    rect(damenarray[occupant].X + 0.5f * boxlength, damenarray[occupant].Y - 1.5f * boxlength, boxlength, boxlength);
                }
                fill(242, 218, 191);
            }
        }//endregion

    }

    private void moveDame(int selectedDame, int direction){
        int x = damenarray[selectedDame].X;
        int y = damenarray[selectedDame].Y;

        switch (direction){
            case 1: x = x-boxlength;
                    y = y+boxlength;
                    break;
            case 2: x = x+boxlength;
                    y = y+boxlength;
                    break;
            case 3: x = x-boxlength;
                    y = y-boxlength;
                    break;
            case 4: x = x+boxlength;
                    y = y-boxlength;
                    break;
            default: break;
        }

        damenarray[selectedDame].setX(x);
        damenarray[selectedDame].setY(y);

        whitesturn = !whitesturn;

    }

    private void beatDame(int occupant, int selectedDame, int caseOfTheArrangement){

        switch (caseOfTheArrangement){
            case 1:
                damenarray[selectedDame].X = damenarray[selectedDame].X - 2*boxlength;
                damenarray[selectedDame].Y = damenarray[selectedDame].Y + 2*boxlength;
                break;
            case 2:
                damenarray[selectedDame].X = damenarray[selectedDame].X + 2*boxlength;
                damenarray[selectedDame].Y = damenarray[selectedDame].Y + 2*boxlength;
                break;
            case 3:
                damenarray[selectedDame].X = damenarray[selectedDame].X - 2*boxlength;
                damenarray[selectedDame].Y = damenarray[selectedDame].Y - 2*boxlength;
                break;
            case 4:
                damenarray[selectedDame].X = damenarray[selectedDame].X + 2*boxlength;
                damenarray[selectedDame].Y = damenarray[selectedDame].Y - 2*boxlength;
                break;
                default:
                    System.out.println("error");
        }

        damenarray[occupant].X = -1;
        damenarray[occupant].Y = -1;

        whitesturn = !whitesturn;

    }
}
