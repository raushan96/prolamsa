/**
 * 
 */
package mx.neoris.core.constants;



/**
 * @author e-lacantu
 * 
 */
public interface SAPConstants
{

	//RFC Constants
	public interface RFC
	{
		//Product Inventory Constants
		public interface PRODUCT_INVENTORY
		{
			String FUNCTION_NAME = "ZHSD_STOCK_INFORMATION";
			String CUSTOMER_TYPE = "ZIND";
			String INPUT_TABLE = "ZHSD_STOCK_IN";
			String PRODUCT_CODE = "MATNR";
			String LOCATION_CODE = "WERKS";

			String OUTPUT_TABLE = "ZHSD_STOCK_OUT";

			String STOCK_IN_BUNDLES = "WKBST";
			String ROLLING_SCHEDULE = "DATE1";
			String UNI = "UNI";

			String PROLAMSA_EXTERNAL = "1";
			String PROLAMSA_INTERNAL = "2";
		}

		//HOMEPAGE INVOICE Constants
		public interface INVOICE
		{
			String HOMEPAGE_INVOICE = "ZHSD_HOMEPAGE_INVOICE";
			//FIELDS
			String BASE_STORE = "VKORG";
			String CUSTOMER = "KUNNR";
			String ROWS = "IN_NUM_OF_INV";
			Integer NO_ROWS = 10;
			String CCODE = "CCODE";
			String CREATE_DATE = "CREATE_DATE";

			//RESULT
			String RESULT_TABLE = "TABLA_FACTURAS";
			String INPUT_TABLE = "TABLA_KUNNR";
			String INVOICE_NUMBER = "VBELN";
			String INVOICE_DATE = "FKDAT";

		}


		//DOCUMENT PDF Constants
		public interface PDF
		{
			String DOCUMENT_PDF = "ZHSD_DOCUMENT_PDF";
			//FIELDS
			String CLIENTE = "CLIENTE";
			String DOCUMENTO = "DOCUMENTO";
			String TIPO_DOCTO = "TIPO_DOCTO";
			String RUTA = "RUTA";

			//RESULT EXPORT
			String EXPORT_ARCHIVO = "ARCHIVO";

			//RFC CODES
			String QUOTE = "S";
			String SALES_ORDER = "S";
			String INVOICE = "F";
			String BILL_OF_LANDING = "B";
			String MILL_TEST_REPORT = "C";
			String PACKING_LIST = "P";
			String TALLY_REPORT = "T";
			String CREDIT_MEMO = "N";
			String DEBIT_MEMO = "N";

		}


		//ACCOUNT BALANCE Constants
		public interface ACCOUNT_BALANCE
		{
			String ACCOUNT_BALANCE1 = "ZHSD_ACCOUNT_BALANCE1";
			String ACCOUNT_BALANCE2 = "ZHSD_ACCOUNT_BALANCE2";

			//TABLES
			String INPUT_TABLE = "ZTA_BALANCE_IN";
			String OUTPUT_TABLE = "ZTA_BALANCE_OUT";

			//FIELDS-INPUT
			String BASE_STORE = "VKORG";
			String CUSTOMER = "KUNNR";

			String BASE_STORE_INPUT = "I_VKORG";
			String CUSTOMER_INPUT = "I_KUNNR";

			//Agregado Christian, para la función ZHSD_ACCOUNT_BALANCE2, 26082014
			String ESTATUS_INPUT = "I_STATUS"; // 1=Actuales 2=Vencidas
			String TIPO_DOCUMENTO_INPUT = "I_TIPODOC"; // F=Factura C=Credito D=Debito

			//FIELDS-OUTPUT
			String INVOICE_NUMBER = "INVOI";
			String INVOICE_DATE = "FKDAT";
			String INVOICE_DUE_DATE = "DUEDA";
			String NET_VALUE = "NETWR";

			String CURRENCY = "WAERS";
			String PAST_DUE = "PASDU";
			String CURRENT = "CURRE";
			String BALANCE = "BALAN";
			String CREDIT_LIMIT = "CLIMI";
			String CREDIT_AVAILABLE = "CAVAI";

