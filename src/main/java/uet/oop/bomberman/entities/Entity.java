package uet.oop.bomberman.entities;

import java.awt.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
  //Tọa độ X tính từ góc trái trên trong Canvas
  protected int x;

  //Tọa độ Y tính từ góc trái trên trong Canvas
  protected int y;

  protected Image img;
  protected int animated = 0; //thời gian để thể hiện sự kiện
  protected int layer;
  protected boolean alive;

  //Khởi tạo đói tượng, chuyển động từ tọa độ đơn vị sang tọa độ trong Canvas
  public Entity(int xUnit, int yUnit, Image img){
    this.x = xUnit * Sprite.SCALED_SIZE;
    this.y = yUnit * Sprite.SCALED_SIZE;
    this.img = img;
  }

  public Entity(int x, int y){
    this.x = x;
    this.y = y;
  }

  //hiển thị hình ảnh lên Game thông qua ảnh, toạ độ x, y
  public void render(GraphicsContext gc){
    gc.drawImage(img, x, y + 30);
  }

  public abstract void update();

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  //trả về số va chạm của đối tượng

  public int getLayer() {
    return layer;
  }

  //Cài đặt chỉ số va chạm của đối tượng (sử dụng để xử lý va chạm)

  public void setLayer(int layer) {
    this.layer = layer;
  }

  //khởi tạo phương thức tra về Alive

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  //Tạo hit bõ cho đối tượng, sử dụng để thma gia các va chạm
  public Rectangle getBounds(){
    return new Rectangle(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
  }

  //TRả về kết quả của Alive(sống hoặc chết)
  public boolean isAlive(){
    return alive;
  }
}
