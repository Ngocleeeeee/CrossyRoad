package Game;
import java.util.Random;



class ManageVehicles {

    private Random rand = new Random();
    Sprite setCar(int stripYLoc) {
        Sprite car = new Sprite();
        car.setYDir(2);
        car.setYLoc(stripYLoc);
        if (rand.nextInt(2) == 1) {
            car.setXLoc(900);
            car.setXDir(-(rand.nextInt(10) + 10));
            car.setImage(GetCar("left"));

        } else {
            car.setXLoc(-200);
            car.setXDir((rand.nextInt(10) + 10));
            car.setImage(GetCar("right"));
        }

        return car;
    }

    String GetCar(String dir) {
        String carImage = "";

        if (dir.equals("left")) {
        	carImage = "/Car/Car_Left.png";
        }

        if (dir.equals("right")) {
        	carImage = "/Car/Car_Right.png";
        }

        return carImage;
    }

    Sprite setTrain(int stripYLoc) {

    	Sprite train = new Sprite();
        train.setYDir(2);
        train.setYLoc(stripYLoc);
        if (rand.nextInt(2) == 1) {
            train.setXLoc(900);
            train.setXDir(-(rand.nextInt(10) + 30));
            train.setImage(GetTrain("left"));

        } else {
            train.setXLoc(-1500);
            train.setXDir((rand.nextInt(10) + 30));
            train.setImage(GetTrain("right"));
        }

        return train;

    }

    String GetTrain(String dir) {

        String trainImage = "";

        if (dir.equals("left")) {
        	trainImage = "/Trains/Train_Blue.png";
        }

        if (dir.equals("right")) {
        	trainImage = "/Trains/Train_Blue.png";
        }

        return trainImage;
    }


}
