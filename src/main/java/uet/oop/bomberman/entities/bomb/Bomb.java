package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Character;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends Character {

  private int timeCounter = 0;
  int radius;

  public Bomb(int xUnit, int yUnit, Image img) {
    super(xUnit, yUnit, img);
    setLayer(2); // chỉ số va cha của bomb
  }

  public Bomb(int xUnit, int yUnit, Image img, int radius) {
    super(xUnit, yUnit, img);
    setLayer(2); // chỉ số va chạm của bomb
    this.radius = radius; // cài đặt chỉ số bán kính nổ
  }

  @Override
  public void update() {
    if (timeCounter++ == 120) // thời gian bomb sẽ nổ sau khi đặt
      explodeUpgrade();
    // cài load ảnh bomb trước khi bomb nổ
    img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeCounter, 60)
        .getFxImage();
  }

  public void explodeUpgrade(){
    Flame f = new Flame(x, y); // tạo flame khi bomb nổ
    f.setRadius(radius); // bán kính nổ
    f.render_explosion(); // chạy các chức năng của Flame
    alive = false; /* tất cả những thực thể có thể bị tiêu diệt bởi Flame
                    (những thực thể đó đều có thuộc tính aliva),
                    sau khi va chạm sẽ trả về alive = flase*/
    //Sound.play("bom_explode"); //âm thanh bomb nổ
  }
}