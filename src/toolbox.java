import javax.swing.JLabel;

public class toolbox {

	public toolbox() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean needToAddLand(int x) {
		boolean ret = (x <= -436) ? true : false;
		return ret;
	}
	
	public static boolean needToAddCloud(int x) {
		boolean ret = (x <= -100) ? true : false;
		return ret;
	}
	
	public static boolean addCactusOrNot() {
		boolean ret = (Math.random() >= 0.3) ? true : false;
		return ret;
	}
	
	public static boolean addCrowOrNot() {
		boolean ret = (Math.random() >= 0.5) ? true : false;
		return ret;
	}

	public static boolean cactusCrashDetect(JLabel dinoLabel, JLabel cactusLabel) {
		try {
			int whichCase = (dinoLabel.getLocation().x <= (cactusLabel.getLocation().x + cactusLabel.getWidth()/2)) ? 1 : 2;
			switch(whichCase) {
				case 1:
					boolean Xindx = (dinoLabel.getLocation().x + dinoLabel.getWidth()) > cactusLabel.getLocation().x;
					boolean Yindx = (dinoLabel.getLocation().y + dinoLabel.getHeight()) > cactusLabel.getLocation().y;
					Thread.sleep(0); // I Don't Know Why
					
					if(Xindx && Yindx)
						return true;
					else 
						return false;
				
				case 2:
					boolean Xindx2 = dinoLabel.getLocation().x < (cactusLabel.getLocation().x + cactusLabel.getWidth());
					boolean Yindx2 = (dinoLabel.getLocation().y + dinoLabel.getHeight()) > cactusLabel.getLocation().y;
					Thread.sleep(0); // I Don't Know Why
					
					if(Xindx2 && Yindx2)
						return true;
					else 
						return false;
					
				default:
					return true;
			}
			
		} catch (InterruptedException e) {
			return true;
		}

	}
	
	public static boolean crowCrashDetect(JLabel dinoLabel, JLabel crowLabel) {
		try {
			int whichCase = (dinoLabel.getLocation().x <= (crowLabel.getLocation().x + crowLabel.getWidth()/2)) ? 1 : 2;
			switch(whichCase) {
				case 1:
					boolean Xindx = (dinoLabel.getLocation().x + dinoLabel.getWidth()) > crowLabel.getLocation().x;
					boolean Yindx = dinoLabel.getLocation().y < (crowLabel.getLocation().y + crowLabel.getHeight());
					Thread.sleep(0); // I Don't Know Why
					
					if(Xindx && Yindx)
						return true;
					else 
						return false;
				
				case 2:
					boolean Xindx2 = dinoLabel.getLocation().x < (crowLabel.getLocation().x + crowLabel.getWidth());
					boolean Yindx2 = dinoLabel.getLocation().y < (crowLabel.getLocation().y + crowLabel.getHeight());
					Thread.sleep(0); // I Don't Know Why
					
					if(Xindx2 && Yindx2)
						return true;
					else 
						return false;
					
				default:
					return true;
			}
			
		} catch (InterruptedException e) {
			return true;
		}

	}


      
}
