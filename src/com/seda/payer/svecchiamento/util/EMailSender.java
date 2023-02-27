package com.seda.payer.svecchiamento.util;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.seda.emailsender.webservices.dati.EMailSenderRequestType;
import com.seda.emailsender.webservices.dati.EMailSenderResponse;
import com.seda.emailsender.webservices.source.EMailSenderInterface;
import com.seda.emailsender.webservices.source.EMailSenderServiceLocator;



public class EMailSender
{
	private EMailSenderInterface emsCaller = null;
	
	public EMailSender(String endPoint)
	{
		EMailSenderServiceLocator emsService = new EMailSenderServiceLocator();
		emsService.setEMailSenderPortEndpointAddress(endPoint);
		try 
		{
			emsCaller = (EMailSenderInterface)emsService.getEMailSenderPort();
		}
		catch (ServiceException e)
		{
			e.printStackTrace();
		}
	} 
	
	public EMailSenderResponse sendEMail(String EMailDataTOList, 
			String EMailDataCCList,
			String EMailDataCCNList,
			String EMailDataSubject,
			String EMailDataText,
			String EMailDataAttacchedFileList,
			String EmailCuteCute)
	{
		EMailSenderResponse emsRes = null;
		EMailSenderRequestType emsBean = new EMailSenderRequestType();
		
		emsBean.setEMailDataTOList(EMailDataTOList);
		emsBean.setEMailDataCCList(EMailDataCCList);
		emsBean.setEMailDataCCNList(EMailDataCCNList);
		emsBean.setEMailDataSubject(EMailDataSubject);
		emsBean.setEMailDataText(EMailDataText);
		emsBean.setEMailDataAttacchedFileList(EMailDataAttacchedFileList);
		emsBean.setEmailCuteCute(EmailCuteCute);

		try
		{
			emsRes = (EMailSenderResponse)emsCaller.getEMailSender(emsBean);
		}
		catch (RemoteException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("[Svecchiamento - sendEMail()] - Exception: " + e.getMessage());
			//System.out.println("EMilSenderFaultType: " + e.getMessage1());
		}
		
		return emsRes;
	}
}
