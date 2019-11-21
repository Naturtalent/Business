package it.naturtalent.business.office.preferences;

import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.widgets.Composite;

import it.naturtalent.business.office.odf.wizards.BusinessWriteWizard;
import it.naturtalent.e4.office.ui.preferences.OfficeSignaturePreferenceComposite;
import it.naturtalent.office.model.address.Signature;

/**
 * Eine Praeferenzliste (Checkliste) mit den Namen der BusinessSignature - Praeferenznamen,
 * 
 * @author dieter
 *
 */
public class BusinessSignaturePreferenceComposite extends OfficeSignaturePreferenceComposite
{

	public BusinessSignaturePreferenceComposite(Composite parent, int style)
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
		
	

	@Override
	public void importSignatures(List<Signature>importSignaturen)
	{	
		super.importSignatures(importSignaturen);
	}
	

}
