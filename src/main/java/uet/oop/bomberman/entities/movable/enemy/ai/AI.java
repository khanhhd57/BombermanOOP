package uet.oop.bomberman.entities.movable.enemy.ai;

import java.util.Random;

public abstract class AI {
    protected Random random = new Random(); // Đối tượng trả về 1 số bất kỳ do lớp ramdom của obj
    public abstract int caculateDirection(); //Trả về 1 số -> Sử dụng để quyết định hướng đi của ênmy
}
