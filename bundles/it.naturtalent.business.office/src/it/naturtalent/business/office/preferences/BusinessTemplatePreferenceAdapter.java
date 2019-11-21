package it.naturtalent.business.office.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeTemplatePreferenceAdapter;
import it.naturtalent.business.office.Messages;
import it.naturtalent.business.office.ODFBusinessWriteAdapter;



/**
 * Adapter zur Verwaltung der Business-Vorlagen (Layouts)
 * 
 * 
 * @author dieter
 *
 */
public class BusinessTemplatePreferenceAdapter extends OfficeTemplatePreferenceAdapter
{	
	
	IDialogSettings settings = WorkbenchSWTActivator.getDefault().getDialogSettings();
	private static final String EXPORT_VORLAGEN_PATH = "businessexportvorlagenpath"; //$NON-NLS-1$

	/**
	 * Konstruktion
	 */
	public BusinessTemplatePreferenceAdapter()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		defaultPreferenceNode = DefaultScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);		
	}

	@Override
	public String getNodePath()
	{
		return Messages.BusinessTemplatePreferenceAdapter_PreferenceTemplate; //$NON-NLS-N$
	}

	@Override
	public String getLabel()
	{		
		return Messages.BusinessTemplatePreferenceAdapter_VorlagenLabel; //$NON-NLS-N$
	}

	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		// Composite beschriften
		referenceNode.setTitle(getLabel());
		
		//businessspezifisches Composite erzeugen
		templateComposite = new BusinessTemplatePreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// Telekomvorlagen anzeigen
		init(templateComposite);
				
		return templateComposite;
	}
	
	/*
	 * die Namen der BusinessVorlagen auflisten
	 */
	public static List<String> readTelekomTemplateNames()
	{
		List<String>templateNames = new ArrayList<String>();
		
		// Ziel ist WorkspaceDir mit den Businessvorlagen 
		File officeWorkspaceDir = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFBusinessWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);
		
		// nach Textdateien 'odt-Extension' filtern 
		IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils
				.suffixFileFilter(ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
		Collection<File> tempFiles = FileUtils.listFiles(officeWorkspaceDir,suffixFilter,null);
		
		// Namen der gefilterten Dateien auflisten
		for(File tmpFile : tempFiles)
			templateNames.add(FilenameUtils.getBaseName(tmpFile.getPath()));
		
		return templateNames;
	}
	
	/*
	 * Export-,Importpfad aus den Dialogsetting lesen 
	 */
	protected String getExportPathSetting(IDialogSettings settings)
	{		
		String settingPath = settings.get(EXPORT_VORLAGEN_PATH);
		return StringUtils.isNotEmpty(settingPath) ? settingPath : System.getProperty("user.dir"); //$NON-NLS-1$
	}

	/*
	 * Export-,Importpfad in Dialogsetting schreiben 
	 */
	protected void setExportPathSetting(IDialogSettings settings, String path)
	{		
		settings.put(EXPORT_VORLAGEN_PATH, path);		
	}
	
	/*
	 * Rueckgabe des Vorlagenverzeichnis
	 */
	protected File getTemporaryPath()
	{
		return templateComposite.getTemporaryDir();
	}


}

