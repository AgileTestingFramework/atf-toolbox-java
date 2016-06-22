package com.agiletestingframework.test.toolbox;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agiletestingframework.toolbox.ATFHandler;
import com.agiletestingframework.toolbox.interfaces.WebService;

public class VerifyEmailSoapService implements WebService
{
private static Logger log = LoggerFactory.getLogger(VerifyEmailSoapService.class);
	
	public static final String VERIFYEMAIL_SERVICE_KEY = "verifyEmailSOAPService";

	@Override
	public String getNamespaceURI() {
		return "http://ws.cdyne.com/";
	}

	@Override
	public String getEndPoint() {
		return "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx";
	}

	@Override
	public void teardown() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * WeatherSOAPService - Constructor
	 * Adds itself to the ATFHandler Services collection
	 */
	public VerifyEmailSoapService()
	{
		ATFHandler.getInstance().getWebServiceAutomation().addSoapService(VERIFYEMAIL_SERVICE_KEY, this);
	}
	
	/**
	 * verifyEmail
	 * @param emailToVerify email input
	 * @param licenseKey license input
	 * @return SOAPResponse
	 */
	public String verifyEmail(String emailToVerify, String licenseKey) {
		try {

			SOAPMessage request = createVerifyEmailSOAPRequest(emailToVerify, licenseKey);
			
			SOAPMessage soapResponse = (SOAPMessage) ATFHandler.getInstance().getWebServiceAutomation().sendSoapMessage(VERIFYEMAIL_SERVICE_KEY, request);
			
			String bodyXML = ATFHandler.getInstance().getWebServiceAutomation().getSoapBodyXMLFromMessage(soapResponse);
		    
			return bodyXML;
			
		} catch (Exception e) {
			log.error("Unable to get response from verify email service.", e);
		}
		
		return null;
	}
	
	/**
	 * createVerifyEmailSOAPRequest
	 * @param emailToVerify email input
	 * @param licenseKey licenseKey input
	 * @return the SOAP Request message
	 * @throws Exception
	 * 
	 * <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:example="http://ws.cdyne.com/">
	 *	   <SOAP-ENV:Header/>
	 *	   <SOAP-ENV:Body>
	 *	      <example:VerifyEmail>
	 *	         <example:email>?</example:email>
	 *	         <example:LicenseKey>?</example:LicenseKey>
	 *	      </example:VerifyEmail>
	 *	   </SOAP-ENV:Body>
	 *	</SOAP-ENV:Envelope>
	 */
	private SOAPMessage createVerifyEmailSOAPRequest(String emailToVerify, String licenseKey) throws Exception {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("example", getNamespaceURI());

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("VerifyEmail", "example");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("email", "example");
		soapBodyElem1.addTextNode(emailToVerify);
		
		SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("LicenseKey", "example");
		soapBodyElem2.addTextNode(licenseKey);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", getNamespaceURI() + "VerifyEmail");

		soapMessage.saveChanges();

		ATFHandler.getInstance().getWebServiceAutomation().logSOAPMessage(soapMessage, "Request");

		return soapMessage;
	}
}