			String CHARGE_OVERDUE = "CHARG";
			String CREDIT_OVERDUE = "CREDI";
			String INVOICE_OVERDUE = "INVOI";
			String PAYMENT_OVERDUE = "PAYME";

			String CHARGE_CURRENT = "CHAR1";
			String CREDIT_CURRENT = "CRED1";
			String INVOICE_CURRENT = "INVO1";
			String PAYMENT_CURRENT = "PAYM1";

			String PAST_DUE_1_30 = "IMP_30";
			String PAST_DUE_31_60 = "IMP_60";
			String PAST_DUE_61_90 = "IMP_90";
			String PAST_DUE_MORE_90 = "MAS_90";

			String SALVAL = "SALVAL";

		}

		public interface BACKORDER
		{
			String BACKORDER = "ZHSD_BACKORDER";

			//TABLES
			String INPUT_TABLE = "ZTA_BALANCE_IN";
			String OUTPUT_TABLE = "ZHSD_BACKORDER";

			//FIELDS-INPUT
			String BASE_STORE = "VKORG";
			String CUSTOMER = "KUNNR";

			//FIELDS-OUTPUT
			String PC_ORDER = "KWMENG";
			String KGS_ORDER = "CANTP";
			String PENDING_KILOS = "PENDI";
			String KILOS_READY = "READY";
			String UOM_READY = "MEINSR";
			String KILOS_LOADING = "LOADI";
			String UOM_LOADING = "MEINSL";
			String BALAANCE_KILOS = "BALAN";
			String UOM_BALANCE = "MEINSB";

			String ORDER_QTY_2 = "CANTP2";
			String PENDING_QTY_2 = "PENDING2";
			String READY_QTY_2 = "READY2";
			String LOADING_QTY_2 = "LOADING2";
			String BALANCE_2 = "BALAN2";
			String UOM_2 = "MEINS2";

			String ORDER_QTY_3 = "CANTP3";
			String PENDING_QTY_3 = "PENDING3";
			String READY_QTY_3 = "READY3";
			String LOADING_QTY_3 = "LOADING3";
			String BALANCE_3 = "BALAN3";
			String UOM_3 = "MEINS3";
		}

		public interface PRICE_CALCULATE
		{
			String PRICE_CALCULATE_FUNCTION = "ZHSD_PRICE_CALCULATION";

			//TABLES
			String INPUT_STRUCTURE = "ZHSD_PRICE_IN1";
			String INPUT_TABLE2 = "ZHSD_PRICE_IN2";
			String INPUT_TABLE3 = "ZHSD_PRICE_IN3";
			String OUTPUT_TABLE = "ZHSD_PRICE_OUT";

			//FIELDS-INPUT_STRUCTURE
			String BASE_STORE = "VKORG"; //Sales Organization / Company
			String DIST_CHANNEL = "VTWEG"; //Distribution Channel  Default=  12
			String CUSTOMER1 = "KUNNR1"; //Bill To
			String CUSTOMER2 = "KUNNR2";
			String REQUESTED_DATE = "EDATU"; //Requested Date
			String TRANSPORTATION_MODE = "VSART"; //Transportation Mode
			String UOM = "VRKME"; //UoM (UoM entered in Hybris)
			String INTERCOM = "INCO1"; //Incoterm  Default =EXW

			//FIELDS-INPUT_TABLE2
			String PRODUCT_ID = "MATNR"; //Product ID
			String QUANTITY = "KWMENG"; //Quantity (Always in Pieces)
			String UNIT_BASE = "MEINS"; //Unidad de medida base
			String LOCATION = "WERKS"; //Location
			String ROLLING_SCHEDULE = "EDATU"; //Rolling Schedule Date
			String LINE_ITEM = "POSNR"; //Line item

