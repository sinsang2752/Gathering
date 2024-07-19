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
public class HotGathDTO {
	private int gNo;
	private String gTitle;
	private String gIntro;
	private int cNo;
	private String cName;
	private String gImg;
	private int metCnt;
	private int memCnt;
}
