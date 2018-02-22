/**
 * 
 */
package mx.neoris.core.exception;

/**
 * @author fdeutsch
 * 
 */
public class NeorisValidationError extends Exception
{
	private static final long serialVersionUID = 1L;

	public NeorisValidationError(final String message)
	{
		super(message);
	}
}
