package ui.frame;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.views.DifficultyView;
import ui.views.GameView;
import ui.views.InputView;
import ui.views.OnDeadView;
import ui.views.RankingView;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	private JPanel currentView;
	private GameView gView;
	private InputView iView;
	private OnDeadView dView;
	private RankingView rView;
	private DifficultyView diView;
	
	public MainFrame(){
		gView = new GameView();
		dView = new OnDeadView();
		iView = new InputView();
		rView = new RankingView();
		diView = new DifficultyView();
	}
	
	public GameView getGView() {
		return gView;
	}

	public RankingView getrView() {
		return rView;
	}
	
	
}
