package uet.oop.bomberman.entities.movable.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.movable.enemy.ai.AI_low;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy{
    public Balloom(int xUnit, int yUnit, Image img){
        super(xUnit, yUnit, img);
        setLayer(1); //set chỉ số va chạm của balloom
        setSpeed(1); //set tốc độ của balloom
        _ai = new AI_low();
        direction = _ai.calculateDirection();
        alive = true;
    }

    @Override
    public void update() {
        if (isAlive()) { //điều kiện còn sống hay ko
            if (direction == 0) goLeft();
            if (direction == 1) goRight();
            if (direction == 2) goUp();
            if (direction == 3) goDown();
        } else if (animated < 30) { //đại diện cho tốc độ load ảnh anemy lúc chết
            animated++;
            img = Sprite.balloom_dead.getFxImage();
        } else { //nếu điều kiện còn sống bằng false thì sẽ xóa enemy
            BombermanGame.enemies.remove(this);
            BombermanGame.score += 100;
        }
    }

    //các phương thức đại diện cho hiển thị sự di chuyển (load nhiều ảnh trồng lên nhau thật nhanh để tạo cảm giác chuyển động)

    @Override
    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, left++, 18).getFxImage();
    }

    @Override
    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, right++, 18).getFxImage();
    }

    @Override
    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, up++, 18).getFxImage();
    }

    @Override
    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, down++, 18).getFxImage();
    }

    @Override
    public void stay() {
        super.stay();
        direction = _ai.calculateDirection();
    }
}
