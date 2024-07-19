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
public class GatheringCnoDTO {
	private int gNo;
	private String gTitle;
	private String gIntro;
	private String mgrId;
	private int cNo;
	private String gImg;
	private int memCnt;
}
