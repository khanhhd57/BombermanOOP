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
        _bombs = bomberman.getBoms(); //tạo bom
    }

}
