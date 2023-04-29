package Game;
	
	import java.util.Random;
	
	
	class StripGenerator {
	
		Sprite[] getStrip() {
	
			Sprite[] spriteStrip = new Sprite[8];
	
			Random gen = new Random();
	
			int y = spriteStrip.length;
	
			int x = gen.nextInt(4);
	
			switch (x) {
				case 0:
					for (int i = 0; i < y; i++) {
						Sprite strip = new Sprite("/Misc/Road.png");
						spriteStrip[i] = strip;
					}
					break;
				case 1:
					for (int i = 0; i < y; i++) {
						Sprite strip = new Sprite("/Misc/Tracks.png");
						spriteStrip[i] = strip;
					}
					break;
				case 2:
					for (int i = 0; i < y; i++) {
						//Holds random number.
						x = gen.nextInt(5);
						spriteStrip[i] = makeSpecialStrip(i, x, "/Misc/Sand.png", "/Misc/Sand.png", "/Misc/Tree_One.png");
					}
					break;
				case 3:
					for (int i = 0; i < y; i++) {
						x = gen.nextInt(5);
						spriteStrip[i] = makeSpecialStrip(i, x, "/Misc/Water.png", "/Misc/Water.png", "/Misc/Wood.png");
					}
			}
	
			return spriteStrip;
		}
	
		private Sprite makeSpecialStrip(int i, int x, 
				String background, String specialBlockOne, String specialBlockTwo) {
	
			Sprite oneBlock = new Sprite();
	
			switch (x) {
				case 0:
					oneBlock.setImage(background);
					break;
	
				case 1:
					oneBlock.setImage(background);
					break;
	
				case 2:
					oneBlock.setImage(background);
					break;
	
				case 3:
					oneBlock.setImage(specialBlockOne);
					break;
	
				case 4:
					oneBlock.setImage(specialBlockTwo);
					break;
			}
	
			return oneBlock;
		}
	
		Sprite[] getLandStrip() {
	
			Random gen = new Random();
	
			Sprite[] spriteStrip = new Sprite[8];
	
			for (int i = 0; i < 8; i++) {
				int x = gen.nextInt(5);
				spriteStrip[i] = makeSpecialStrip(i, x, "/Misc/Sand.png",
						"/Misc/Sand.png", "/Misc/Tree_One.png");
			}
			return spriteStrip;
		}
	
		Sprite[] getSpecialLandStrip() {
	
			Random gen = new Random();
	
			Sprite[] spriteStrip = new Sprite[8];
	
			for (int i = 0; i < 8; i++) {
				int x = gen.nextInt(5);
				spriteStrip[i] = makeSpecialStrip(i, x, "/Misc/Sand.png",
						"/Misc/Tree_Two.png", "/Misc/Shrub.png");
			}
	
			return spriteStrip;
		}
	
		Sprite[] getWaterStrip() {
	
			Random gen = new Random();
	
			Sprite[] spriteStrip = new Sprite[8];
	
			for (int i = 0; i < 8; i++) {
				int x = gen.nextInt(5);
				spriteStrip[i] = makeSpecialStrip(i, x, "/Misc/Water.png",
						"/Misc/Water.png", "/Misc/Wood.png");
			}
	
			return spriteStrip;
		}
	}
