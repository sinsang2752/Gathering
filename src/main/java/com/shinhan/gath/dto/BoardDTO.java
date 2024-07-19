package com.shinhan.gath.dto;

import java.util.List;

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
public class BoardDTO {
	private int bNo;
	private String bTitle;
	private String bContent;
	private String mgrId;
	private int gNo;
	private String isFile;
	private List<BoardfileDTO> files;
}
