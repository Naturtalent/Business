package it.naturtalent.business.office.actions;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.business.office.dialogs.DHLExportDialog;
import it.naturtalent.e4.office.ui.dialogs.KontakteExportDialog;

/**
 * Aktuelle Exportfunktion fuer Kontakte
 * 
 * @author dieter
 *
 */
public class DHLExportAction extends Action
{
	@Inject
	@Optional
	private IEclipseContext context;
	
	private Shell shell;
	
	@PostConstruct
	private void postConstruct(@Named(IServiceConstants.ACTIVE_SHELL) @Optional Shell shell)
	{
		this.shell = shell;
	}


	@Override
	public void run()
	{
		DHLExportDialog exportDialog = ContextInjectionFactory.make(DHLExportDialog.class, context);
		exportDialog.open();
		super.run();	
	}

	
}
