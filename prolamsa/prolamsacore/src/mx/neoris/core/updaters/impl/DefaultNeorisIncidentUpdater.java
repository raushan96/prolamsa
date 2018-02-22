/**
 * 
 */
package mx.neoris.core.updaters.impl;

import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.model.ModelService;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import mx.neoris.core.model.IncidentModel;
import mx.neoris.core.model.IncidentStateModel;
import mx.neoris.core.services.NeorisIncidentService;
import mx.neoris.core.services.NeorisIncidentStateService;
import mx.neoris.core.updaters.NeorisObjectUpdater;
import mx.neoris.core.xml.orders.NeorisXMLIncident;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


/**
 * @author hector.castaneda
 * 
 */
public class DefaultNeorisIncidentUpdater implements NeorisObjectUpdater
{
	private static final Logger LOG = Logger.getLogger(DefaultNeorisIncidentUpdater.class);

	public static final String INCIDENT_UPDATE_ACTION = "Update";

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "neorisIncidentService")
	private NeorisIncidentService neorisIncidentService;

	@Resource(name = "neorisIncidentStateService")
	private NeorisIncidentStateService neorisIncidentStateService;

	@Override
	public void updateFromFile(final File file) throws Exception
	{
		FileInputStream fileStream = null;

		try
		{
			fileStream = new FileInputStream(file);

			final JAXBContext jaxbContext = JAXBContext.newInstance(NeorisXMLIncident.class);
			final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			final NeorisXMLIncident incidentXML = (NeorisXMLIncident) jaxbUnmarshaller.unmarshal(file);

			if (incidentXML == null)
			{
				throw new Exception("Incident not found in file");
			}

			Registry.activateMasterTenant();

			IncidentModel incidentModel = null;

			if (INCIDENT_UPDATE_ACTION.equalsIgnoreCase(incidentXML.getAction()))
			{
				incidentModel = neorisIncidentService.getIncident(incidentXML.getId());

				if (incidentModel != null)
				{
					updateIncident(incidentModel, incidentXML);
				}
				else
				{
					throw new Exception("Incident not found, incident code: " + incidentXML.getId());
				}
			}
			else
			{
				throw new Exception("Unexpected action in incident xml: " + incidentXML.getAction() + " " + INCIDENT_UPDATE_ACTION
						+ " was expected");
			}
		}
		finally
		{
			IOUtils.closeQuietly(fileStream);
		}
	}

	public void updateIncident(final IncidentModel incidentModel, final NeorisXMLIncident incidentXML) throws Exception
	{
		Registry.activateMasterTenant();

		LOG.info("Starting update incident with id: " + incidentXML.getId());

		final String incidentStatusXML = incidentXML.getStatus();

		final IncidentStateModel incidentStateModel = neorisIncidentStateService.getState(incidentStatusXML);

		if (incidentStateModel == null)
		{
			throw new Exception("Incident status with code: " + incidentStatusXML + " not found.");
		}

		incidentModel.setState(incidentStateModel);
		modelService.save(incidentModel);

		LOG.info("Finished update incident with id: " + incidentXML.getId());
	}
}
