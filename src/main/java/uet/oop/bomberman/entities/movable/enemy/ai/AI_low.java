package uet.oop.bomberman.entities.movable.enemy.ai;

public class AI_low extends AI{
    @Override
    public int caculateDirection() {
        return random.nextInt(4); //trả về 1 số bất kì từ 0 - 3
    }
}
