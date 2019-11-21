package it.naturtalent.business.office.preferences;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.widgets.Hyperlink;

import it.naturtalent.application.IPreferenceNode;
import it.naturtalent.e4.office.ui.ODFDefaultWriteAdapter;
import it.naturtalent.e4.office.ui.OfficeUtils;
import it.naturtalent.e4.office.ui.preferences.OfficeDefaultPreferenceUtils;
import it.naturtalent.e4.preferences.AbstractPreferenceAdapter;
import it.naturtalent.office.model.address.AddressPackage;
import it.naturtalent.office.model.address.Referenz;
import it.naturtalent.office.model.address.Referenzen;
import it.naturtalent.business.office.Messages;
import it.naturtalent.business.office.ODFBusinessWriteAdapter;

/**
 * Adapter zur Anpassund der BusinessOffice-Referenzen Praeferenz
 *  
 *  Eine Referenz beinhaltet zusaetzliche Angaben des Absenders (z.B. Ansprechpartner, Funktion, Durchwahlnummer etc)
 *  Die Referenzen werden als Praeferenzen unter ihrem Namen gespeichert. Die gecheckte Referenz wird beim Erstellen des
 *  Anschreibens verwendet.
 *  
 * @author dieter
 *
 */
public class BusinessReferenzPreferenceAdapter extends AbstractPreferenceAdapter
{
	// Composite Referenzcomposite
	private BusinessReferenzPreferenceComposite referenzComposite;
	
	@Override
	public String getNodePath()
	{
		return Messages.BusinessReferenzPreferenceAdapter_PreferenceBusinessLabel; //$NON-NLS-N$
	}

	@Override
	public String getLabel()
	{		
		return Messages.BusinessReferenzPreferenceAdapter_ReferenzenLabel; //$NON-NLS-N$
	}
	
	@Override
	public Composite createNodeComposite(IPreferenceNode referenceNode)
	{
		referenceNode.setTitle(getLabel());
		
		// Checkliste zur Anzeige der Referenznamen
		referenzComposite = new BusinessReferenzPreferenceComposite(referenceNode.getParentNode(), SWT.NONE);
		
		// einen Infotext hinzufuegen
		Label label = new Label(referenzComposite, SWT.NONE);
		label.setText("Referenzen definieren, einen präferenzierten selektieren"); //$NON-NLS-N$; //$NON-NLS-1$
		
		new Label(referenzComposite, SWT.NONE);
		new Label(referenzComposite, SWT.NONE);
		new Label(referenzComposite, SWT.NONE);
		
		Hyperlink hyperlinkExport = new Hyperlink(referenzComposite, SWT.NONE);
		hyperlinkExport.setText("exportieren"); //$NON-NLS-1$
		hyperlinkExport.setToolTipText("alle Referenzen exportieren"); //$NON-NLS-1$
		hyperlinkExport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
            	Referenzen referenzen = (Referenzen) OfficeUtils.findObject(AddressPackage.eINSTANCE.getReferenzen());
            	OfficeDefaultPreferenceUtils.exportPreference(referenzen);
            }
        });
		
		
		new Label(referenzComposite, SWT.NONE);
		
		Hyperlink hyperlinkImport = new Hyperlink(referenzComposite, SWT.NONE);
		hyperlinkImport.setText("importieren"); //$NON-NLS-1$
		hyperlinkImport.setToolTipText("Referenzen importieren"); //$NON-NLS-1$
		hyperlinkImport.addListener(SWT.MouseDown, new Listener() {

            @Override
            public void handleEvent(Event event) 
            {
               	List<EObject>eObjects = OfficeDefaultPreferenceUtils.importPreference();
            	if((eObjects != null) && (!eObjects.isEmpty()))
            	{
            		if(eObjects.get(0) instanceof Referenzen)
            		{     
            			Referenzen referenzen = (Referenzen) eObjects.get(0); 
            			EList<Referenz>allReferenzen = referenzen.getReferenzen();            			
            			
            			// importierte Referenzen in einer Liste sammeln
            			if(allReferenzen != null)
            			{
            				List<Referenz>importedReferenzenList = new ArrayList<Referenz>();            				
            				for(Referenz importReferenz : allReferenzen)            				
            					importedReferenzenList.add(importReferenz);
            				
            				referenzComposite.importReferenzen(importedReferenzenList);
            			}
            		}
            	}
            }
        });
		

		return referenzComposite;
	}

	@Override
	public void restoreDefaultPressed()
	{		
		// TODO Auto-generated method stub
	}

	/* 
	 * Abbruch, die aktuelle Praeferenzliste wird nicht gesoeichert.
	 * Aenderungen am EMF-Modell werden rueckgaenig gemacht 'undo'.
	 *  
	 * (non-Javadoc)
	 * @see it.naturtalent.e4.preferences.AbstractPreferenceAdapter#cancelPressed()
	 */
	@Override
	public void cancelPressed()
	{	
		referenzComposite.doCancel();
	}

	@Override
	public void appliedPressed()
	{
		// TODO Auto-generated method stub
		referenzComposite.appliedPressed();
	}
	



}

