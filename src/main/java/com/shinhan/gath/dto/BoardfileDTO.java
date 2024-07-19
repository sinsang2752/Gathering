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
@NoArgsConstructor
@AllArgsConstructor
public class BoardfileDTO {
	private int fNo;
	private int bNo;
	private String fName;
	private Date fUploadDate;
}
