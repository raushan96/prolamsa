/**
 * Read CSV File
 */
package mx.neoris.core.util;

/**
 * @author e-lacantu
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CustomerCSVToImpex
{

	public static void main(final String[] args)
	{

		final CustomerCSVToImpex obj = new CustomerCSVToImpex();
		obj.run();

	}

	public void run()
	{

		final String csvFile = "d:/test.csv";
		final String impexFile = "d:/test.impex";
		BufferedReader br = null;
		FileWriter writer = null;
		String line = "";
		final String cvsSplitBy = ",";
		final String country = "US";

		boolean init = false;
		boolean initHeader = false;
		String rowBefore = "";
		String customer;
		String customerComp;
		String name;
		String street;
		String city;
		String region;
		String tel;
		String fax;
		String destinationType;
		String pfDesc = "";
		final List<String> key = new ArrayList<String>();

		try
		{

			writer = new FileWriter(impexFile);

			br = new BufferedReader(new FileReader(csvFile));

			while ((line = br.readLine()) != null)
			{

				// use comma as separator
				final String[] row = line.split(cvsSplitBy);

				try
				{

					customer = row[0];
					customerComp = row[1];
					name = row[2];
					street = row[3];
					city = row[4];
					region = row[5];
					tel = row[6];
					fax = row[7];
					destinationType = row[8];

					//Validate if is the begin of the file
					if (init == false)
					{
						init = true;

						writer.append("# File Generated Automatically by GenerateCSV class");
						writer.append("\n");

						writer.append("$lang=en");
						writer.append("\n\n");
					}


					if (!rowBefore.trim().equalsIgnoreCase(customer))
					{

						//insert the relation B2BUnit at the end of each client
						if (rowBefore != "")
						{
							writer.append("\n\n");
							writer.append("# Add addresses to B2BUnit");
							writer.append("\n");

							writer.append("INSERT_UPDATE B2BUnit;uid[unique=true];Addresses(&addId)[mode=append];");
							writer.append("\n");

							for (final String data : key)
							{
								writer.append(rowBefore + ";");
								writer.append(rowBefore + "_" + data);
								writer.append("\n");
							}

							writer.append("\n\n");

							//initialize variables
							initHeader = false;
							key.clear();

						}


						//Add the customerComp array					
						key.add(customerComp);

						//Starts a new customer line
						writer.append("# " + customer);
						writer.append("\n");

						writer.append("# B2BUnit");
						writer.append("\n");

						writer.append("INSERT_UPDATE B2BUnit;uid[unique=true];description;locName;&B2BUnitID;");
						writer.append("\n");
						writer.append(customer + ";");
						writer.append(name + ";");
						writer.append(name + ";");
						writer.append(customer);
						writer.append("\n\n");



						//Check if is Shipping or Billing Address
						if (destinationType.trim().equalsIgnoreCase("SOLD-TO"))
						{
							pfDesc = "billingAddress";
						}
						else
						{
							pfDesc = "shippingAddress";
						}

						writer.append("# " + pfDesc);
						writer.append("\n");

						writer.append("INSERT_UPDATE Address;streetname[unique=true];town;region(isocode);country(isocode);phone1;fax;"
								+ pfDesc + ";owner(&B2BUnitID)[unique=true];&addId;");
						writer.append("\n");
						writer.append(street + ";");
						writer.append(city + ";");
						writer.append(country + "-" + region + ";");
						writer.append(country + ";");
						writer.append(tel + ";");
						writer.append(fax + ";");
						writer.append("TRUE" + ";");
						writer.append(customer + ";");
						writer.append(customer + "_" + customerComp);
						writer.append("\n\n");


						rowBefore = customer;

					}
					else
					{

						//Add the customerComp array					
						key.add(customerComp);

						//Check if is Shipping or Billing Address
						if (destinationType.trim().equalsIgnoreCase("SOLD-TO"))
						{
							pfDesc = "billingAddress";
						}
						else
						{
							pfDesc = "shippingAddress";
						}


						if (initHeader == false)
						{
							initHeader = true;

							writer.append("# " + pfDesc);
							writer.append("\n");

							writer.append("INSERT_UPDATE Address;streetname[unique=true];town;region(isocode);country(isocode);phone1;fax;"
									+ pfDesc + ";owner(&B2BUnitID)[unique=true];&addId;");
						}

						writer.append("\n");
						writer.append(street + ";");
						writer.append(city + ";");
						writer.append(country + "-" + region + ";");
						writer.append(country + ";");
						writer.append(tel + ";");
						writer.append(fax + ";");
						writer.append("TRUE" + ";");
						writer.append(customer + ";");
						writer.append(customer + "_" + customerComp);

					}

				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}

			}


			//add the last relation B2BUnit at the end of each client
			writer.append("\n\n");
			writer.append("# Add addresses to B2BUnit");
			writer.append("\n");

			writer.append("INSERT_UPDATE B2BUnit;uid[unique=true];Addresses(&addId)[mode=append];");
			writer.append("\n");

			for (final String data : key)
			{
				writer.append(rowBefore + ";");
				writer.append(rowBefore + "_" + data);
				writer.append("\n");
			}

			writer.append("\n\n");

			//initialize variables
			initHeader = false;
			key.clear();

		}
		catch (final FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
					writer.flush();
					writer.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}