package com.monka.inventorydetails.config;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.monka.inventorydetails.config.context.RequestContext;
import com.monka.inventorydetails.config.context.RequestContext.RequestAcess;

import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import static com.monka.inventorydetails.config.Constants.CALLEDBY;
import static com.monka.inventorydetails.config.Constants.E2EREQUESTID;
import static com.monka.inventorydetails.config.Constants.TRANSACTIONID;
import static com.monka.inventorydetails.config.Constants.REQUESTURI;
import static com.monka.inventorydetails.config.Constants.STARTTIME;

public class CustomWebFilter implements WebFilter{
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		LOGGER.info("Filter called");
		String e2eRequestId = getE2ERequestId(exchange); 
		String transactionId = UUID.randomUUID().toString(); 
		String requestUri = exchange.getRequest().getURI().getPath();
		String calledBy = getHeadervalue(CALLEDBY, exchange); 
		final String StartTime = new Date().toString();
		addResponseData(exchange, transactionId, e2eRequestId);
		return chain.filter(exchange).subscriberContext(ctx -> addRequestData(ctx, exchange, e2eRequestId, transactionId, 
				requestUri, calledBy, StartTime)).doFinally(signal -> {LOGGER.info("Inside dofinally");});
	}

	

	private void addResponseData(ServerWebExchange exchange, String transactionId, String e2eRequestId) {
		exchange.getResponse().getHeaders().add(TRANSACTIONID, transactionId);
		exchange.getResponse().getHeaders().add(E2EREQUESTID, e2eRequestId);
	}



	private Context addRequestData(Context ctx, ServerWebExchange exchange, String e2eRequestId, String transactionId,
			String requestUri, String calledBy, String startTime) {
		ServerHttpRequest httpReq = exchange.getRequest();
		RequestAcess reqAc = new RequestAcess();
		if(ctx.hasKey(RequestContext.REQUEST_CONTEXT_KEY)) {
			reqAc = new RequestAcess(ctx.get(RequestContext.REQUEST_CONTEXT_KEY));
		} else if(httpReq != null) {
			reqAc = new RequestAcess(httpReq.getCookies(), httpReq.getHeaders());
		}
		reqAc.addAttribute(TRANSACTIONID, transactionId);
		reqAc.addAttribute(E2EREQUESTID, e2eRequestId);
		reqAc.addAttribute(CALLEDBY, calledBy);
		reqAc.addAttribute(REQUESTURI, requestUri);
		reqAc.addAttribute(STARTTIME, startTime);
		exchange.getAttributes().put(RequestContext.REQUEST_CONTEXT_KEY, reqAc);
		return ctx.put(RequestContext.REQUEST_CONTEXT_KEY, reqAc);
	}

	private String getE2ERequestId(ServerWebExchange exchange) {
		String e2eRequestId = getHeadervalue(E2EREQUESTID, exchange);
		if(e2eRequestId == null || e2eRequestId.isEmpty()) {
			e2eRequestId = "id-" + UUID.randomUUID().toString();
		}
		return e2eRequestId;
	}
	
	private String getHeadervalue(String headerName, ServerWebExchange exchange) {
		String headerValue = "";
		if(exchange.getRequest() != null && exchange.getRequest().getHeaders() != null) {
			headerValue = exchange.getRequest().getHeaders().getFirst(headerName);
		}
		return headerValue;
	}

}
