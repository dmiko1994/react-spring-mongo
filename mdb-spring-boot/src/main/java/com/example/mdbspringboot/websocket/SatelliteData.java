package com.example.mdbspringboot.websocket;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SatelliteData {
	private Map<String, List<BigDecimal>> elements;
	private BigDecimal epoch;
	
	public SatelliteData() {
		this.elements = new HashMap<>();
		this.epoch = new BigDecimal(0);
	}
	
	public SatelliteData(Map<String, List<BigDecimal>> elements, BigDecimal epoch) {
		this.elements = elements;
		this.epoch = epoch;
	}
	
	public Map<String, List<BigDecimal>> getElements() {
		return elements;
	}
	
	public void setElements(Map<String, List<BigDecimal>> elements) {
		this.elements = elements;
	}
	
	public BigDecimal getEpoch() {
		return epoch;
	}
	
	public void setEpoch(BigDecimal epoch) {
		this.epoch = epoch;
	}
}
