package com.lino4000.petFinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
@AllArgsConstructor
public class PathToGoogleMaps {
	
	public double lat; 
	public double lng;
}
