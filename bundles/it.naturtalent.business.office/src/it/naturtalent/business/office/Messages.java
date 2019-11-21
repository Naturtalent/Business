package it.naturtalent.business.office;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "it.naturtalent.business.office.messages"; //$NON-NLS-1$
	public static String BusinessAbsenderPreferenceAdapter_PreferenceBusinessLabel;
	public static String BusinessAbsenderPreferenceAdapter_SenderLabel;
	public static String BusinessFootNotePreferenceAdapter_FootnoteLabel;
	public static String BusinessFootNotePreferenceAdapter_PreferenceBusinessLabel;
	public static String BusinessReferenzPreferenceAdapter_PreferenceBusinessLabel;
	public static String BusinessReferenzPreferenceAdapter_ReferenzenLabel;
	public static String BusinessSignaturePreferenceAdapter_PreferenceSignature;
	public static String BusinessSignaturePreferenceAdapter_SignatureLabel;
	public static String BusinessTemplatePreferenceAdapter_PreferenceTemplate;
	public static String BusinessTemplatePreferenceAdapter_VorlagenLabel;
	public static String ODFBusinessWriteAdapterFactory_BusinessLabel;
	public static String ODFTelekomFooterWizardPage_this_title;
	public static String ODFTelekomFooterWizardPage_this_message;
	public static String ODFTelekomFooterWizardPage_lblFooter;
	public static String ODFTelekomFooterWizardPage_lblReferenz;
	
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
