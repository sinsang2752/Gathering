package com.shinhan.gath.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailGatheringDTO {
	private int gNo;
	private String gTitle;
	private String gIntro;
	private List<MeetingDTO> meetArr;
	private String mgrId;
	private String gImg;
}
