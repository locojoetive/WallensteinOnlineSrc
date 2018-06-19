package Application.Server;


public class Statistik {
	
	public void addWinCounter(User user) {
		user.setWinCounter(user.getWinCounter() + 1);
		
	}

	public void addLoseCounter(User user) {
		user.setLoseCounter(user.getLoseCounter() + 1);
	}
	
	
	public static void main(String[] args) {
		
	}

}
