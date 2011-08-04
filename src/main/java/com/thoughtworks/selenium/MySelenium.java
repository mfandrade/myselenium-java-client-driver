package com.thoughtworks.selenium;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.SeleneseTestBase;

public class MySelenium extends DefaultSelenium
{

	private static final int	ONE_SECOND	= 1000;
	private int					timeout		= 30000;

	/** timeout in milliseconds */

	/**
	 * Uses a CommandBridgeClient, specifying a server host/port, a command to
	 * launch the browser, and a starting URL for the browser.
	 * 
	 * <p>
	 * <i>browserStartCommand</i> may be any one of the following:
	 * <ul>
	 * <li><code>*firefox [absolute path]</code> - Automatically launch a new
	 * Firefox process using a custom Firefox profile. This profile will be
	 * automatically configured to use the Selenium Server as a proxy and to
	 * have all annoying prompts ("save your password?" "forms are insecure"
	 * "make Firefox your default browser?" disabled. You may optionally specify
	 * an absolute path to your firefox executable, or just say "*firefox". If
	 * no absolute path is specified, we'll look for firefox.exe in a default
	 * location (normally c:\program files\mozilla firefox\firefox.exe), which
	 * you can override by setting the Java system property
	 * <code>firefoxDefaultPath</code> to the correct path to Firefox.</li>
	 * <li><code>*iexplore [absolute path]</code> - Automatically launch a new
	 * Internet Explorer process using custom Windows registry settings. This
	 * process will be automatically configured to use the Selenium Server as a
	 * proxy and to have all annoying prompts ("save your password?"
	 * "forms are insecure" "make Firefox your default browser?" disabled. You
	 * may optionally specify an absolute path to your iexplore executable, or
	 * just say "*iexplore". If no absolute path is specified, we'll look for
	 * iexplore.exe in a default location (normally c:\program files\internet
	 * explorer\iexplore.exe), which you can override by setting the Java system
	 * property <code>iexploreDefaultPath</code> to the correct path to Internet
	 * Explorer.</li>
	 * <li><code>/path/to/my/browser [other arguments]</code> - You may also
	 * simply specify the absolute path to your browser executable, or use a
	 * relative path to your executable (which we'll try to find on your path).
	 * <b>Warning:</b> If you specify your own custom browser, it's up to you to
	 * configure it correctly. At a minimum, you'll need to configure your
	 * browser to use the Selenium Server as a proxy, and disable all
	 * browser-specific prompting.
	 * </ul>
	 * 
	 * @param serverHost
	 *            the host name on which the Selenium Server resides
	 * @param serverPort
	 *            the port on which the Selenium Server is listening
	 * @param browserStartCommand
	 *            the command string used to launch the browser, e.g.
	 *            "*firefox", "*iexplore" or
	 *            "c:\\program files\\internet explorer\\iexplore.exe"
	 * @param browserURL
	 *            the starting URL including just a domain name. We'll start the
	 *            browser pointing at the Selenium resources on this URL, e.g.
	 *            "http://www.google.com" would send the browser to
	 *            "http://www.google.com/selenium-server/SeleneseRunner.html"
	 * 
	 * @see DefaultSelenium#DefaultSelenium(String, int, String, String)
	 */
	public MySelenium(String host, int port, String browser, String url)
	{
		super(host, port, browser, url);
	}

	/**
	 * Uses an arbitrary CommandProcessor.
	 * 
	 * @see DefaultSelenium#DefaultSelenium(CommandProcessor)
	 */
	public MySelenium(CommandProcessor processor)
	{
		super(processor);
	}

	/**
	 * @see SeleneseTestBase#join(String[], char)
	 */
	private static String join(String[] sa, char c)
	{
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < sa.length; j++)
		{
			sb.append(sa[j]);
			if (j < sa.length - 1)
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String join(String[] sa, String sep)
	{
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < sa.length; j++)
		{
			sb.append(sa[j]);
			if (j < sa.length - 1)
			{
				sb.append(sep);
			}
		}
		return sb.toString();
	}

