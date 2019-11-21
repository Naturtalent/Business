package it.naturtalent.business.office.dialogs;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;

import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.dialogs.ODFSelectVorlagenDialog;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.business.office.preferences.PreferenceUtils;
import it.naturtalent.business.office.preferences.BusinessTemplatePreferenceAdapter;

/**
 * Mit diesem Dialog wird die Vorlage fuer das Business-Textdokument festgelegt.
 * 
 * @author dieter
 *
 */
public class ODFSelectBusinessVorlagenDialog extends ODFSelectVorlagenDialog
{

	public ODFSelectBusinessVorlagenDialog(Shell parentShell)
	{
		super(parentShell);		
	}
	
	/*
	 * Listet alle verfuegbaren Business-Vorlagen auf,
	 * 
	 */
	protected List<String>getInputList()
	{
		return BusinessTemplatePreferenceAdapter.readTelekomTemplateNames();
	}

	// die praeferenzierte Vorlage wird selektiert
	protected void selectPreferencedLayput()
	{
		IEclipsePreferences instancePreferenceNode = InstanceScope.INSTANCE
				.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		
		String preferencedName = instancePreferenceNode.get(OfficeDefaultPreferenceUtils.TEMPLATE_PREFERENCE,
				ODFDefaultWriteAdapter.ODFTEXT_TEMPLATE_NAME);
		
		if(StringUtils.isNotEmpty(preferencedName) && (tableViewer != null) && (!table.isDisposed()))
				tableViewer.setSelection(new StructuredSelection(preferencedName));		
	}


}
