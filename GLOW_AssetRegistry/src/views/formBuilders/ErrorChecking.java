package views.formBuilders;

import javax.swing.JTextField;

public abstract class ErrorChecking {

	JTextField textField;
	public boolean containsAtLeast1Letter(){
		return false;
	}
	
	public void setTextField(JTextField textField){
		this.textField = textField;
	}
}

class containLetters extends ErrorChecking{

	@Override
	public boolean containsAtLeast1Letter() {
		if(textField.getText().equals(""))
			return false;
		else {
			String s = textField.getText();
			char c;
			for(int i = 0 ; i<s.length();i++){
				c = s.charAt(i);
				if(Character.isLetter(c))
					return true;
			}
		}
		return false;
	}
	
}