package week_5;

import java.io.Serializable;

public class FarmTest implements Serializable {
    private static final long serialVersionUID = -7939626668600985625L;

    public static void main(String[] args) {
        try {
            Farm farm = (Farm) ReadXml.getObject();
            Animal animal = farm.newAnimal();
            Plant plant = farm.newPlant();
            animal.show();
            plant.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

interface Animal {
    public void show();
}

class Horse implements Animal {
    public Horse() {
    }

    @Override
    public void show() {
        System.out.println("动物种类为：马");
    }
}

class Sheep implements Animal {
    public Sheep() {
    }

    @Override
    public void show() {
        System.out.println("动物种类为：羊");
    }
}

interface Plant {
    public void show();
}

class Baicai implements Plant {
    public Baicai() {
    }

    @Override
    public void show() {
        System.out.println("植物种类为：白菜");
    }
}

class Luobu implements Plant {
    public Luobu() {
    }

    @Override
    public void show() {
        System.out.println("植物种类为：萝卜");
    }
}

interface Farm {
    public Animal newAnimal();

    public Plant newPlant();
}

class XiangBiFarm implements Farm {
    public XiangBiFarm() {
    }

    @Override
    public Animal newAnimal() {
        return new Sheep();
    }

    @Override
    public Plant newPlant() {
        return new Baicai();
    }
}

class GuanShanFarm implements Farm {
    public GuanShanFarm() {
    }

    @Override
    public Animal newAnimal() {
        return new Horse();
    }

    @Override
    public Plant newPlant() {
        return new Luobu();
    }
}

