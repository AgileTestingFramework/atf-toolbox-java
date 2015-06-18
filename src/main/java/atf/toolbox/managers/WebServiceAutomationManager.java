package atf.toolbox.managers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import atf.toolbox.interfaces.WebService;

public class WebServiceAutomationManager
{
	private static Logger log = LoggerFactory.getLogger(WebServiceAutomationManager.class);

	private Map<String, WebService> webServices;

	/**
	 * WebServiceAutomationManager
	 */
	public WebServiceAutomationManager()
	{
		log.info("Initializing the WebServiceAutomationManager.");
		webServices = new HashMap<String, WebService>();
	}

	/**
	 * createWebServiceContext - Create the instance of the JAXBContext to use
	 * within this service
	 * 
	 * @param webServiceContextPath
	 */
	private JAXBContext createWebServiceContext(String webServiceContextPath)
	{
		try
		{
			JAXBContext jaxbContext = JAXBContext.newInstance(webServiceContextPath);
			return jaxbContext;
		}
		catch (JAXBException j)
		{
			log.error("Unable to create JAXBContext for contextServicePath " + webServiceContextPath, j);
			return null;
		}
	}

	/**
	 * addSoapService Adds a Soap Service to the collection with the key
	 * provided If the key already exists, the Soap Service will be replaced
	 * within the collection
	 * 
	 * @param key
	 *            the key for the Soap Service in the collection
	 * @param webService
	 *            instance of webService
	 */
	public void addSoapService(String key, WebService webService)
	{
		if (webServices.containsKey(key))
		{
			log.info("Replaced web service for key :" + key);
			webServices.put(key, webService);
		}
		else
		{
			log.info("Added web Service with key: " + key);
			webServices.put(key, webService);
		}
	}

	/**
	 * removeSoapService
	 * 
	 * @param key
	 *            key to locate the Soap Service to remove from the collection
	 */
	public void removeSoapService(String key)
	{
		if (webServices.containsKey(key))
		{
			try
			{
				webServices.remove(key);
				log.info("Successfully removed web service : " + key);
			}
			catch (Exception ex)
			{
				log.info("Unable to remove web service: " + key, ex);
			}
		}
		else
		{
			log.info("Unable to remove web service. No web service found with key : " + key);
		}
	}

	/**
	 * getWebSource
	 * 
	 * @param key
	 *            used to locate the web source
	 * @return the WebService located for the key provided
	 */
	public WebService getWebService(String key)
	{
		if (webServices.containsKey(key))
		{
			return webServices.get(key);
		}
		else
		{
			log.warn("Unable to locate web Service for key: " + key + " returning null.");
			return null;
		}
	}

	/**
	 * sendSoapMessage Connect to the service, will log the request and response
	 * 
	 * @param webServiceKey
	 *            the key to locate which web service to use
	 * @param request
	 *            - SoapMessage to send to the service
	 * @return - SoapMessage response
	 * @throws MalformedURLException
	 * @throws SOAPException
	 */
	public SOAPMessage sendSoapMessage(String webServiceKey, SOAPMessage request) throws MalformedURLException, SOAPException
	{
		SOAPMessage response = null;

		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		SOAPConnection connection = soapConnectionFactory.createConnection();
		try
		{

			WebService service = getWebService(webServiceKey);

			logSOAPMessage(request, "SOAP Request");

			URL endpoint = new URL(service.getEndPoint());

			response = connection.call(request, endpoint);

			logSOAPMessage(response, "SOAP Response");
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			connection.close();
		}

		return response;
	}

	/**
	 * callSoapServiceAndGenerateSOAPMessage
	 * 
	 * @param request
	 *            - request object containing the body of the soap message
	 * @return - response from service call
	 * @throws MalformedURLException
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	public SOAPMessage callSoapServiceAndGenerateSOAPMessage(String webServiceKey, Object request) throws MalformedURLException, SOAPException, JAXBException
	{
		SOAPMessage message = createSOAPRequestMessage(webServiceKey, request, null);
		SOAPMessage responseMessage = sendSoapMessage(webServiceKey, message);

		return responseMessage;
	}

	/**
	 * createSOAPRequestMessage - create a SOAP message from an object
	 * 
	 * @param webServiceKey
	 *            key to locate the web service
	 * @param request
	 *            - request body content
	 * @param action
	 *            - SOAP Action string
	 * @return SOAPMessage
	 * @throws SOAPException
	 * @throws JAXBException
	 */
	private SOAPMessage createSOAPRequestMessage(String webServiceKey, Object request, String action) throws SOAPException, JAXBException
	{
		WebService service = getWebService(webServiceKey);

		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart soapPart = message.getSOAPPart();

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("example", service.getNamespaceURI());

		if (action != null)
		{
			MimeHeaders headers = message.getMimeHeaders();
			headers.addHeader("SOAPAction", service.getNamespaceURI() + "VerifyEmail");
		}

		// SOAP Body
		SOAPBody body = message.getSOAPBody();

		marshallObject(webServiceKey, request, body);

		message.saveChanges();
		return message;
	}

