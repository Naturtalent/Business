package it.naturtalent.business.office;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.widgets.Display;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.meta.Meta;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.business.office.dialogs.ODFSelectBusinessVorlagenDialog;
import it.naturtalent.business.office.odf.wizards.BusinessWriteWizard;
import it.naturtalent.e4.office.ui.IODFWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.OfficeUtils;

/**
 * Diese Klasse spezialisiert den Adapter fuer die Businessanschreiben.
 * 
 * @author dieter
 *
 */
public class ODFBusinessWriteAdapter implements IODFWriteAdapter
{

	// in diesem Verzeichnis befinden sich die hardcoded Default Vorlagen
	public static final String PLUGIN_TEMPLATE_DIR = "templates"; //$NON-NLS-1$

	// Subdirectory der Vorlagen relativ zu Workspace/OFFICEDATADIR 
	public static final String ODFTEXT_TEMPLATE_DIRECTORY = "telekom"+File.separator+"templates"; //$NON-NLS-1$
	
	
	// in diesem Verzeichnis befinden sich die Default Vorlagen
	//public static final String TELEKOM_TEMPLATES_DIR = "/telekom/"+PLUGIN_TEMPLATE_DIR; //$NON-NLS-1$

	
	// Basisname der neuen Textdatei (Zieldatei)
	//public static final String BASE_ODFTEXT_FILENAME = "telekomtext.odt"; //$NON-NLS-1$
	
	// ODF - Dokument
	private TextDocument odfDocument;
	
	/* 
	 * der vom Adapter zur Verfuegung gestellte Wizard 
	 */
	@Override
	public IWizard createWizard(IEclipseContext context)
	{						
		return ContextInjectionFactory.make(BusinessWriteWizard.class, context);
	}

	/* Ein neues Dokument wird 'erzeugt' durch kopieren einer Vorlage in das Zielverzichnis.
	 * Mit 'getAutoFileName()' wird verhndert, dass ein bereits besthender Dateiname verwendet wird.
	 * Aufruf durch den NewTextHandler.
	 */
	@Override
	public File createODF(File destDir)
	{
		File newFile = null;
		
		if(destDir.isDirectory())
		{
			// neues Dokument als Kopie der Vorlage erzeugen
			newFile = createODFFile(destDir);			
			if (newFile != null)
			{
				try
				{
					// die Factoryklassname des Adapters als Property im neuen ODF-TextDokument speichern
					TextDocument odfDocument = TextDocument.loadDocument(newFile);
					Meta meta = odfDocument.getOfficeMetadata();
					meta.setUserDefinedData(ODFADAPTERFACTORY, "Text",
							ODFBusinessWriteAdapterFactory.class.getName()); // $NON-NLS-N$
					odfDocument.save(newFile);
					
					// das neue ODF-Dokument im E4Context hinterlegen
					E4Workbench.getServiceContext().set(OfficeUtils.E4CONTEXTKEY_CREATED_ODFDOCUMENT, odfDocument);

				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}		
		return newFile;
	}

	/*
	 * Der Adapter sellt einen Dialog 'ODFSelectBusinessVorlagenDialog' zur Verfügung, mit dem ein 
	 * Template ausgewählt werden kann.
	 *   
	 */
	private File createODFFile(File destDir)
	{		
		// Vorlage im Dialog auswaehlen
		ODFSelectBusinessVorlagenDialog selectLayoutDialog = new ODFSelectBusinessVorlagenDialog(Display.getDefault().getActiveShell());
		if(selectLayoutDialog.open() == ODFSelectBusinessVorlagenDialog.OK)
		{
			// Name der Vorlage wird 'Basis'-Name der neuen Datei
			String baseName = selectLayoutDialog.getSelectedName();
			
			// neue Datei im Zielverzeichnis erzeugen
			// sicherstellen, dass kein bereits vorhandener Name benutzt wird
			String newFileName = getAutoFileName(destDir, baseName+"."+ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
			File newFile = new File(destDir, newFileName);
			
			// die mit dem Dialog selektierte Vorlage
			String vorlagenName = selectLayoutDialog.getSelectedName();
			
			// Verzeichnis mit den Vorlagen
			File destOfficeWorkspaceDir = new File(
					ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
					it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFBusinessWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);			
			File destOfficeWorkspaceFile = new File(destOfficeWorkspaceDir,vorlagenName + "." 
					+ ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION);
			
			try
			{
				// Vorlage in neue Datei kopieren				
				FileUtils.copyFile(destOfficeWorkspaceFile, newFile);
				return newFile;
				
			} catch (IOException e1)
			{						
				//log.error(Messages.DesignUtils_ErrorCreateDrawFile);
				e1.printStackTrace();
			}			
		}
		return null;
	}

	@Override
	public void openODF(File odfFile)
	{
		try
		{
			odfDocument = TextDocument.loadDocument(odfFile);
			
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void closeODF()
	{
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Durch 'Hochzaehlen' von Dateienamen wird Namensgleichheit verhindert.
	 */
	protected String getAutoFileName(File destDir, String originalFileName)
	{
		String autoFileName;

		if (destDir == null)
			return "";

		int counter = 1;
		while (true)
		{
			if (counter > 1)
			{
				autoFileName = FilenameUtils.getBaseName(originalFileName)
						+ new Integer(counter) + "."
						+ FilenameUtils.getExtension(originalFileName);
			}
			else
			{
				autoFileName = originalFileName;
			}

			File res = new File(destDir, autoFileName);
			if (!res.exists())
				return autoFileName;

			counter++;
		}
	}

}
