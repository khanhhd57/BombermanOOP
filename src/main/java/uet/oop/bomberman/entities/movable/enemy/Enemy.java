package uet.oop.bomberman.entities.movable.enemy;

import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.movable.enemy.ai.AI;
import javafx.scene.image.Image;

public abstract class Enemy extends Character {
    protected AI _ai; //tự điều kiển
    public int direction; //Đại diện về phương hướng
    public Enemy(int xUnit, int yUnit, Image img){
        super(xUnit, yUnit, img);
    }
}
