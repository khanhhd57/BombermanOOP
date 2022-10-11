package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;

public class Balloom extends Enemy{
    public Balloom(int xUnit, int yUnit, Image img){
        super(xUnit, yUnit, img);
        setLayer(1); //set chỉ số va chạm của balloom
        setSpeed(1); //set tốc độ của balloom
        _ai = new
    }
}
