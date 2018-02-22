/**
 * 
 */
package mx.neoris.core.services;

import java.util.List;

import mx.neoris.core.model.TransportationModeModel;



public interface NeorisTransportationModeService
{
	TransportationModeModel getTransportationModeForCode(String code) throws Exception;

	TransportationModeModel getTransportationModeForInternalCode(String internalCode) throws Exception;

	List<TransportationModeModel> getTransportationModesForCode(String code) throws Exception;

	TransportationModeModel getTransportationModeForCodeForXML(String code, String incoterm) throws Exception;
}
