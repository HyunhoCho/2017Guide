import bwapi.Color;
import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.Flag.Enum;
import bwta.BWTA;

public class MyBotModule extends DefaultBWListener {
	
	private Mirror mirror = new Mirror();
	public static Game Broodwar;

	public void run() {
		mirror.getModule().setEventListener(this);
		mirror.startGame();
	}

	@Override
	public void onStart() {
		Broodwar = mirror.getGame();
		
		if (Broodwar.isReplay()) {
			return;
		}

		// ��ü ���� �� ����� �̺�Ʈ�� �� �ľ��ϴ� ���
		//game.enableFlag(Enum.CompleteMapInformation.getValue());

		// Ű����/���콺�� ���� �÷��̸� ������ �� �ִ� ���
		Broodwar.enableFlag(Enum.UserInput.getValue());

		// ������ ���� ������ �ϳ��� ó���ؼ� CPU �δ��� �ٿ���
		Broodwar.setCommandOptimizationLevel(1);

		// Speedups for automated play, sets the number of milliseconds bwapi spends in each frame
		// Fastest: 42 ms/frame.  1�ʿ� 24 frame. �Ϲ������� 1�ʿ� 24frame�� ���� ���Ӽӵ��� �Ѵ�
		// Normal: 67 ms/frame. 1�ʿ� 15 frame
		// As fast as possible : 0 ms/frame. CPU�� �Ҽ��ִ� ���� ���� �ӵ�. 
		Broodwar.setLocalSpeed(20);
		// frameskip�� �ø��� ȭ�� ǥ�õ� ������Ʈ ���ϹǷ� �ξ� ������
		Broodwar.setFrameSkip(0);

		System.out.println("Map analyzing started");
		BWTA.readMap();
		BWTA.analyze();
		BWTA.buildChokeNodes();
		System.out.println("Map analyzing finished");		
	}

	@Override
	public void onEnd(boolean isWinner) {
		if (isWinner){
			System.out.println("I won the game");
		} else {
			System.out.println("I lost the game");
		}
		
        System.out.println("Match ended");
        System.exit(0);
	}

	@Override
	public void onFrame() {
		
		// ��� �������� 500 frame �� �Ǿ��� �� 1���� ǥ��
		if (Broodwar.getFrameCount() == 500) {

			// ���� ������Ʈ�� ǥ��
			System.out.println("Hello Starcraft command prompt");

			// ���� ȭ�鿡 ǥ��
			Broodwar.printf("Hello Starcraft game screen");
		}

		
		if (Broodwar.isReplay()) {
			return;
		}
	}

	@Override
	public void onSendText(String text){
		// Display the text to the game
		Broodwar.sendText(text);
		
		Broodwar.printf(text);
	}

	@Override
	public void onReceiveText(Player player, String text){
		Broodwar.printf(player.getName() + " said \"" + text + "\"");
	}

	@Override
	public void onPlayerLeft(Player player){
		Broodwar.printf(player.getName() + " left the game.");
	}

	@Override
	public void onNukeDetect(Position target){
		if (target != Position.Unknown)	{
			Broodwar.drawCircleMap(target, 40, Color.Red, true);
			Broodwar.printf("Nuclear Launch Detected at " + target);
		} else {
			Broodwar.printf("Nuclear Launch Detected");
		}
	}

	@Override
	public void onUnitCreate(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " created at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitMorph(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " morphed at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitDestroy(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " destroyed at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitShow(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " showed at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitHide(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " hid at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitRenegade(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " renegaded at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitDiscover(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " discovered at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitEvade(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " evaded at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onUnitComplete(Unit unit){
		if (unit.getPlayer().isNeutral() == false) {
			Broodwar.printf(unit.getType() + " " + unit.getID() + " completed at " + unit.getTilePosition().getX() + ", " + unit.getTilePosition().getY());
		}	
	}

	@Override
	public void onSaveGame(String gameName){
		Broodwar.printf("The game was saved to \"" + gameName + "\".");
	}
}