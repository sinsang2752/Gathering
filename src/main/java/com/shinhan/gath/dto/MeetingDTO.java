package com.shinhan.gath.dto;

import java.sql.Date;

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
public class MeetingDTO {
	private int metNo;
	private String metTitle;
	private int metPrice;
	private String metPlace;
	private Date metEntDate;
	private String metExpDate;
	private String metContent;
	private int metMaxMem;
	private int gNo;
	private String mgrId;
	private String jCount;
	private String metImg;
	private int attCheck;
}
