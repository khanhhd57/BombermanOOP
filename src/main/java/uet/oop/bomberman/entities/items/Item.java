package uet.oop.bomberman.entities.items;

import uet.oop.bomberman.entities.still.StillEntity;

import java.awt.*;

public abstract class Item extends StillEntity {
    public Item(int xUnit, int yUnit, Image img){
        super(xUnit, yUnit, img);
        setLayer(1); //chỉ số va chạm của các item
    }

}