			//FIELDS-INPUT_TABLE3
			String LINE_ITEM2 = "POSNR"; //Line item
			String CHARACT = "CARAC"; //Characteristic
			String VALUE = "VALUE"; //Value

			//FIELDS-OUTPUT_TABLE
			String TOTAL_NET_PRICE = "NETWR"; //Total net Price
			String PRICE_PER_FEET = "KWERT";//Price per feet
			String PRICE_PER_TON = "KWERT_STON";
			String PRICE_PER_PIECE = "KWERT_SPZA";//Price per piece 
			String LINE_ITEM_OUTPUT = "POSNR"; //Line Item
			String CURRENCY = "WAERK"; //Currency Key
			String TOTAL_WEIGHT = "NETGW"; //Total Weight
			String VARIANT_CONDITION = "VARCOND"; //Variant condition
			String WEIGHT_UNIT = "GEWEI"; //Weight unit
			String LABEL_UNIT = "KWERT_UP"; //Label unit
			String DELIVERY_COST = "KWERT_FLETE";
			String ASSURANCE = "KWERT_SEGURO";
			String TAX = "KWERT_IVA";
			String IS_AVAILABLE_TO_NEGOTIATE_PRICE = "PRICNEG";
			
			String TOTAL_WEIGHT_TON = "NETGW_TON"; //Total Weight TON

			String KWERT_REF = "KWERT_REF";
			String KWERT_REFUP = "KWERT_REFUP";

		}

		public interface BACKORDERDETAIL
		{
			String BACKORDER_DETAIL = "ZHSD_BACKORDER_DETAIL";

			//TABLES
			String OUTPUT_TABLE = "ZHSD_BACKORDER2_OUT";

			//FIELDS-INPUT
			String PI_VKORG = "PI_VKORG";
			String PI_KUNNR = "PI_KUNNR";
			String PI_VBELN = "PI_VBELN";
			String PI_BSTKD = "PI_BSTKD";
			String PI_ERDAT = "PI_ERDAT";
			String PI_ERDAT2 = "PI_ERDAT2";
			String PI_KUNN2 = "PI_KUNN2";

			//FIELDS-OUTPUT
			String KUNNR = "KUNNR";
			String MATNR = "MATNR";
			String ARKTX = "ARKTX";
			String EDATU = "EDATU";
			String VBELN = "VBELN";
			String AUDAT = "AUDAT";
			String BSTKD = "BSTKD";
			String KWMENG = "KWMENG";
			String VARCOND = "VARCOND";
			String POSNR = "POSNR";
			String PENDI = "PENDI";
			String MEINS = "MEINS";
			String READY = "READY";
			String MEINSR = "MEINSR";
			String LOADI = "LOADI";
			String MEINSL = "MEINSL";
			String DESC_SHORT = "DESC_SHORT";
			String DESC_SHORT_EN = "DESC_SHORT_EN";
			String PLANT = "WERKS";

			String KDMAT = "KDMAT";
			String POSTX = "POSTX";

			String ORDER_QTY = "CANTP";
			String UOM_ORDER_QTY = "MEINSP";
			String BALANCE = "BALAN";
			String UOM_BALANCE = "MEINSB";

			String ORDER_QTY_2 = "CANTP2";
			String PENDING_QTY_2 = "PENDING2";
			String READY_QTY_2 = "READY2";
			String LOADING_QTY_2 = "LOADING2";
			String BALANCE_2 = "BALAN2";
			String UOM_2 = "MEINS2";

			String ORDER_QTY_3 = "CANTP3";
			String PENDING_QTY_3 = "PENDING3";
			String READY_QTY_3 = "READY3";
			String LOADING_QTY_3 = "LOADING3";
			String BALANCE_3 = "BALAN3";
			String UOM_3 = "MEINS3";
			String LOGISTICT = "LOGISTIC";
			String LOCATION = "LOCATION";
		}

		public interface ORDER_STATUS
		{
			String ORDER_STATUS = "ZHSD_ORDER_STATUS";