	@Override
	public void setTimeout(String timeout)
	{
		try
		{
			this.timeout = Integer.parseInt(timeout);
			super.setTimeout(timeout);
		} catch (Exception e)
		{}
	}

	private void sleep(int miliseconds)
	{
		try
		{
			Thread.sleep(miliseconds);

		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

	private void fail(String msg)
	{
		throw new AssertionError(msg);
	}

	// waitFor... --------------------------------------------------------------
	/**
	 * @see DefaultSelenium#waitForFrameToLoad(String, String)
	 */
	public void waitForFrameToLoad(String frameAddress)
	{
		waitForFrameToLoad(frameAddress, String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#waitForPageToLoad(String)
	 */
	public void waitForPageToLoad()
	{
		waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#getAlert()
	 */
	public void waitForAlert(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getAlert()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isAlertPresent()
	 */
	public void waitForAlertNotPresent()
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isAlertPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isAlertPresent()
	 */
	public void waitForAlertPresent()
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isAlertPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllButtons(String)
	 */
	public void waitForAllButtons(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllButtons(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllFields(String)
	 */
	public void waitForAllFields(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllFields(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllLinks(String)
	 */
	public void waitForAllLinks(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllLinks(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowIds(String)
	 */
	public void waitForAllWindowIds(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllWindowIds(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowNames(String)
	 */
	public void waitForAllWindowNames(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllWindowNames(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowTitles(String)
	 */
	public void waitForAllWindowTitles(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAllWindowTitles(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAttribute(String)
	 */
	public void waitForAttribute(String attributeLocator, String pattern)
	{
		for (int second = 0;; second++)
		{
			if (second >= timeout)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getAttribute(attributeLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAttributeFromAllWindows(String)
	 */
	public void waitForAttributeFromAllWindows(String attributeName, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getAttributeFromAllWindows(attributeName), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getBodyText()
	 */
	public void waitForBodyText(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getBodyText()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isChecked(String)
	 */
	public void waitForChecked(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isChecked(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getConfirmation()
	 */
	public void waitForConfirmation(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getConfirmation()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isConfirmationPresent()
	 */
	public void waitForConfirmationNotPresent()
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isConfirmationPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isConfirmationPresent()
	 */
	public void waitForConfirmationPresent()
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isConfirmationPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCookie()
	 */
	public void waitForCookie(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getCookie()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCookieByName(String)
	 */
	public void waitForCookieByName(String name, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getCookieByName(name)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isCookiePresent(String)
	 */
	public void waitForCookieNotPresent(String name)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isCookiePresent(name))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isCookiePresent(String)
	 */
	public void waitForCookiePresent(String name)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isCookiePresent(name))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCursorPosition(String)
	 */
	public void waitForCursorPosition(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getCursorPosition(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isEditable(String)
	 */
	public void waitForEditable(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isEditable(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementHeight(String)
	 */
	public void waitForElementHeight(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getElementHeight(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementIndex(String)
	 */
	public void waitForElementIndex(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getElementIndex(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isElementPresent()
	 */
	public void waitForElementNotPresent(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isElementPresent(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementPositionLeft(String)
	 */
	public void waitForElementPositionLeft(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getElementPositionLeft(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementPositionTop(String)
	 */
	public void waitForElementPositionTop(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getElementPositionTop(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isElementPresent(String)
	 */
	public void waitForElementPresent(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isElementPresent(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementWidth(String)
	 */
	public void waitForElementWidth(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getElementWidth(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getEval(String)
	 */
	public void waitForEval(String script, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getEval(script)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getExpression(String)
	 */
	public void waitForExpression(String expression, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getExpression(expression)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getHtmlSource(String)
	 */
	public void waitForHtmlSource(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getHtmlSource()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getLocation()
	 */
	public void waitForLocation(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getLocation()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getMouseSpeed()
	 */
	public void waitForMouseSpeed(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getMouseSpeed()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isOrdered(String, String)
	 */
	public void waitForOrdered(String locator1, String locator2)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isOrdered(locator1, locator2))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getPrompt()
	 */
	public void waitForPrompt(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getPrompt()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isPromptPresent()
	 */
	public void waitForPromptNotPresent(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isPromptPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isPromptPresent()
	 */
	public void waitForPromptPresent(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isPromptPresent())
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectOptions(String)
	 */
	public void waitForSelectOptions(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(join(getSelectOptions(selectLocator), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedId(String)
	 */
	public void waitForSelectedId(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedId(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedIndex(String)
	 */
	public void waitForSelectedIndex(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedIndex(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedIndexes(String)
	 */
	public void waitForSelectedIndexes(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedIndexes(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedLabel(String)
	 */
	public void waitForSelectedLabel(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedLabel(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedLabels(String)
	 */
	public void waitForSelectedLabels(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedLabels(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedValue(String)
	 */
	public void waitForSelectedValue(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedValue(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedValues(String)
	 */
	public void waitForSelectedValues(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getSelectedValues(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getTable(String)
	 */
	public void waitForTable(String tableCellAddress, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getTable(tableCellAddress)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getText(String)
	 */
	public void waitForText(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getText(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isTextPresent()
	 */
	public void waitForTextNotPresent(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isTextPresent(pattern))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isTextPresent()
	 */
	public void waitForTextPresent(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isTextPresent(pattern))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getTitle()
	 */
	public void waitForTitle(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getTitle()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getValue(String)
	 */
	public void waitForValue(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getValue(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isVisible()
	 */
	public void waitForVisible(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (isVisible(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getWhetherThisFrameMatchFrameExpression(String,
	 *      String)
	 */
	public void waitForWhetherThisFrameMatchFrameExpression(String currentFrameString, String target, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getWhetherThisFrameMatchFrameExpression(currentFrameString, target)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getWhetherThisWindowMatchWindowExpression(String,
	 *      String)
	 */
	public void waitForWhetherThisWindowMatchWindowExpression(String currentWindowString, String target, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getWhetherThisWindowMatchWindowExpression(currentWindowString, target)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getXpathCount(String)
	 */
	public void waitForXpathCount(String xpath, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (pattern.equals(getXpathCount(xpath)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAlert()
	 */
	public void waitForNotAlert(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getAlert()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllButtons(String)
	 */
	public void waitForNotAllButtons(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllButtons(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllFields(String)
	 */
	public void waitForNotAllFields(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllFields(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllLinks(String)
	 */
	public void waitForNotAllLinks(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllLinks(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowIds(String)
	 */
	public void waitForNotAllWindowIds(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllWindowIds(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowNames(String)
	 */
	public void waitForNotAllWindowNames(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllWindowNames(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAllWindowTitles(String)
	 */
	public void waitForNotAllWindowTitles(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAllWindowTitles(), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAttribute(String)
	 */
	public void waitForNotAttribute(String attributeLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getAttribute(attributeLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getAttributeFromAllWindows(String)
	 */
	public void waitForNotAttributeFromAllWindows(String attributeName, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getAttributeFromAllWindows(attributeName), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getBodyText()
	 */
	public void waitForNotBodyText(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getBodyText()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isChecked()
	 */
	public void waitForNotChecked(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isChecked(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getConfirmation()
	 */
	public void waitForNotConfirmation(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getConfirmation()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCookie()
	 */
	public void waitForNotCookie(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getCookie()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCookieByName(String)
	 */
	public void waitForNotCookieByName(String name, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getCookieByName("name")))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getCursorPosition(String)
	 */
	public void waitForNotCursorPosition(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getCursorPosition(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isEditable(String)
	 */
	public void waitForNotEditable(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isEditable(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementHeight(String)
	 */
	public void waitForNotElementHeight(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getElementHeight(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementIndex(String)
	 */
	public void waitForNotElementIndex(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getElementIndex(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementPositionLeft(String)
	 */
	public void waitForNotElementPositionLeft(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getElementPositionLeft(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementPositionTop(String)
	 */
	public void waitForNotElementPositionTop(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getElementPositionTop(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getElementWidth(String)
	 */
	public void waitForNotElementWidth(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getElementWidth(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getHtmlSource(String)
	 */
	public void waitForNotHtmlSource(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getHtmlSource()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getLocation()
	 */
	public void waitForNotLocation(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getLocation()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getMouseSpeed()
	 */
	public void waitForNotMouseSpeed(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getMouseSpeed()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isOrdered(String, String)
	 */
	public void waitForNotOrdered(String locator1, String locator2)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isOrdered(locator1, locator2))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getPrompt()
	 */
	public void waitForNotPrompt(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getPrompt()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectOptions(String)
	 */
	public void waitForNotSelectOptions(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(join(getSelectOptions(selectLocator), ',')))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedId(String)
	 */
	public void waitForNotSelectedId(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedId(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedIndex(String)
	 */
	public void waitForNotSelectedIndex(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedIndex(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedIndexes(String)
	 */
	public void waitForNotSelectedIndexes(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedIndexes(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedLabel(String)
	 */
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedLabel(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedLabels(String)
	 */
	public void waitForNotSelectedLabels(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedLabels(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedValue(String)
	 */
	public void waitForNotSelectedValue(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedValue(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getSelectedValues(String)
	 */
	public void waitForNotSelectedValues(String selectLocator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getSelectedValues(selectLocator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getTable(String)
	 */
	public void waitForNotTable(String tableCellAddress, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getTable(tableCellAddress)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getText(String)
	 */
	public void waitForNotText(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getText(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getTitle()
	 */
	public void waitForNotTitle(String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getTitle()))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getValue(String)
	 */
	public void waitForNotValue(String locator, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getValue(locator)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#isVisible()
	 */
	public void waitForNotVisible(String locator)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!isVisible(locator))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getWhetherThisFrameMatchFrameExpression(String,
	 *      String)
	 */
	public void waitForNotWhetherThisFrameMatchFrameExpression(String currentFrameString, String target, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getWhetherThisFrameMatchFrameExpression(currentFrameString, target)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getWhetherThisWindowMatchWindowExpression(String,
	 *      String)
	 */
	public void waitForNotWhetherThisWindowMatchWindowExpression(String currentWindowString, String target, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getWhetherThisWindowMatchWindowExpression(currentWindowString, target)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	/**
	 * @see DefaultSelenium#getXpathCount(String)
	 */
	public void waitForNotXpathCount(String xpath, String pattern)
	{
		int time = timeout / 1000;
		for (int second = 0;; second++)
		{
			if (second >= time)
				fail("TIMEOUT!");
			try
			{
				if (!pattern.equals(getXpathCount(xpath)))
					break;
			} catch (Exception e)
			{}
			sleep(ONE_SECOND);
		}
	}

	// ...AndWait's ------------------------------------------------------------
	/**
	 * @see DefaultSelenium#addLocationStrategy(String, String)
	 */
	public void addLocationStrategyAndWait(String strategyName, String functionDefinition)
	{
		super.addLocationStrategy(strategyName, functionDefinition);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#addScript(String, String)
	 */
	public void addScriptAndWait(String scriptContent, String scriptTagId)
	{
		super.addScript(scriptContent, scriptTagId);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#addSelection(String, String)
	 */
	public void addSelectionAndWait(String locator, String optionLocator)
	{
		super.addSelection(locator, optionLocator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#allowNativeXpathAndWait(String)
	 */
	public void allowNativeXpathAndWait(String allow)
	{
		super.allowNativeXpath(allow);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#altKeyDown()
	 */
	public void altKeyDownAndWait()
	{
		super.altKeyDown();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#altKeyUp()
	 */
	public void altKeyUpAndWait()
	{
		super.altKeyUp();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#captureEntirePageScreenshot(String, String)
	 */
	public void captureEntirePageScreenshotAndWait(String filename, String kwargs)
	{
		super.captureEntirePageScreenshot(filename, kwargs);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#check(String)
	 */
	public void checkAndWait(String locator)
	{
		super.check(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#chooseCancelOnNextConfirmation()
	 */
	public void chooseCancelOnNextConfirmationAndWait()
	{
		super.chooseCancelOnNextConfirmation();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#chooseOkOnNextConfirmation()
	 */
	public void chooseOkOnNextConfirmationAndWait()
	{
		super.chooseOkOnNextConfirmation();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#click(String)
	 */
	public void clickAndWait(String locator)
	{
		super.click(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#clickAt(String, String)
	 */
	public void clickAtAndWait(String locator, String coordString)
	{
		super.clickAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#contextMenu(String)
	 */
	public void contextMenuAndWait(String locator)
	{
		super.contextMenu(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#contextMenuAt(String, String)
	 */
	public void contextMenuAtAndWait(String locator, String coordString)
	{
		super.contextMenuAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#controlKeyDown()
	 */
	public void controlKeyDownAndWait()
	{
		super.controlKeyDown();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#controlKeyDown()
	 */
	public void controlKeyUpAndWait()
	{
		super.controlKeyUp();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#controlKeyDownAndWait()
	 */
	public void createCookieAndWait(String nameValuePair, String optionsString)
	{
		super.createCookie(nameValuePair, optionsString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#deleteAllVisibleCookies()
	 */
	public void deleteAllVisibleCookiesAndWait()
	{
		super.deleteAllVisibleCookies();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#deleteCookie(String, String)
	 */
	public void deleteCookieAndWait(String name, String optionsString)
	{
		super.deleteCookie(name, optionsString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#deselectPopUp()
	 */
	public void deselectPopUpAndWait()
	{
		super.deselectPopUp();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#doubleClick(String)
	 */
	public void doubleClickAndWait(String locator)
	{
		super.doubleClick(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#doubleClickAt(String, String)
	 */
	public void doubleClickAtAndWait(String locator, String coordString)
	{
		super.doubleClickAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#dragAndDrop(String, String)
	 */
	public void dragAndDropAndWait(String locator, String movementsString)
	{
		super.dragAndDrop(locator, movementsString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#dragAndDropToObject(String, String)
	 */
	public void dragAndDropToObjectAndWait(String locatorOfObjectToBeDragged, String locatorOfDragDestinationObject)
	{
		super.dragAndDropToObject(locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#dragdrop(String, String)
	 */
	public void dragdropAndWait(String locator, String movementsString)
	{
		super.dragdrop(locator, movementsString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#fireEvent(String, String)
	 */
	public void fireEventAndWait(String locator, String eventName)
	{
		super.fireEvent(locator, eventName);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#focus(String)
	 */
	public void focusAndWait(String locator)
	{
		super.focus(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#goBack()
	 */
	public void goBackAndWait()
	{
		super.goBack();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#highlight(String)
	 */
	public void highlightAndWait(String locator)
	{
		super.highlight(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#ignoreAttributesWithoutValue(String)
	 */
	public void ignoreAttributesWithoutValueAndWait(String ignore)
	{
		super.ignoreAttributesWithoutValue(ignore);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#keyDown(String, String)
	 */
	public void keyDownAndWait(String locator, String keySequence)
	{
		super.keyDown(locator, keySequence);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#keyPress(String, String)
	 */
	public void keyPressAndWait(String locator, String keySequence)
	{
		super.keyPress(locator, keySequence);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#keyUp(String, String)
	 */
	public void keyUpAndWait(String locator, String keySequence)
	{
		super.keyUp(locator, keySequence);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#metaKeyDown()
	 */
	public void metaKeyDownAndWait()
	{
		super.metaKeyDown();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#metaKeyUp()
	 */
	public void metaKeyUpAndWait()
	{
		super.metaKeyUp();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseDown(String)
	 */
	public void mouseDownAndWait(String locator)
	{
		super.mouseDown(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseDownAt(String, String)
	 */
	public void mouseDownAtAndWait(String locator, String coordString)
	{
		super.mouseDownAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseDownRight(String)
	 */
	public void mouseDownRightAndWait(String locator)
	{
		super.mouseDownRight(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseDownRightAt(String, String)
	 */
	public void mouseDownRightAtAndWait(String locator, String coordString)
	{
		super.mouseDownRightAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseMove(String)
	 */
	public void mouseMoveAndWait(String locator)
	{
		super.mouseMove(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseMoveAt(String, String)
	 */
	public void mouseMoveAtAndWait(String locator, String coordString)
	{
		super.mouseMoveAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseOut(String)
	 */
	public void mouseOutAndWait(String locator)
	{
		super.mouseOut(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseOver(String)
	 */
	public void mouseOverAndWait(String locator)
	{
		super.mouseOver(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseUp(String)
	 */
	public void mouseUpAndWait(String locator)
	{
		super.mouseUp(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseUpAt(String, String)
	 */
	public void mouseUpAtAndWait(String locator, String coordString)
	{
		super.mouseUpAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseUpRight(String)
	 */
	public void mouseUpRightAndWait(String locator)
	{
		super.mouseUpRight(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#mouseUpRightAt(String, String)
	 */
	public void mouseUpRightAtAndWait(String locator, String coordString)
	{
		super.mouseUpRightAt(locator, coordString);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#openWindow(String, String)
	 */
	public void openWindowAndWait(String url, String windowID)
	{
		super.openWindow(url, windowID);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#refresh()
	 */
	public void refreshAndWait()
	{
		super.refresh();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#removeAllSelections(String)
	 */
	public void removeAllSelectionsAndWait(String locator)
	{
		super.removeAllSelections(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#removeScript(String)
	 */
	public void removeScriptAndWait(String scriptTagId)
	{
		super.removeScript(scriptTagId);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#removeSelection(String, String)
	 */
	public void removeSelectionAndWait(String locator, String optionLocator)
	{
		super.removeSelection(locator, optionLocator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#rollup(String, String)
	 */
	public void rollupAndWait(String rollupName, String kwargs)
	{
		super.rollup(rollupName, kwargs);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#runScript(String)
	 */
	public void runScriptAndWait(String script)
	{
		super.runScript(script);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#select(String, String)
	 */
	public void selectAndWait(String selectLocator, String optionLocator)
	{
		super.select(selectLocator, optionLocator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#selectPopUp(String)
	 */
	public void selectPopUpAndWait(String windowID)
	{
		super.selectPopUp(windowID);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#setBrowserLogLevel(String)
	 */
	public void setBrowserLogLevelAndWait(String logLevel)
	{
		super.setBrowserLogLevel(logLevel);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#setCursorPosition(String, String)
	 */
	public void setCursorPositionAndWait(String locator, String position)
	{
		super.setCursorPosition(locator, position);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#setMouseSpeed(String)
	 */
	public void setMouseSpeedAndWait(String pixels)
	{
		super.setMouseSpeed(pixels);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#setSpeed(String)
	 */
	public void setSpeedAndWait(String value)
	{
		super.setSpeed(value);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#shiftKeyDown()
	 */
	public void shiftKeyDownAndWait()
	{
		super.shiftKeyDown();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#shiftKeyUp()
	 */
	public void shiftKeyUpAndWait()
	{
		super.shiftKeyUp();
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#submit(String)
	 */
	public void submitAndWait(String formLocator)
	{
		super.submit(formLocator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#type(String, String)
	 */
	public void typeAndWait(String locator, String value)
	{
		super.type(locator, value);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#typeKeys(String, String)
	 */
	public void typeKeysAndWait(String locator, String value)
	{
		super.typeKeys(locator, value);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#uncheck(String)
	 */
	public void uncheckAndWait(String locator)
	{
		super.uncheck(locator);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * @see DefaultSelenium#useXpathLibrary(String)
	 */
	public void useXpathLibraryAndWait(String libraryName)
	{
		super.useXpathLibrary(libraryName);
		super.waitForPageToLoad(String.valueOf(timeout));
	}

	public void pause()
	{
		pause(1000);
	}

	public void pause(long milis)
	{
		try
		{
			Thread.sleep(milis);
		} catch (InterruptedException e)
		{
			System.err.println("[ERROR] Damn it! Some weird error while pausing... (" + e.getMessage() + ")");
		}
	}
}
