package it.naturtalent.business.office.actions;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecp.spi.common.ui.SelectModelElementWizardFactory;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.business.office.ODFDHLCSVExportAdapter;
import it.naturtalent.business.office.dialogs.DHLExportDialog;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.project.expimp.ExpImportData;
import it.naturtalent.libreoffice.OpenLoDocument;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Kontakt;

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
		if(exportDialog.open() == DHLExportDialog.OK)
		{
			String exportPath = exportDialog.getExportPath();
			ExpImportData[] selectedData = exportDialog.getSelectedData();
			if(StringUtils.isNotEmpty(exportPath) && ArrayUtils.isNotEmpty(selectedData))
				doExport(exportPath, selectedData);
		}
			
		super.run();	
	}

	// die ausgewaehlten Kontakte werden mit dem Adapter in das DHL-Dokument geschrieben

	public void doExport(String exportPath, ExpImportData[] selectedData)
	{
		// Vorlage oeffnen, Kontaktdaten exportieren, Vorlage speichern und schliessen	
		ODFDHLCSVExportAdapter dhlAdapter = new ODFDHLCSVExportAdapter();
		
		dhlAdapter.openODF(exportPath);
		dhlAdapter.runExportData(selectedData);
		
		// vor dem Schliessen noch den Empfaenger auswaehlen
		EList<Kontakt>allKontacts = OfficeUtils.getKontakte().getKontakte();		
		Set<EObject> elements = new LinkedHashSet<EObject>();
		for(Kontakt kontact : allKontacts)
			elements.add(kontact);
		EReference eReference = AddressPackage.eINSTANCE.getKontakte_Kontakte();
		
		final Set<EObject> selectedElements = SelectModelElementWizardFactory
				.openModelElementSelectionDialog(elements, eReference.isMany());
		
		if ((selectedElements != null) && (!selectedElements.isEmpty()))
		{
			Kontakt sender = (Kontakt) selectedElements.iterator().next();
			dhlAdapter.exportSender(sender);
		}
			
		// ODF Dokument schliessen
		dhlAdapter.closeODF();
		
		// Dokument in LibreOffice oeffen
		OpenLoDocument.loadLoDocument(exportPath);
	}

	
}
