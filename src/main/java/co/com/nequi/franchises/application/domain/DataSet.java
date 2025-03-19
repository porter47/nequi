package co.com.nequi.franchises.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataSet<T, K, J> {

	private T valueOne;

	private K valueTwo;

	private J valueThree;
}