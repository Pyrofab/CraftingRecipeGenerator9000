package crafthelper;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gui extends JFrame {

	public Gui(Data data) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		Controller control = new Controller(this, data);
		JPanel main = new JPanel();
		JPanel craftingGrid = makeCraftingGrid(control);
		main.add(craftingGrid);
		JButton result = new JButton("result");
		result.addActionListener(control::onResultAction);
		main.add(result);
		JButton craft = new JButton("make recipe");
		craft.addActionListener(control::onCompileAction);
		main.add(craft);
		this.setTitle("Crafting-o-tron");
		this.setContentPane(main);
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private JPanel makeCraftingGrid(Controller control) {
		JPanel craftingGrid = new JPanel();
		craftingGrid.setLayout(new GridLayout(3,3));
		for(int i = 0; i < 9; i++) {
			JButton ingredient_button = new JButton();
			ingredient_button.setActionCommand(String.valueOf(i));
			ingredient_button.addActionListener(control::ingredientActionEvent);
			craftingGrid.add(ingredient_button);
		}
		return craftingGrid;
	}
}
