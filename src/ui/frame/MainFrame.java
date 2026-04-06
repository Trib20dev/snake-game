package ui.frame;

import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controllers.GameController;
import models.Game;
import models.Player;
import ui.views.DifficultyView;
import ui.views.GameView;
import ui.views.InputView;
import ui.views.OnDeadView;
import ui.views.RankingView;

@SuppressWarnings("serial")
public class MainFrame extends JFrame { // TODO igual lo cambio el cardLayout a un mainPanel despues
	private GameView gView;
	private InputView iView;
	private OnDeadView dView;
	private RankingView rView;
	private DifficultyView diView;
	private CardLayout cardLayout;

	private JPanel temporal; //Por ahora hara que sea posible ver ambos cosos

	public MainFrame() {
		configurar();
	}

	public InputView getIView() {
		return iView;
	}

	public RankingView getrView() {
		return rView;
	}

	private void initializeProperties() {
		gView = new GameView();
		dView = new OnDeadView();
		iView = new InputView();
		rView = new RankingView();
		diView = new DifficultyView();
		cardLayout = new CardLayout();
	}

	private void configurar() {
		initializeProperties();
		configureTemporal();
		configureFrame();
	}

	private void configureFrame() {
		setLayout(cardLayout);
		add(iView, "iView");
		add(diView, "diView");
		add(gView, "gView");
		add(temporal, "temporal");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	private void configureTemporal() {
		temporal = new JPanel();
		temporal.setLayout(new BoxLayout(temporal, BoxLayout.Y_AXIS));
		temporal.add(dView);
		temporal.add(rView);
	}
	
	public void setControllers(GameController gController) {
		gView.setController(gController);
		dView.setgController(gController);
		iView.setController(gController);
		diView.setController(gController);
	}
	
	public void switchToDiView() {
		cardLayout.show(getContentPane(), "diView");
	}
	
	public void switchToGView() {
		cardLayout.show(getContentPane(), "gView");
		gView.requestFocusInWindow();
	}
	
	public void switchToTemporal() {
		cardLayout.show(getContentPane(), "temporal");
	}
	
	public void switchToIView() {
		iView.clear();
		cardLayout.show(getContentPane(), "iView");
	}
	
	public void renderGame(Game game) {
		gView.render(game);
	}
	
	public void renderRanking(Player[] rankers) {
		rView.render(rankers);
	}
	
	

}
