package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bomber extends Character {
    private int bombRemain; // khai báo biê "số bomb dự trữ"
    private boolean placeBompCommand = false; //quản lý việc đặt bomb (trả về true or false)
    private boolean isAlllowerGoToBom = false; //quản lý việc đi xuyên qua bomb (trả về true or false)
    private final List<Bomb> bombs = new ArrayList<>(); //khai báo list quản lý bom
    private int radius; //khai báo biến bán kính nổ của bom
    private KeyCode direction = null; //khai báo phím keyboard
    private int timeAfterDie = 0; //khai báo thời gian sau khi chết
    private int power;
    public Bomber(int x, int y, Image img){
        super(x, y, img);
        setLayer(1); //chỉ số va chạm của bomber
        setSpeed(3); //tốc độ
        setBombRemain(1); //số bom dự trữ
        setRadius(1); //bán kính nổ
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void update(){
        if (direction == KeyCode.LEFT)
            goLeft();
        if (direction == KeyCode.RIGHT)
            goRight();
        if (direction == KeyCode.UP)
            goUp();
        if (direction == KeyCode.DOWN)
            goDown();
        if (placeBompCommand)
            placeBomb();
        for (int i = 0; i < bombs.size(); i++){ //duyệt cái lít của bomb
            Bomb bomb = bombs.get(i);
            if (!bomb.isAlive()){
                bombs.remove(bomb);
                bombRemain++;
            }
        }
        //animate();
        if (!isAlive()){
            timeAfterDie++;
            die();
        }
    }

    //khai báo các sự kiện về bàn phím
    public void handleKeyPressedEvent(KeyCode keyCode){
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT
            || keyCode == KeyCode.UP || keyCode == KeyCode.DOWN){
            this.direction = keyCode;
        }
        if (keyCode == KeyCode.SPACE)
            placeBompCommand = true;
    }

    //điều khiển bombẻ di chuyển và đặt bom từ bàn phím (phương thức này sử dụng để load ảnh hiển thị) (xử lý các sự kiện bàn phím)
    public void handleKeyReleasedEvent(KeyCode keyCode){
        if (direction == keyCode){
            if (direction == KeyCode.LEFT){
                img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.RIGHT){
                img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.UP){
                img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.DOWN){
                img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode = KeyCode.SPACE){
            placeBompCommand = false;
        }
    }

    //các load ảnh hiển thị di chuyển

    @Override
    public void goLeft() {
        super.goLeft();
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1, Sprite.player_left_2, left++, 20).getFxImage();
    }

    @Override
    public void goRight() {
        super.goRight();
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1, Sprite.player_right_2, right++, 20).getFxImage();
    }

    @Override
    public void goUp() {
        super.goUp();
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1, Sprite.player_up_2, up++, 20).getFxImage();
    }

    @Override
    public void goDown() {
        super.goDown();
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, 20).getFxImage();
    }

    //phương thức xử lý chức năng đặt bom
    public void placeBomb(){
        if (bombRemain > 0){
            int xB = (int)Math.round((x + 4) / (double) Sprite.SCALED_SIZE); //
            int yB = (int)Math.round((y + 4) / (double) Sprite.SCALED_SIZE); //Tọa độ bom
            for (Bomb bomb : bombs) // duyệt list bombs
                if (xB * Sprite.SCALED_SIZE == bomb.getX() && yB * Sprite.SCALED_SIZE == bomb.getY()) return;
            bombs.add(new Bomb(xB, yB, Sprite.bomb.getFxImage(), radius)); //tạo bom và add vào list bomb
            isAlllowerGoToBom = true; //xuyên qua bom trả về true
            bombRemain--; //trừ đi số lượng bom dự trữ sau khi đã đặt
        }
    }

    public void setBombRemain(int bombRemain) {
        this.bombRemain = bombRemain;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    @Override
    public boolean isAlive() {
        return super.isAlive();
    }

    public void die(){
        if (timeAfterDie == 20) cout--; //kể từ sau khi bom nổ đến khi 20 đơn vị thời gian thì mạng giảm xuống 1
        if (timeAfterDie <= 45){ //load ảnh bomber chết trong 45 đơn vị thời gian
              img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                        Sprite.player_dead3, timeAfterDie, 20).getFxImage();
        }
    }

    public Rectangle getBounds(){ //tạo bao cho bomber
        return new Rectangle(desX + 2, desY + 5, Sprite.SCALED_SIZE - 1, Sprite.SCALED_SIZE *3/4);
    }

    public void handleCollisions(){ //xử lý va chạm cho bomber và các thực thể
        List<Bomb> bombs = bomberman.getBombs(); //tạo list bomb
        Rectangle r1 = bomberman.getBounds(); //tạo bound cho bomber
        boolean bomberIntersectsBom = false; //biến kiểm tra va chạm bomber và bom
        for (Bomb bomb : bombs){
            Rectangle r2 = bomb.getBounds(); //tạo bound cho bomb
            if (r1.intersects(r2)){
                bomberIntersectsBom = true; //trả về true nếu bomber va chạm với bomb
            }
        }

        //Bomber vs stillObject
        for (Entity stillObject : stillObjects) { //duyệt all thực thể
            Rectangle r2 = stillObject.getBounds(); //tạo bao cho all thực thể
            if (r1.intersects(r2)){ //nếu bomber va chạm với các vật thể th trả về true
                if (bomberman.getLayer() == stillObject.getLayer() && stillObject instanceof Item) { //nếu chỉ số va chạm của bomber = thực thể và thực thể đó là item
                    if (stillObject instanceof BombItem) {
                        startBomb++;
                        bomberman.setBombRemain(startBombn); //set up lại số bom dự trữ
                        stillObjects.remove(stillObject); //xóa vật thể đó khỏi list thực thể (chính là hành động ăn item)
                    } else if (stillObject instanceof SpeedItem) { //tương tự bomb item
                        bomberman.setSpeed(startSpeed);
                        stillObjects.remove(stillObject);
                    }

                }
            }
        }
    }
}
