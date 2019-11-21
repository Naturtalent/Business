package it.naturtalent.business.office;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import it.naturtalent.business.office.preferences.BusinessAbsenderPreferenceAdapter;
import it.naturtalent.business.office.preferences.BusinessFootNotePreferenceAdapter;
import it.naturtalent.business.office.preferences.BusinessReferenzPreferenceAdapter;
import it.naturtalent.business.office.preferences.BusinessSignaturePreferenceAdapter;
import it.naturtalent.business.office.preferences.BusinessTemplatePreferenceAdapter;
import it.naturtalent.e4.office.ui.IODFWriteAdapterFactoryRepository;
import it.naturtalent.e4.preferences.IPreferenceRegistry;


public class BusinessOfficeProcessor
{
	// Repository mit den Factories zur Erzeugung der Anschreibenapdapter 
	private @Inject @Optional IODFWriteAdapterFactoryRepository writeAdapterFactoryRepository;
	
	// Registry aller Praeferenceadapter
	private @Inject @Optional IPreferenceRegistry preferenceRegistry;
	
	@Execute
	void init (IEventBroker eventBroker, IEclipseContext context)
	{
		// ein Telekomanschreiben-FactoryAdapter im Factory-Repository eintragen
		if(writeAdapterFactoryRepository != null)
			writeAdapterFactoryRepository.getWriteAdapterFactories().add(new ODFBusinessWriteAdapterFactory());
		
		// Praeferenceadapter in das Repository eintragen
		if(preferenceRegistry != null)	
		{
			preferenceRegistry.getPreferenceAdapters().add(new BusinessTemplatePreferenceAdapter()); // Vorlagenpraeferenzen
			preferenceRegistry.getPreferenceAdapters().add(new BusinessAbsenderPreferenceAdapter()); // Absenderpraeferenzen
			preferenceRegistry.getPreferenceAdapters().add(new BusinessReferenzPreferenceAdapter()); // Referenzpraeferenzen
			preferenceRegistry.getPreferenceAdapters().add(new BusinessFootNotePreferenceAdapter());	// FootNotepraeferenzen	
			preferenceRegistry.getPreferenceAdapters().add(new BusinessSignaturePreferenceAdapter());// Signaturepraeferenzen	
		}

		// WorkspaceDir in dem die Businessvorlagen gespeichert werden 
		File destOfficeWorkspaceDir = new File(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile(),
				it.naturtalent.e4.office.ui.Activator.OFFICEDATADIR + File.separator + ODFBusinessWriteAdapter.ODFTEXT_TEMPLATE_DIRECTORY);

		if(!destOfficeWorkspaceDir.exists())
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

				// die hardcoded Vorlagen werden kopiert
				FileUtils.copyDirectory(FileUtils.toFile(urlPluginTemplate),destOfficeWorkspaceDir);
				
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
