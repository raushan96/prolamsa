/**
 * 
 */
package mx.neoris.core.services.sharepoint.soap;



/**
 * @author eduardo.carrillo
 * 
 */
public interface NeorisSharePointConstants
{
	String LINE_SEPARATOR = "<br/>";
	String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	String DATE_FORMAT_MMDDYYYY = "MM/dd/yyyy";

	String DECIMAL_FORMAT = "###,###.##";
	String DECIMAL_FORMAT_FT_LB_PC_KS = "###,###";
	String DECIMAL_FORMAT_TON = "###,###.###";

	interface ClaimsIntegration
	{
		interface IncidentType
		{
			String Customer = "Cliente";
		}

		interface City
		{
			String _1100 = "Escobedo";
			String _1200 = "Monterrey";
			String _1300 = "Guadalajara";
			String _1500 = "San Luis Potosi";
			String _1400 = "México";
			String _1600 = "Iztapalapa";
			String _2200 = "Laredo";
			String _6100 = "Bryan";
		}

		interface Location
		{
			String MX = "Mexico";
			String USA = "USA";
		}

		interface Incoterm
		{
			String CIN = "CIN - Seguro Pagado";
			String CIP = "CIP - Porte pagado y seguro";
			String CPT = "CPT - Porte pagado";
			String EXW = "EXW - Ex-Works";
		}
	}

	interface QuoteNegotiationIntegration
	{
		interface OrderConcept
		{
			String HOT_ROLLED = "Hot Rolled";
			String COLD_ROLLED = "Cold Rolled";
			String GALVANIZED = "Galvanized";
			String GALVAMETAL = "Galvametal";
			String ALUMINIZED = "Aluminized";
			String INSURANCE = "Insurance";
			String DELIVERY_COST = "Freight";
			String TAX = "Tax";
			String TOTALS = "Totals";
		}

		interface Plant
		{
			String _6100 = "Bryan, US";
		}
	}
}
