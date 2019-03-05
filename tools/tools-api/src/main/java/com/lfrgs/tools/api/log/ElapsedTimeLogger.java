package com.lfrgs.tools.api.log;

import com.liferay.portal.kernel.log.Log;

import java.util.function.Supplier;

/**
 * @author Andrew Betts
 */
public class ElapsedTimeLogger {

	public ElapsedTimeLogger(Log log) {
		this(log, Level.INFO);
	}

	public ElapsedTimeLogger(Log log, Level level) {
		_level = level;
		_log = log;
		_start = System.currentTimeMillis();
	}

	public void reset() {
		_start = System.currentTimeMillis();
	}

	public void start(Supplier<String> messageSupplier) {
		Supplier<String> compositeSupplier =
			() -> "start " + messageSupplier.get();

		logAtLevel(compositeSupplier);

		reset();
	}

	public void log(Supplier<String> messageSupplier) {
		long end = System.currentTimeMillis();
		long time = end - _start;

		Supplier<String> compositeSupplier =
			() -> messageSupplier.get() + " took " + time + " ms";

		logAtLevel(compositeSupplier);
	}

	protected void logAtLevel(Supplier<String> messageSupplier) {
		switch (_level) {
			case FATAL:
				if (_log.isFatalEnabled()) {
					_log.fatal(messageSupplier.get());
				}

				break;

			case ERROR:
				if (_log.isErrorEnabled()) {
					_log.error(messageSupplier.get());
				}

				break;

			case WARN:
				if (_log.isWarnEnabled()) {
					_log.warn(messageSupplier.get());
				}

				break;

			case INFO:
				if (_log.isInfoEnabled()) {
					_log.info(messageSupplier.get());
				}

				break;

			case DEBUG:
				if (_log.isDebugEnabled()) {
					_log.debug(messageSupplier.get());
				}

				break;

			case TRACE:
				if (_log.isTraceEnabled()) {
					_log.trace(messageSupplier.get());
				}

				break;
		}
	}

	private final Log _log;
	private long _start;
	private Level _level;

	public enum Level {
		FATAL, ERROR, WARN, INFO, DEBUG, TRACE
	}

}