			//TABLES
			String TI_PEDIDO = "TI_PEDIDO";
			String TI_PARTIDA = "TI_PARTIDA";
			String TO_STATUS_PEDIDO = "TO_STATUS_PEDIDO";

			//FIELDS
			String BASE_STORE = "I_VKORG";
			String PEDIDO = "PEDIDO";
			String PARTIDA = "PARTIDA";

			//RESULT
			String STATUS_HEAD_ESP = "DSSTATHED_ES";
			String STATUS_HEAD_ENG = "DSSTATHED_EN";
			String STATUS_DETAIL_ESP = "DESC_SHORT";
			String STATUS_DETAIL_ENG = "DESC_SHORT_EN";
			String TIPO_DOCUMENTO = "VBTYP";



		}

		//DOCUMENT SEARCH Constants
		public interface DOCUMENT_SEARCH
		{
			String DOCUMENT_SEARCH = "ZHSD_DOCUMENT_SEARCH";

			//TABLES
			String INPUT_TABLE = "ZHSD_KUNNR_IN";
			String OUTPUT_TABLE = "ZHSD_DOCUMENT_OUT";

			//FIELDS-INPUT
			String BASE_STORE = "PI_VKORG";
			String CUSTOMER = "KUNNR";
			String DOCUMENT_TYPE = "PI_DOCTY";
			String DOCUMENT = "PI_VBELN";
			String DATE_INI = "PI_ERDATI";
			String DATE_END = "PI_ERDATF";

			//FIELDS-OUTPUT
			String CUSTOMER_ID = "KUNNR";
			String CUSTOMER_NAME = "NAME1";
			String DOCUMENT_INVOICE = "INVOICE";
			String DOCUMENT_MTR = "MTR";
			String DOCUMENT_REMISSION = "REMISSION";
			String DOCUMENT_DEBIT_NOTE = "DEBITN";
			String DOCUMENT_CREDIT_NOTE = "CREDITN";
			String DOCUMENT_TALLY = "TALLY";
			String DOCUMENT_BoL = "BOL";
			String DOCUMENT_PO = "PO";
			String DOCUMENT_SO = "SO";
			String DOCUMENT_QUOTE = "QUOTE";
			String DATE_INVOICE = "ERDAT";

		}

		public interface CREATE_ORDER_QUOTE_HYBRIS_SAP
		{
			String FUNCTION = "ZHSD_CREATE_SALESOR_QUOTE";

			//TABLES
			String INPUT_STRUCTURE = "DAT_CABECERA";
			String INPUT_TABLE1 = "DAT_ITEMS";
			String INPUT_TABLE2 = "DAT_ITEM_VARIANT";
			String INPUT_TABLE3 = "DAT_PARTNERS";
			String INPUT_TABLE4 = "DAT_SHIP";
			String OUTPUT_TABLE = "MESSAGE";
			//EMAIL unit Order Fix
			String OUTPUT_ITEM_TABLE="DAT_ITEMS";

			//FIELDS-INPUT
			String TYPEOP = "TYPEOP";

			//FIELDS-INPUT_STRUCTURE
			String SALES = "SALES";
			String DISTR_CHAN = "DISTR_CHAN";
			String PO_METH_S = "PO_METH_S";
			String INCOTERMS1 = "INCOTERMS1";
			String INCOTERMS2 = "INCOTERMS2";
			String TRATY = "TRATY";
			String PURCH_NO_C = "PURCH_NO_C";
			String PO_ATT = "PO_ATT";
			String ERDAT = "ERDAT";
			String ERNAM = "ERNAM";
			String BNAME_V = "BNAME_V";
			String VRKME = "VRKME";
			String REF_1_S = "REF_1_S";
			String VBELN = "VBELN";
			String VBELN_REF = "VBELN_REF";
			String COMM_QUOTE = "COMM_QUOTE";
			String REQ_DATE_H = "REQ_DATE_H";
			String SHIP_INST = "SHIP_INST";
			String NETPR = "NETPR";
			String WAERK = "WAERK";
			String KPEIN = "KPEIN";
			String KMEIN = "KMEIN";
			String KONDA = "KONDA";

