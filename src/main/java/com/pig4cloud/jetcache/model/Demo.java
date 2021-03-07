package com.pig4cloud.jetcache.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author lengleng
 * @date 2021/3/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Demo implements Serializable {

	private String username;

	private String password;
}
