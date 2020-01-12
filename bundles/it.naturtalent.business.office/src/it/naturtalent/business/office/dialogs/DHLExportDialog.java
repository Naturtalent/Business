package it.naturtalent.business.office.dialogs;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.e4.project.expimp.dialogs.AbstractExportDialog;
import it.naturtalent.e4.project.expimp.dialogs.ExportDestinationComposite;
import it.naturtalent.office.model.address.Kontakt;
import it.naturtalent.office.model.address.Kontakte;

public class DHLExportDialog extends AbstractExportDialog
{
	
	private static final String KONTAKTEXPORTPATH_SETTING_KEY = "dhlexportkontaktpathsetting"; //$NON-NLS-N$
	
	//private ODFDHLCSVExportAdapter dhlAdapter = new ODFDHLCSVExportAdapter();
	
	private int firstRowIndex = 6;

	public DHLExportDialog()
	{
		super(shell);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	protected Control createDialogArea(Composite parent)
	{
		Control control = super.createDialogArea(parent);
		setMessage("die ausgewählten Einträge in einem Spreadsheet speichern");
		return control;
	}



	@Inject
	@Optional
	public void handleModelChangedEvent(@UIEventTopic(ExportDestinationComposite.EXPORTDESTINATION_EVENT) String exportPath)
	{
		if(StringUtils.isNotEmpty(exportPath))					
			this.exportPath = FilenameUtils.removeExtension(exportPath)+".ods";		
	}
	
	@PostConstruct
	public void postConstruct(@ Named (IServiceConstants.ACTIVE_SHELL) @ Optional Shell shell)
	{
		this.shell = shell;
	}
	
	/*
	 * Initialisiert Viewer mit den zuexportierenten Daten.
	 * 
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.project.expimp.dialogs.AbstractExportDialog#init()
	 */
	@Override
	protected void init()
	{	
		exportSettingKey = KONTAKTEXPORTPATH_SETTING_KEY;
		super.init();
		
		List<ExpImportData>lexpimpdata = new ArrayList<ExpImportData>();
		Kontakte kontakte = OfficeUtils.getKontakte();
		EList<Kontakt>kontaktList = kontakte.getKontakte();
		for(Kontakt kontakt : kontaktList)
		{
			ExpImportData expimpdata = new ExpImportData();
			expimpdata.setLabel(kontakt.getName());
			expimpdata.setData(kontakt);
			lexpimpdata.add(expimpdata);
		}
		setModelData(lexpimpdata);
	}
	
	@Override
	protected void update()
	{		
		okButton.setEnabled(checkboxTableViewer.getCheckedElements().length > 0);
	}	



}