	/**
	 * extractDocument
	 * 
	 * @param message
	 *            SOAPMessage with document to be extracted
	 * @return Document extracted from the message body
	 * @throws SOAPException
	 */
	public Document extractDocument(SOAPMessage message) throws SOAPException
	{
		SOAPBody soapBody = message.getSOAPBody();

		Document doc = soapBody.extractContentAsDocument();

		return doc;
	}

	/**
	 * extractDocument
	 * 
	 * @param message
	 *            SOAPMessage with document to be extracted and unmashalled
	 * @return SOAPBody extracted from the message and unmarshalled, null if
	 *         errors encountered
	 */
	public Object extractBody(String contextPath, SOAPMessage message)
	{
		return unmarshallObject(contextPath, message);
	}

	/**
	 * unmarshallObject
	 * 
	 * @param webServiceContextPath
	 *            the context path for the webservice
	 * @param message
	 *            - the document/element to unmarshal XML data from. The caller
	 *            must support at least Document and Element.
	 * @return - unmashalled object
	 */
	public Object unmarshallObject(String webServiceContextPath, SOAPMessage message)
	{
		Object answer = null;
		try
		{
			JAXBContext serviceContext = createWebServiceContext(webServiceContextPath);
			answer = (Object) serviceContext.createUnmarshaller().unmarshal(message.getSOAPBody().getFirstChild());
		}
		catch (JAXBException e)
		{
			log.error("Unmarshalling soap message: " + message, e);
		}
		catch (SOAPException e)
		{
			log.error("Unmarshalling soap message: " + message, e);
			e.printStackTrace();
		}

		return answer;
	}

	/**
	 * marshallObject
	 * 
	 * @param request
	 *            the content tree to be marshalled
	 * @param body
	 *            , will contain the updated XML content after unmashalling
	 * @throws JAXBException
	 */
	public Object marshallObject(String webServiceContextPath, Object request, SOAPBody body) throws JAXBException
	{
		JAXBContext serviceContext = createWebServiceContext(webServiceContextPath);
		serviceContext.createMarshaller().marshal(request, body);

		return request;
	}

	/**
	 * logSOAPMessage
	 * 
	 * @param message
	 *            - SOAPMessage to log
	 * @param logMsg
	 *            - Message prefix when logging
	 */
	public void logSOAPMessage(SOAPMessage message, String logMsg)
	{
		if (logMsg == null || logMsg == StringUtils.EMPTY) logMsg = "logSOAPMessage";

		if (message != null)
		{
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			try
			{
				message.writeTo(bout);
				log.trace(logMsg + ": " + bout.toString("UTF-8"));
				System.out.println(bout.toString("UTF-8"));
			}
			catch (SOAPException se)
			{
				se.printStackTrace();
				log.error("Error logging SOAPMessage: ", se);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
				log.error("Error logging SOAPMessage: ", ioe);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				log.error("Error logging SOAPMessage: ", ex);
			}
		}
		else
		{
			log.trace(logMsg + ": " + "null");
		}
	}

	/**
	 * getSoapBodyXMLFromMessage
	 * 
	 * @param response
	 *            SOAPMessage response
	 * @return String representation of the SOAPBody
	 */
	public String getSoapBodyXMLFromMessage(SOAPMessage response)
	{
		String message = null;
		try
		{
			DOMSource source = new DOMSource(response.getSOAPBody());
			StringWriter stringResult = new StringWriter();

			TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(stringResult));

			message = stringResult.toString();

		}
		catch (TransformerException | TransformerFactoryConfigurationError | SOAPException e)
		{
			log.error("Unable to parse body from response.", e);
		}

		return message;
	}

	/**
	 * teardown
	 */
	public void teardown()
	{

	}
}
