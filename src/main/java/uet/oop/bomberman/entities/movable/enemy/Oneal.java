package uet.oop.bomberman.entities.movable.enemy;


import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.enemy.ai.AI_high;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

import static uet.oop.bomberman.BombermanGame.bomberman;
import static uet.oop.bomberman.BombermanGame.score;

public class Oneal extends Enemy{

    public Oneal(int xUnit, int yUnit, Image img){
        super(xUnit, yUnit, img);
        setLayer(1); // chỉ số va chạm của Oneal
        setSpeed(2);
        _ai = new AI_high(bomberman, this);
        direction = _ai.calculateDirection();
        alive = true;
    }

    Rectangle e = this.getBounds(); //tạo bao cho oneal
    int n = 0;

    @Override
    public void update() {
        n++;
        if (isAlive()) { // điều kiện còn sống hay ko
            if (direction == 3) goLeft();
            if (direction == 1) goRight();
            if (direction == 0) goUp();
            if (direction == 2) goDown();
            if ( n == 32) {
                direction = _ai.calculateDirection();
                n = 0;
            }
            if (!bomberman.isAlive()) bomberman.update();

        } else if (animated < 30) { // đại diện tốc độ load ảnh lúc enemy DEAD
            animated++;
            img = Sprite.oneal_dead.getFxImage();
        } else {
            BombermanGame.enemies.remove(this); // đk còn sống trả về false thì xóa enemy
            score += 300;
        }
    }

    //Các phương thức đại diện cho hiển thị sự di chuyển (load nhiều ảnh trồng lên nhau nhanh để tạo cảm giác chuyển động)

    @Override
    public void goLeft() {
        super.goLeft();
        e = this.getBounds();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, left++, 18).getFxImage();
    }

    @Override
    public void goRight() {
        super.goRight();
        e = this.getBounds();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, right++, 18).getFxImage();
    }

    @Override
    public void goUp() {
        super.goUp();
        e = this.getBounds();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, up++, 18).getFxImage();
    }

    @Override
    public void goDown() {
        super.goDown();
        e = this.getBounds();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, down++, 18).getFxImage();
    }

    @Override
    public void stay() {
        super.stay();
        direction = _ai.calculateDirection();
    }
}
