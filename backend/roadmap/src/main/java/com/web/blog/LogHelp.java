package com.web.blog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 로그 설정 : https://www.sangkon.com/hands-on-springboot-logging/
// 로그 단계 : https://goddaehee.tistory.com/206
public class LogHelp {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private void log() {
		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
	}

}
