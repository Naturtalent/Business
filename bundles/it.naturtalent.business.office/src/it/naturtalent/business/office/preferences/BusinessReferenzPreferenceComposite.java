package it.naturtalent.business.office.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.business.office.odf.wizards.BusinessWriteWizard;
import it.naturtalent.e4.office.ui.preferences.OfficeReferenzPreferenceComposite;

/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der BusinessReferenz - Praeferenznamen,
 * 
 * @author dieter
 *
 */
public class BusinessReferenzPreferenceComposite extends OfficeReferenzPreferenceComposite
{

	public BusinessReferenzPreferenceComposite(Composite parent, int style)
	{		
		super(parent, style);
	}

	@Override
	protected void init()
	{
		officeContext =  BusinessWriteWizard.BUSINESS_OFFICE_CONTEXT;
		instancePreferenceNode = InstanceScope.INSTANCE.getNode(PreferenceUtils.ROOT_BUSINESS_PREFERENCES_NODE);
		super.init();
	}
}
