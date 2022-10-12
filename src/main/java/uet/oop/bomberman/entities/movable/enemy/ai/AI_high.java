package uet.oop.bomberman.entities.movable.enemy.ai;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;

import java.util.List;

import static uet.oop.bomberman.BombermanGame.bomberman;

public class AI_high extends AI{
    Bomber _bomber; //khai báo bomber

    Enemy _enemy; //khai báo enemy

    List<Bomb> _bombs; //Tạo 1 list về Bomb (cho tTH sau khi BomItem sẽ có nhiều đối tượng Bomb trên map)

    public AI_high(Bomber bomber, Enemy e){
        _bomber = bomber;
        _enemy = e;
        _bombs = bomberman.getBombs(); //tạo bom
    }

    protected int calculateColDirection(){
        //điều khiển di chuyển trên hàng của enemy
        if (_bomber.getX() < _enemy.getX())
            return 3;
        else if (_bomber.getX() > _enemy.getX())
            return 1;

        return -1;
    }

    protected int calculateRowDirection(){
        //điều khiển di chuyển trên cột của enemy
        if (_bomber.getY() < _enemy.getY())
            return 0;
        else if (_bomber.getY() > _enemy.getY())
            return 2;
        return -1;
    }

    @Override
    public int calculateDirection() {
        //tránh bom
        for (int i = 0; i < _bombs.size(); i++){
            if (_bombs.get(i).getX() > _enemy.getX())
                return 3;
            else if (_bombs.get(i).getX() < _enemy.getX())
                return 1;
            else if (_bombs.get(i).getY() > _enemy.getY())
                return 2;
            else
                return 0;
        }

        //đuổi theo bomberman
        int vertical = random.nextInt(2); //trả về 1 số bất kỳ từ 0 đến n-1 (từ 0 -> 1)

        if (vertical == 1){
            if (calculateColDirection() != -1)
                return calculateColDirection(); //khi tọa đọ x của cả bomber và enemy khác nhau thì trả về giá trị giúp điển khiển trên háng ngang
            else
                return calculateRowDirection(); //khi tọa đọ x của cả bomber và enemy bằng nhau thì trả về giá trị giúp điển khiển trên háng dọc
        } else {
            if (calculateRowDirection() != -1)
                return calculateRowDirection(); //khi tọa đọ x của cả bomber và enemy khác nhau thì trả về giá trị giúp điển khiển trên háng dọc
            else
                return calculateColDirection(); //khi tọa đọ x của cả bomber và enemy bằng nhau thì trả về giá trị giúp điển khiển trên háng ngang
        }
    }

}

