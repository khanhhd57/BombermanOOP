package uet.oop.bomberman.entities.character.enemy.ai;

import java.util.Random;

public abstract class AI {
	
	protected Random random = new Random();

	/**
	 * Thuật toán tìm đường đi
	 */
	public abstract int calculateDirection();
}