			//FIELDS-INPUT_TABLE1
			String MATERIAL = "MATERIAL";
			String PLANT = "PLANT";
			String REQ_QTY = "REQ_QTY";
			String VRKME2 = "VRKME";
			String REQ_DATE = "REQ_DATE";

			//FIELDS-INPUT_TABLE2
			String POSNR = "POSNR";
			String ATWRT = "ATWRT";
			String ATWTB = "ATWTB";

			//FIELDS-INPUT_TABLE3
			String PARTN_ROLE = "PARTN_ROLE";
			String PARTN_NUMB = "PARTN_NUMB";

			//FIELDS-INPUT_TABLE4
			String TDLINE = "TDLINE";
			int SHIP_INST_MAX_CHAR_PER_LINE = 132;

			//FIELDS-OUTPUT
			String MESSAGE = "MESSAGE";
			String SALESDOCUMENT = "SALESDOCUMENT";

			//FIJOS
			String ORDER = "S";
			String QUOTE = "Q";

			//Pedido pendiente de aprobacion 29082014
			String STATUS = "STATUS";

			//Campo para saber si es un producto que no tiene stock 01042016
			String IHREZ = "IHREZ";

			//Negotiation amount for Axis only
			String PR_NEG = "PR_NEG";
		}

		public interface REJECTED_QUOTE_ORDER
		{
			String FUNCTION = "ZHSD_MODIFY_STATUS";

			//FIELDS-INPUT
			String STATUS = "STATUS";
			String DOCUMENT = "DOCUMENT";
			String COMMENTS = "COMMENTS";

			//FIELDS-OUTPUT
			String PROCESS = "PROCESS";
			String MESSAGE = "MESSAGE";
		}


		public interface NOTIFICATIONS
		{
			public interface CALCULATE_PRICE
			{
				//RFC: CALCULATE PRICE
				String SUBJECT = "RFC Execution Error: ";
				String BODY = "Detail of the Error: ";
			}

			public interface CREATE_ORDER
			{
				//RFC: CREATE ORDER
				String SUBJECT = "RFC Execution Error: ";
				String BODY = "Detail of the Error: ";
			}
		}


		public interface CREDIT_SCORE_CARD
		{
			String CREDIT_SCORE_CARD_FUNCTION = "ZHSD_VALORIZATION";

			//TABLES
			String INPUT_STRUCTURE = "ZHSD_PRICE_IN1";
			String INPUT_TABLE2 = "ZHSD_PRICE_IN2";
			String INPUT_TABLE3 = "ZHSD_PRICE_IN3";
			String OUTPUT_TABLE = "ZHSD_PRICE_OUT";
			String OUTPUT_TABLE_SCORE_CARD = "ZHSD_VAL";


			//FIELDS-INPUT_STRUCTURE
			String BASE_STORE = "VKORG"; //Sales Organization / Company
			String DIST_CHANNEL = "VTWEG"; //Distribution Channel  Default=  12
			String CUSTOMER1 = "KUNNR1"; //Bill To
			String CUSTOMER2 = "KUNNR2";
			String REQUESTED_DATE = "EDATU"; //Requested Date
			String TRANSPORTATION_MODE = "VSART"; //Transportation Mode
			String UOM = "VRKME"; //UoM (UoM entered in Hybris)
			String INTERCOM = "INCO1"; //Incoterm  Default =EXW

			//FIELDS-INPUT_TABLE2
			String PRODUCT_ID = "MATNR"; //Product ID
			String QUANTITY = "KWMENG"; //Quantity (Always in Pieces)
			String UNIT_BASE = "MEINS"; //Unidad de medida base
			String LOCATION = "WERKS"; //Location
			String ROLLING_SCHEDULE = "EDATU"; //Rolling Schedule Date
			String LINE_ITEM = "POSNR"; //Line item

			//FIELDS-INPUT_TABLE3
			String LINE_ITEM2 = "POSNR"; //Line item
			String CHARACT = "CARAC"; //Characteristic
			String VALUE = "VALUE"; //Value

