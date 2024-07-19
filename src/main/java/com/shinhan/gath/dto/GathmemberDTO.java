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
public class GathmemberDTO {
	private String id;
	private int gNo;
	private Date joinDate;
	private String name;
	private String img;
}
