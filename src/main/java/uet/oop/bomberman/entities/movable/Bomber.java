package uet.oop.bomberman.entities.movable;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.items.BombItem;
import uet.oop.bomberman.entities.items.FlameItem;
import uet.oop.bomberman.entities.items.Item;
import uet.oop.bomberman.entities.items.SpeedItem;
import uet.oop.bomberman.entities.movable.enemy.Enemy;
import uet.oop.bomberman.entities.still.Portal;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static uet.oop.bomberman.BombermanGame.*;

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
        if (keyCode == KeyCode.SPACE){
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
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1, Sprite.player_down_2, down++, 20).getFxImage();
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
                        bomberman.setBombRemain(startBomb); //set up lại số bom dự trữ
                        stillObjects.remove(stillObject); //xóa vật thể đó khỏi list thực thể (chính là hành động ăn item)
                    } else if (stillObject instanceof SpeedItem) { //tương tự bomb item
                        bomberman.setSpeed(startSpeed);
                        stillObjects.remove(stillObject);
                    } else if (stillObject instanceof FlameItem) {
                        startFlame++;
                        System.out.println(startFlame);
                        bomberman.setRadius(startFlame);
                        stillObjects.remove(stillObject);
                    }
                    bomberman.stay(); //lệnh yêu cầu bomberman dừng lại (không vượt qua đc)
                } else if (bomberman.getLayer() == stillObject.getLayer() && stillObject instanceof Portal) { //chỉ số va chạm của bomber bằng thực thể va thực thể là portal
                    if (enemies.size() == 0) { //số anemy =
                        //pass level
                        level++; //lv up
                        check = true;
                    }
                } else if ((bomberman.getLayer() >= stillObject.getLayer()) && !bomberIntersectsBom) { //chỉ số va chạm của bomber > grass tại vị trí đặt bom và người không va chạm với bom
                    bomberman.move(); //cho phép đi qua bom
                    isAlllowerGoToBom = false; //trả về false (không cho phép vượt qua bom nữa)
                } else if ((bomberman.getLayer() >= stillObject.getLayer()) && bomberIntersectsBom) {
                    if (isAlllowerGoToBom == true)
                        bomberman.move(); //nếu biến trả về true thì cho phép đi qua bom
                    else
                        bomberman.stay(); //nếu biến trả về false thì đứng lại
                }
                else
                    bomberman.stay();
                break;
            }
        }

        //Bomber vs Enemy
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getBounds(); //tạo bound cho enemy
            if (r1.intersects(r2)) { //điều kiên bomber va chạm enemy
                bomberman.setAlive(false); //cài bomber chết
                if (bomberman.isAlive() == false){ //thuộc tính alive trả về false (chết)
                    if (cout >= 0) { //nếu vẫn còn mạng
                        Timer count = new Timer(); //chạy lại trò chơi
                        count.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage()); //tạo lại bomberman mới
                                count.cancel();
                            }
                        }, 500, 1);
                    }
                }
            }
        }

        //Enemies vs Bomb
        for (Enemy enemy : enemies) { //duyệt list enemy
            Rectangle r2 = enemy.getBounds(); //tạo bound cho enemy
            for (Bomb bomb : bombs) { //duyệt list bomb
                Rectangle r3 = bomb.getBounds(); //tạo bound cho bomb
                if (r2.intersects(r3)) { //enemy chạm vào bomb
                    enemy.stay(); //enemy không vượt qua đc bomb
                    break;
                }
            }
        }

        //Enemies vs StillObjects
        for (Enemy enemy : enemies) {
            Rectangle r2 = enemy.getBounds();
            for (Entity stillObject : stillObjects) {
                Rectangle r3 = stillObject.getBounds();
                if (r2.intersects(r3)) {
                    if (enemy.getLayer() >= stillObject.getLayer()) {
                        enemy.move();
                    } else {
                        enemy.stay();
                    }
                    break;
                }
            }
        }
    }

    //va chạm của flame
    public void checkCollisionFlame(){
        for (int i = 0; i < flameList.size(); i++) { // duyệt list của flame
            Rectangle r1 = flameList.get(i).getBounds(); // tạo bound cho các flame
            for (int j = 0; j < stillObjects.size(); j++) { // duyệt all thực thể
                Rectangle r2 = stillObjects.get(j).getBounds(); // tạo bound cho all thực thể
                if (r1.intersects(r2) && !(stillObjects.get(j) instanceof Item)) // nếu flame va chạm thực thể và thực thể khác item
                    stillObjects.get(j).setAlive(false); // những thực thể có thuộc tính alive sẽ dc set false -> brick, bomber, enemy sẽ bị xóa (phương thức xóa với điều kiện isAlive trả về alive = false đã dc cài riêng tại các class cụ thể)
            }
            for (int j = 0; j < enemies.size(); j++) { // duyệt list của enemy
                Rectangle r2 = enemies.get(j).getBounds();// tạo bound cho enemy
                if (r1.intersects(r2)){ // nếu flame va chạm enemy
                    //Sound.play("enemy_die"); // âm quái chết
                    enemies.get(j).setAlive(false);
                }
            }
            Rectangle r2 = bomberman.getBounds(); // tạo bound cho bomber
            if (r1.intersects(r2)) { // flame va chạm bomber
                bomberman.setAlive(false); // set thuộc tính alive về false
                //Sound.play("bomberman_die"); // âm kết thúc
                if (bomberman.isAlive() == false) { // dkien bomber chết
                    if (cout >= 0) { // mạng vẫn > 0
                        Timer count = new Timer(); // cài lại game mới
                        count.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage()); // set up player mới
                                count.cancel();
                            }
                        }, 800, 1);
                    }

                }
            }
        }
    }
}
