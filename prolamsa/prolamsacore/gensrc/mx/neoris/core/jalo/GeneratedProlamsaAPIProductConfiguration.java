/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at Feb 16, 2018 10:42:19 AM                    ---
 * ----------------------------------------------------------------
 */
package mx.neoris.core.jalo;

import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.Item.AttributeMode;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.neoris.core.constants.ProlamsaCoreConstants;

/**
 * Generated class for type {@link de.hybris.platform.jalo.GenericItem ProlamsaAPIProductConfiguration}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedProlamsaAPIProductConfiguration extends GenericItem
{
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.locationOfTest</code> attribute **/
	public static final String LOCATIONOFTEST = "locationOfTest";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute **/
	public static final String SPECIFICLENGTH = "specificLength";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute **/
	public static final String TESTTEMP = "testTemp";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute **/
	public static final String SPECIALDRIFTER = "specialDrifter";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.specificStencil</code> attribute **/
	public static final String SPECIFICSTENCIL = "specificStencil";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.sampleDirection</code> attribute **/
	public static final String SAMPLEDIRECTION = "sampleDirection";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute **/
	public static final String PRESSURE = "pressure";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.sampleSize</code> attribute **/
	public static final String SAMPLESIZE = "sampleSize";
	/** Qualifier of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute **/
	public static final String DURATION = "duration";
	protected static final Map<String, AttributeMode> DEFAULT_INITIAL_ATTRIBUTES;
	static
	{
		final Map<String, AttributeMode> tmp = new HashMap<String, AttributeMode>();
		tmp.put(LOCATIONOFTEST, AttributeMode.INITIAL);
		tmp.put(SPECIFICLENGTH, AttributeMode.INITIAL);
		tmp.put(TESTTEMP, AttributeMode.INITIAL);
		tmp.put(SPECIALDRIFTER, AttributeMode.INITIAL);
		tmp.put(SPECIFICSTENCIL, AttributeMode.INITIAL);
		tmp.put(SAMPLEDIRECTION, AttributeMode.INITIAL);
		tmp.put(PRESSURE, AttributeMode.INITIAL);
		tmp.put(SAMPLESIZE, AttributeMode.INITIAL);
		tmp.put(DURATION, AttributeMode.INITIAL);
		DEFAULT_INITIAL_ATTRIBUTES = Collections.unmodifiableMap(tmp);
	}
	@Override
	protected Map<String, AttributeMode> getDefaultAttributeModes()
	{
		return DEFAULT_INITIAL_ATTRIBUTES;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute.
	 * @return the duration
	 */
	public Integer getDuration(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, DURATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute.
	 * @return the duration
	 */
	public Integer getDuration()
	{
		return getDuration( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @return the duration
	 */
	public int getDurationAsPrimitive(final SessionContext ctx)
	{
		Integer value = getDuration( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @return the duration
	 */
	public int getDurationAsPrimitive()
	{
		return getDurationAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @param value the duration
	 */
	public void setDuration(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, DURATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @param value the duration
	 */
	public void setDuration(final Integer value)
	{
		setDuration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @param value the duration
	 */
	public void setDuration(final SessionContext ctx, final int value)
	{
		setDuration( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.duration</code> attribute. 
	 * @param value the duration
	 */
	public void setDuration(final int value)
	{
		setDuration( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.locationOfTest</code> attribute.
	 * @return the locationOfTest
	 */
	public String getLocationOfTest(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATIONOFTEST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.locationOfTest</code> attribute.
	 * @return the locationOfTest
	 */
	public String getLocationOfTest()
	{
		return getLocationOfTest( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.locationOfTest</code> attribute. 
	 * @param value the locationOfTest
	 */
	public void setLocationOfTest(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATIONOFTEST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.locationOfTest</code> attribute. 
	 * @param value the locationOfTest
	 */
	public void setLocationOfTest(final String value)
	{
		setLocationOfTest( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute.
	 * @return the pressure
	 */
	public Integer getPressure(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, PRESSURE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute.
	 * @return the pressure
	 */
	public Integer getPressure()
	{
		return getPressure( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @return the pressure
	 */
	public int getPressureAsPrimitive(final SessionContext ctx)
	{
		Integer value = getPressure( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @return the pressure
	 */
	public int getPressureAsPrimitive()
	{
		return getPressureAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @param value the pressure
	 */
	public void setPressure(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, PRESSURE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @param value the pressure
	 */
	public void setPressure(final Integer value)
	{
		setPressure( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @param value the pressure
	 */
	public void setPressure(final SessionContext ctx, final int value)
	{
		setPressure( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.pressure</code> attribute. 
	 * @param value the pressure
	 */
	public void setPressure(final int value)
	{
		setPressure( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.sampleDirection</code> attribute.
	 * @return the sampleDirection
	 */
	public String getSampleDirection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SAMPLEDIRECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.sampleDirection</code> attribute.
	 * @return the sampleDirection
	 */
	public String getSampleDirection()
	{
		return getSampleDirection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.sampleDirection</code> attribute. 
	 * @param value the sampleDirection
	 */
	public void setSampleDirection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SAMPLEDIRECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.sampleDirection</code> attribute. 
	 * @param value the sampleDirection
	 */
	public void setSampleDirection(final String value)
	{
		setSampleDirection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.sampleSize</code> attribute.
	 * @return the sampleSize
	 */
	public String getSampleSize(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SAMPLESIZE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.sampleSize</code> attribute.
	 * @return the sampleSize
	 */
	public String getSampleSize()
	{
		return getSampleSize( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.sampleSize</code> attribute. 
	 * @param value the sampleSize
	 */
	public void setSampleSize(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SAMPLESIZE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.sampleSize</code> attribute. 
	 * @param value the sampleSize
	 */
	public void setSampleSize(final String value)
	{
		setSampleSize( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute.
	 * @return the specialDrifter
	 */
	public Double getSpecialDrifter(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, SPECIALDRIFTER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute.
	 * @return the specialDrifter
	 */
	public Double getSpecialDrifter()
	{
		return getSpecialDrifter( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @return the specialDrifter
	 */
	public double getSpecialDrifterAsPrimitive(final SessionContext ctx)
	{
		Double value = getSpecialDrifter( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @return the specialDrifter
	 */
	public double getSpecialDrifterAsPrimitive()
	{
		return getSpecialDrifterAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @param value the specialDrifter
	 */
	public void setSpecialDrifter(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, SPECIALDRIFTER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @param value the specialDrifter
	 */
	public void setSpecialDrifter(final Double value)
	{
		setSpecialDrifter( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @param value the specialDrifter
	 */
	public void setSpecialDrifter(final SessionContext ctx, final double value)
	{
		setSpecialDrifter( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specialDrifter</code> attribute. 
	 * @param value the specialDrifter
	 */
	public void setSpecialDrifter(final double value)
	{
		setSpecialDrifter( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute.
	 * @return the specificLength
	 */
	public Double getSpecificLength(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, SPECIFICLENGTH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute.
	 * @return the specificLength
	 */
	public Double getSpecificLength()
	{
		return getSpecificLength( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @return the specificLength
	 */
	public double getSpecificLengthAsPrimitive(final SessionContext ctx)
	{
		Double value = getSpecificLength( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @return the specificLength
	 */
	public double getSpecificLengthAsPrimitive()
	{
		return getSpecificLengthAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @param value the specificLength
	 */
	public void setSpecificLength(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, SPECIFICLENGTH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @param value the specificLength
	 */
	public void setSpecificLength(final Double value)
	{
		setSpecificLength( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @param value the specificLength
	 */
	public void setSpecificLength(final SessionContext ctx, final double value)
	{
		setSpecificLength( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificLength</code> attribute. 
	 * @param value the specificLength
	 */
	public void setSpecificLength(final double value)
	{
		setSpecificLength( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificStencil</code> attribute.
	 * @return the specificStencil
	 */
	public String getSpecificStencil(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SPECIFICSTENCIL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.specificStencil</code> attribute.
	 * @return the specificStencil
	 */
	public String getSpecificStencil()
	{
		return getSpecificStencil( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificStencil</code> attribute. 
	 * @param value the specificStencil
	 */
	public void setSpecificStencil(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SPECIFICSTENCIL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.specificStencil</code> attribute. 
	 * @param value the specificStencil
	 */
	public void setSpecificStencil(final String value)
	{
		setSpecificStencil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute.
	 * @return the testTemp
	 */
	public Integer getTestTemp(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, TESTTEMP);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute.
	 * @return the testTemp
	 */
	public Integer getTestTemp()
	{
		return getTestTemp( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @return the testTemp
	 */
	public int getTestTempAsPrimitive(final SessionContext ctx)
	{
		Integer value = getTestTemp( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @return the testTemp
	 */
	public int getTestTempAsPrimitive()
	{
		return getTestTempAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @param value the testTemp
	 */
	public void setTestTemp(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, TESTTEMP,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @param value the testTemp
	 */
	public void setTestTemp(final Integer value)
	{
		setTestTemp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @param value the testTemp
	 */
	public void setTestTemp(final SessionContext ctx, final int value)
	{
		setTestTemp( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProlamsaAPIProductConfiguration.testTemp</code> attribute. 
	 * @param value the testTemp
	 */
	public void setTestTemp(final int value)
	{
		setTestTemp( getSession().getSessionContext(), value );
	}
	
}
