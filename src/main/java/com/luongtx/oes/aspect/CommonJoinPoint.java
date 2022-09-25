package com.luongtx.oes.aspect;

public class CommonJoinPoint {
	public static final String WEB_LAYER_EXECUTION = "execution(* com.luongtx.oes.web..*(..))";
	public static final String SERVICE_LAYER_EXECUTION = "execution(* com.luongtx.oes.service..*(..))";
	public static final String DATA_LAYER_EXECUTION = "execution(* com.luongtx.oes.repository..*(..))";

	public static final String WEB_LAYER_WITHIN = "within(com.luongtx.oes.web.*)";
	public static final String SERVICE_LAYER_WITHIN = "within(com.luongtx.oes.service.*)";
	public static final String DATA_LAYER_WITHIN = "within(com.luongtx.oes.repository.*)";
}
