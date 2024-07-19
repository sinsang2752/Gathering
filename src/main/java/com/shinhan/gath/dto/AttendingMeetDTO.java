package com.shinhan.gath.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AttendingMeetDTO {
	private String gTitle;
	private String expDate;
	private String metTitle;
	private int metPrice;
	private String metPlace;
	private String joinCount;
	private String gImg;
	private String metImg;
}
