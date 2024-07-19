package com.shinhan.gath.dto;

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
public class AccDTO {
	private String id;
	private String pw;
	private String name;
	private int auth;
	private String img;
}
