package crafthelper;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class Controller {
	
	private Gui view;
	private Data data;
	private final JFileChooser fileChooser;
	
	public Controller(Gui view, Data data) {
		this.view = view;
		this.data = data;
		fileChooser = new JFileChooser(new File("."));
		fileChooser.setFileFilter(new FileFilter(){
			public boolean accept(File f) {
			    if (f.isDirectory())
			        return true;

			    String extension = null;
		        String s = f.getName();
		        int i = s.lastIndexOf('.');
	            return i > 0 &&  i < s.length() - 1 && "json".equals(s.substring(i+1).toLowerCase());
			}

			@Override
			public String getDescription() {
				return "Json files";
			}
		});
	}
	
	public void ingredientActionEvent(ActionEvent e) {
		int pos = Integer.parseInt(e.getActionCommand());
		String id = (String) JOptionPane.showInputDialog(
				view, 
				"enter an id", 
				"ingredient selection", 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				null, 
				data.getIngredient(pos));
		if(id != null)
			data.setIngredient(pos, id);
	}
	
	public void onResultAction(ActionEvent e) {
		String id = (String) JOptionPane.showInputDialog(
				view, 
				"enter an id", 
				"ingredient selection", 
				JOptionPane.PLAIN_MESSAGE, 
				null, 
				null, 
				data.getResult());
		if(id != null)
			data.setResult(id);
	}
	
	public void onCompileAction(ActionEvent e) {
		if(data.getResult().isEmpty()) {
			JOptionPane.showMessageDialog(view,
				    "You need a result for this recipe.",
				    "Incomplete recipe",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		int returnVal = fileChooser.showOpenDialog(view);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if(!file.exists() && !file.getName().contains(".json"))
            	file = new File(file.getPath() + ".json");
            
            data.saveRecipe(file);
        }
	}

}
