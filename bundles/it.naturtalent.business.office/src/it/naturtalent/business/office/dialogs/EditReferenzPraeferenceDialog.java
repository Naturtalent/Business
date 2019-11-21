package it.naturtalent.business.office.dialogs;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;

public class EditReferenzPraeferenceDialog extends InputDialog
{

	public EditReferenzPraeferenceDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, String initialValue,
			IInputValidator validator)
	{
		super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void configureShell(Shell shell)
	{
		// TODO Auto-generated method stub
		super.configureShell(shell);
		shell.setBounds(100, 100, 100, 100);
	}
	
	

}
