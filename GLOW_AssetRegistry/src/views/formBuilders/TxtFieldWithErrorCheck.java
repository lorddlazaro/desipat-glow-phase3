package views.formBuilders;

import javax.swing.JTextField;

public class TxtFieldWithErrorCheck extends JTextField {
	private ErrorChecking errorCheck;

	public ErrorChecking getErrorCheck() {
		return errorCheck;
	}

	public void setErrorCheck(ErrorChecking errorCheck) {
		this.errorCheck = errorCheck;
	}
}
