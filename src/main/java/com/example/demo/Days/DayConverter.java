package com.example.demo.Days;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.convert.converter.Converter;

@Component
public class DayConverter implements Converter<String, Days>{
	@Override
	public Days convert(String id) {
		System.out.println("Converting");
		
		int parseID = Integer.parseInt(id);
		List<Days> selectableDays = Arrays.asList(
				new Days(1L, "Monday"),
				new Days(2L, "Tuesday"),
				new Days(3L, "Wednesday"),
				new Days(4L, "Thursday"),
				new Days(5L, "Friday"),
				new Days(6L, "Saturday"),
				new Days(7L, "Sunday")
				);
		int index = parseID-1;
		return selectableDays.get(index);
	}

}