			//FIELDS-OUTPUT_TABLE
			String TOTAL_NET_PRICE = "NETWR"; //Total net Price
			String PRICE_PER_FEET = "KWERT";//Price per feet
			String LINE_ITEM_OUTPUT = "POSNR"; //Line Item
			String CURRENCY = "WAERK"; //Currency Key
			String TOTAL_WEIGHT = "NETGW"; //Total Weight
			String VARIANT_CONDITION = "VARCOND"; //Variant condition
			String WEIGHT_UNIT = "GEWEI"; //Weight unit
			String LABEL_UNIT = "KWERT_UP"; //Label unit

			//FIELDS-OUTPUT_TABLE_SCORE_CARD
			String CREDIT_LIMIT = "CREDITLIMIT"; //credit limit
			String DELTA_TO_LIMIT = "DELTA_TO_LIMIT"; //credit used
			String SUM_OPENS = "SUM_OPENS"; //credit available
			String PERCENTAGE = "PERCENTAGE"; //credit limit used %
			String SIMUL_VALUE = "SIMUL_VALUE";
			String SIMUL_WAERK = "SIMUL_WAERK"; //currency key
			String SIMUL_PERCENTAGE = "SIMUL_PERCENTAGE"; //percentage available
			String SEMAPHORE = "IND";

		}


		public interface VALIDATE_INVOICE
		{
			String FUNCTION = "ZHSD_VALIDATE_INVOICE";

			String DETAIL_OUTPUT_TABLE = "DAT_INVOICE_DET";
			String OUTPUT_TABLE = "DAT_INVOICE";
			String MESSGE_TABLE = "MESSAGE";

			//INPUT
			String INPUT_BASE_STORE = "VKORG";
			String INPUT_CUSTOMER = "KUNNR";
			String INPUT_INVOICE = "INVOICE";

			//OUTPUT
			String OUTPUT_INVOICE = "INVOICE";
			String OUTPUT_INVOICE_P = "INVOICE_P";
			String OUTPUT_SORDER = "SORDER";
			String OUTPUT_SORDER_P = "SORDER_P";
			String OUTPUT_PRODUCT = "MATERIAL";
			String OUTPUT_BATCH = "BATCH";
			String OUTPUT_WEIGHT = "NETWEIGHT";
			String OUTPUT_WEIGHT_UNIT = "WEIGHT_UNIT";
			String OUTPUT_QUANTITY = "QUANT";
			String OUTPUT_SALES_UNIT = "SALES_UNIT";
			String OUTPUT_PLANT = "PLANT";
			String OUTPUT_SHIPTO = "SHIPTO";
			String OUTPUT_DESCRIPTION = "DESCRIPTION";

			String OUTPUT_COUNTRY = "LAND1";
			String OUTPUT_INCOTERM = "INCO1";

		}

		public interface ALERTS
		{
			String FUNCTION = "ZHSD_ALERT";

			String INPUT_TABLE = "T_ALERT";

			//INPUT
			String INPUT_ALERTCODE = "ALERTCODE";
			String INPUT_B2BUNIT = "B2BUNIT";
			String INPUT_BASE_STORE = "BASE_STORE";
			String INPUT_B2BCUSTOMER = "B2BCUSTOMER";
			String INPUT_NOTIFY = "NOTIFY";
			String INPUT_PERIODICITY = "PERIODICITY";
			String INPUT_TIME = "TIME";
			String INPUT_DAYSNOT = "DAYSNOT";
			String INPUT_DAYSOFMONTH = "DAYSOFMONTH";
			String INPUT_PRODUCTOPTIONS = "PRODUCTOPTIONS";
			String INPUT_INCLUDEMTR = "INCLUDEMTR";
			String INPUT_CCEMAIL = "CCEMAIL";

			//OUTPUT
		}

	}



	public interface Page
	{
		Integer PAGE_SIZE = 20;
	}

}
