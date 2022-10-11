package uet.oop.bomberman.entities.movable.enemy.ai;

import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Enemy;

public class AI_medium extends AI{
    Bomber _bomber;

    Enemy _enemy;

    public AI_medium(Bomber bomber, Enemy enemy){
        _bomber = bomber;
        _enemy = enemy;
    }

    @Override
    public int caculateDirection() {
        int vertical = random.nextInt(2);

        if (vertical == 1) {
            if (caculateColDirection() != -1)
                return caculateColDirection();
            else
                return caculateRowDirection();
        } else {
            if (caculateRowDirection() != -1)
                return caculateRowDirection();
            else
                return caculateColDirection();
        }
    }

    protected int caculateColDirection(){
        if (_bomber.getX() < _enemy.getX())
            return 3;
        else if (_bomber.getX() > _enemy.getX())
            return 1;
        return -1;
    }

    protected int caculateRowDirection(){
        if (_bomber.getY() < _enemy.getY())
            return 0;
        else if (_bomber.getY() > _enemy.getY())
            return 2;
        return -1;
    }
}
