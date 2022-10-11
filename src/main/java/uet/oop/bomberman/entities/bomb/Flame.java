package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Entity {
  private int left;
  private int right;
  private int top;
  private int down;
  public int radius;
  private int size = Sprite.SCALED_SIZE;
  private int direction; // lựa chọn
  private int time = 0; // thời gian flame tồn tại

  public Flame(int x, int y, Image image, int direction){
    super(x, y);
    this.img = image;
    this.direction = direction;
  }

  public Flame(int x, int y, Image image){
    super(x, y);
    this.img = image;
    this.radius = 1;
  }

  public Flame(int x, int y){
    super(x, y);
  }

  public void setRadius(int radius) {
    this.radius = radius; //cài bán kính của flame
  }

  @Override
  public void update(){ //phương thức kết thúc vụ nổ
    if (time < 20){ // thời gian flame tồn tại
      time++;
      setImg();
    } else
      BombermanGame.flameFist.remove(this);
  }

  public void render_exlosion(){ //chạy hết các phương thức nổ -> hiển thị vụ nổ lên game
    Right();
    Left();
    Top();
    Down();
    create_explosion();
  }

  //phương thức khởi tạo vụ nổ về mặt hình ảnh
  private void create_explosion(){
    
  }
}
