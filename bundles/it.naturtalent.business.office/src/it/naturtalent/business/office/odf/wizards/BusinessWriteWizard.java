package it.naturtalent.business.office.odf.wizards;

import org.eclipse.core.runtime.preferences.InstanceScope;

import it.naturtalent.business.office.preferences.PreferenceUtils;
import it.naturtalent.e4.office.ui.wizards.ODFDefaultWriteAdapterWizard;

/**
 * Dieser Wizard, wird vom Adapter zur Verfuegung gestellt und ermoegllicht die
 * businessspezifischen Einstellungen.
 * 
 * @see ODFBusinessWriteAdapter
 * 
 * @author dieter
 *
 */
public class BusinessWriteWizard extends ODFDefaultWriteAdapterWizard
{
	// Businesskontext indem der Wizard arbeitet
	public static final String BUSINESS_OFFICE_CONTEXT = "businessofficecontext";


	/**
	 * Konstruktion
	 */
	public BusinessWriteWizard()
	{
		super();

		// OfficeContext auf BusinessContext einstellen (steuert einige Renderer)
		officeContext = BUSINESS_OFFICE_CONTEXT;
		
		// die Businesspraeferenzen sollen verwendet werden 
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
	}

/*
	@Override
	public void addPages()
	{		
		super.addPages();
		
		//addPage(ContextInjectionFactory.make(TelekomReferenzWizardPage.class, context));
	}
	*/
	

}
