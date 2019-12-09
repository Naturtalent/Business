package it.naturtalent.business.office.preferences;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Composite;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.business.office.ODFBusinessWriteAdapter;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.preferences.OfficeTemplatePreferenceComposite;

/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der BusinessSignature - Praeferenznamen,
 * 
 * @author dieter
 *
 */
public class BusinessTemplatePreferenceComposite extends OfficeTemplatePreferenceComposite
{

	public BusinessTemplatePreferenceComposite(Composite parent, int style)
	{		
		super(parent, style);
	}
	
	/*
	 * den PreferenceNode setzen
	 */
	protected void initPreferenceNode()
	{
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
	}

	@Override
	protected void init()
	{
		// alle Template-Files aus dem TemplateVerzeichnis einlesen
		templateNames = BusinessTemplatePreferenceAdapter.readBusinessTemplateNames();
		checkboxTableViewer.setInput(templateNames);
		updateWidgets();
	}
	
	// gibt das Verzeichnis der Vorlagen im Workspace zurueck
	
	protected File getWSTemplateDirectory()
	{
		return new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR
						+ File.separator
						+ ODFBusinessWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);
	}
	
	/*
	 * Die im PlugIn gespeicherten hardcodierten Vorlagn werden in das temporaere Arbeitsverzeichnis kopiert
	 */
	public void doRestoreDefaultPressed()
	{				
		// existiert das Verzeichnis noch nicht wird es initialisiert
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		BundleContext bundleContext = bundle.getBundleContext();
		URL urlPluginTemplate = FileLocator.find(bundleContext.getBundle(),
				new Path(ODFBusinessWriteAdapter.PLUGIN_TEMPLATE_DIR), null);
		try
		{
			// Quelle ist das Verzeichnis mit den hardcodierten Vorlagen im PlugIn
			urlPluginTemplate = FileLocator.resolve(urlPluginTemplate);
			
			// Ziel ist temporaere Arbeitsverzeichnis
			File wsTeplateDir = getTemporaryDir();
			
			// hardcodierte 'odt'-Files kopieren
			IOFileFilter suffixFilter = FileFilterUtils.or(FileFilterUtils.suffixFileFilter(
					ODFDefaultWriteAdapter.OFFICEWRITEDOCUMENT_EXTENSION));
			FileUtils.copyDirectory(FileUtils.toFile(urlPluginTemplate),wsTeplateDir,suffixFilter);	
			
			// Praeferenzliste aktualisieren
			setTemplateNames();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
